package com.example.chapter09.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        if (intent !=null ) {
            NetworkInfo networkInfo = intent.getParcelableExtra("networkInfo");
//            networkInfo.getTypeName();
            String text = String .format("收到一个网络变更广播，网络大类为%s,"+"网络小类为%s,网络制式为%s，网络状态为%s",networkInfo.getTypeName(),networkInfo.getSubtypeName(),networkInfo.getSubtype(),networkInfo.getState());
            Log.d("wcy", "onReceive: 网络变更广播"+text);
        }

    }
}