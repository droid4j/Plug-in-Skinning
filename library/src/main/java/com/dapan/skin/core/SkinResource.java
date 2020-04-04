package com.dapan.skin.core;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinResource {

    private static final String TAG = "SkinResource";
    private Resources mSkinResources;
    private String mPkgName;

    public SkinResource(Context context, String path) {

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            Log.e(TAG, "SkinResource: 加载 " + path + " 路径下的皮肤资源");
            Resources hostResources = context.getResources();
            mSkinResources = new Resources(assetManager, hostResources.getDisplayMetrics(),
                    hostResources.getConfiguration());
            mPkgName = context.getPackageManager().getPackageArchiveInfo(path,
                    PackageManager.GET_ACTIVITIES).packageName;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Drawable getDrawableByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mPkgName);
            Drawable drawable = mSkinResources.getDrawable(resId);
            return drawable;
        } catch (Exception e) {
            return null;
        }

    }

    public ColorStateList getColorByName(String resName) {
        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mPkgName);
            ColorStateList colorStateList = mSkinResources.getColorStateList(resId);
            return colorStateList;
        } catch (Exception e) {
            return null;
        }

    }
}
