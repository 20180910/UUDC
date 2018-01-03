package com.sk.uudc.module.home.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.androidtools.PhoneUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.customview.MyTextView;
import com.sk.uudc.Constant;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SpalshActivity extends BaseActivity {
    @BindView(R.id.vp_spalsh)
    ViewPager vp_spalsh;
    @BindView(R.id.ll_spalsh)
    LinearLayout ll_spalsh;
    private int beforIndex=0;
    List<View>list=new ArrayList<>();
    private Integer[]img=new Integer[]{R.drawable.spalsh_1,R.drawable.spalsh_2,R.drawable.spalsh_3};

    @Override
    protected int getContentView() {
        return R.layout.act_spalsh;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean flag = SPUtils.getPrefBoolean(this, Constant.isFirstIntoApp, true);
        Log.i(TAG+"===","==="+flag);
        if(!flag){
            STActivity(WelcomeActivity.class);
            finish();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        ll_spalsh.removeAllViews();
        View view;
        for (int i = 0; i < img.length; i++) {
            view=getLayoutInflater().inflate(R.layout.item_spalsh,null);
            ImageView imageView = view.findViewById(R.id.iv_spalsh);
            imageView.setImageResource(img[i]);
            View fl_spalsh = view.findViewById(R.id.fl_spalsh);
            if(i==img.length-1){
                fl_spalsh.setVisibility(View.VISIBLE);
                fl_spalsh.setOnClickListener(new MyOnClickListener() {
                    @Override
                    protected void onNoDoubleClick(View view) {
                        SPUtils.setPrefBoolean(mContext, Constant.isFirstIntoApp,false);
                        STActivity(WelcomeActivity.class);
                        finish();
                    }
                });
            }else{
                fl_spalsh.setVisibility(View.INVISIBLE);
            }
            list.add(view);

            MyTextView myTextView=new MyTextView(mContext);
            myTextView.setWidth(PhoneUtils.dip2px(mContext,8));
            myTextView.setHeight(PhoneUtils.dip2px(mContext,8));
            myTextView.setRadius(PhoneUtils.dip2px(mContext,4));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,PhoneUtils.dip2px(mContext,10),0);
            if(i==0){
                myTextView.setSolidColor(ContextCompat.getColor(mContext,R.color.home_green));
            }else{
                myTextView.setSolidColor(ContextCompat.getColor(mContext,R.color.gray_99));
            }
            myTextView.setLayoutParams(layoutParams);
            myTextView.complete();
            ll_spalsh.addView(myTextView);
        }
        SpalshAdapter adapter=new SpalshAdapter();
        adapter.setList(list);
        vp_spalsh.setAdapter(adapter);
        vp_spalsh.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                MyTextView childAt = (MyTextView) ll_spalsh.getChildAt(position);
                childAt.setSolidColor(ContextCompat.getColor(mContext,R.color.home_green));
                childAt.complete();

                MyTextView beforView = (MyTextView) ll_spalsh.getChildAt(beforIndex);
                beforView.setSolidColor(ContextCompat.getColor(mContext,R.color.gray_99));
                beforView.complete();
                beforIndex=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {

    }
    public class SpalshAdapter extends PagerAdapter{
        List<View>list;

        public void setList(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }
}
