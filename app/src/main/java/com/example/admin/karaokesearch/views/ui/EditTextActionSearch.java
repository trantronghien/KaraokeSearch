package com.example.admin.karaokesearch.views.ui;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by admin on 9/11/2017.
 */


public class EditTextActionSearch extends android.support.v7.widget.AppCompatEditText{

    public EditTextActionSearch(Context context) {
        super(context);
    }
    public EditTextActionSearch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EditTextActionSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setOnSearchChangeListener(onSearchChangeListener searchEditText) {
        this.setOnEditorActionListener(searchEditText);
        this.addTextChangedListener(searchEditText);
    }

    public interface onSearchChangeListener extends TextWatcher , TextView.OnEditorActionListener{}
}
