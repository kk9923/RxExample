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
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * debounce
 *
 * 过滤掉发射速率过快的数据项

 */

public class RxDebounceActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;

    @Override
    protected String getSubTitle() {
        return "Distinct";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    /**
     * 去除发送间隔时间小于 500 毫秒的发射事件，所以 1 和 3 被去掉了
     * @param savedInstanceState
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe( ObservableEmitter<Integer> emitter) throws Exception {
                        // send events with simulated time wait
                        emitter.onNext(1); // skip
                        Thread.sleep(400);
                        emitter.onNext(2); // deliver
                        Thread.sleep(505);
                        emitter.onNext(3); // skip
                        Thread.sleep(100);
                        emitter.onNext(4); // deliver
                        Thread.sleep(605);
                        emitter.onNext(5); // deliver
                        Thread.sleep(510);
                        emitter.onComplete();
                    }
                }).debounce(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept( Integer integer) throws Exception {
                                rxOperatorsText.append("debounce :" + integer + "\n");
                            }
                        });
            }
        });
    }
}
