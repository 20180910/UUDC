package com.sk.uudc.module.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sk.uudc.R;
import com.sk.uudc.module.my.network.response.AccountObj;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/11.
 */

public class BankManageAdapter extends BaseAdapter {
    private Context context;
    List<AccountObj> date;

    public BankManageAdapter(Context context, List<AccountObj> date) {
        this.context = context;
        this.date = date;
    }

    @Override
    public int getCount() {
        return date.size();
    }

    @Override
    public Object getItem(int i) {
        return date.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank_manage, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).
                load(date.get(i).getBank_image()).
                error(R.color.c_press).
                into(holder.iv_item_bank_manage_icon);
        holder. tv_item_bank_manage_name.setText(date.get(i).getBank_name());
        holder.tv_item_bank_manage_type.setText(date.get(i).getCard_type());
        holder.tv_item_bank_manage_num.setText(date.get(i).getBank_card());


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_item_bank_manage_icon)
        ImageView iv_item_bank_manage_icon;
        @BindView(R.id.tv_item_bank_manage_name)
        TextView tv_item_bank_manage_name;
        @BindView(R.id.tv_item_bank_manage_type)
        TextView tv_item_bank_manage_type;
        @BindView(R.id.tv_item_bank_manage_num)
        TextView tv_item_bank_manage_num;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
