package com.xk.rxexample.itemActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xk.rxexample.R;
import com.xk.rxexample.base.ToolbarBaseActivity;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxConcatActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;

    @Override
    protected String getSubTitle() {
        return "Concat";
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
                Observable<Integer> Observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {   // ObservableEmitter  RxJava2.0  中的事件发送器
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onComplete();
                    }
                });
                Observable<String> Observable2 = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {   // ObservableEmitter  RxJava2.0  中的事件发送器
                        emitter.onNext("first");
                        emitter.onNext("second");
                        emitter.onComplete();
                    }
                });
                Observable.concat(Observable1,Observable2)
                        .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Exception {
                        rxOperatorsText.append("concat : " + s +"\n"+"\n");
                    }
                });
            }
        });
    }

}