package com.example.admin.karaokesearch.views.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.karaokesearch.R;
import com.example.admin.karaokesearch.views.fragment.BaseUtilFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/6/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener
        , TabLayout.OnTabSelectedListener {

    public boolean isDegBugDisLay = true;
    private List<ViewPagerModel> listPage = new ArrayList<>();
    private AppCompatActivity context;
    private TabLayout tabLayout;
    private boolean tabSelected = true;
    SparseArray<BaseUtilFragment> registeredFragments = new SparseArray<BaseUtilFragment>();
    private int selectedTabPosition = 0;

    public ViewPagerAdapter(FragmentManager manager, AppCompatActivity context) {
        super(manager);
        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        return listPage.get(position).fragmentPage;
    }

    @Override
    public int getCount() {
        return listPage.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listPage.get(position).titleTab;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseUtilFragment fragment = (BaseUtilFragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public BaseUtilFragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    public void addPage(ViewPagerModel pagerModel) {
        listPage.add(pagerModel);
    }

    public void addListPage(List<ViewPagerModel> listPagerModel) {
        listPage.clear();
        listPage.addAll(listPagerModel);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    //============================================================================
    //                              Tab View Custom
    //============================================================================
    public List<View> listTabView = new ArrayList<>();

    public void setupTabView(int position, boolean selected) {
        ViewPagerModel pagerModel = listPage.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null);
        if (!selected) {
            TextView txtTitle = (TextView) view.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            txtTitle.setTextColor(context.getResources().getColor(R.color.tab_unselected_text_color));
            icon.setImageResource(pagerModel.idIconTab);
            icon.setColorFilter(context.getResources().getColor(R.color.tab_unselected_color)
                    , android.graphics.PorterDuff.Mode.MULTIPLY); // set for vector drawable // android.graphics.PorterDuff.Mode.SRC_IN
            txtTitle.setText(pagerModel.titleTab);
        } else {
            TextView txtTitle = (TextView) view.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            txtTitle.setTextColor(context.getResources().getColor(R.color.tab_selected_text_color));
            icon.setImageResource(pagerModel.idIconTabSelected);
            icon.setColorFilter(context.getResources().getColor(R.color.tab_selected_color)
                    , android.graphics.PorterDuff.Mode.MULTIPLY);
            txtTitle.setText(pagerModel.titleTab);
        }
        listTabView.add(position, view);
    }

    /**
     * change title and icon tab
     *
     * @param positionChange position item tab view
     * @param selected       status tab
     */
    public void swapView(int positionChange, boolean selected) {
        View view = listTabView.get(positionChange);
        ViewPagerModel pagerModel = listPage.get(positionChange);
        if (!selected) {
            TextView txtTitle = (TextView) view.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            txtTitle.setTextColor(context.getResources().getColor(R.color.tab_unselected_text_color));
            icon.setImageResource(pagerModel.idIconTab);
            icon.setColorFilter(context.getResources().getColor(R.color.tab_unselected_text_color)
                    , android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            TextView txtTitle = (TextView) view.findViewById(R.id.tab_title);
            ImageView icon = (ImageView) view.findViewById(R.id.tab_icon);
            txtTitle.setTextColor(context.getResources().getColor(R.color.tab_selected_text_color));
            icon.setImageResource(pagerModel.idIconTabSelected);

            icon.setColorFilter(context.getResources().getColor(R.color.tab_selected_color)
                    , android.graphics.PorterDuff.Mode.MULTIPLY);
        }
        listTabView.set(positionChange, view);
    }

    //============================================================================
    //                              Page Change listener
    //============================================================================
    private OnViewPagerChangeListener changeListener;

    /**
     * not implements OnViewPagerChangeListener , can using only change icon tab
     */

    public void setOnTabSelectChangeListener(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
        tabLayout.addOnTabSelectedListener(this);
    }

    public void setOnViewPagerChangeListener(ViewPager viewPager, OnViewPagerChangeListener changeListener) {
        this.changeListener = changeListener;
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (changeListener != null)
            changeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        selectedTabPosition = position;
        if (changeListener != null)
            changeListener.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (changeListener != null)
            changeListener.onPageScrollStateChanged(state);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        selectedTabPosition = tab.getPosition();
        swapView(tab.getPosition(), tabSelected);
        tab.setCustomView(listTabView.get(tab.getPosition()));
        if (isDegBugDisLay) {
            Log.i("fragment", "Dislay: "
                    + listPage.get(tab.getPosition()).fragmentPage.getFragmentName());
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        swapView(tab.getPosition(), !tabSelected);
        tab.setCustomView(listTabView.get(tab.getPosition()));
        if (isDegBugDisLay) {
            Log.i("fragment", "leave: "
                    + listPage.get(tab.getPosition()).fragmentPage.getFragmentName());
        }
    }

    public int getSelectedTabPosition() {
        return selectedTabPosition;
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    public interface OnViewPagerChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }

}


