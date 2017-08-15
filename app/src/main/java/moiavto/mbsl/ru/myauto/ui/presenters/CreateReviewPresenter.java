package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.ReviewCreateRequestModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.CreateReviewView;

import javax.inject.Inject;

/**
 * Created by Fedor on 12.07.2017.
 */
@InjectViewState
public class CreateReviewPresenter extends BasePresenter<CreateReviewView> {

    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;

    private CompanyIdModel companyId;

    public CreateReviewPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    @Override
    public void attachView(CreateReviewView view) {
        super.attachView(view);
        internetService.updatePing();
    }

    public void setCompanyId(CompanyIdModel companyId) {
        this.companyId = companyId;
    }

    public void createReview(String text, int rating) {
        ReviewCreateRequestModel body = new ReviewCreateRequestModel(companyId.getCompanyId(), rating, text);

        Disposable createReview = dataProvider.createReview(body)
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doOnSubscribe(__ -> {
                    getViewState().hideEmptyError();
                    getViewState().startLoading();
                })
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(() -> {
                            getViewState().showMsg(R.string.msg_reviews_create_success);
                            getViewState().reviewSendSuccessful();
                        },
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable))
                );
        unsubscribeOnDestroy(createReview);
    }

    public void showEmptyError() {
        getViewState().showEmptyError();
    }
}
