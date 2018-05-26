package com.example.admin.karaokesearch.views.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.SongTable;
import com.example.admin.karaokesearch.presenter.BaseSongPresenter;
import com.example.admin.karaokesearch.presenter.RoutePresenter;
import com.example.admin.karaokesearch.util.ConfigSongTable;
import com.example.admin.karaokesearch.util.KeyBoard;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.SearchCallbackView;
import com.example.admin.karaokesearch.views.adapter.SongRecyclerAdapter;
import com.example.admin.karaokesearch.views.ui.EditTextActionSearch;
import com.example.admin.karaokesearch.views.ui.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 3/7/2017.
 */

public class SearchFragment extends BaseFragment implements SearchCallbackView , View.OnTouchListener{


    private ImageButton btnSearchVoice;
    private EditTextActionSearch editSearch;
    private RelativeLayout contentLayout;
    private RecyclerView recyclerView;
    private ImageButton btnClean;
    private RadioGroup groupButton;
    private TextView txthelp;
    private View view;
    private ProgressBar progressBar;

    private SongRecyclerAdapter adapter;
    private BaseSongPresenter presenter;
    private String voiceResult;
    private List<SongTable> listSong;
    private int idTableSearch;

    private String TAG = "SearchFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view != null)
            return view;
        return view = super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void inItView(View view) {
        progressBar = (ProgressBar) this.view.findViewById(R.id.proccessbar_search);
        progressBar.setVisibility(View.GONE);
        txthelp = (TextView) this.view.findViewById(R.id.txt_help_search_fragment);
        btnSearchVoice = (ImageButton) this.view.findViewById(R.id.button_search_voice);
        editSearch = (EditTextActionSearch) this.view.findViewById(R.id.edit_search);
        contentLayout = (RelativeLayout) this.view.findViewById(R.id.layout_content_search_fragment);
        recyclerView = (RecyclerView) this.view.findViewById(R.id.recycler_search);
        btnClean = (ImageButton) this.view.findViewById(R.id.button_search_clean);
        groupButton = (RadioGroup) this.view.findViewById(R.id.group_filter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(getLayoutManager());

        idTableSearch = GroupButtonChecker();

    }

    @Override
    protected void inItData() {
        // khơi tạo;
        presenter = RoutePresenter.RegisterRoutesPresenter(idTableSearch, this);    // new SongArirangPresenter
        listSong = new ArrayList<>();
        adapter = new SongRecyclerAdapter(listSong , this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected SongRecyclerAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected BaseSongPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        inItView(view);
        listener();
    }

    @Override
    protected void listener() {
        adapter.setCheckBoxChangeListner(this);
        btnClean.setOnClickListener(this);
        btnSearchVoice.setOnClickListener(this);

        // hide keyboard when not focus EditSearch
        contentLayout.setOnTouchListener(this);
        recyclerView.setOnTouchListener(this);
        groupButton.setOnTouchListener(this);

        editSearch.setOnSearchChangeListener(new EditTextActionSearch.onSearchChangeListener() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                // btn clean click
                setVisibilityCleanButton(s);
                // nhập 3 ký tự 2 giấy mới call presenter
                if (before < count && s.length() >= 3) {
                    callPresenter(s);
                }
                // clean
                if (before > count){
                    callPresenter(s);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.queryData(text.getText().toString());
                    return true;
                }
                return false;
            }
        });
        //============================================================================
        //                                // todo call presenter and referst list
        //============================================================================


        groupButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int idTable = GroupButtonChecker();
                String request = editSearch.getText().toString();
                Log.i(TAG, "onCheckedChanged: " + idTable + "\tRequest: "
                        + request +"\t getTable: "
                        + getIdTable());
                notifyChangePresenter(idTable);
                presenter.queryData(editSearch.getText().toString());
            }
        });

        // ==================== Group listener===================

        recyclerView.addOnScrollListener( new EndlessRecyclerOnScrollListener(getLayoutManager()) {
            @Override
            public void onScrolledToEnd(int itemCount) {

            }
        });
    }

    private void setVisibilityCleanButton(CharSequence s) {
        if (!s.toString().isEmpty()) {
            btnClean.setVisibility(View.VISIBLE);
        } else {
            btnClean.setVisibility(View.INVISIBLE);
        }
    }
    private void notifyChangePresenter(int idTable){
        if(idTable != getIdTable()) {
            presenter = RoutePresenter.RegisterRoutesPresenter(idTable, this);
            // here BaseFragment
            notifyChangePresenter(presenter);
            idTableSearch = idTable;
        }
    }

    private Timer timer=new Timer();
    final long DELAY = 1000; // 1 giây
    private void callPresenter(final CharSequence s){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {@Override
                    public void run() {
                        getActivitys().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                presenter.queryData(s);
                            }
                        });
                    }
                }, DELAY);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search_voice:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
//                    KeyBoard.hide(getActivity());
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(activity, R.string.noffication_Voice_devices, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_search_clean:
                editSearch.setText("");
                adapter.clearData();
                txthelp.setVisibility(View.VISIBLE);
                btnClean.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private int GroupButtonChecker() {
        switch (groupButton.getCheckedRadioButtonId()) {
            case R.id.radio_table_arirang:
                return ConfigSongTable.ID_SONG_ARIRANG;
            case R.id.radio_table_california:
                return ConfigSongTable.ID_SONG_CALIFORNIA;
            case R.id.radio_table_misic:
                return ConfigSongTable.ID_SONG_MUSICCORE;
            case R.id.radio_table_vietktv:
                return ConfigSongTable.ID_SONG_VIETKTV;
        }
        return 0;
    }
    //============================================================================
    //                              todo Presenter implement
    //============================================================================


    @Override
    public void onSearching() {
        txthelp.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onSearchFinished(List list) {
        adapter.clearData();
        if (list!=null && list.size() != 0){
            Log.i("DEG", "onSearchFinished: " + list.size());
            adapter.addAll(list);
        }else if (list == null || list.isEmpty()){
            txthelp.setVisibility(View.VISIBLE);
            txthelp.setText(UtilHelper.getStringRes(R.string.search_result_no_data));
            txthelp.setTextColor(R.color.color_txt_search_result_no_data);
        }
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSearchFailed(String error) {
        Toast.makeText(getActivitys(), error, Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected int getIdTable() {
        return idTableSearch;
    }

    @Override
    public int getIdFragment() {
        return UtilHelper.FRAGMENT_SEARCH;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    voiceResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                    editSearch.setText(voiceResult);
                }
                break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (editSearch.isFocused()) {
                Rect outRect = new Rect();
                editSearch.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    editSearch.clearFocus();
                    KeyBoard.hide(v);
                }
            }
        }
        return false;
    }
}
