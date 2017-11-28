package com.sk.uudc.module.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sk.uudc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/10.
 */

public class CancelReasonAdapter extends RecyclerView.Adapter<CancelReasonAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;


    public CancelReasonAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.item_cancel_reason, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


    }


    @Override
    public int getItemCount() {
        return 5;
    }



    static  class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.checkbox)
        CheckBox checkbox;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
