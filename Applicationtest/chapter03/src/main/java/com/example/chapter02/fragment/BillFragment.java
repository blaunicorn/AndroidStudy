package com.example.chapter02.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.chapter02.R;
import com.example.chapter02.adapter.BillListAdapter;
import com.example.chapter02.database.BillDBHelper;
import com.example.chapter02.entity.BillInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @param yearMonth
     * @return A new instance of fragment BillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillFragment newInstance(String yearMonth) {
        BillFragment fragment = new BillFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        args.putString("yearMonth",yearMonth);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ListView lv_bill = view.findViewById(R.id.lv_bill);

        // 根据参数获取数据库数据，
        // 并用适配器给ListView添加数据 ps 2022-12
        Bundle arguments = getArguments();
        String  yearMonth = arguments.getString("yearMonth");
        BillDBHelper billDBHelper = BillDBHelper.getInstance(getContext());
        List<BillInfo> billInfoList = billDBHelper.queryByMonth(yearMonth);
        Log.d("wcy", "onCreateView: "+ yearMonth + ":"+billInfoList);
        BillListAdapter adapter = new BillListAdapter(getContext(),billInfoList);
        lv_bill.setAdapter(adapter);
//        return inflater.inflate(R.layout.fragment_bill, container, false);
        return  view;
    }
}