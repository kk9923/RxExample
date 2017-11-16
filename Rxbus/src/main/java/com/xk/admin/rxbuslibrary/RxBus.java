package com.xk.admin.rxbuslibrary;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by admin on 2017/11/16.
 */

public class RxBus {
    private static volatile RxBus mDefaultInstance;
  //  private final Subject<Object, Object> mBus;

    private final Map<Class<?>, Object> mStickyEventMap;
    private final CompositeDisposable disposables ;

    private PublishSubject<Object> mBus ;

    public RxBus() {
       // mBus = new SerializedSubject<>(PublishSubject.create());
        mBus = PublishSubject.create();
        mStickyEventMap = new ConcurrentHashMap<>();
        disposables = new CompositeDisposable();
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }
    /**
     * 发送事件
     */
    public void post(Object event) {
        mBus.onNext(event);
    }
    public Observable<Object> toObservable() {
        return mBus.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
