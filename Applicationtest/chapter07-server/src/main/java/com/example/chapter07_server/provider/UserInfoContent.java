package com.example.chapter07_server.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserInfoContent implements BaseColumns {
    public static final String AUTHORITIES="com.example.chapter07_serve..provider.UserInfoProvider";

    public static  final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES +"/user");

//    表中的各个字段名称
    public  static final String USER_NAME="name";
    public  static final String USER_AGE="age";
    public  static final String USER_HEIGHT="height";
    public  static final String USER_WEIGHT="weight";
    public  static final String USER_MARRIED="married";
}
