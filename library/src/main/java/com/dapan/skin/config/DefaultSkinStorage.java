package com.dapan.skin.config;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class DefaultSkinStorage implements ISkinStorage {

    private Context mContext;

    public DefaultSkinStorage(Context context) {
        mContext = context;
    }

    @Override
    public void saveSkinPath(String path) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(path);
    }

    @Override
    public String getSkinPath() {
        return SkinPreUtils.getInstance(mContext).getSkinPath();
    }

    @Override
    public boolean checkSkin() {
        String skinPath = getSkinPath();
        return !TextUtils.isEmpty(skinPath);
    }

    @Override
    public void clearSkin() {
        SkinPreUtils.getInstance(mContext).clearSkin();
    }
}
