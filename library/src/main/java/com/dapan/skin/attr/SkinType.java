package com.dapan.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dapan.skin.SkinManager;
import com.dapan.skin.core.SkinResource;

/**
 * Created by per4j
 * on 2020-04-04
 */
public abstract class SkinType {

    private static final String TAG = "SkinType";
    static final class TextColorSkinType extends SkinType {

        @Override
        public String getResName() {
            return "textColor";
        }

        @Override
        void skin(String resName, View view) {
            Log.e(TAG, "skin: " + resName + ", " + view );
            SkinResource skinResource = SkinType.getSkinResource();
            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if (colorStateList != null) {
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(colorStateList.getDefaultColor());
                }
            }
        }
    }

    static final class BackgroundSkinType extends SkinType {

        @Override
        public String getResName() {
            return "background";
        }

        @Override
        void skin(String resName, View view) {
            SkinResource skinResource = SkinType.getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                view.setBackground(drawable);
            }

            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if (colorStateList != null) {
                view.setBackgroundColor(colorStateList.getDefaultColor());
            }
        }
    }

    static final class SrcSkinType extends SkinType {

        @Override
        public String getResName() {
            return "src";
        }

        @Override
        void skin(String resName, View view) {
            SkinResource skinResource = SkinType.getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageDrawable(drawable);
                }
            }
        }
    }

    private String mResName;
    abstract void skin(String resName, View view);

    public String getResName() {
        return mResName;
    }

    private static ArrayMap<String, SkinType> skinTypeArrayMap = new ArrayMap<>();

    public static SkinType getSkinType(String resName) {
        if (!TextUtils.isEmpty(resName)) {
            if (skinTypeArrayMap.get(resName) == null) {
                switch (resName) {
                    case "src":
                        SrcSkinType srcSkinType = new SrcSkinType();
                        skinTypeArrayMap.put(resName, srcSkinType);
                        return srcSkinType;
                    case "background":
                        BackgroundSkinType backgroundSkinType = new BackgroundSkinType();
                        skinTypeArrayMap.put(resName, backgroundSkinType);
                        return backgroundSkinType;
                    case "textColor":
                        TextColorSkinType textColorSkinType = new TextColorSkinType();
                        skinTypeArrayMap.put(resName, textColorSkinType);
                        return textColorSkinType;
                }
            }
        }

        return skinTypeArrayMap.get(resName);
    }

    private static SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }
}
