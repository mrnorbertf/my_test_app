package moiavto.mbsl.ru.myauto.data.domainData;

import moiavto.mbsl.ru.myauto.data.serverModel.BookingModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;

/**
 * Created by Fedor on 13.07.2017.
 */

public class BookingDomainModel {
    private BookingModel booking;
    private CompanyModel company;

    public BookingDomainModel(BookingModel booking) {
        this.booking = booking;
        company = bookToCompMapper();
    }

    private CompanyModel bookToCompMapper() {
        return new CompanyModel.Builder()
                .setCompanyId(booking.getCompanyId())
                .setName(booking.getName())
                .setAddress(booking.getAddress())
                .setPhone(booking.getPhone())
                .setLat(booking.getLat())
                .setLng(booking.getLng())
                .setPreviewImageUrl(booking.getPreviewImageUrl())
                .setServices(booking.getServices())
                .build();
    }


    public BookingModel getBooking() {
        return booking;
    }

    public void setBooking(BookingModel booking) {
        this.booking = booking;
    }

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }


}
