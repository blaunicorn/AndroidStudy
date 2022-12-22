package com.example.chapter02.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.chapter02.MainActivity;
import com.example.chapter02.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaunchFromUrlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchFromUrlFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView iv_launch;

    public LaunchFromUrlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @param image_id Parameter 2.
     * @return A new instance of fragment LaunchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaunchFromUrlFragment newInstance(int count, int position, String image_id) {
        LaunchFromUrlFragment fragment = new LaunchFromUrlFragment();
        Bundle args = new Bundle();
        args.putInt("count", count);
        args.putInt("position", position);
        args.putString("image_id", image_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context = getContext();
        Bundle arguments = getArguments();

            int count = arguments.getInt("count", 0);
            int position = arguments.getInt("position", 0);
            String image_id = arguments.getString("image_id");

                View view = LayoutInflater.from(context).inflate(R.layout.item_launch, container,false);
        iv_launch = view.findViewById(R.id.iv_launch);
                RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
                Button btn_start = view.findViewById(R.id.btn_start);
                // 每个页面都分配一组对应的单选按钮
                for (int j = 0; j < count; j++) {
                    RadioButton radioButton = new RadioButton(context);
                    radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    radioButton.setPadding(10,10,10,10);

                    // 当前位置的按钮要高亮显示 ，比如第二个引导页就高亮第二个单选按钮。方式一
                    if (j==position) {
                        radioButton.setChecked(true);
                    }
                    rg_indicate.addView(radioButton);
                }
                // 当前位置的按钮要高亮显示 方式二
//            ((RadioButton)rg_indicate.getChildAt(i)).setChecked(true);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = null;
                try {
                    bmp = getURLImage(image_id);
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = bmp;
                    System.out.println("000");
                    handle.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        if (position== count-1) {
                    btn_start.setVisibility(View.VISIBLE);
                    btn_start.setOnClickListener(v-> {
                        Toast.makeText(context,"第页",Toast.LENGTH_SHORT).show();

                        //写入缓存
                        SharedPreferences sp=context.getSharedPreferences("name",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putBoolean("ok",false);
                        editor.apply();

                        //监听按钮，如果点击，就跳转
                        Intent intent = new Intent();
                        //前一个（context）是目前页面，后面一个是要跳转的下一个页面
                        intent.setClass(context,MainActivity.class);
                        startActivity(intent);
                    });
                }





        return view;
    }

    //在消息队列中实现对控件的更改
    @SuppressLint("HandlerLeak")
    private Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("wcy", "01 onCreateView: ");
            switch (msg.what) {
                case 0:
                    System.out.println("111");
                    Bitmap bmp=(Bitmap)msg.obj;
                    iv_launch.setImageBitmap(bmp);
                    break;
            }
        };
    };

    public  Bitmap getURLImage(String url) throws IOException {

        Bitmap bmp = null;
        try {

            URL myUrl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);//读取图像数据
            //读取文本数据
            //byte[] buffer = new byte[100];
            //inputStream.read(buffer);
            //text = new String(buffer);
            is.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}