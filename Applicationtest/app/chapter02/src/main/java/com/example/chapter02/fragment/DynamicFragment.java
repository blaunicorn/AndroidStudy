package com.example.chapter02.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter02.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DynamicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DynamicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DynamicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param position Parameter 1.
     * @param image_id Parameter 2.
     * @return A new instance of fragment DynamicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DynamicFragment newInstance(int position, int image_id, String desc) {
        DynamicFragment fragment = new DynamicFragment();
        //把参数打包，传入fragment中
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("image_id", image_id);
        args.putString("desc", desc);
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
        // 第一参数的布局文件，第二个参数是根节点，第三个参数 表示不是添加进去的，而是作为一个返回值
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        ImageView iv_pic = view.findViewById(R.id.iv_pic);
        TextView tv_desc = view.findViewById(R.id.iv_pic);

        Bundle arguments = getArguments();
        if (arguments!=null) {
            iv_pic.setImageResource(arguments.getInt("image_id",R.drawable.c01));
            tv_desc.setText(arguments.getString("desc"));
        }
        return view;
    }
}