package com.sk.uudc.module.near.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sk.uudc.R;
import com.sk.uudc.module.near.network.response.ShangJiaShangPingObj;

import java.util.List;

/**
 * Created by Administrator on 2017/11/24.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder>{
    List<ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean> list;
    Context context;

    public GoodsAdapter(Context context) {
        this.context = context;
    }

    public List<ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean> getList() {
        return list;
    }

    public void setList(List<ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder=new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_shang_jia_goods,parent,false));
        return holder;
    }

    //ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ShangJiaShangPingObj.MerchantClassListBean.GoodsListBean bean =list.get(position);
//                Glide.with(context).load(bean.getGoods_image()).error(R.color.c_press).into(holder.iv_goods_img);
        holder.tv_goods_name.setText(  bean.getGoods_name());
        holder.tv_goods_xiaoliang.setText(  "月销量" + bean.getSales_volume() + "份");
        holder.tv_goods_price.setText( bean.getGoods_price() + "");
        /*if (sparseIntArray.get(bean.getNavigation_id()) == position) {
            holder.tv_goods_title.setVisibility(View.VISIBLE);
            holder.tv_goods_title.setText( bean.getGoods_price() + "");
        } else {
            holder.tv_goods_title.setVisibility(View.GONE);
        }*/
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_goods_name;
        public TextView tv_goods_xiaoliang;
        public TextView tv_goods_price;
        public TextView tv_goods_title;
        public ImageView iv_goods_img;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_goods_name=itemView.findViewById(R.id.tv_goods_name);
            tv_goods_xiaoliang=itemView.findViewById(R.id.tv_goods_xiaoliang);
           tv_goods_price= itemView.findViewById(R.id.tv_goods_price);
            tv_goods_title= itemView.findViewById(R.id.tv_goods_title);
            iv_goods_img= itemView.findViewById(R.id.iv_goods_img);
        }
    }
}
