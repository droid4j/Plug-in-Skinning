package com.dapan.skin.core;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;

import com.dapan.skin.SkinManager;
import com.dapan.skin.attr.SkinAttr;
import com.dapan.skin.attr.SkinView;
import com.dapan.skin.support.SkinAttrSupport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinLayoutInflater implements LayoutInflaterFactory {

    private static final String TAG = "SkinLayoutInflater";
    private static SkinLayoutInflater sInstance = new SkinLayoutInflater();
    private Activity mActivity;


    public static SkinLayoutInflater getInstance() {
        return sInstance;
    }

    public void setInflater(Activity context) {
        this.mActivity = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutInflaterCompat.setFactory(inflater, this);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // 拦截View的创建，获取View之后解析
        Log.e(TAG, "onCreateView: " + name );
        // 1. 创建View
        View view = createView(parent, name, context, attrs);
        Log.e(TAG, "onCreateView: " + view + " -> " + name);

        // 2. 解析属性，如：src, background, textColor，自定义属性
        if (view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getSkinAttrs(context, attrs);

            SkinView skinView = new SkinView(view, skinAttrs);

            // 3. 统一交给 SkinManager 管理
            manageSkinView(skinView);

        }


        return view;
    }

    private void manageSkinView(SkinView skinView) {
        List<SkinView> skinViews = SkinManager.getInstance().getSkinViews(mActivity);
        if (skinViews == null) {
            skinViews = new ArrayList<>();
            SkinManager.getInstance().register(mActivity, skinViews);
        }
        skinViews.add(skinView);
    }

    private Object mAppCompatViewInflater;
    private Class<?> mInflaterClazz;
    private static final Class<?>[] sMethodSignature = new Class[]{
            View.class, String.class, Context.class, AttributeSet.class,
            boolean.class, boolean.class, boolean.class, boolean.class};

    private static final Map<String, Method> sMethodMap
            = new ArrayMap<>();

    private View createView(View parent, String name, Context context, AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;


        try {
            if (mAppCompatViewInflater == null) {
                mInflaterClazz = Class.forName("android.support.v7.app.AppCompatViewInflater");
                Constructor<?> constructor = mInflaterClazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                mAppCompatViewInflater = constructor.newInstance();
            }

            Method createView = sMethodMap.get(name);
            final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent)parent);
            if (createView == null) {
                createView = mInflaterClazz.getDeclaredMethod("createView", sMethodSignature);
                sMethodMap.put(name, createView);
            }

            return (View) createView.invoke(mAppCompatViewInflater, parent, name, context, attrs, inheritContext,
                    isPre21 /* Only read android:theme pre-L (L+ handles this anyway) */, true, shouldBeUsed()
            );
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "createView#ClassNotFoundException: 没有找到 android.support.v7.app.AppCompatViewInflater 类！");
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.e(TAG, "createView#InstantiationException: android.support.v7.app.AppCompatViewInflater 实例化失败！");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "createView#NoSuchMethodException: 方法没找到");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.e(TAG, "createView: createView方法执行失败！");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(TAG, "createView#IllegalAccessException: 不合法的访问");
            e.printStackTrace();
        }

        return null;
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            return false;
        } else {
            for(View windowDecor = mActivity.getWindow().getDecorView(); parent != null; parent = parent.getParent()) {
                if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View)parent)) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * The maximum API level where this class is needed.
     */
    public static final int MAX_SDK_WHERE_REQUIRED = 20;

    public static boolean shouldBeUsed() {
        return AppCompatDelegate.isCompatVectorFromResourcesEnabled()
                && Build.VERSION.SDK_INT <= MAX_SDK_WHERE_REQUIRED;
    }
}
