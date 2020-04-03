package com.dapan.skin.core;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinLayoutInflater implements LayoutInflaterFactory {

    private static final String TAG = "SkinLayoutInflater";
    private static SkinLayoutInflater sInstance = new SkinLayoutInflater();

    public static SkinLayoutInflater getInstance() {
        return sInstance;
    }

    public void setInflater(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutInflaterCompat.setFactory(inflater, this);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 拦截View的创建，获取View之后解析
        Log.e(TAG, "onCreateView: " + name );
        // 1. 创建View

        // 2. 解析属性，如：src, background, textColor，自定义属性

        // 3. 统一交给 SkinManager 管理

        return null;
    }
}
