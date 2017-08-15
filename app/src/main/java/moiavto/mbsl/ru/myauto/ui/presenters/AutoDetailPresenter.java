package moiavto.mbsl.ru.myauto.ui.presenters;

import android.text.TextUtils;
import com.arellomobile.mvp.InjectViewState;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.data.domainData.AutoDomainModel;
import moiavto.mbsl.ru.myauto.data.domainData.ChangeableAutoIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.*;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.AutoDetailView;

import javax.inject.Inject;

/**
 * Created by Fedor on 17.07.2017.
 */
@InjectViewState
public class AutoDetailPresenter extends BasePresenter<AutoDetailView> {
    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;

    private ChangeableAutoIdModel autoId;
    private AutoDomainModel auto = new AutoDomainModel();

    public AutoDetailPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }


    public void setAutoId(ChangeableAutoIdModel autoId) {
        this.autoId = autoId;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().updateMenu(autoId.isEdit());
        if (autoId.isEdit()) {
            attemptLoadData();
        } else {
            getViewState().showData(auto);
        }
    }

    @Override
    public void attachView(AutoDetailView view) {
        super.attachView(view);

        internetService.updatePing();
    }

    private void attemptLoadData() {
        if (autoId.isEdit()) {
            Disposable disposable = dataProvider.getAuto(autoId.getAutoIdModel())
                    .compose(NetworkUtils.applySchedulers())
                    .doOnSubscribe(__ -> getViewState().startLoading())
                    .doFinally(() -> getViewState().finishLoading())
                    .subscribe(this::getDetail,
                            throwable -> {
                                getViewState().showMsg(getThrowableMessage(throwable));
                                getViewState().complete();
                            });
            unsubscribeOnDestroy(disposable);
        }
    }

    private void getDetail(AutoModel auto) {
        Observable<AutoDomainModel> combine = Observable.combineLatest(
                dataProvider.getAutoListMarks().flatMap(Observable::fromIterable).filter(mark -> mark.getId().equals(auto.getAutoMarkId())),
                dataProvider.getAutoListModels(new AutoMarkIdModel(auto.getAutoMarkId()))
                        .flatMap(Observable::fromIterable).filter(model -> model.getId().equals(auto.getAutoModelId())),
                dataProvider.getAutoListBodyTypes(new AutoModelIdModel(auto.getAutoModelId()))
                        .flatMap(Observable::fromIterable).filter(body -> body.getId().equals(auto.getAutoBodyTypeId())),
                dataProvider.getAutoListColors()
                        .flatMap(Observable::fromIterable).filter(color -> color.getId().compareTo(auto.getAutoColorId()) == 0),
                (mark, model, body, color) -> {
                    AutoDomainModel domainModel = new AutoDomainModel(auto);
                    domainModel.setAutoMark(mark);
                    domainModel.setAutoModel(model);
                    domainModel.setAutoBody(body);
                    domainModel.setAutoColor(color);
                    return domainModel;
                });

        Disposable disposable = combine
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(autoDomainModel -> {
                            this.auto = autoDomainModel;
                            getViewState().showData(this.auto);
                        },
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }

    public void changeNumber(String autoNumber) {
        if (auto != null) {
            auto.setNumber(autoNumber);
            getViewState().showAutoNumberFieldError(false);
        }
    }

    public void showMarkDialog() {
        Disposable disposable = dataProvider.getAutoListMarks()
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(autoMarkModels -> getViewState().showDialog(autoMarkModels),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));
        unsubscribeOnDestroy(disposable);
    }

    public void showModelDialog() {
        Disposable disposable = dataProvider.getAutoListModels(new AutoMarkIdModel(auto.getAutoMarkId()))
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(autoMarkModels -> getViewState().showDialog(autoMarkModels),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));
        unsubscribeOnDestroy(disposable);
    }

    public void showBodyDialog() {
        Disposable disposable = dataProvider.getAutoListBodyTypes(new AutoModelIdModel(auto.getAutoModelId()))
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(autoMarkModels -> getViewState().showDialog(autoMarkModels),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));
        unsubscribeOnDestroy(disposable);
    }

    public void showColorDialog() {
        Disposable disposable = dataProvider.getAutoListColors()
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(autoMarkModels -> getViewState().showDialog(autoMarkModels),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));
        unsubscribeOnDestroy(disposable);
    }

    public <T> void updateField(T item) {
        if (item instanceof AutoMarkModel) {
            updateMark((AutoMarkModel) item);
            return;
        }
        if (item instanceof AutoModelModel) {
            updateModel((AutoModelModel) item);
            return;
        }
        if (item instanceof AutoBodyTypeModel) {
            updateBodyType((AutoBodyTypeModel) item);
            return;
        }
        if (item instanceof AutoColorModel) {
            updateColor((AutoColorModel) item);
        }
    }

    private void updateColor(AutoColorModel color) {
        auto.setAutoColor(color);
        getViewState().showData(auto);
    }

    private void updateBodyType(AutoBodyTypeModel body) {
        auto.setAutoBody(body);
        getViewState().showData(auto);
    }

    private void updateModel(AutoModelModel model) {
        auto.setAutoModel(model);
        getViewState().showData(auto);
    }

    private void updateMark(AutoMarkModel mark) {
        auto.setAutoMark(mark);
        getViewState().showData(auto);
    }

    public void changeYear(Integer year) {
        auto.setYear(year);
    }

    public void changeAuto() {
        if (autoId.isEdit())
            editAuto();
        else
            addAuto();
    }

    private void addAuto() {
        if (!TextUtils.isEmpty(auto.getNumber()) && auto.getNumber().length() != 0) {
            Disposable disposable = dataProvider.addAuto(auto.convert())
                    .compose(NetworkUtils.applyCompletableSchedulers())
                    .doOnSubscribe(__ -> getViewState().startLoading())
                    .doFinally(() -> getViewState().finishLoading())
                    .subscribe(() -> getViewState().complete(),
                            throwable -> getViewState().showMsg(getThrowableMessage(throwable)));
            unsubscribeOnDestroy(disposable);
        } else {
            getViewState().showAutoNumberFieldError(true);
        }
    }

    private void editAuto() {
        Disposable disposable = dataProvider.editAuto(auto.convert())
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(() -> getViewState().complete(),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));
        unsubscribeOnDestroy(disposable);
    }

    public void deleteAuto() {
        if (autoId.isEdit()) {
            Disposable disposable = dataProvider.deleteAuto(autoId.getAutoIdModel())
                    .compose(NetworkUtils.applyCompletableSchedulers())
                    .doOnSubscribe(__ -> getViewState().startLoading())
                    .doFinally(() -> getViewState().finishLoading())
                    .subscribe(() -> getViewState().complete(),
                            throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

            unsubscribeOnDestroy(disposable);
        } else {
            getViewState().complete();
        }
    }
}
