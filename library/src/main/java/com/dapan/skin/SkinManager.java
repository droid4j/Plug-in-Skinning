package com.dapan.skin;

import android.app.Activity;
import android.util.ArrayMap;

import com.dapan.skin.attr.SkinView;

import java.util.List;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinManager {
    private static SkinManager sInstance;

    private ArrayMap<Activity, List<SkinView>> skinViewMap = new ArrayMap<>();

    static {
        sInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return sInstance;
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
}
