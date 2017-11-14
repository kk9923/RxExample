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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxZipActivity extends ToolbarBaseActivity {
    @BindView(R.id.rx_operators_btn)
    Button rxOperatorsBtn;
    @BindView(R.id.rx_operators_text)
    TextView rxOperatorsText;
    private Disposable d;

    @Override
    protected String getSubTitle() {
        return "Zip";
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_rx_operator_base;
    }

    /**
     *  * zip
     * <p>
     * 合并事件专用
     * <p>
     * 分别从两个上游事件中各取出一个组合
     * 一个事件只能被使用一次，顺序严格按照事件发送的顺序
     * 最终下游事件收到的是和上游事件最少的数目相同（必须两两配对，多余的舍弃)
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        rxOperatorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Integer> integerObservable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(6);
                        emitter.onNext(4);
                        emitter.onComplete();
                    }
                });

                Observable<String> stringObservable2 = Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("I am value first");
                        emitter.onNext("I am value second");
                        emitter.onNext("I am value three");
                        emitter.onComplete();
                    }
                });
                Disposable subscribe = Observable.zip(integerObservable1, stringObservable2, new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        //   将  stringObservable2 和  integerObservable1  发送的事件两者合并，处理后最终得到我们想要的结果   一一对应， 多的事件舍弃
                        return s + "      --     " + integer;
                    }
                }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            rxOperatorsText.append("accept : " + s + "\n");
                        }
                    });

            }
        });
    }

}