package com.dapan.plug_in_skinning;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dapan.skin.BaseSkinActivity;
import com.dapan.skin.SkinManager;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class TestActivity extends BaseSkinActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
    }

    public void changeSkin(View view) {
        SkinManager.getInstance().loadSkin(SkinManager.skinPath);
    }

    public void resetSkin(View view) {
        SkinManager.getInstance().restoreSkin();
    }
}
