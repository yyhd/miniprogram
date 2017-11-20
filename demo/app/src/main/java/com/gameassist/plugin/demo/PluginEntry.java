package com.gameassist.plugin.demo;

import android.os.Bundle;
import android.view.View;

import com.gameassist.plugin.Plugin;
import com.yyhd.mini.program.f.CommodityInfo;
import com.yyhd.mini.program.f.NativeInterface;
import com.yyhd.mini.program.f.PluginEntryView;

import java.util.Vector;


public class PluginEntry extends Plugin implements NativeInterface {

    private PluginEntryView pluginEntryView;

    @Override
    public boolean OnPluginCreate() {
        return true;
    }

    @Override
    public void OnPlguinDestroy() {
    }

    @Override
    public boolean pluginHasUI() {
        return true;
    }

    @Override
    public boolean pluginAutoHide() {
        return true;
    }


    // TODO: 2017/11/8 ui
    @Override
    public View OnPluginUIShow() {
        if (pluginEntryView == null) {
            pluginEntryView = new PluginEntryView(getContext(), this, this);
        }
        return pluginEntryView;
    }

    @Override
    public void OnPluginUIHide() {
    }


    // TODO: 2017/11/8 过滤activity 悬浮窗层级 数据传递
    @Override
    public Bundle pluginCall(Plugin plugin, String pluginName, int cmd, Bundle params) {
        if (cmd == 101) {
            Bundle bundle = new Bundle();
            bundle.putString("FilterActivity", "");
            bundle.putString("FlagType", "");
            return bundle;
        } else {
            return pluginEntryView.pluginCall(pluginName, cmd, params);
        }
    }


    // TODO: 2017/11/8 初始化数据
    @Override
    public Vector<CommodityInfo> getList() {
        Vector<CommodityInfo> listinfo = new Vector<>();
        listinfo.add(new CommodityInfo(getContext(), "xxxxxx", 1, R.string.demo, 1, 0));
        return listinfo;
    }

    @Override
    public String getSelfLibName(int i) {
        return "";
    }
}