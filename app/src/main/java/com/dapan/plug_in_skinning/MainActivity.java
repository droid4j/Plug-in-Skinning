package com.dapan.plug_in_skinning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dapan.skin.SkinManager;
import com.dapan.skin.core.SkinLayoutInflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinLayoutInflater.getInstance().setInflater(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkinManager.getInstance().init(this);
    }

    public void changeSkin(View view) {
        SkinManager.getInstance().loadSkin(SkinManager.skinPath);
    }

    public void resetSkin(View view) {
        SkinManager.getInstance().restoreSkin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinLayoutInflater.getInstance().unregister();
    }

    public void jumpTo(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }
}
