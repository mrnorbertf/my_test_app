package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.data.domainData.BookingCreateDomainModel;
import moiavto.mbsl.ru.myauto.data.mappers.Mappers;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingCreateRequestModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingDateModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingPrepareResponseModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.network.CheckInternetService;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.network.NetworkUtils;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.BookingView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Fedor on 13.07.2017.
 */
@InjectViewState
public class BookingPresenter extends BasePresenter<BookingView> {

    @Inject
    CheckInternetService internetService;
    @Inject
    NetworkDataProvider dataProvider;
    @Inject
    DataManager dataManager;

    private CompanyIdModel companyId;
    private BookingPrepareResponseModel booking;
    private BookingCreateDomainModel resultBooking;
    private int error;

    public BookingPresenter() {
        MyAutoApp.getAppComponent().inject(this);
    }

    public void setCompanyId(CompanyIdModel companyId) {
        this.companyId = companyId;
    }

    @Override
    public void attachView(BookingView view) {
        super.attachView(view);
        internetService.updatePing();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        attemptLoadData();
    }

    private void attemptLoadData() {
        if (isNetworkConnected())
            loadData();
        else
            getViewState().showMsg(R.string.error_no_internet_connection);
    }

    private void loadData() {
        error = -1;
        Disposable disposable = dataProvider.getBookingPrepare(companyId)
                .compose(NetworkUtils.applySchedulers())
                .filter(booking -> isListEmpty(booking.getBookingDates(), R.string.msg_no_available_dates))
                .filter(booking -> isListEmpty(booking.getCars(), R.string.msg_no_available_auto))
                .filter(booking -> {
                    Iterator iterator = booking.getBookingDates().iterator();
                    while (iterator.hasNext()) {
                        BookingDateModel date = (BookingDateModel) iterator.next();
                        if (date.getTimes().isEmpty())
                            iterator.remove();
                    }
                    return isListEmpty(booking.getBookingDates(), R.string.msg_no_available_dates);
                })
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> {
                    getViewState().finishLoading();
                    if (error != -1) {
                        getViewState().showMsg(error);
                        getViewState().bookSuccessful();
                    }
                })
                .subscribe(booking -> {
                            this.booking = booking;

                            resultBooking = new BookingCreateDomainModel.Builder()
                                    .setBookingDate(booking.getBookingDates().get(0).getDate())
                                    .setBookingTime(booking.getBookingDates().get(0).getTimes().get(0))
                                    .setCarId(booking.getCars().get(0).getCarId())
                                    .setCarName(booking.getCars().get(0).getName())
                                    .setServicesAvailable(booking.getCars().get(0).getServices())
                                    .setCompanyId(companyId.getCompanyId())
                                    .build();

                            getViewState().showData(resultBooking);
                        },
                        throwable -> {
                            getViewState().showMsg(getThrowableMessage(throwable));
                            getViewState().showMsg("Возникли проблемы с бронированием.");
                            getViewState().bookSuccessful();
                        });
        unsubscribeOnDestroy(disposable);
    }

    private <T> boolean isListEmpty(List<T> list, int availableError) {
        boolean isEmpty = list.isEmpty();
        if (isEmpty)
            this.error = availableError;
        return !isEmpty;
    }

    public void updateDate(Date date) {
        resultBooking.setBookingDate(date);
        getViewState().updateData(resultBooking);
    }

    public void updateTime(String time) {
        resultBooking.setBookingTime(time);
        getViewState().updateData(resultBooking);
    }

    public void updateCar(Integer carId) {
        resultBooking.setCarId(carId);
        resultBooking.setServicesResult(new ArrayList<>());
        getViewState().showData(resultBooking);
    }

    public void showCarDialog() {
        getViewState().showCarDialog(booking.getCars());
    }

    public void showTimeDialog() {
        int pos = findInDateList(booking.getBookingDates());
        if (pos != -1)
            getViewState().showTimeDialog(booking.getBookingDates().get(pos).getTimes());
        else
            getViewState().showMsg("нет свободных дат");
    }

    public void showDateDialog() {
        getViewState().showDateDialog(booking.getBookingDates());
    }

    public void book() {
        Mappers mappers = new Mappers(dataManager);
        BookingCreateRequestModel body = mappers.bookingMap(resultBooking);

        Disposable disposable = dataProvider.bookingCreate(body)
                .compose(NetworkUtils.applySchedulers())
                .doOnSubscribe(__ -> getViewState().startLoading())
                .doFinally(() -> getViewState().finishLoading())
                .subscribe(bookingResponse -> {
                            if (bookingResponse.isSuccess()) {
                                getViewState().showMsg(R.string.msg_booking_is_success);
                                getViewState().bookSuccessful();
                            }

                            if (bookingResponse.isEngaged()) {
                                attemptLoadData();
                                getViewState().showMsg(bookingResponse.getMessage());
                            }
                        },
                        throwable ->
                                getViewState().showMsg(getThrowableMessage(throwable)));
        unsubscribeOnDestroy(disposable);
    }

    private int findInDateList(List<BookingDateModel> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDate().getTime() == resultBooking.getBookingDate().getTime())
                return i;
        }
        return -1;
    }

    public void updateServices(List<Integer> resultIdsList) {
        resultBooking.setServicesResult(resultIdsList);
        getViewState().updateData(resultBooking);
    }
}
