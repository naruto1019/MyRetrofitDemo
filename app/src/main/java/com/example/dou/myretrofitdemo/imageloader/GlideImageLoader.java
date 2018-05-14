package com.example.dou.myretrofitdemo.imageloader;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;
import com.example.dou.myretrofitdemo.R;

/**
 * @author Smile
 * @time 2017/4/19  下午3:38
 * @desc ${TODD}
 */
public class GlideImageLoader implements RxPickerImageLoader {

    @Override
    public void display(ImageView imageView, String path, int width, int height) {
        Glide.with(imageView.getContext())
                .load(path).
                error(R.drawable.ic_preview_image)
                .centerCrop()
                .override(width, height)
                .into(imageView);
    }
}
