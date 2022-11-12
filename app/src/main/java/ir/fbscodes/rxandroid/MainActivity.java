package ir.fbscodes.rxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.Timed;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {
    private Disposable disposable;
    private static final String TAG = "MainActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        createRangeObservable()
//                .timestamp()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Timed<Integer>>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Timed<Integer> s) {
//                        Log.i(TAG, "onNext: "+ s);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.e(TAG, "onError: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG, "onComplete: ");
//                    }
//                });

        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);

        subject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.i(TAG, "onNext: "+ integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        subject.onNext(4);
        subject.onNext(5);
        subject.onNext(6);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private Observable<Integer> createRangeObservable() {
        return Observable.create(emitter -> {
            try {
                for (int i = 0; i < 15; i++) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(i);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }catch (Exception e) {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            }
        });
    }
}