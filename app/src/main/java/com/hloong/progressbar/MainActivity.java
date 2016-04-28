package com.hloong.progressbar;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.hloong.progressbar.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_loadingA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showA();
            }
        });

        findViewById(R.id.btn_loadingB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showB();
            }
        });

    }


    private void showA(){
        final ProgressA progressA = new ProgressA(this);
        progressA.setCanceledOnTouchOutside(false);
        progressA.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressA.cancel();
            }
        },2000);
    }
    private void showB(){
        final ProgressB progressB = new ProgressB(this);
        progressB.setCanceledOnTouchOutside(false);
        progressB.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressB.cancel();
            }
        },2000);
    }
}
