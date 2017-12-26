package com.sk.uudc.module.near.activity;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.near.Constant;
import com.sk.uudc.module.near.adapter.ShangJiaImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/6.
 */

public class ImageDetailActivity extends BaseActivity {
    @BindView(R.id.vp_image_detail)
    ViewPager vp_image_detail;
    ShangJiaImageAdapter adapter;

    List<View> list=new ArrayList<>();
    private ArrayList<String> photoList;

    @Override
    protected int getContentView() {
        setAppTitle("商家图片");
        return R.layout.act_image_detail;
    }

    @Override
    protected void initView() {
        photoList = getIntent().getStringArrayListExtra(com.sk.uudc.module.near.Constant.IParam.imgList);
        int index = getIntent().getIntExtra(Constant.IParam.imgIndex, 0);
        for (int i = 0; i < photoList.size(); i++) {
            ImageView imageView =new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setAdjustViewBounds(true);
            Glide.with(mContext).load(photoList.get(i)).error(R.color.c_press).into(imageView);
            list.add(imageView);

        }
        ShangJiaImageAdapter adapter=new ShangJiaImageAdapter();
        adapter.setList(list);
        vp_image_detail.setAdapter(adapter);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                vp_image_detail.setCurrentItem(index);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
