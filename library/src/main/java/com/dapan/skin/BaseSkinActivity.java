package com.dapan.skin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dapan.skin.core.SkinLayoutInflater;

/**
 * Created by per4j
 * on 2020-04-04
 */
public class BaseSkinActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SkinLayoutInflater.getInstance().setInflater(this);
        super.onCreate(savedInstanceState);
        SkinManager.getInstance().init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
