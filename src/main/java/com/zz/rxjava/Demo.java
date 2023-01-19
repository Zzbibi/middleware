package com.zz.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @Author zhangzhen
 * @create 2023/1/19 16:20
 */
public class Demo {

    public static void main(String[] args) {
        Observable<String> observable = Observable.just("Hello world!");
        //observable.subscribe(System.out::println);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String o) {
                System.out.println(o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

}
