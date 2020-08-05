package com.example.chenq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chenq.R;

import java.util.ArrayList;
import java.util.List;

/**
 * create by chenqi on 2020/8/5.
 * Email: chenqwork@gmail.com
 * Desc:
 */
public class MainListAdapter extends ArrayAdapter {

    private List<String> data = new ArrayList<>();
    private int mResource;

    public MainListAdapter(@NonNull Context context, int resource, List<String> data) {
        super(context, resource);
        this.data = data;
        mResource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String text = getItem(position);
        View view = null;
        ViewHolder holder = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.mText = view.findViewById(R.id.tv_text);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.mText.setText(text);
        return view;
    }

    class ViewHolder {
        TextView mText;
    }
}
