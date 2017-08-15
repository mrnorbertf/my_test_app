package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.CompanyDetailView;

import javax.inject.Inject;

/**
 * Created by Fedor on 07.07.2017.
 */
@InjectViewState
public class CompanyDetailPresenter extends BasePresenter<CompanyDetailView> {

    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;

    private CompanyIdModel companyId;
    private CompanyModel companyModel;

    public CompanyDetailPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    public void setCompanyId(CompanyIdModel companyId) {
        this.companyId = companyId;
    }

    @Override
    public void attachView(CompanyDetailView view) {
        super.attachView(view);
        internetService.updatePing();
        isNetworkConnected(this::loadData, getViewState());
    }

    private void loadData() {
        Disposable disposable = dataProvider.getCompany(companyId)
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doOnNext(companyModel -> this.companyModel = companyModel)
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(companyModel -> getViewState().showData(companyModel),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }

    public void toggleFavorite() {
        if (companyModel.getInFavorites())
            removeFromFavorite();
        else
            addToFavorite();

    }

    private void addToFavorite() {
        getViewState().startLoading();
        internetService.execute(
                () -> {
                    Disposable disposable = dataProvider.addToFavorites(companyId)
                            .compose(NetworkUtils.applyCompletableSchedulers())
                            .doOnSubscribe(__ -> getViewState().startLoading())
                            .doFinally(() -> getViewState().finishLoading())
                            .subscribe(() -> {
                                        companyModel.setInFavorites(true);
                                        getViewState().addToFavorite();
                                    },
                                    throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

                    unsubscribeOnDestroy(disposable);
                },
                throwable -> getViewState().showMsg(R.string.error_no_internet_connection)
        );
    }

    private void removeFromFavorite() {
        getViewState().startLoading();
        internetService.execute(
                () -> {
                    Disposable disposable = dataProvider.removeFromFavorites(companyId)
                            .compose(NetworkUtils.applyCompletableSchedulers())
                            .doOnSubscribe(__ -> getViewState().startLoading())
                            .doFinally(() -> getViewState().finishLoading())
                            .subscribe(() -> {
                                        companyModel.setInFavorites(false);
                                        getViewState().removeFromFavorite();
                                    },
                                    throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

                    unsubscribeOnDestroy(disposable);
                },
                throwable -> getViewState().showMsg(R.string.error_no_internet_connection)
        );
    }

    public void enrollService() {
        getViewState().enrollService(companyId);
    }

    public void callToCompany() {
        Disposable disposable = dataProvider.increaseCallsCounter(companyId)
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doFinally(() -> getViewState().callToCompany(companyModel))
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }

    public void navigateToCompany() {
        Disposable disposable = dataProvider.increaseRoutesCounter(companyId)
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doFinally(() -> getViewState().navigateToCompany(companyModel))
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }
}
