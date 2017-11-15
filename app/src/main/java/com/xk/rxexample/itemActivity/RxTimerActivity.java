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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 2.x 操作符
 */

public class RxTimerActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;

    @Override
    protected String getSubTitle() {
        return "Timer";
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
        rxOperatorsText.append("开始时间为：  " + System.currentTimeMillis() + "\n"+ "\n");
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 相当于一个定时任务， 在delay 时间之后才会发送事件
                 *  在2s之后才会发送事件
                 */
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                rxOperatorsText.append("结束时间为：  " + System.currentTimeMillis() + "\n"+ "\n");
                            }
                        });
            }
        });
    }

}
