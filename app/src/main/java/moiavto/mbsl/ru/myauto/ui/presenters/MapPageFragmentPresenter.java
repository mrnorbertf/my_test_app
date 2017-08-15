package moiavto.mbsl.ru.myauto.ui.presenters;

import android.location.Location;
import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.google.android.gms.maps.model.VisibleRegion;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyListRequestModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.MapPageView;

import javax.inject.Inject;

/**
 * Created by Fedor on 28.06.2017.
 */
@InjectViewState
public class MapPageFragmentPresenter extends BasePresenter<MapPageView> {

    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;
    @Inject
    DataManager dataManager;

    private Location location;
    private boolean isFirstViewAttached;
    private VisibleRegion oldVisibleRegion;

    public MapPageFragmentPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        isFirstViewAttached = true;
    }

    @Override
    public void attachView(MapPageView view) {
        super.attachView(view);
        internetService.updatePing();
    }

    public void loadData(VisibleRegion visibleRegion) {
        if (isFirstViewAttached || isVisibleAreaChanged(visibleRegion)) {
            oldVisibleRegion = visibleRegion;
            if (isNetworkConnected()) {
                loadData(getRequestModel(visibleRegion));
            } else
                getViewState().showMsg(R.string.error_no_internet_connection);
        }

        isFirstViewAttached = false;
    }

    private boolean isVisibleAreaChanged(VisibleRegion visibleRegion) {
        return this.oldVisibleRegion == null ||
                (Math.abs(oldVisibleRegion.nearLeft.latitude - visibleRegion.nearLeft.latitude) > 0.5) ||
                (Math.abs(oldVisibleRegion.nearLeft.longitude - visibleRegion.nearLeft.longitude) > 0.5) ||
                (Math.abs(oldVisibleRegion.nearRight.latitude - visibleRegion.nearRight.latitude) > 0.5) ||
                (Math.abs(oldVisibleRegion.nearRight.longitude - visibleRegion.nearRight.longitude) > 0.5) ||
                (Math.abs(oldVisibleRegion.farLeft.latitude - visibleRegion.farLeft.latitude) > 0.5) ||
                (Math.abs(oldVisibleRegion.farLeft.longitude - visibleRegion.farLeft.longitude) > 0.5) ||
                (Math.abs(oldVisibleRegion.farRight.latitude - visibleRegion.farRight.latitude) > 0.5) ||
                (Math.abs(oldVisibleRegion.farRight.longitude - visibleRegion.farRight.longitude) > 0.5);
    }

    @NonNull
    private CompanyListRequestModel getRequestModel(VisibleRegion visibleRegion) {
        CompanyListRequestModel requestModel = dataManager.getDataClass(CompanyListRequestModel.class);
        if (requestModel == null)
            requestModel = new CompanyListRequestModel();

        if (location != null) {
            requestModel.setLat(location.getLatitude());
            requestModel.setLng(location.getLongitude());
        }
        if (visibleRegion != null) {
            requestModel.setMaxLat(visibleRegion.farRight.latitude);
            requestModel.setMinLat(visibleRegion.nearLeft.latitude);
            requestModel.setMaxLng(visibleRegion.farRight.longitude);
            requestModel.setMinLng(visibleRegion.nearLeft.longitude);
        }
        return requestModel;
    }

    private void loadData(CompanyListRequestModel companyListRequestModel) {
        Disposable disposable = dataProvider.getCompanyList(companyListRequestModel)
                .compose(NetworkUtils.applySchedulers())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(companyModels -> getViewState().addMarkersOnMap(companyModels),
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }

    public void setLocation(Location location) {
        this.location = location;
        if (isFirstViewAttached && location != null) {
            getViewState().showUserLocation(location);
        }
    }
}
