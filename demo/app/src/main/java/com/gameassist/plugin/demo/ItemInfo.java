//
// Source code recreated from ItemInfo .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gameassist.plugin.demo;

import android.content.Context;

public class ItemInfo {

    private boolean trialing;
    private String commodityId;
    private String name;
    private boolean owned;
    private boolean open;
    private int type;

    public ItemInfo() {
    }

    public void setOwned(boolean var1) {
        this.owned = var1;
    }

    public void setOpen(boolean var1) {
        this.open = var1;
    }


    public ItemInfo(Context var1, String var2, int var4, int var5) {
        this.commodityId = var2;
        this.name = var1.getResources().getString(var4);
        this.type = var5;
    }


    public String getCommodityId() {
        return this.commodityId;
    }

    public boolean isOwned() {
        return this.owned;
    }


    public boolean isTrialing() {
        return this.trialing;
    }

    public void setTrialing(boolean var1) {
        this.trialing = var1;
    }


    public int getType() {

        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }
}
