package com.example.hientran.karaoke_search;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class customtable2 extends Activity {
	String baihat;
	String loi;
	String nhacsi;
	String id1;
	int fav1;
	private TextView id;
	private TextView baihat1;
	private TextView loi1;
	private TextView nhacsi1;
	int newvalue;
	DatabaseHelper helper;
	SQLiteDatabase db;
	CheckBox chk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_layout_tablebox);
		// this.getActionBar().setDisplayHomeAsUpEnabled(true);
		// bat su kien
		chk = (CheckBox) findViewById(R.id.checkBox1);
		id1 = getIntent().getStringExtra(Timkiem.ID_EXTRA);
		fav1 = getIntent().getIntExtra(Timkiem.FAVORITE_EXTRA, 0);
		if (fav1 == 1) {
			chk.setChecked(true);
		} else {
			chk.setChecked(false);
		}

		baihat = getIntent().getStringExtra(Timkiem.TITLE_EXTRA);
		loi = getIntent().getStringExtra(Timkiem.LYRIC_EXTRA);
		nhacsi = getIntent().getStringExtra(Timkiem.SOURCE_EXTRA);

		// goi textview
		baihat1 = (TextView) findViewById(R.id.txvbaihat);
		loi1 = (TextView) findViewById(R.id.txvloi);
		nhacsi1 = (TextView) findViewById(R.id.txvcasi);
		id = (TextView) findViewById(R.id.textid);

		// settext cho ham
		baihat1.setText(baihat);
		id.setText(id1);
		loi1.setText(loi);
		nhacsi1.setText(nhacsi);

		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if (isChecked) {
					newvalue = 1;
					helper = new DatabaseHelper(getBaseContext());
					db = helper.openDatabase();
					helper.update(id1, newvalue);
				} else {
					newvalue = 0;
					helper = new DatabaseHelper(getBaseContext());
					db = helper.openDatabase();
					helper.update(id1, newvalue);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);

	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {

		super.onResume();
		if (fav1 == 1) {
			chk.setChecked(true);
		} else {
			chk.setChecked(false);
		}
	}

	@Override
	protected void onRestart() {

		super.onRestart();
	}
}
