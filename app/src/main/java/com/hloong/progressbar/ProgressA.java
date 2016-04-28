package com.hloong.progressbar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

/**
 * TODO: document your custom view class.
 */
public class ProgressA extends Dialog {
    private View mView;
    private ProgressBar mProgressBar;

    public ProgressA(Context context) {
        super(context,android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_a, null);
        mProgressBar=(ProgressBar)mView.findViewById(R.id.progressbar);
        if (android.os.Build.VERSION.SDK_INT > 22) {//android 6.0替换clip的加载动画
            final Drawable drawable =  context.getApplicationContext().getResources().getDrawable(R.drawable.loading_a_6);
            mProgressBar.setIndeterminateDrawable(drawable);
        }
        setContentView(mView);
    }
}
