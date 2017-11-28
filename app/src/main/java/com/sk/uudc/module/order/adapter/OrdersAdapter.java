package com.sk.uudc.module.order.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.customview.MyTextView;
import com.sk.uudc.R;
import com.sk.uudc.module.order.activity.BusinessEvaluationActivity;
import com.sk.uudc.module.order.activity.CancelReasonsActivity;
import com.sk.uudc.module.order.activity.OrderDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    public OrdersAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_order, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == 0) {
            holder.tv_order_item_type.setText("待付款");
            holder.tv_order_item_lijifukuan.setVisibility(View.VISIBLE);
            holder.tv_order_item_quxiao.setVisibility(View.GONE);
            holder.tv_order_item_chakan.setVisibility(View.GONE);
            holder.tv_order_item_pingjia.setVisibility(View.GONE);
            holder.tv_order_item_jiacai.setVisibility(View.GONE);


        } else if (position == 1) {
            holder.tv_order_item_type.setText("待使用");
            holder.tv_order_item_lijifukuan.setVisibility(View.GONE);
            holder.tv_order_item_quxiao.setVisibility(View.VISIBLE);
            holder.tv_order_item_chakan.setVisibility(View.VISIBLE);
            holder.tv_order_item_pingjia.setVisibility(View.GONE);
            holder.tv_order_item_jiacai.setVisibility(View.GONE);

        } else if (position == 2) {
            holder.tv_order_item_type.setText("待评价");
            holder.tv_order_item_lijifukuan.setVisibility(View.GONE);
            holder.tv_order_item_quxiao.setVisibility(View.GONE);
            holder.tv_order_item_chakan.setVisibility(View.GONE);
            holder.tv_order_item_pingjia.setVisibility(View.VISIBLE);
            holder.tv_order_item_jiacai.setVisibility(View.VISIBLE);

        } else if (position == 3) {
            holder.tv_order_item_type.setText("已取消");
            holder.tv_order_item_lijifukuan.setVisibility(View.GONE);
            holder.tv_order_item_quxiao.setVisibility(View.GONE);
            holder.tv_order_item_chakan.setVisibility(View.GONE);
            holder.tv_order_item_pingjia.setVisibility(View.GONE);
            holder.tv_order_item_jiacai.setVisibility(View.GONE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("type", position);
                context.startActivity(intent);

            }
        });
        holder.tv_order_item_pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pingjia = new Intent(context, BusinessEvaluationActivity.class);
                context.startActivity(pingjia);
            }
        });
        holder.tv_order_item_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quxiao = new Intent(context, CancelReasonsActivity.class);
                context.startActivity(quxiao);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }




    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_order_item_icon)
        ImageView iv_order_item_icon;
        @BindView(R.id.tv_order_item_shangjia_name)
        TextView tv_order_item_shangjia_name;
        @BindView(R.id.tv_order_item_type)
        TextView tv_order_item_type;
        @BindView(R.id.tv_order_item_shangpin_name)
        TextView tv_order_item_shangpin_name;
        @BindView(R.id.tv_order_item_price)
        TextView tv_order_item_price;
        @BindView(R.id.tv_order_item_time)
        TextView tv_order_item_time;
        @BindView(R.id.tv_order_item_lijifukuan)
        MyTextView tv_order_item_lijifukuan;
        @BindView(R.id.tv_order_item_quxiao)
        MyTextView tv_order_item_quxiao;
        @BindView(R.id.tv_order_item_pingjia)
        MyTextView tv_order_item_pingjia;
        @BindView(R.id.tv_order_item_chakan)
        MyTextView tv_order_item_chakan;
        @BindView(R.id.tv_order_item_jiacai)
        MyTextView tv_order_item_jiacai;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
