package com.mayi.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mayi.view.GradientColorProgressView;
import java.util.Random;

/**
 * Created by Administrator on 2016/6/23 0023.
 *
 * @Author CaiWF
 * @Email 401885064@qq.com
 * @TODO   进度条渐变色
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private GradientColorProgressView progressView;
    private Random random = new Random(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        progressView = (GradientColorProgressView) findViewById(R.id.spring_progress_view);
        progressView.setMaxCount(1000.0f);
        findViewById(R.id.click).setOnClickListener(this);
        findViewById(R.id.max_butt).setOnClickListener(this);
        findViewById(R.id.min_butt).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                progressView.setCurrentCount(random.nextInt(1000));
                textView.setText("最大值：" + progressView.getMaxCount() + "   当前值：" + progressView.getCurrentCount());
                break;
            case R.id.max_butt:
                progressView.setCurrentCount(1000);
                textView.setText("最大值：" + progressView.getMaxCount() + "   当前值：" + progressView.getCurrentCount());
                break;
            case R.id.min_butt:
                progressView.setCurrentCount(1);
                textView.setText("最大值：" + progressView.getMaxCount() + "   当前值：" + progressView.getCurrentCount());
                break;
        }

    }
}