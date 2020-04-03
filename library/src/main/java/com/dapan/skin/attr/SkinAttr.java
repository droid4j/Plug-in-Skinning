package com.dapan.skin.attr;

import android.view.View;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class SkinAttr {

    private String mResName;
    private SkinType mType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mType = skinType;
    }

    public void skin(View view) {
        mType.skin(mResName, view);
    }
}
