package com.hloong.progressbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * TODO: document your custom view class.
 */
public class ProgressB extends Dialog {
    private View mView;
    private ImageView iv;
    private ProgressBar mProgressBar;
    private AnimationDrawable mAnimation;

    @SuppressLint("NewApi")
    public ProgressB(Context context) {
        super(context, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_b, null);
        mProgressBar=(ProgressBar)mView.findViewById(R.id.progressbar);
        iv = (ImageView) mView.findViewById(R.id.iv_loading);
        mAnimation = new AnimationDrawable();
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.ic_popup_sync_1),100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.ic_popup_sync_2),100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.ic_popup_sync_3),100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.ic_popup_sync_4),100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.ic_popup_sync_5),100);
        mAnimation.addFrame(getContext().getResources().getDrawable(R.drawable.ic_popup_sync_6),100);
        mAnimation.setOneShot(false);
        iv.setBackground(mAnimation);
        if (mAnimation != null && !mAnimation.isRunning()) {
            mAnimation.start();
        }
        setContentView(mView);
    }


}
