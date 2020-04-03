package com.dapan.skin.attr;

import android.view.View;

/**
 * Created by per4j
 * on 2020-04-04
 */
public abstract class SkinType {

    static final class TextColorSkinType extends SkinType {

        @Override
        void skin(String resName, View view) {

        }
    }

    static final class BackgroundSkinType extends SkinType {

        @Override
        void skin(String resName, View view) {

        }
    }

    static final class SrcSkinType extends SkinType {

        @Override
        void skin(String resName, View view) {

        }
    }

    private String mResName;
    abstract void skin(String resName, View view);

    public String getResName() {
        return mResName;
    }
}
