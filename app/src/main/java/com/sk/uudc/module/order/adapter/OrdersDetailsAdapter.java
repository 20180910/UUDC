package com.sk.uudc.module.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sk.uudc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/21.
 */

public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;

    public OrdersDetailsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_order_details, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 4;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_details_item_name)
        TextView tv_order_details_item_name;
        @BindView(R.id.tv_order_details_item_num)
        TextView tv_order_details_item_num;
        @BindView(R.id.tv_order_details_item_price)
        TextView tv_order_details_item_price;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
