package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Модель даты, доступной для бронирования
 **/
@ApiModel(description = "Модель даты, доступной для бронирования")
public class BookingDateModel {

    @SerializedName("date")
    private Date date = null;
    @SerializedName("times")
    private List<String> times = null;

    /**
     * Доступная для записи дата
     **/
    @ApiModelProperty(required = true, value = "Доступная для записи дата")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Массив строк времени, доступных для записи в формате \"HH:MM\"
     **/
    @ApiModelProperty(required = true, value = "Массив строк времени, доступных для записи в формате \"HH:MM\"")
    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookingDateModel bookingDateModel = (BookingDateModel) o;
        return (this.date == null ? bookingDateModel.date == null : this.date.equals(bookingDateModel.date)) &&
                (this.times == null ? bookingDateModel.times == null : this.times.equals(bookingDateModel.times));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.date == null ? 0 : this.date.hashCode());
        result = 31 * result + (this.times == null ? 0 : this.times.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "class BookingDateModel {\n" +
                "  date: " + date + "\n" +
                "  times: " + times + "\n" +
                "}\n";
    }
}
