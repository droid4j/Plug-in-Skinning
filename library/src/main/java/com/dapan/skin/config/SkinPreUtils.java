package com.dapan.skin.config;

import android.content.Context;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinPreUtils {

    private static SkinPreUtils sInstance;
    private Context mContext;

    private SkinPreUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public static SkinPreUtils getInstance(Context context) {
        if (sInstance == null) {
            synchronized (SkinPreUtils.class) {
                if (sInstance == null) {
                    sInstance = new SkinPreUtils(context);
                }
            }
        }
        return sInstance;
    }

    public void saveSkinPath(String path) {
        mContext.getSharedPreferences(SkinConfig.KSIN_INFO_NAME, Context.MODE_PRIVATE)
                .edit().putString(SkinConfig.SKIN_PATH, path).apply();
    }

    public String getSkinPath() {
        return mContext.getSharedPreferences(SkinConfig.KSIN_INFO_NAME, Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_PATH, "");
    }

    public void clearSkin() {
        saveSkinPath("");
    }
}
