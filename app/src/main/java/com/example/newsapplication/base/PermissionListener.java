package com.example.newsapplication.base;

public interface PermissionListener {

    void onGranted(int requestCode);

    void onRejected(int requestCode);
}