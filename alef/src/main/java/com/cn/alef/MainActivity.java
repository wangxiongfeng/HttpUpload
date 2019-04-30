package com.cn.alef;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.alef.entity.Voice;
import com.cn.alef.fragment.DictionaryFragment;
import com.cn.alef.fragment.HistoryRecordFragment;
import com.cn.alef.fragment.TranslateFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private ViewPager vp;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    float density;
    private LinearLayout translinear, dictionaryll, historyll;
    private ImageView img1, img2, img3;
    private TextView tv1, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();

    }




    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);
        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }


    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        density = dm.density;
        vp = (ViewPager) findViewById(R.id.vp);
        translinear = (LinearLayout) findViewById(R.id.translinear);
        translinear.setOnClickListener(this);
        dictionaryll = (LinearLayout) findViewById(R.id.dictionaryll);
        dictionaryll.setOnClickListener(this);
        historyll = (LinearLayout) findViewById(R.id.historyll);
        historyll.setOnClickListener(this);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
    }


    private void initDatas() {

        TranslateFragment translateFragment = new TranslateFragment();
        DictionaryFragment dictionaryFragment = new DictionaryFragment();
        HistoryRecordFragment historyRecordFragment = new HistoryRecordFragment();
        mTabs.add(translateFragment);
        mTabs.add(dictionaryFragment);
        mTabs.add(historyRecordFragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                GoTOTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp.setAdapter(mAdapter);
        vp.setOffscreenPageLimit(2);
        GoTOTab(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translinear:
                GoTOTab(0);

                break;
            case R.id.dictionaryll:
                GoTOTab(1);
                break;
            case R.id.historyll:
                GoTOTab(2);
                break;
        }
    }


    private void setDefault() {
        img1.setBackgroundResource(R.drawable.ic_alef_voice2);
        img2.setBackgroundResource(R.drawable.ic_alef_dictionary2);
        img3.setBackgroundResource(R.drawable.ic_alef_history2);
        tv1.setTextColor(getResources().getColor(R.color.shallowgrey));
        tv2.setTextColor(getResources().getColor(R.color.shallowgrey));
        tv3.setTextColor(getResources().getColor(R.color.shallowgrey));

    }

    public void GoTOTab(int s) {
        setDefault();
        switch (s) {
            case 0:
                tv1.setTextColor(getResources().getColor(R.color.bule));
                img1.setBackgroundResource(R.drawable.ic_alef_voice);
                break;
            case 1:
                tv2.setTextColor(getResources().getColor(R.color.bule));
                img2.setBackgroundResource(R.drawable.ic_alef_dictionary);
                break;
            case 2:
                tv3.setTextColor(getResources().getColor(R.color.bule));
                img3.setBackgroundResource(R.drawable.ic_alef_history);
                break;
        }


        vp.setCurrentItem(s, false);
    }


}
