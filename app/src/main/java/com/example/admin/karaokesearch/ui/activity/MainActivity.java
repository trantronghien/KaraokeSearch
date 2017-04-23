package com.example.admin.karaokesearch.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.admin.karaokesearch.models.Entities.DaoMaster;
import com.example.admin.karaokesearch.models.api.App;
import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.models.Entities.DaoSession;
import com.example.admin.karaokesearch.models.Entities.SongArirang;
import com.example.admin.karaokesearch.models.api.DatabaseOpenHelper;
import com.example.admin.karaokesearch.ui.fragment.FavoriteFragment;
import com.example.admin.karaokesearch.ui.fragment.SearchFragment;
import com.example.admin.karaokesearch.ui.fragment.SongFragment;
import com.example.admin.karaokesearch.ui.fragment.UpdateFragment;
import com.example.admin.karaokesearch.adapter.ViewPagerAdapter;
import com.example.admin.karaokesearch.util.KeyBoard;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public Context context;
    private View view ;
    private final int CURRENT_TAB = 1;
    private int[] tabIcons = {
            R.drawable.ic_tab_search,
            R.drawable.ic_tab_listsong,
            R.drawable.ic_tab_favourite,
            R.drawable.ic_tab_update
    };

    final private String[] tabTitle = {
            "Tra mã", "Danh mục", "Yêu thích" , "Cập nhật"
    };

    private void inIt(){
        this.context = getApplicationContext();
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        tabLayout = (TabLayout) findViewById(R.id.tabs_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        view =  this.findViewById(R.id.main_content);
        inIt();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(CURRENT_TAB);
        setupTabIcons();
//        onViewPageChangeListener(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(4);   // limit page restart data when change page

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SearchFragment(), tabTitle[0]);
        adapter.addFrag(new SongFragment(), tabTitle[1]);
        adapter.addFrag(new FavoriteFragment(), tabTitle[2]);
        adapter.addFrag(new UpdateFragment(), tabTitle[3]);
        viewPager.setAdapter(adapter);
    }

    private void onViewPageChangeListener(ViewPager viewPager){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    //============================================================================
    //                             SET EVENT NAVIGATION KEY
    //============================================================================
    int countClickBackButton = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){  // back button
            ++countClickBackButton;
            if (countClickBackButton == 1){
                Snackbar.make( view ,R.string.snackbar_text , Snackbar.LENGTH_LONG).show();
                new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() { countClickBackButton = 0;    // restart value
                    }
                }.start();
                return false;
            }
            else if (countClickBackButton == 2){
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
