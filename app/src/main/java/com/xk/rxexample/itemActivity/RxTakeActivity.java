package com.xk.rxexample.itemActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xk.rxexample.R;
import com.xk.rxexample.base.ToolbarBaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava 2.x 操作符
 *
 */

public class RxTakeActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;
    private String [] titles = {"1","2","3","4","5"};

    @Override
    protected String getSubTitle() {
        return "Take";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    /**
     * RxJava 2.x 新增Consumer，可自定义实现，accept 里面相当于原本的onNext
     *   * @param count
     *            the maximum number of items to emit    事件的最大发送量
     * @return an Observable that emits only the first {@code count} items emitted by the source ObservableSource, or
     *         all of the items from the source ObservableSource if that ObservableSource emits fewer than {@code count} items
     *       如果事件发射数大于count ，则只能发送count个事件  ，  反之 则都能发送
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsText.append("开始事件个数：  " + 6 + "\n"+ "\n");
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1,2,3,4,5,6)
                        .take(4)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                rxOperatorsText.append("结束事件：  " + integer + "\n"+ "\n");
                            }
                        });
            }
        });
    }

}
