package com.example.admin.karaokesearch.views.ui;

import com.example.admin.karaokesearch.views.fragment.BaseUtilFragment;

/**
 * Created by admin on 25/10/2017.
 */

public class ViewPagerModel {
    public BaseUtilFragment fragmentPage;
    public int idIconTab;
    public String titleTab;
    public int idIconTabSelected;

    public ViewPagerModel(BaseUtilFragment fragmentPage, int idIconTab , int idIconTabSelected, String titleTab) {
        this.fragmentPage = fragmentPage;
        this.idIconTab = idIconTab;
        this.titleTab = titleTab;
        this.idIconTabSelected = idIconTabSelected;
    }


}
