package moiavto.mbsl.ru.myauto.data.mappers;

import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.common.Utils.DateConverter;
import moiavto.mbsl.ru.myauto.data.domainData.BookingCreateDomainModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingCreateRequestModel;

import java.util.Date;

/**
 * Created by Fedor on 14.07.2017.
 */

public class Mappers {

    private DataManager dataManager;

    public Mappers(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public BookingCreateRequestModel bookingMap(BookingCreateDomainModel domain) {
        return new BookingCreateRequestModel(
                domain.getCompanyId(),
                DateConverter.serverDate(new Date(domain.getBookingDate().getTime() + dataManager.getAuthTime())),
                domain.getBookingTime(),
                domain.getCarId(),
                domain.getServicesResult()
        );
    }
}
