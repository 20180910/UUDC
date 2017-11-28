package com.sk.uudc.module.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sk.uudc.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21.
 */

public class MyEvaluateImgAdapter extends RecyclerView.Adapter<MyEvaluateImgAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<String> img;

    public MyEvaluateImgAdapter(Context context, List<String> img) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.img = img;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_my_evaluate_img, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(img.get(position)).error(R.color.c_press).into( holder.iv_order_item_icon);

    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_order_item_icon)
        ImageView iv_order_item_icon;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
