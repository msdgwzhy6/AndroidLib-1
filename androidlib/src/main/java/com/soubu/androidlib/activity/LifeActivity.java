package com.soubu.androidlib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：余天然 on 2017/3/15 上午9:02
 */
public abstract class LifeActivity extends AppCompatActivity {

    private List<LifeCallback> lifeCallbacks = new ArrayList<>();

    protected void initLifeCallbacks() {

    }

    public void addLifeCallback(LifeCallback lifeCallback) {
        lifeCallbacks.add(lifeCallback);
    }

    public void removeLifeCallback(LifeCallback lifeCallback) {
        lifeCallbacks.remove(lifeCallback);
    }

    public List<LifeCallback> getLifeCallbacks() {
        return lifeCallbacks;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLifeCallbacks();
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onCreate(savedInstanceState);
        }
        init();
    }

    /**
     * 所有的Callback都执行完了之后才执行
     */
    public abstract void init();

    @Override
    protected void onStart() {
        super.onStart();
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onDestroy();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onRestart();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onSaveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onRestoreInstanceState(savedInstanceState);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (LifeCallback callBack : lifeCallbacks) {
            callBack.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected AppCompatActivity getActivity() {
        return this;
    }
}
