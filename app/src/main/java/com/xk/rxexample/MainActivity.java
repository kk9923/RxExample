package com.xk.rxexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xk.admin.rxbuslibrary.RxBus;
import com.xk.rxexample.itemActivity.RxBusActivity;
import com.xk.rxexample.itemActivity.RxCompletableActivity;
import com.xk.rxexample.itemActivity.RxConcatActivity;
import com.xk.rxexample.itemActivity.RxConcatMapActivity;
import com.xk.rxexample.itemActivity.RxCreateActivity;
import com.xk.rxexample.itemActivity.RxDebounceActivity;
import com.xk.rxexample.itemActivity.RxDistinctActivity;
import com.xk.rxexample.itemActivity.RxDoOnNextActivity;
import com.xk.rxexample.itemActivity.RxFilterActivity;
import com.xk.rxexample.itemActivity.RxFlatMapActivity;
import com.xk.rxexample.itemActivity.RxIntervalActivity;
import com.xk.rxexample.itemActivity.RxJustActivity;
import com.xk.rxexample.itemActivity.RxLastActivity;
import com.xk.rxexample.itemActivity.RxMapActivity;
import com.xk.rxexample.itemActivity.RxMergeActivity;
import com.xk.rxexample.itemActivity.RxReduceActivity;
import com.xk.rxexample.itemActivity.RxScanActivity;
import com.xk.rxexample.itemActivity.RxSingleActivity;
import com.xk.rxexample.itemActivity.RxSkipActivity;
import com.xk.rxexample.itemActivity.RxTakeActivity;
import com.xk.rxexample.itemActivity.RxTimerActivity;
import com.xk.rxexample.itemActivity.RxZipActivity;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;
    private  ArrayList<OperatorModel> data ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        fillData();
        OperatorsAdapter adapter = new OperatorsAdapter(data) {
            @Override
            public void onItemClick(int position) {
                itemClick(position);
            }
        };
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
       // mRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
      //  mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        RxBus.getDefault().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println(throwable.toString());
            }
        });
}
    protected void fillData() {
        data = new ArrayList<>();
        data.add(new OperatorModel(getString(R.string.rx_create),"可用于获取一个被观察的对象"));
        data.add(new OperatorModel(getString(R.string.rx_map),"基本是RxJava 最简单的操作符了作用是对上游发送的每一个事件应用一个函数，" +
                "使得每一个事件都按照指定的函数去变化"));
        data.add(new OperatorModel(getString(R.string.rx_flatMap),"FlatMap将一个发送事件的上游Observable变换成多个发送事件的Observables， 然后将它们发射的时间合并后放进一个单独的Observable里"));
        data.add(new OperatorModel(getString(R.string.rx_concatMap),"concatMap作用和flatMap几乎一模一样，唯一的区别是它能保证事件的顺序"));
        data.add(new OperatorModel(getString(R.string.rx_zip),"合并事件专用," +
                "分别从两个上游事件中各取出一个组合," +
                "一个事件只能被使用一次，顺序严格按照事件发送的顺序," +
                "最终下游事件收到的是和上游事件最少的数目相同（必须两两配对，多余的舍弃)"));
        data.add(new OperatorModel(getString(R.string.rx_doOnNext),"让订阅者在接收到数据前干点事情的操作符"));
        data.add(new OperatorModel(getString(R.string.rx_filter),"过滤操作符，取正确的值"));
        data.add(new OperatorModel(getString(R.string.rx_skip),"接受一个long型参数，代表跳过多少个数目的事件再开始接收"));
        data.add(new OperatorModel(getString(R.string.rx_take),"用于指定订阅者最多收到多少数据"));
        data.add(new OperatorModel(getString(R.string.rx_timer),"在Rxjava中timer 操作符既可以延迟执行一段逻辑，也可以间隔执行一段逻辑\n" +
                "【注意】但在RxJava 2.x已经过时了，现在用interval操作符来间隔执行，详见RxIntervalActivity\n" +
                "timer和interval都默认执行在一个新线程上。"));
        data.add(new OperatorModel(getString(R.string.rx_interval),"间隔执行操作，默认在新线程"));
        data.add(new OperatorModel(getString(R.string.rx_just),"just操作符，和RxJava 1.x 没有什么区别，就是接受一个可变参数，依次发送"));
        data.add(new OperatorModel(getString(R.string.rx_single),"顾名思义，Single只会接收一个参数" +
                "而SingleObserver只会调用onError或者onSuccess"));
        data.add(new OperatorModel(getString(R.string.rx_concat),"连接操作符，可接受Observable的可变参数，或者Observable的集合,Observable.concat只接受相同泛型的参数"));
        data.add(new OperatorModel(getString(R.string.rx_distinct),"去重操作符，其实就是简单的去重"));
        data.add(new OperatorModel(getString(R.string.rx_buffer),"buffer(count, skip)` 从定义就差不多能看出作用了，将 observable 中的数据按 skip（步长）分成最长不超过 count 的 buffer，然后生成一个 observable"));
        data.add(new OperatorModel(getString(R.string.rx_debounce),"过滤掉发射速率过快的数据项"));
        data.add(new OperatorModel(getString(R.string.rx_defer),"就是在每次订阅的时候就会创建一个新的 Observable"));
        data.add(new OperatorModel(getString(R.string.rx_last),"取出最后一个值，参数是没有值的时候的默认值"));
        data.add(new OperatorModel(getString(R.string.rx_merge),"将多个Observable合起来，接受可变参数，也支持使用迭代器集合"));
        data.add(new OperatorModel(getString(R.string.rx_reduce),"就是一次用一个方法处理一个值，可以有一个seed作为初始值,只会将最终结果作为事件发送"));
        data.add(new OperatorModel(getString(R.string.rx_scan),"和上面的reduce差不多，区别在于reduce()只输出结果，而scan()会将过程中每一个结果作为事件发送"));
        data.add(new OperatorModel(getString(R.string.rx_window),"按照时间划分窗口，将数据发送给不同的Observable"));
        data.add(new OperatorModel(getString(R.string.rx_PublishSubject),"onNext() 会通知每个观察者，仅此而已"));
        data.add(new OperatorModel(getString(R.string.rx_AsyncSubject),"在调用 onComplete() 之前，除了 subscribe() 其它的操作都会被缓存，在调用 onComplete() 之后只有最后一个 onNext() 会生效"));
        data.add(new OperatorModel(getString(R.string.rx_BehaviorSubject),"BehaviorSubject 的最后一次 onNext() 操作会被缓存，然后在 subscribe() 后立刻推给新注册的 Observer"));
        data.add(new OperatorModel(getString(R.string.rx_Completable),"只关心结果，也就是说 Completable 是没有 onNext 的，要么成功要么出错，不关心过程，在 subscribe 后的某个时间点返回结果"));
        data.add(new OperatorModel(getString(R.string.rx_Flowable),"专用于解决背压问题"));
        data.add(new OperatorModel(getString(R.string.rx_RxBus),"简单版本RxBus"));
    }
    protected void itemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, RxCreateActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, RxMapActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, RxFlatMapActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, RxConcatMapActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, RxZipActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, RxDoOnNextActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, RxFilterActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, RxSkipActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, RxTakeActivity.class));
                break;
            case 9:
               startActivity(new Intent(this, RxTimerActivity.class));
                break;
            case 10:
               startActivity(new Intent(this, RxIntervalActivity.class));
                break;
            case 11:
                startActivity(new Intent(this, RxJustActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, RxSingleActivity.class));
                break;
            case 13:
                startActivity(new Intent(this, RxConcatActivity.class));
                break;
            case 14:
               startActivity(new Intent(this, RxDistinctActivity.class));
                break;
            case 15:
              //  startActivity(new Intent(getActivity(), RxBufferActivity.class));
                break;
            case 16:
                startActivity(new Intent(this, RxDebounceActivity.class));
                break;
            case 17:
               // startActivity(new Intent(getActivity(), RxDeferActivity.class));
                break;
            case 18:
                startActivity(new Intent(this, RxLastActivity.class));
                break;
            case 19:
              startActivity(new Intent(this, RxMergeActivity.class));
                break;
            case 20:
                startActivity(new Intent(this, RxReduceActivity.class));
                break;
            case 21:
               startActivity(new Intent(this, RxScanActivity.class));
                break;
            case 22:
              //  startActivity(new Intent(getActivity(), RxWindowActivity.class));
                break;
            case 23:
              //  startActivity(new Intent(getActivity(), RxPublishSubjectActivity.class));
                break;
            case 24:
              //  startActivity(new Intent(getActivity(), RxAsyncSubjectActivity.class));
                break;
            case 25:
             //   startActivity(new Intent(getActivity(), RxBehaviorSubjectActivity.class));
                break;
            case 26:
                startActivity(new Intent(this, RxCompletableActivity.class));
                break;
            case 27:
               // startActivity(new Intent(getActivity(), RxFlowableActivity.class));
                break;
               case 28:
                startActivity(new Intent(this, RxBusActivity.class));
                break;
        }
    }

}
