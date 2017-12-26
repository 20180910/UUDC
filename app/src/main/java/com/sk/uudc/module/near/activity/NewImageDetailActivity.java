package com.sk.uudc.module.near.activity;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.module.my.Constant;
import com.sk.uudc.module.near.adapter.ShangJiaImageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/6.
 */

public class NewImageDetailActivity extends BaseActivity {
    @BindView(R.id.vp_image_detail)
    ViewPager vp_image_detail;
    ShangJiaImageAdapter adapter;

    List<View> list=new ArrayList<>();
    private ArrayList<String> photoList;
    private String content;
    @Override
    protected int getContentView() {
        return R.layout.act_new_image_detail;
    }

    @Override
    protected void initView() {
        showProgress();
        photoList = getIntent().getStringArrayListExtra(Constant.IParam.imgList);
        content = getIntent().getStringExtra(Constant.IParam.imgEvaluate);
        if(TextUtils.isEmpty(content)){
            //Constant.IParam.imgEvaluate
        }else{
        }
        int index = getIntent().getIntExtra(Constant.IParam.imgIndex, 0);
        for (int i = 0; i < photoList.size(); i++) {
//            ImageView imageView =new ImageView(mContext);
            PhotoView imageView =new PhotoView(mContext);
            imageView.setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    finish();
                }
            });
            imageView.enable();
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
                pl_load.showContent();
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
