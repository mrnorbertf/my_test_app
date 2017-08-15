package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.ReviewsView;

import javax.inject.Inject;

/**
 * Created by Fedor on 11.07.2017.
 */
@InjectViewState
public class ReviewsFragmentPresenter extends BasePresenter<ReviewsView> {

    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;

    private CompanyIdModel companyId;

    public ReviewsFragmentPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    public void setCompanyId(CompanyIdModel companyId) {
        this.companyId = companyId;
    }

    @Override
    public void attachView(ReviewsView view) {
        super.attachView(view);
        internetService.updatePing();
        isNetworkConnected(this::loadData, getViewState());
    }

    private void loadData() {
        Disposable disposable = dataProvider.getReviews(companyId)
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(reviews -> getViewState().showData(reviews),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }


    public void createReview() {
        getViewState().createReview(companyId);
    }
}
