package com.cn.alef.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cn.alef.R;
import com.cn.alef.adapter.HistoryAdapter;
import com.cn.alef.entity.HistoryInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/8/11.
 */

public class HistoryRecordFragment extends Fragment {

    private ListView listView;
    private List<HistoryInfo> historyData = new ArrayList<>();
    private HistoryAdapter mHistoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historyrecordfragment, container, false);
        initView(view);
        initData();
        return view;

    }

    private void initData() {
        HistoryInfo history1 = new HistoryInfo();
        historyData.add(history1);
        HistoryInfo history2 = new HistoryInfo();
        historyData.add(history2);
        HistoryInfo history3 = new HistoryInfo();
        historyData.add(history3);
        HistoryInfo history4 = new HistoryInfo();
        historyData.add(history4);
        HistoryInfo history5 = new HistoryInfo();
        historyData.add(history5);
        HistoryInfo history6 = new HistoryInfo();
        historyData.add(history6);
        mHistoryAdapter = new HistoryAdapter(getActivity(), historyData);
        listView.setAdapter(mHistoryAdapter);

    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listview);
    }
}
