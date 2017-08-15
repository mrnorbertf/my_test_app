package moiavto.mbsl.ru.myauto.ui.presenters;

import android.text.TextUtils;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.common.FragmentFactory;
import moiavto.mbsl.ru.myauto.common.Utils.DeviceUtils;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountRegisterDeviceModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.fragments.carWashMapAndListPager.CarWashesPagerFragment;
import moiavto.mbsl.ru.myauto.ui.views.MainView;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * Created by Fedor on 22.06.2017.
 */
@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();

    @Inject
    DataManager dataManager;
    @Inject
    NetworkDataProvider provider;
    @Inject
    CheckInternetService internetService;

    private FragmentFactory factory;
    private String hotStartTag = CarWashesPagerFragment.TAG_FACTORY;
    private Object innerData = null;

    public MainPresenter() {
        MyAutoApp.getAppComponent().inject(this);
        factory = new FragmentFactory();
    }

    public void setStartFragment(String factoryTag, Object innerData) {
        this.hotStartTag = factoryTag;
        this.innerData = innerData;
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);

        if (TextUtils.isEmpty(dataManager.getAppId())) {
            startLogin();
        }
    }

    public void startLogin() {
        getViewState().startUserRegistration();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        if (TextUtils.isEmpty(dataManager.getAppId())) {
            getViewState().startUserRegistration();
        } else {
            sendPushToken();

            getViewState().showCurrentFragment(factory.createFragment(hotStartTag, innerData), hotStartTag);
        }
    }

    private void sendPushToken() {
        internetService.execute(
                () -> {
                    Disposable savePushDis = provider.getAccountRegisterDevice(
                            new AccountRegisterDeviceModel.Builder()
                                    .setPushToken(dataManager.getPushToken())
                                    .setDeviceId(DeviceUtils.getDeviceId())
                                    .setDeviceModel(DeviceUtils.getDeviceName())
                                    .build()
                    ).compose(NetworkUtils.applyCompletableSchedulers())
                            .subscribe(() -> Log.d(TAG, "push send successful"),
                                    this::logError);

                    unsubscribeOnDestroy(savePushDis);
                }, this::logError);
    }

    private void logError(Throwable throwable) {
        throwable.printStackTrace();
        Timber.e(throwable.getLocalizedMessage());
    }

}
