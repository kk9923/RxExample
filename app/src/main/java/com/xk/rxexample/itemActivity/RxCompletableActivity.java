package com.xk.rxexample.itemActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xk.rxexample.R;
import com.xk.rxexample.base.ToolbarBaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 2.x 操作符
 */

public class RxCompletableActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;

    @Override
    protected String getSubTitle() {
        return "Completable";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    /**
     * RxJava 2.x 新增Consumer，可自定义实现，accept 里面相当于原本的onNext
     * 只关心结果，也就是说 Completable 是没有 onNext 的，要么成功要么出错，不关心过程，在 subscribe 后的某个时间点返回结果
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxOperatorsText.append("Completable\n");

                Completable.timer(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                rxOperatorsText.append("onSubscribe : d :" + d.isDisposed() + "\n");
                            }

                            @Override
                            public void onComplete() {
                                rxOperatorsText.append("onComplete\n");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                rxOperatorsText.append("onError :" + e.getMessage() + "\n");
                            }
                        });
            }
        });
    }

}
