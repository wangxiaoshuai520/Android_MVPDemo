package com.krttech.mymvpdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.krttech.mymvpdemo.base.MVPView;
import com.krttech.mymvpdemo.p.MvpPresenter;

public class MainActivity extends AppCompatActivity implements MVPView {

    private TextView textView;
    private ProgressDialog progressDialog;
    private MvpPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_result);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载请稍后");
        mvpPresenter = new MvpPresenter(this);
    }

    public void button1(View view) {
        mvpPresenter.getData("normal");
    }
    public void button2(View view) {
        mvpPresenter.getData("failure");
    }
    public void button3(View view) {
        mvpPresenter.getData("error");
    }

    @Override
    public void showLoading() {
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showData(String data) {
        textView.setText(data);
    }

    @Override
    public void showFailureMessage(String meg) {
        Toast.makeText(this,meg,Toast.LENGTH_SHORT).show();
        textView.setText(meg);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(this,"网络异常",Toast.LENGTH_SHORT).show();
        textView.setText("网络异常");
    }
}
