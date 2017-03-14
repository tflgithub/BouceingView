package com.cn.tfl.boucemenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by Happiness on 2017/3/10.
 */
public class BouncingMenu {

    private ViewGroup decorView;
    private View mView;
    private BouncingView bv;
    private RecyclerView rv;

    public BouncingMenu(View view, int resId, RecyclerView.Adapter adapter) {
        //两种方式获取decorView

        //1:一个小技巧，找到PhoneWindow里面最顶级的一个id为content的FrameLayout  源码布局 R.layout.sample_screen.
//        decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView()
//                .findViewById(android.R.id.content);
        //2:循环查找
        decorView = findRootParent(view);
        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        mView = layoutInflater.inflate(resId, null, false);
        bv = (BouncingView) mView.findViewById(R.id.bv);
        rv = (RecyclerView) mView.findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rv.setAdapter(adapter);
        bv.setAnimationListener(new AnimationImp());
    }

    public static BouncingMenu makeMenu(View view, int resId, RecyclerView.Adapter adapter) {
        return new BouncingMenu(view, resId, adapter);
    }


    public BouncingMenu show() {
        if (mView.getParent() != null) {
            decorView.removeView(mView);
        }
        ViewGroup.LayoutParams lp =
                new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(mView, lp);
        bv.show();
        return this;
    }

    private ViewGroup findRootParent(View view) {
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return null;
    }


    public void dismiss() {
        ObjectAnimator translationOut = ObjectAnimator.ofFloat(mView,
                "translationY", 0, mView.getHeight());
        translationOut.setDuration(600);
        translationOut.setInterpolator(new DecelerateInterpolator());
        translationOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                decorView.removeView(mView);
            }
        });
        translationOut.start();
    }

    class AnimationImp implements BouncingView.AnimationListener {

        @Override
        public void onStart() {
            rv.setVisibility(View.GONE);
        }

        @Override
        public void onContentShow() {
            rv.setVisibility(View.VISIBLE);
            rv.scheduleLayoutAnimation();
        }
    }
}
