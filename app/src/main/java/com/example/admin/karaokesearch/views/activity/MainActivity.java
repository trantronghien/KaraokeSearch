package com.example.admin.karaokesearch.views.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.util.UtilHelper;
import com.example.admin.karaokesearch.views.NotifyDataFavoriteChange;
import com.example.admin.karaokesearch.views.fragment.FavoriteFragment;
import com.example.admin.karaokesearch.views.fragment.SearchFragment;
import com.example.admin.karaokesearch.views.fragment.SongFragment;
import com.example.admin.karaokesearch.views.fragment.UpdateFragment;
import com.example.admin.karaokesearch.views.ui.FloatingActionButtonMove;
import com.example.admin.karaokesearch.views.ui.ViewPagerAdapter;
import com.example.admin.karaokesearch.views.ui.ViewPagerModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener,NotifyDataFavoriteChange {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LinearLayout mainContent;
    private FloatingActionButton btnFloat;
    private View view;
    private  ViewPagerAdapter adapter;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        view = this.findViewById(R.id.main_content);
        inIt();
        listener();
    }

    private final int SELECTED_TAB = 1;
    private int[] tabIcons = {
            R.drawable.ic_tab_search,
            R.drawable.ic_tab_listsong,
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_update
    };

    private int[] tabIconsSelected = {
            R.drawable.ic_tab_search,
            R.drawable.ic_tab_listsong,
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_update
    };

    final private String[] tabTitle = {
            UtilHelper.getStringRes(R.string.title_tab_one),
            UtilHelper.getStringRes(R.string.title_tab_two),
            UtilHelper.getStringRes(R.string.title_tab_three),
            UtilHelper.getStringRes(R.string.title_tab_four),
    };

    private List<ViewPagerModel> listPage;

    @SuppressLint("NewApi")
    private void inIt() {
        this.context = getApplicationContext();

        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        tabLayout = (TabLayout) findViewById(R.id.tabs_main);
        mainContent = (LinearLayout) findViewById(R.id.main_content);
        listPage = new ArrayList<>();
        listPage.add(new ViewPagerModel(new SearchFragment(), tabIcons[0], tabIconsSelected[0], tabTitle[0]));
        listPage.add(new ViewPagerModel(new SongFragment(), tabIcons[1], tabIconsSelected[1], tabTitle[1]));
        listPage.add(new ViewPagerModel(new FavoriteFragment(), tabIcons[2], tabIconsSelected[2], tabTitle[2]));
        listPage.add(new ViewPagerModel(new UpdateFragment(), tabIcons[3], tabIconsSelected[3], tabTitle[3]));

        //---- setup Viewpage ------
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        setupViewPager(viewPager, adapter);
//        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_selected_color));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(SELECTED_TAB);
        setupTabLayout(adapter);

        // float btn
        btnFloat = (FloatingActionButton) findViewById(R.id.btnFloat);
        // làm mờ
        btnFloat.setAlpha(0.45f);
    }

    private void listener(){
        btnFloat.setOnClickListener(this);
        btnFloat.setOnTouchListener(new FloatingActionButtonMove(btnFloat , mainContent));
    }

    private void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter) {
        viewPager.setOffscreenPageLimit(4);   // limit page restart data when change page
        adapter.addListPage(listPage);
        viewPager.setAdapter(adapter);
    }

    //            tabLayout.getTabAt(i).setIcon(listPage.get(i).idIconTab);
    private void setupTabLayout(ViewPagerAdapter adapter) {
        for (int position = 0; position < listPage.size(); position++) {
            //custom item tab
            if(position != SELECTED_TAB)
                adapter.setupTabView(position ,false);
            else
                adapter.setupTabView(position ,true);

            tabLayout.getTabAt(position).setCustomView(adapter.listTabView.get(position));
        }
        // listener when tab select change
        adapter.setOnTabSelectChangeListener(tabLayout);
    }

    //============================================================================
    //                             SET EVENT NAVIGATION BACK CLICK
    //============================================================================
    int countClickBackButton = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {  // back button
            ++countClickBackButton;
            if (countClickBackButton == 1) {
                Snackbar.make(view, R.string.snackbar_text, Snackbar.LENGTH_LONG).show();
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        countClickBackButton = 0;    // restart value
                    }
                }.start();
                return false;
            } else if (countClickBackButton == 2) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //============================================================================
    //                              Onclick implement
    //============================================================================
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFloat:
                startActivity(new Intent(MainActivity.this, SearchOnlineActivity.class));
                return;
        }
    }

    //todo notify for list all fragment
    @Override
    public void notifyDataChange(int favorite) {
        for (int i = 0; i < adapter.getCount();i++) {
            if (i != adapter.getSelectedTabPosition()){
                adapter.getRegisteredFragment(i).notifyDataCheckChange();
            }
        }
    }
}
