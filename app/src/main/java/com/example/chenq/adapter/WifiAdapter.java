package com.example.chenq.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chenq.library.bean.WifiBean;
import com.example.chenq.R;

import java.util.List;

public class WifiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final SpanSizeLookup spanSizeLookup = new SpanSizeLookup();
    private LayoutInflater inflater;

    private List<WifiBean> mData = null;
    private Activity mActivity;

    public WifiAdapter(Activity mActivity, List<WifiBean> list) {
        this.mActivity = mActivity;
        this.inflater = LayoutInflater.from(mActivity);
        this.mData = list;
    }

    public void refreshWifi(List<WifiBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }


    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return spanSizeLookup;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.set_wifi_item, parent, false);
        return new SettingHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SettingHolder holder = (SettingHolder) viewHolder;
        WifiBean info = mData.get(position);
        holder.txtName.setText(info.getSsid());
        if (info.getLevel() == 0) {
            holder.imgLogo.setImageResource(R.mipmap.status_bar_wifi_on_icon_0);
        } else if (info.getLevel() == 1) {
            holder.imgLogo.setImageResource(R.mipmap.status_bar_wifi_on_icon_1);
        } else if (info.getLevel() == 2) {
            holder.imgLogo.setImageResource(R.mipmap.status_bar_wifi_on_icon_2);
        } else if (info.getLevel() == 3) {
            holder.imgLogo.setImageResource(R.mipmap.status_bar_wifi_on_icon_3);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    class SettingHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgLogo;

        SettingHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mData != null && position < mData.size()) {

                        if (listener != null) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private class SpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            return 0;
        }
    }
}
