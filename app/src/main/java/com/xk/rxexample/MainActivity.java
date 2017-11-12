package com.xk.rxexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv);
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.e(TAG, "emit 1");
//                emitter.onNext(1);
//                Log.e(TAG, "emit 2");
//                emitter.onNext(2);
//                Log.e(TAG, "emit 3");
//                emitter.onNext(3);
//                Log.e(TAG, "emit complete");
//                emitter.onComplete();
//                Log.e(TAG, "emit 4");
//                emitter.onNext(4);
//            }
//        }).map(new Function<Integer,String>() {
//            @Override
//            public String apply(@NonNull Integer integer) throws Exception {
//                return integer*10+"";
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                System.out.println(s);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(@NonNull Throwable throwable) throws Exception {
//            }
//        });
        // 学习RxJava操作符  map  和  flatMap  的基本使用  以及在使用flatMap嵌套网络请求时第一层请求失败的处理 doOnError()方法
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
            // 学习RxJava操作符  map  和  flatMap  的基本使用
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                if (integer ==2 ){
                    throw new NullPointerException("dssd");
                }
            }
        }).doOnError(new Consumer<Throwable>() {      //
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println(throwable.toString());
            }
        })
                .concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
              //  return Observable.just(integer* 10+"").delay(1, TimeUnit.SECONDS);
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10,TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("throwable=        "+throwable.toString());

            }
        });
//                .subscribe(new Observer<Integer>() {
//            private Disposable mDisposable;
//            private int i;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG, "subscribe");
//                mDisposable = d;
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.e(TAG, "onNext: " + value);
//                i++;
//                if (i == 2) {
//                    Log.e(TAG, "dispose");
//                    mDisposable.dispose();
//                    Log.e(TAG, "isDisposed : " + mDisposable.isDisposed());
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "error");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "complete");
//            }
//        });
}}
