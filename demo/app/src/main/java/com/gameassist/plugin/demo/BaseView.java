package com.gameassist.plugin.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ljp on 2017/11/22.
 */

public class BaseView extends FrameLayout {

    private List<ItemInfo> mList = new ArrayList<>();
    private ItemAdapter itemAdapter = new ItemAdapter();

    public BaseView(Context context) {
        super(context);
        initView();
    }


    private void initView() {
        inflate(getContext(), R.layout.mainsdk, this);
        ((GridView) findViewById(R.id.cheat_gridview)).setAdapter(itemAdapter);
    }


    void addDatas(Collection<? extends ItemInfo> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        itemAdapter.notifyDataSetChanged();
    }


    private class ItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (null == convertView) {
                convertView = inflate(getContext(), R.layout.itemsdk, null);
                viewHolder = new ViewHolder();
                viewHolder.i_name = (TextView) convertView.findViewById(R.id.i_tv);
                viewHolder.i_cb = (CheckBox) convertView.findViewById(R.id.i_cb);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final ItemInfo commodityInfo = (ItemInfo) getItem(position);
            if (null != commodityInfo) {
                boolean i = (commodityInfo.getType() == 1);
                viewHolder.i_name.setVisibility(i ? GONE : VISIBLE);
                viewHolder.i_cb.setVisibility(i ? VISIBLE : GONE);
                viewHolder.i_name.setText(commodityInfo.getName());
                viewHolder.i_cb.setText(commodityInfo.getName());
                viewHolder.i_cb.setChecked(commodityInfo.isOpen());
                convertView.setBackgroundResource(commodityInfo.isOpen() ? R.drawable.plugin_bg3 : R.drawable.plugin_bg2);
                viewHolder.i_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (PluginEntry.getIntance().getIsOwned(commodityInfo.getCommodityId())) {
                            Toast.makeText(getContext(), commodityInfo.getName(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                viewHolder.i_cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (PluginEntry.getIntance().getIsOwned(commodityInfo.getCommodityId())) {
                            commodityInfo.setOpen(viewHolder.i_cb.isChecked());
                        } else {
                            viewHolder.i_cb.setChecked(false);
                        }
                        notifyDataSetChanged();
                    }
                });

            }
            return convertView;
        }

        private class ViewHolder {
            CheckBox i_cb;
            TextView i_name;
        }
    }
}
