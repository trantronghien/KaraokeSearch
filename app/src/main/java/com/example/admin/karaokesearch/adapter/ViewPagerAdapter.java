package com.example.admin.karaokesearch.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 3/6/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>() ;
    private List<String> mFragmentTitleList = new ArrayList<>();
    private String mGeneralString;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    //received from ManagerFragment
//    public void update(String string) {
//        mGeneralString = string;
//        //updated
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemPosition(Object object) {
//        if (object instanceof ) {
//            //sent to FirstFragment and SecondFragment
//            ((UpdateableFragmentListener) object).update(mGeneralString);
//        }
        return super.getItemPosition(object);
    }
}


