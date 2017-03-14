package com.cn.tfl.boucemenu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Happiness on 2017/3/10.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MViewHolder> {

    private List<String> mList;

    public MyAdapter(List<String> list) {
        this.mList = list;
    }

    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, null, false);
        return new MViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {
        holder.textView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_menu);
        }
    }
}
