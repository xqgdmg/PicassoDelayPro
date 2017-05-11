package com.example.qhsj.picassodelaypro;

import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    String[] data = new String[]{
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://pic1.win4000.com/wallpaper/1/58d47bfeebebf.jpg",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac693c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd",
            "http://boss.pigamegroup.com/file/img?type=user&size=2&id=7ba7ac67ef524437b993c11e6c8c4ffd"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.HORIZONTAL));
    }


    private class MyAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v = getLayoutInflater().inflate(R.layout.item,null);
            View v = getLayoutInflater().inflate(R.layout.item,parent,false);
            MyViewHolder holder = new MyViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final String itemData = data[position];
            final MyViewHolder myHolder = (MyViewHolder) holder;

//            Picasso.with(MainActivity.this).load(itemData).into(myHolder.iv);
            ImageHelp.displayImage(itemData,myHolder.iv,R.mipmap.ic_launcher);
        }

        @Override
        public int getItemCount() {
            return data.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView iv;

            public MyViewHolder(View view) {
                super(view);
                iv = (ImageView) view.findViewById(R.id.iv);
            }
        }

    }

    public static String getFilePath(String url) {
        return  Environment.getExternalStorageDirectory().getPath() + "/Chris/" + "image_cache/" + MD5.MD5(url) + ".png.cache";
    }


}
