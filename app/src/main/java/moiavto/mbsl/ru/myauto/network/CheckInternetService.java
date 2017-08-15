package moiavto.mbsl.ru.myauto.network;

import android.support.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.common.DataManager;
import timber.log.Timber;

/**
 * Created by Fedor on 27.06.2017.
 */

public class CheckInternetService {
    private static final String TAG = CheckInternetService.class.getSimpleName();

    private DataManager dataManager;
    private NetworkDataProvider provider;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CheckInternetService(NetworkDataProvider dataProvider, DataManager dataManager) {
        this.provider = dataProvider;
        this.dataManager = dataManager;
    }

    public void execute(ServerConnectionSuccessListener successListener,
                        ServerConnectionFailureListener failureListener) {
        Disposable subscription = provider.getAppPing()
                .compose(NetworkUtils.applySchedulers())
                .subscribe(appPingModel -> {
                            dataManager.saveAuthTime(NetworkUtils.calculatePing(appPingModel.getServerTime().getTime()));
                            successListener.internetConnectionSuccess();
                        },
                        failureListener::internetConnectionFailure);

        unsubscribeOnDestroy(subscription);
    }

    public void updatePing() {
        Disposable subscription = provider.getAppPing()
                .compose(NetworkUtils.applySchedulers())
                .subscribe(appPingModel -> {
                    dataManager.saveAuthTime(NetworkUtils.calculatePing(appPingModel.getServerTime().getTime()));
                }, Timber::e);

        unsubscribeOnDestroy(subscription);
    }

    protected void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }


    @FunctionalInterface
    public interface ServerConnectionFailureListener {
        void internetConnectionFailure(Throwable throwable);
    }

    @FunctionalInterface
    public interface ServerConnectionSuccessListener {
        void internetConnectionSuccess();
    }
}
