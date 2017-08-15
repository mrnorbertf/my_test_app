package moiavto.mbsl.ru.myauto.ui.presenters;

import android.net.Uri;
import com.arellomobile.mvp.InjectViewState;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.data.domainData.AccountData;
import moiavto.mbsl.ru.myauto.data.domainData.SettingsData;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountInfoModel;
import moiavto.mbsl.ru.myauto.data.serverModel.AutoIdModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.SettingsView;
import timber.log.Timber;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by Fedor on 17.07.2017.
 */
@InjectViewState
public class SettingsPresenter extends BasePresenter<SettingsView> {
    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;
    @Inject
    DataManager dataManager;
    private SettingsData settingsData;

    public SettingsPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    @Override
    public void attachView(SettingsView view) {
        super.attachView(view);

        internetService.updatePing();
        attemptLoadData();
    }

    private void attemptLoadData() {
        Observable<SettingsData> combine = Observable.combineLatest(dataProvider.getAutoList(), dataProvider.getAccountInfo(),
                (list, account) -> new SettingsData(account, list));

        Disposable disposable = combine
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(data -> {
                            settingsData = data;
                            getViewState().showData(data);
                        },
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }


    public void addCar() {
        getViewState().startAddingCar();
    }

    public void editCar(AutoIdModel autoIdModel) {
        getViewState().startEditCar(autoIdModel);
    }

    public void showProfileEditDialog() {
        getViewState().showProfileEditDialog(settingsData.getUser());
    }

    public void showPhotoDialog() {
        getViewState().showPhotoDialog();
    }

    public void editUserData(AccountData user) {
        AccountInfoModel body = settingsData.getUser();
        body.setUsername(user.getUserName());
        body.setPhone(user.getPhone());

        Disposable disposable = dataProvider.editAccountInfo(settingsData.getUser())
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(() -> {
                            settingsData.setUser(body);
                            getViewState().showData(settingsData);
                        },
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }

    public void setServerPhoto(Uri uri) {
        File file = new File(uri.getPath());
        Disposable disposable = dataProvider.uploadAvatar(
                NetworkUtils.prepareFilePart(file)
        )
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(body -> {
                            settingsData.getUser().setAvatarUrl(body.getAvatarUrl());
                            getViewState().showData(settingsData);
                        },
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }

    public void attemptLogout() {
        getViewState().showLogoutMessage();
    }

    public void logout() {
        Disposable logout = dataProvider.logout()
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doOnSubscribe(__ -> {
                    dataManager.logout();
                    getViewState().hideLogoutDialog();
                    getViewState().startLoading();
                })
                .doFinally(() -> {
                    getViewState().startLogin();
                    getViewState().finishLoading();
                })
                .subscribe(() ->
                                Timber.i("user logout was success"),
                        throwable -> {
                            Timber.i("user logout was failed");
                            Timber.e(throwable.getMessage());
                            throwable.printStackTrace();
                        });

        unsubscribeOnDestroy(logout);
    }

    public void hideLogoutDialog() {
        getViewState().hideLogoutDialog();
    }
}
