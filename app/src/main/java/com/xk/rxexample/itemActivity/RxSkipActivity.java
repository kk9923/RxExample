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
 * skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
 */

public class RxSkipActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;
    private String [] titles = {"1","2","3","4","5"};

    @Override
    protected String getSubTitle() {
        return "Skip";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    /**
     * RxJava 2.x 新增Consumer，可自定义实现，accept 里面相当于原本的onNext
     * 代表跳过 skip(count) 个数目开始接收。   跳过2个事件流开始接受事件
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsText.append("开始事件个数：  " + 6 + "\n"+ "\n");
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1,2,3,4,5,6)
                        .skip(2)
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
