package com.dapan.skin;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;

import com.dapan.skin.attr.SkinView;
import com.dapan.skin.core.SkinResource;

import java.util.List;
import java.util.Map;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinManager {

    private static SkinManager sInstance;
    private Context mContext;
    private SkinResource mSkinResource;
    public static String skinPath = "/sdcard/red.skin";
    private ArrayMap<Activity, List<SkinView>> skinViewMap = new ArrayMap<>();

    static {
        sInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();

        mSkinResource = new SkinResource(mContext, skinPath);
    }

    public void loadSkin(String path) {

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, path);

        for (Map.Entry<Activity, List<SkinView>> entry : skinViewMap.entrySet()) {
            for (SkinView skinView : entry.getValue()) {
                skinView.skin();
            }
        }
    }

    public void restoreSkin() {

        // 获取当前app的apk路径(资源路径)
        String resourcePath = mContext.getPackageResourcePath();

        // 重置 SkinResource 对象
        mSkinResource = new SkinResource(mContext, resourcePath);

        for (Map.Entry<Activity, List<SkinView>> entry : skinViewMap.entrySet()) {
            for (SkinView skinView : entry.getValue()) {
                skinView.skin();
            }
        }
    }

    public List<SkinView> getSkinViews(Activity activity) {
        return skinViewMap.get(activity);
    }

    /**
     * 注册
     *  一个Activity 对应一组 SkinView
     * @param activity
     * @param skinViews
     */
    public void register(Activity activity, List<SkinView> skinViews) {
        skinViewMap.put(activity, skinViews);
    }

    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
