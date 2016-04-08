package com.example.hientran.karaoke_search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Tab_Help extends Activity {
    ListView list;
    Integer []image = {
            R.drawable.gioithieu,
            R.drawable.loctheodauso,
            R.drawable.locchucai,
            R.drawable.update,
            R.drawable.viettat,
    };

    String[] chuoi = {
           "Bạn có thể tìm kiếm bằng chữ thường hoặc chữ hoa không dấu" ,
            "Bạn có thể lọc theo đầu các đầu số hiện hành",
            "Bạn có thể lọc theo Chữ Cái ứng với âm đầu tiên của bài hát bạn thích",
            "Bạn có thể cập nhập mã số tại đây",
            "Bạn có thể tìm kiếm bằng cách viết cái chữ đầu của bài hát"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab__help);
        CustomListView_help adapter = new CustomListView_help(this , image , chuoi);
        list = (ListView)findViewById(R.id.listViewhelp);
        list.setAdapter(adapter);
    }
}
