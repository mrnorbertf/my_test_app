package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.data.domainData.AccountCreateRequestDomainModel;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountCreateRequestModel;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountCreateResponseModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.SignInView;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * Created by Fedor on 26.06.2017.
 */
@InjectViewState
public class SignInPresenter extends BasePresenter<SignInView> {
    private static final String TAG = SignInPresenter.class.getSimpleName();

    @Inject
    NetworkDataProvider provider;
    @Inject
    CheckInternetService internetService;
    @Inject
    DataManager dataManager;

    public SignInPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    public void attemptLogin(AccountCreateRequestDomainModel requestUser) {
        AccountCreateRequestModel requestModel = requestUser.convert();
        if (requestModel.getPhone().length() == 11) {
            getViewState().showFieldError(null);

            getViewState().startLoading();
            internetService.execute(
                    () -> {
                        Disposable disposable = provider.getAccountCreate(requestModel)
                                .compose(NetworkUtils.applySchedulers())
                                .doFinally(() -> getViewState().finishLoading())
                                .subscribe(accountCreateResponseModel -> {
                                    saveUserCredential(accountCreateResponseModel);
                                    getViewState().startSignInVerify();
                                }, throwable -> {
                                    Timber.e(throwable);
                                    getViewState().showMsg(getThrowableMessage(throwable));
                                });

                        unsubscribeOnDestroy(disposable);
                    },
                    throwable -> {
                        getViewState().showMsg(getThrowableMessage(throwable));
                        getViewState().finishLoading();
                    }
            );
        } else {
            getViewState().showFieldError(R.string.msg_this_field_is_required);
        }
    }

    private void saveUserCredential(AccountCreateResponseModel credential) {
        dataManager.saveAppId(credential.getAppId());
        dataManager.saveAppSecret(credential.getAppSecret());
    }
}
