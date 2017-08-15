package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyListRequestModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.ListView;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Fedor on 12.07.2017.
 */
@InjectViewState
public class FavoritesPresenter extends BasePresenter<ListView> {
    private static final int sCountStep = 20;
    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;
    @Inject
    DataManager dataManager;
    private int lastVisiblePosition = 0;

    public FavoritesPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        loadData();
    }

    @Override
    public void attachView(ListView view) {
        super.attachView(view);

        internetService.updatePing();
    }

    public void loadData() {
        if (isNetworkConnected()) {
            CompanyListRequestModel favoritesRequest = new CompanyListRequestModel(true);

            loadData(favoritesRequest);
        } else
            getViewState().showMsg(R.string.error_no_internet_connection);
    }

    private void loadData(CompanyListRequestModel companyListRequestModel) {
        Disposable disposable = dataProvider.getCompanyList(companyListRequestModel)
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> {
                    getViewState().startLoading();
                    getViewState().hideEmptyList();
                })
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(this::onLoadingSuccess,
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }

    private void onLoadingSuccess(List<CompanyModel> list) {
        lastVisiblePosition += list.size();

        if (lastVisiblePosition != 0 || list.size() != 0) {
            getViewState().setCompanyList(list);
        } else
            getViewState().setEmptyList();
    }

    public void refreshOrders() {
        lastVisiblePosition = 0;
        loadData();
    }

    public void callToCompany(CompanyModel company) {
        Disposable disposable = dataProvider.increaseCallsCounter(new CompanyIdModel(company.getCompanyId()))
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doFinally(() -> getViewState().callToCompany(company))
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }

    public void navigateToCompany(CompanyModel company) {
        Disposable disposable = dataProvider.increaseRoutesCounter(new CompanyIdModel(company.getCompanyId()))
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doFinally(() -> getViewState().navigateToCompany(company))
                .subscribe();

        unsubscribeOnDestroy(disposable);
    }
}
