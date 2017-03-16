package com.soubu.androidlib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 作者：余天然 on 2017/3/15 上午9:24
 */
public abstract class LifeCallback {
    private AppCompatActivity activity;

    public LifeCallback(AppCompatActivity activity) {
        this.activity = activity;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public abstract void onCreate(Bundle savedInstanceState);

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onRestart() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }
}
