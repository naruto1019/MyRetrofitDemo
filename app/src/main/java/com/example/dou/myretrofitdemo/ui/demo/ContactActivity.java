package com.example.dou.myretrofitdemo.ui.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.dou.myretrofitdemo.R;
import com.example.dou.myretrofitdemo.adapter.ContactAdapter;
import com.example.dou.myretrofitdemo.itemDecoration.MyItemDecoration;
import com.example.dou.myretrofitdemo.itemDecoration.SectionDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;

    List<String> list;
    private SwipeRefreshLayout mSrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();

        mToolbar.setTitle("demo");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        itemDecoration = new MyItemDecoration(this);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(list.get(position).charAt(0));
            }

            @Override
            public String getGroupFirstLine(int position) {
                return list.get(position).substring(0, 1).toUpperCase();
            }
        }));
        initData();
        adapter = new ContactAdapter(R.layout.layout, list);
        mRecyclerView.setAdapter(adapter);

    }

    private void initData() {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.clear();
        for (int i = 0; i < 100; i++) {
            list.add("" + (char) (Math.random() * 26 + 65) + i);
            sort();
        }
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
        }
        if (mSrl.isRefreshing()){
            mSrl.setRefreshing(false);
        }
    }

    // 排序
    private void sort() {
        Collections.sort(list);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                }, 2000);
            }
        });
    }
}
