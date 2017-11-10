package com.example.jsoup.jsoupdemo.ui.fragment.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;
import com.example.jsoup.jsoupdemo.ui.fragment.mobel.MenuInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MenuInfoAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<MenuInfo> datas;

    public MenuInfoAdapter(Context context, List<MenuInfo> menuInfos) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.datas = menuInfos;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_button,null);
            holder = new ViewHolder();
            holder.icon.setImageResource(datas.get(position).icon);
        }

        return null;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
    }
}
