package com.cn.alef.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cn.alef.NounInterpretationActivity;
import com.cn.alef.R;

/**
 * Created by wang on 2017/8/11.
 */

public class TranslateFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll1, ll2;
    private ImageView img1, img2;
    private TextView tv1, tv2;
    private TextView editcontent;
    private TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.translatefragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        tv=(TextView) v.findViewById(R.id.tv);
        ll1 = (LinearLayout) v.findViewById(R.id.ll1);
        ll1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        tv1.setTextColor(getResources().getColor(R.color.white));
//                        img1.setBackgroundResource(R.drawable.ic_alef_record2);
//                        ll1.setBackgroundResource(R.drawable.circlr_bg2);
                        mPopUpWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 20);
                       tv.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        tv1.setTextColor(getResources().getColor(R.color.white));
//                        img1.setBackgroundResource(R.drawable.ic_alef_record2);
//                        ll1.setBackgroundResource(R.drawable.circlr_bg2);
                        mPopUpWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 20);
                        tv.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
//                        tv1.setTextColor(getResources().getColor(R.color.bule));
//                        img1.setBackgroundResource(R.drawable.ic_alef_record);
//                        ll1.setBackgroundResource(R.drawable.circlr_bg1);
                        mPopUpWindow.dismiss();
                        tv.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
        ll2 = (LinearLayout) v.findViewById(R.id.ll2);
        ll2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        tv2.setTextColor(getResources().getColor(R.color.white));
//                        img2.setBackgroundResource(R.drawable.ic_alef_record2);
//                        ll2.setBackgroundResource(R.drawable.circlr_bg2);
                        mPopUpWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 20);
                        tv.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        tv2.setTextColor(getResources().getColor(R.color.white));
//                        img2.setBackgroundResource(R.drawable.ic_alef_record2);
//                        ll2.setBackgroundResource(R.drawable.circlr_bg2);
                        mPopUpWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 20);
                        tv.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
//                        tv2.setTextColor(getResources().getColor(R.color.bule));
//                        img2.setBackgroundResource(R.drawable.ic_alef_record);
//                        ll2.setBackgroundResource(R.drawable.circlr_bg1);
                        mPopUpWindow.dismiss();
                        tv.setVisibility(View.GONE);
                        break;
                }
                return true;
            }
        });
        editcontent = (TextView) v.findViewById(R.id.editcontent);
        img1=(ImageView) v.findViewById(R.id.img1);
        img2=(ImageView) v.findViewById(R.id.img2);
        tv1=(TextView) v.findViewById(R.id.tv1);
        tv2=(TextView) v.findViewById(R.id.tv2);
        holdSpeak2();


        String msg="你好请问怎么去东方明珠";
        SpannableString spannableString=new SpannableString(msg);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), NounInterpretationActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.bule));
                ds.setUnderlineText(false);
            }
        },7,msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editcontent.setText(spannableString);
        editcontent.setMovementMethod(LinkMovementMethod.getInstance());


    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editcontent:

                break;
            default:
                break;
        }
    }


//    public void holdSpeak() {
//        passdDialog = new Dialog(getActivity());
//        View view2 = LayoutInflater.from(getActivity()).inflate(
//                R.layout.speak_dialog, null);
//        passdDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        passdDialog.setContentView(view2);
//        Window dlgWindow = passdDialog.getWindow();
//        dlgWindow.setGravity(Gravity.CENTER);
//        WindowManager m = (WindowManager) getActivity()
//                .getSystemService(Context.WINDOW_SERVICE);
//        Display d = m.getDefaultDisplay();
//        WindowManager.LayoutParams p = dlgWindow.getAttributes();
//        p.height = (int) (d.getHeight() * 0.35);
//        p.width = (int) (d.getWidth() * 0.8);
////         p.alpha = 0.5f;
//        dlgWindow.setAttributes(p);
//        passdDialog.setCanceledOnTouchOutside(true);
//    }


    PopupWindow mPopUpWindow;
    public void holdSpeak2() {
        View mContentView = null;
        if(mContentView == null){
            mContentView = LayoutInflater.from(getActivity()).inflate(R.layout.speak_dialog, null);
        }
        if(mPopUpWindow == null){
            WindowManager wm = (WindowManager) getActivity()
                    .getSystemService(getActivity().WINDOW_SERVICE);
            int width = wm.getDefaultDisplay().getWidth();
            mPopUpWindow = new PopupWindow(mContentView, width*3/4, ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopUpWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopUpWindow.setOutsideTouchable(true);
            mPopUpWindow.setFocusable(true);
        }
    }
















}