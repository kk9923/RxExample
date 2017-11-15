package com.xk.rxexample.itemActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xk.rxexample.R;
import com.xk.rxexample.base.ToolbarBaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 2.x 操作符
 * interval 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
 */

public class RxIntervalActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;
    int count = 3;
    @Override
    protected String getSubTitle() {
        return "Interval";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    /**
     * RxJava 2.x 新增Consumer，可自定义实现，accept 里面相当于原本的onNext
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsText.append("开始时间为：  " + count + "\n"+ "\n");

        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/**
 * RxJava 2.x 操作符
 * interval 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
 */
                /**
                 * initialDelay  2     第一次发送事件的延迟时间    2s后才发送事件
                 * period  1           每次发送事件之间的间隔时间
                 * unit  时间单位。
                 */
                Observable.interval(2, 1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
                            Disposable d;
                            @Override
                            public void onSubscribe(Disposable d) {
                                    this.d= d;
                            }
                            @Override
                            public void onNext(Long aLong) {
                                rxOperatorsText.append("结束时间为：  " + count-- + "\n" + "\n");
                                if (count<=-10){
                                    d.dispose();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }

}
