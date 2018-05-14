package com.example.dou.myretrofitdemo.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.dou.myretrofitdemo.R;

import java.util.List;

/**
 * Created by doudou on 2017/11/1.
 */

public class ContactAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ContactAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_contact_item, item);
    }
}
