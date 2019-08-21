package com.scujcc.videoplayer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

public class QiDong2Activity extends AppCompatActivity {
    private ImageView yuanImageView;
    private ImageView logoImageView;
    private ImageView bgImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_dong2);
        yuanImageView = findViewById(R.id.yuan);
        logoImageView =findViewById(R.id.logo);
        bgImageView=findViewById(R.id.backGroundImg);

        starAnimation();

        Integer time = 4000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(QiDong2Activity.this, MainActivity.class));
                QiDong2Activity.this.finish();
            }
        }, time);
    }

    private void starAnimation(){
        //从右下到左上
        ObjectAnimator yuanAnimator = ObjectAnimator.ofFloat(yuanImageView,"scaleX",
                0,6000).setDuration(1000);
        ObjectAnimator yuanAnimator2 = ObjectAnimator.ofFloat(yuanImageView,"scaleY",
                0,6000).setDuration(1000);
        yuanAnimator.setInterpolator(new AccelerateInterpolator());//加速运动
        yuanAnimator2.setInterpolator(new AccelerateInterpolator());//加速运动
        AnimatorSet animatorSet = new AnimatorSet();
        //背景图片
        ObjectAnimator backGroudAnimator=ObjectAnimator.ofFloat(bgImageView,"alpha",
                0,1).setDuration(1500);
        //logo显示

        ObjectAnimator logoAnimatorAlpha=ObjectAnimator.ofFloat(logoImageView,"alpha",
                0,1).setDuration(2500);
        ObjectAnimator logoAnimatorSclaeX= ObjectAnimator.ofFloat(logoImageView,"scaleX",
                0,3).setDuration(2500);
        ObjectAnimator logoAnimatorSclaeY= ObjectAnimator.ofFloat(logoImageView,"scaleY",
                0,3).setDuration(2500);
        animatorSet
                .play(yuanAnimator)
                .with(yuanAnimator2)
                .before(backGroudAnimator)
                .before(logoAnimatorAlpha)
                .before(logoAnimatorSclaeX)
                .before(logoAnimatorSclaeY);

        animatorSet.start();

    }
}
