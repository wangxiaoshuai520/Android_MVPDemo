package com.krttech.mymvpdemoup2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.krttech.mymvpdemoup2.base.BaseActivity;
import com.krttech.mymvpdemoup2.base.MVPView;
import com.krttech.mymvpdemoup2.p.MvpPresenter;

public class MainActivity extends BaseActivity implements MVPView{

    private TextView textView;
    private MvpPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_result);
        mvpPresenter = new MvpPresenter();
        mvpPresenter.attachView(this);
    }

    public void button4(View view) {
        mvpPresenter.getData("normal");
    }
    public void button5(View view) {
        mvpPresenter.getData("failure");
    }
    public void button6(View view) {
        mvpPresenter.getData("error");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mvpPresenter.detachView();
    }

    @Override
    public void showData(String data) {
        textView.setText(data);
    }
}
