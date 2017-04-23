package com.example.admin.karaokesearch.ui.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.presenter.ISongPresenter;
import com.example.admin.karaokesearch.util.KeyBoard;

/**
 * Created by admin on 3/7/2017.
 */

public class SearchFragment extends BaseFragment implements View.OnClickListener{

    private ISongPresenter presenter;
    private ImageButton btnSearchVoice;
    private EditText editSearch;
    private RelativeLayout layoutSearch;
    private RecyclerView recyclerView;
    private String voiceResult;
    private ImageButton btnClean;
    private IBinder binder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void inItView(View view){
        btnSearchVoice = (ImageButton) view.findViewById(R.id.button_search_voice);
        editSearch = (EditText) view.findViewById(R.id.edit_search);
        layoutSearch = (RelativeLayout) view.findViewById(R.id.search_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_search);
        btnClean = (ImageButton) view.findViewById(R.id.button_search_clean);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater , container , savedInstanceState);
        inItView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binder = getView().getWindowToken();
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        btnClean.setOnClickListener(this);
        btnSearchVoice.setOnClickListener(this);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) btnClean.setVisibility(View.VISIBLE);
                else btnClean.setVisibility(View.INVISIBLE);
//               presenter.queryData(s);
//               SongRecyclerAdapter adapter = new SongRecyclerAdapter(getActivity(), );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // hide keyboard when not focus EditSearch
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyBoard.hide(getActivity());
                return false;
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_search_voice:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try {
                    KeyBoard.hide(getActivity());
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(activity , R.string.noffication_Voice_devices,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_search_clean:
                editSearch.setText("");
                btnClean.setVisibility(View.INVISIBLE);
                break;
        }
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

    // checkbox into item
    @Override
    public void onCheckedChanged(boolean isChecked, long idSong, String songNameCurrent, int favoriteValue) {

    }

}
