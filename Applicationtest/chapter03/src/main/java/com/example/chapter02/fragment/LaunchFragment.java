package com.example.chapter02.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chapter02.MainActivity;
import com.example.chapter02.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaunchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaunchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LaunchFragment() {
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
    public static LaunchFragment newInstance(int count,int position, int image_id) {
        LaunchFragment fragment = new LaunchFragment();
        Bundle args = new Bundle();
        args.putInt("count", count);
        args.putInt("position", position);
        args.putInt("image_id", image_id);
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
            int image_id = arguments.getInt("image_id", 0);

                View view = LayoutInflater.from(context).inflate(R.layout.item_launch, container,false);
                ImageView iv_launch = view.findViewById(R.id.iv_launch);
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
                 iv_launch.setImageResource(image_id);

                if (position== count-1) {
                    btn_start.setVisibility(View.VISIBLE);
                    btn_start.setOnClickListener(v-> {
                        Toast.makeText(context,"第页",Toast.LENGTH_SHORT).show();
                        //监听按钮，如果点击，就跳转
                        Intent intent = new Intent();
                        //前一个（context）是目前页面，后面一个是要跳转的下一个页面
                        intent.setClass(context,MainActivity.class);
                        startActivity(intent);
                    });
                }





        return view;
    }
}