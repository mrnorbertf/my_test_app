package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountConfirmRequestModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.SignInVerifyView;
import timber.log.Timber;

import javax.inject.Inject;

/**
 * Created by Fedor on 28.06.2017.
 */
@InjectViewState
public class SignInVerifyPresenter extends BasePresenter<SignInVerifyView> {
    private static final String TAG = SignInVerifyPresenter.class.getSimpleName();

    @Inject
    NetworkDataProvider provider;
    @Inject
    CheckInternetService internetService;

    public SignInVerifyPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    public void verifyLogin(String verifyCode) {
        if (verifyCode.length() == 4) {
            internetService.execute(
                    () -> {
                        Disposable disposable = provider.getAccountRevalidate(new AccountConfirmRequestModel(verifyCode))
                                .compose(NetworkUtils.applyCompletableSchedulers())
                                .doFinally(() -> getViewState().finishLoading())
                                .subscribe(() -> getViewState().finishAuth(),
                                        throwable -> {
                                            Timber.e("getAccountRevalidate: ", throwable);
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
}
