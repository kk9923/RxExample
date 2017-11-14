package com.xk.rxexample.itemActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xk.rxexample.R;
import com.xk.rxexample.base.ToolbarBaseActivity;

import java.util.Random;

import butterknife.BindView;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * RxJava 2.x 操作符
 * 顾名思义，Single只会接收一个参数
 * 而SingleObserver只会调用onError或者onSuccess
 */

public class RxSingleActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;

    @Override
    protected String getSubTitle() {
        return "Single";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.just(new Random().nextInt())
                        .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull Integer integer) {
                                rxOperatorsText.append("single : onSuccess : "+integer+"\n");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                rxOperatorsText.append("single : onError : "+e.getMessage()+"\n");
                            }
                        });
            }
        });
    }

}
