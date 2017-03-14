package com.cn.tfl.boucemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
    }

    private void initData() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + " item");
        }
        mAdapter = new MyAdapter(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    BouncingMenu bouncingMenu;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (bouncingMenu!=null) {
            bouncingMenu.dismiss();
            bouncingMenu=null;
        } else {
            bouncingMenu= BouncingMenu.makeMenu(findViewById(R.id.ll), R.layout.content_main, mAdapter).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
