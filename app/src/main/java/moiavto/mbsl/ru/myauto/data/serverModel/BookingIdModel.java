package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Модель идентификатора бронирования
 **/
@ApiModel(description = "Модель идентификатора бронирования")
public class BookingIdModel {

    @SerializedName("bookingId")
    private Integer bookingId = null;

    public BookingIdModel(Integer bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Идентификатор бронирования
     **/
    @ApiModelProperty(required = true, value = "Идентификатор бронирования")
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookingIdModel bookingIdModel = (BookingIdModel) o;
        return (this.bookingId == null ? bookingIdModel.bookingId == null : this.bookingId.equals(bookingIdModel.bookingId));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.bookingId == null ? 0 : this.bookingId.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "class BookingIdModel {\n" +
                "  bookingId: " + bookingId + "\n" +
                "}\n";
    }
}
