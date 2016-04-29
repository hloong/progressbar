# Android帧动画animation-list在ProgressBar上失效的解决办法
Android帧动画animation-list在ProgressBar上失效的解决办法

做Loading的时候，帧动画我们经常用到，主要是一些比较复杂的动画，比如小人跑动，人物翻转等等;
常规的做法参考：

drawable下放一个如下的文件loading_a，图片是连续的几张图切分
```
<?xml version="1.0" encoding="utf-8"?>
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="false"
    android:variablePadding="true">
    <item android:duration="100">
        <clip xmlns:android="http://schemas.android.com/apk/res/android"
            android:clipOrientation="horizontal"
            android:drawable="@drawable/ic_popup_sync_1"
            android:gravity="left"></clip>
    </item>
    <item android:duration="100">
        <clip xmlns:android="http://schemas.android.com/apk/res/android"
            android:clipOrientation="horizontal"
            android:drawable="@drawable/ic_popup_sync_2"
            android:gravity="left"></clip>
    </item>
    <item android:duration="100">
        <clip xmlns:android="http://schemas.android.com/apk/res/android"
            android:clipOrientation="horizontal"
            android:drawable="@drawable/ic_popup_sync_3"
            android:gravity="left"></clip>
    </item>
    <item android:duration="100">
        <clip xmlns:android="http://schemas.android.com/apk/res/android"
            android:clipOrientation="horizontal"
            android:drawable="@drawable/ic_popup_sync_4"
            android:gravity="left"></clip>
    </item>
    <item android:duration="100">
        <clip xmlns:android="http://schemas.android.com/apk/res/android"
            android:clipOrientation="horizontal"
            android:drawable="@drawable/ic_popup_sync_5"
            android:gravity="left"></clip>
    </item>
    <item android:duration="100">
        <clip xmlns:android="http://schemas.android.com/apk/res/android"
            android:clipOrientation="horizontal"
            android:drawable="@drawable/ic_popup_sync_6"
            android:gravity="left"></clip>
    </item>

</animation-list>


```
然后在自定义一个Dialog，ProgressA
```
public class ProgressA extends Dialog {
    private View mView;
    private ProgressBar mProgressBar;

    public ProgressA(Context context) {
        super(context,android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_a, null);
        mProgressBar=(ProgressBar)mView.findViewById(R.id.progressbar);
        setContentView(mView);
    }
}
```
progress_dialog_a的布局如下：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <ProgressBar
        android:id="@+id/progressbar"
        style="?android:attr/progressBarStyleLarge"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:indeterminate="false"
        android:indeterminateBehavior="repeat"
        android:indeterminateDrawable="@drawable/loading_a"
        android:indeterminateDuration="600" />
</LinearLayout>
```
然后Activity如下：

```
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
}
```
可以发现这些写了之后可以基本上满足常用手机的需求，但是最近我发现在一些手机上会出现如下2个问题：

>1，在Android6.0上此动画和6.0以下在显示上有区别（已解决）；
2，在加载动画的时候中兴V5以及其他某些机型会产生只加载第一张图余下的几张都不加载的情况；


如下图在6.0上无法显示loading图：
![弹出](http://img.blog.csdn.net/20160428155940738)

当然这个问题很容易解决：
我们只需要在ProgressA中加上判断就可以了
```
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
```
其中loading_a_6代码如下：

```
<?xml version="1.0" encoding="utf-8"?>
<animation-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:oneshot="false"
    android:variablePadding="true">
    <item
        android:drawable="@drawable/ic_popup_sync_1"
        android:duration="100"
        android:gravity="center"></item>
    <item
        android:drawable="@drawable/ic_popup_sync_2"
        android:duration="100"
        android:gravity="center"></item>
    <item
        android:drawable="@drawable/ic_popup_sync_3"
        android:duration="100"
        android:gravity="center"></item>
    <item
        android:drawable="@drawable/ic_popup_sync_4"
        android:duration="100"
        android:gravity="center"></item>
    <item
        android:drawable="@drawable/ic_popup_sync_5"
        android:duration="100"
        android:gravity="center"></item>
    <item
        android:drawable="@drawable/ic_popup_sync_6"
        android:duration="100"
        android:gravity="center"></item>
</animation-list>

```

可是第二个问题我找遍所有方法都解决不了，后来我跟踪该机型发现只要是xml布局中的帧动画都无法用，厂商修改源码的时候不知道改了啥，很是蛋疼！
为了解决这个问题，我只能弃用ProgressBar了，改用ImageView，结果完美解决以上2个问题
解决方法：

直接在自定义的Dialog中用ImageView替代ProgressBar来做，换这种方法可以一次解决上面2个问题
```
public class ProgressB extends Dialog {
    private View mView;
    private ImageView iv;
    private AnimationDrawable mAnimation;

    @SuppressLint("NewApi")
    public ProgressB(Context context) {
        super(context, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog);
        mView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_b, null);
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

```
![这里写图片描述](http://img.blog.csdn.net/20160428160926558)

代码地址：https://github.com/hloong/progressbar

总结：因为机型繁多，很多系统方法在一些手机上无法使用或者用起来不是很爽，最后只能自定义的情况还是挺多的，以前写过一篇头像的也是系统方法不好用只能自定义：http://blog.csdn.net/tmacsky/article/details/51179789

