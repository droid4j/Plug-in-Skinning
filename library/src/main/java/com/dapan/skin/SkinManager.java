package com.dapan.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.dapan.skin.attr.SkinView;
import com.dapan.skin.config.DefaultSkinStorage;
import com.dapan.skin.config.ISkinStorage;
import com.dapan.skin.config.SkinPreUtils;
import com.dapan.skin.core.SkinResource;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinManager {

    private static final String TAG = "SkinManager";

    private static SkinManager sInstance;
    private Context mContext;
    private SkinResource mSkinResource;
    public static String skinPath = "/sdcard/red.skin";
    private ArrayMap<Activity, List<SkinView>> skinViewMap = new ArrayMap<>();
    private ISkinStorage skinStorage;

    static {
        sInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return sInstance;
    }

    public void setSkinStorage(ISkinStorage skinStorage) {
        this.skinStorage = skinStorage;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();

        if (skinStorage == null) {
            skinStorage = new DefaultSkinStorage(mContext);
        }

        String skinPath = skinStorage.getSkinPath();
        if (TextUtils.isEmpty(skinPath)) {
            Log.e(TAG, "init: skinPath is empty");
            return;
        }

        File skinFile = new File(skinPath);
        if (!skinFile.exists()) {
            skinStorage.clearSkin();
            Log.e(TAG, "init: " + skinPath+ " skin file is not exist");
            return;
        }

        // 校验包名
        String pkgName = mContext.getPackageManager().getPackageArchiveInfo(skinPath,
                PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(pkgName)) {
            // 获取不到包名，说明不是皮肤包，清除
            skinStorage.clearSkin();
            Log.e(TAG, "init: " + skinPath + " is not a skin file: " + pkgName);
            return;
        }

        mSkinResource = new SkinResource(mContext, skinPath);
        Log.e(TAG, "init: " + skinPath + " 皮肤包初始化完成");
    }

    public void loadSkin(String path) {

        if (mContext == null) {
            throw new IllegalArgumentException("是否忘记调用 init() 方法？");
        }

        if (TextUtils.isEmpty(path)) {
            Log.e(TAG, "loadSkin: " + path + " is empty");
            return;
        }

        File skinFile = new File(path);
        if (!skinFile.exists()) {
            Log.e(TAG, "loadSkin: " + path + " skin file is not exit");
            return;
        }

        String pkgName = mContext.getPackageManager().getPackageArchiveInfo(path,
                PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(pkgName)) {
            Log.e(TAG, "loadSkin: " + path + " is not a skin file: " + pkgName);
            return;
        }

        String skinPath = skinStorage.getSkinPath();
        if (path.equals(skinPath)) {
            Log.e(TAG, "loadSkin: 当前是 " + skinPath + " 皮肤，不需要加载!");
            return;
        }

        // 初始化资源管理
        mSkinResource = new SkinResource(mContext, path);
        Log.e(TAG, "loadSkin: " + path + " 皮肤加载完成");

        for (Map.Entry<Activity, List<SkinView>> entry : skinViewMap.entrySet()) {
            for (SkinView skinView : entry.getValue()) {
                skinView.skin();
            }
        }
        Log.e(TAG, "loadSkin: " + path + " 皮肤应用完成");

        // 保存皮肤包的路径
        skinStorage.saveSkinPath(path);
        Log.e(TAG, "loadSkin: " + path + " 皮肤包已缓存" );
    }

    public void restoreSkin() {

        if (mContext == null) {
            throw new IllegalArgumentException("是否忘记调用 init() 方法？");
        }

        // skinPath 为空，表示没有换肤，不需要恢复
        if (!skinStorage.checkSkin()) {
            Log.e(TAG, "restoreSkin: 当前为默认皮肤，不需要恢复");
            return;
        }

        // 获取当前app的apk路径(资源路径)
        String resourcePath = mContext.getPackageResourcePath();

        // 重置 SkinResource 对象
        mSkinResource = new SkinResource(mContext, resourcePath);
        Log.e(TAG, "restoreSkin: " + resourcePath + " 默认皮肤资源初始化成功");

        for (Map.Entry<Activity, List<SkinView>> entry : skinViewMap.entrySet()) {
            for (SkinView skinView : entry.getValue()) {
                skinView.skin();
            }
        }
        Log.e(TAG, "loadSkin: " + resourcePath + " 皮肤应用完成");

        // 为空时，表示原资源
        skinStorage.clearSkin();
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

    public void checkSkin(SkinView skinView) {
        if (skinStorage != null && skinStorage.checkSkin()) {
            skinView.skin();
        }
    }
}
