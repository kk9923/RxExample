package com.xk.rxexample.itemActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xk.rxexample.R;
import com.xk.rxexample.base.ToolbarBaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * RxJava 2.x 操作符
 */

public class RxReduceActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;

    @Override
    protected String getSubTitle() {
        return "Reduce";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    /**
     * RxJava 2.x 新增Consumer，可自定义实现，accept 里面相当于原本的onNext
     * 支持一个 function 为两数值相加(其它也可以)，先1+2 =3，然后在3+3 = 6      只不过reduce发送的事件为最终的结果，而Scan 会把每一个运算都作为事件发送
     *   @param seed    the initial (seed) accumulator value    一个初始值
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1, 2, 3)
                        .reduce(100,new BiFunction<Integer, Integer, Integer>() {
                            @Override
                            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                                return integer + integer2;
                            }
                        }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        rxOperatorsText.append("reduce : " + integer + "\n");
                    }
                });
            }
        });
    }

}
