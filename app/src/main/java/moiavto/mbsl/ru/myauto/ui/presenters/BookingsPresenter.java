package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.data.domainData.BookingDomainModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.BookingListView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fedor on 13.07.2017.
 */
@InjectViewState
public class BookingsPresenter extends BasePresenter<BookingListView> {
    private static final int sCountStep = 20;
    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;
    @Inject
    DataManager dataManager;
    private int lastVisiblePosition = 0;

    public BookingsPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        attemptLoadData();
    }

    @Override
    public void attachView(BookingListView view) {
        super.attachView(view);

        internetService.updatePing();
    }

    public void attemptLoadData() {
        if (isNetworkConnected()) {
            loadData();
        } else
            getViewState().showMsg(R.string.error_no_internet_connection);
    }

    private void loadData() {
        Disposable disposable = dataProvider.getBookings()
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> {
                    getViewState().startLoading();
                    getViewState().hideEmptyList();
                })
                .doFinally(() -> getViewState().finishLoading())
                .map(bookingModels -> {
                    List<BookingDomainModel> bookings = new ArrayList<>();
                    for (BookingModel item :
                            bookingModels) {
                        bookings.add(new BookingDomainModel(item));
                    }
                    return bookings;
                })
                .subscribe(this::onLoadingSuccess,
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }

    private void onLoadingSuccess(List<BookingDomainModel> list) {
        lastVisiblePosition += list.size();

        if (lastVisiblePosition != 0 || list.size() != 0) {
            getViewState().setBookingList(list);
        } else
            getViewState().setEmptyList();
    }

    public void refreshOrders() {
        lastVisiblePosition = 0;
        attemptLoadData();
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

    public void dismissBooking(BookingIdModel bookingId) {
        Disposable disposable = dataProvider.cancelBooking(bookingId)
                .compose(NetworkUtils.applyCompletableSchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(() -> {
                            lastVisiblePosition--;
                            getViewState().showMsg(R.string.msg_booking_is_canceled);
                            getViewState().removeElementFromList(bookingId);
                            if (lastVisiblePosition == 0)
                                getViewState().setEmptyList();
                        },
                        throwable -> getViewState().showMsg(getThrowableMessage(throwable)));

        unsubscribeOnDestroy(disposable);
    }
}
