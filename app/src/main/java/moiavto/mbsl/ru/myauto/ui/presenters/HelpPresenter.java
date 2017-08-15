package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.HelpListView;

import javax.inject.Inject;

/**
 * Created by Fedor on 14.07.2017.
 */
@InjectViewState
public class HelpPresenter extends BasePresenter<HelpListView> {
    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;

    public HelpPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        if (isNetworkConnected()) {
            loadData();
        } else
            getViewState().showMsg(R.string.error_no_internet_connection);
    }

    @Override
    public void attachView(HelpListView view) {
        super.attachView(view);

        internetService.updatePing();
    }


    private void loadData() {
        Disposable disposable = dataProvider.getHelpList()
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> {
                    getViewState().startLoading();
                    getViewState().hideEmptyList();
                })
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(helpModels -> {
                            if (helpModels.isEmpty())
                                getViewState().setEmptyList();
                            else
                                getViewState().showData(helpModels);
                        },
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }
}
