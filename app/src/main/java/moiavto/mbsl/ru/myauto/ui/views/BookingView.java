package moiavto.mbsl.ru.myauto.ui.views;

import moiavto.mbsl.ru.myauto.data.domainData.BookingCreateDomainModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingCarModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingDateModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyServiceModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

import java.util.List;

/**
 * Created by Fedor on 13.07.2017.
 */

public interface BookingView extends BaseView {
    void showDateDialog(List<BookingDateModel> bookingDates);

    void showTimeDialog(List<String> times);

    void showCarDialog(List<BookingCarModel> cars);

    void bookSuccessful();

    void updateServiceList(List<CompanyServiceModel> services);

    void showData(BookingCreateDomainModel resultBooking);

    void updateData(BookingCreateDomainModel resultBooking);
}
