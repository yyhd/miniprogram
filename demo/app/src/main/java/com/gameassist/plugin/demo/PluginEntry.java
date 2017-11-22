package com.gameassist.plugin.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.gameassist.plugin.Plugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;


public class PluginEntry extends Plugin {

    @SuppressLint("StaticFieldLeak")
    private static PluginEntry intance;
    private Vector<ItemInfo> listinfo = new Vector<>();
    private BaseView baseView;

    @Override
    public boolean OnPluginCreate() {
        intance = this;
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
        if (baseView == null) {
            baseView = new BaseView(getContext());
            initList();
        }
        return baseView;
    }

    @Override
    public void OnPluginUIHide() {
    }


    /**
     * 8,mod是否支持试玩
     * 7,商品详细信息
     * 9，试用开始
     * 10，试用结束
     * 6，兑换结构
     *
     * @param plugin
     * @param pluginName
     * @param cmd
     * @param params
     * @return
     */
    @Override
    public Bundle pluginCall(Plugin plugin, String pluginName, int cmd, Bundle params) {
        String result = params.getString("data");
        Bundle bundle = new Bundle();
        if (cmd == 101) {
            bundle.putString("FilterActivity", "");
            bundle.putString("FlagType", "");
            return bundle;
        } else if (cmd == 6) {
            if (!TextUtils.isEmpty(result)) {
                try {
                    JSONObject jsret = new JSONObject(result);
                    String commodityid = jsret.optString("Commodityid", "");
                    if (jsret.getString("rc").equals("0") || jsret.getString("rc").equals("20002")) {
                        for (ItemInfo commodityInfo : listinfo) {
                            if (commodityid.equals(commodityInfo.getCommodityId())) {
                                commodityInfo.setOwned(true);
                                commodityInfo.setOpen(true);
                                switchState();
                                break;
                            }
                        }
                    }
                } catch (JSONException ignored) {
                }
            }
        } else if (cmd == 8) {
            bundle.putBoolean("isSupprtTrial", true);//是否试用
            return bundle;
        } else if (cmd == 9) {
            String id = params.getString("Commodityid");
            for (ItemInfo itemInfo : listinfo) {
                assert id != null;
                if (itemInfo.getCommodityId().contentEquals(id)) {
                    itemInfo.setTrialing(true);
                    itemInfo.setOpen(true);
                    switchState();
                    break;
                }
            }
        } else if (cmd == 10) {
            String commodityId = params.getString("commodityId");
            for (ItemInfo itemInfo : listinfo) {
                assert commodityId != null;
                if (commodityId.equals(itemInfo.getCommodityId())) {
                    itemInfo.setTrialing(false);
                    itemInfo.setOpen(false);
                    switchState();
                    break;
                }
            }
        }
        return bundle;
    }

    private void switchState() {
        baseView.post(new Runnable() {
            @Override
            public void run() {
                baseView.addDatas(listinfo);
            }
        });
    }


    // TODO: 2017/11/8 初始化数据
    private void initList() {
        listinfo.add(new ItemInfo(getContext(), "xxxxxx", R.string.demo, 1));
        listinfo.add(new ItemInfo(getContext(), "xxxxxx", R.string.demo, 1));
        listinfo.add(new ItemInfo(getContext(), "xxxxxx", R.string.demo, 1));
        switchState();
        if (null != this.listinfo && !this.listinfo.isEmpty()) {
            (new Thread(new Runnable() {
                public void run() {
                    try {
                        Bundle var1 = getPluginManager().call(PluginEntry.this, getPluginInfo().packageName, 12, (Bundle) null);
                        JSONObject var2 = new JSONObject(var1.getString("data", ""));
                        if (var2.has("bought")) {
                            JSONArray var3 = var2.getJSONArray("bought");
                            if (null != var3) {
                                for (int var4 = 0; var4 < var3.length(); ++var4) {
                                    for (ItemInfo var6 : listinfo) {
                                        if (var3.get(var4).equals(var6.getCommodityId())) {
                                            var6.setOwned(true);
                                        }
                                    }
                                }
                            }
                        }
                        switchState();
                    } catch (JSONException ignored) {
                    }

                }
            })).start();
        }
    }


    boolean getIsOwned(final String var1) {
        ItemInfo var2 = new ItemInfo();
        for (ItemInfo var4 : this.listinfo) {
            if (var1.contentEquals(var4.getCommodityId())) {
                var2 = var4;
            }
        }

        if (var2.isOwned()) {
            return true;
        } else if (var2.isTrialing()) {
            return true;
        } else {
            (new Thread(new Runnable() {
                public void run() {
                    Bundle var1x = new Bundle();
                    var1x.putString("Commodityid", var1);
                    getPluginManager().call(PluginEntry.this, getPluginInfo().packageName, 11, var1x);
                }
            })).start();
            return false;
        }
    }

    static PluginEntry getIntance() {
        return intance;
    }
}