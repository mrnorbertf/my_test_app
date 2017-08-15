package moiavto.mbsl.ru.myauto.data.domainData;

import com.google.gson.annotations.SerializedName;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyServiceModel;

import java.util.Date;
import java.util.List;

/**
 * Created by Fedor on 14.07.2017.
 */

public class BookingCreateDomainModel {
    @SerializedName("companyId")
    private Integer companyId = null;
    @SerializedName("bookingDate")
    private Date bookingDate = null;
    @SerializedName("bookingTime")
    private String bookingTime = null;
    @SerializedName("carId")
    private Integer carId = null;
    @SerializedName("duration")
    private Integer duration = null;
    @SerializedName("sum")
    private Double sum = null;
    @SerializedName("carName")
    private String carName = null;
    @SerializedName("servicesResult")
    private List<Integer> servicesResult = null;
    @SerializedName("servicesAvailable")
    private List<CompanyServiceModel> servicesAvailable = null;

    private BookingCreateDomainModel(Integer companyId, Date bookingDate, String bookingTime, Integer carId,
                                     String carName, List<Integer> servicesResult, List<CompanyServiceModel> servicesAvailable,
                                     Double sum, Integer duration) {
        this.companyId = companyId;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.carId = carId;
        this.carName = carName;
        this.servicesResult = servicesResult;
        this.servicesAvailable = servicesAvailable;
        this.sum = sum;
        this.duration = duration;
    }

    private void updateSumAndDuration() {
        sum = 0d;
        duration = 0;
        for (Integer id : servicesResult) {
            int pos = isServiceContain(id);
            if (pos != -1) {
                sum += servicesAvailable.get(pos).getPrice().intValue();
                duration += servicesAvailable.get(pos).getDuration();
            }
        }
    }

    private int isServiceContain(Integer id) {
        for (int i = 0; i < servicesAvailable.size(); i++) {
            if (servicesAvailable.get(i).equalsById(id))
                return i;
        }
        return -1;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public List<Integer> getServicesResult() {
        return servicesResult;
    }

    public void setServicesResult(List<Integer> servicesResult) {
        this.servicesResult = servicesResult;
        updateSumAndDuration();
    }

    public List<CompanyServiceModel> getServicesAvailable() {
        return servicesAvailable;
    }

    public void setServicesAvailable(List<CompanyServiceModel> servicesAvailable) {
        this.servicesAvailable = servicesAvailable;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.companyId == null ? 0 : this.companyId.hashCode());
        result = 31 * result + (this.bookingDate == null ? 0 : this.bookingDate.hashCode());
        result = 31 * result + (this.bookingTime == null ? 0 : this.bookingTime.hashCode());
        result = 31 * result + (this.carId == null ? 0 : this.carId.hashCode());
        result = 31 * result + (this.servicesResult == null ? 0 : this.servicesResult.hashCode());
        result = 31 * result + (this.carName == null ? 0 : this.carName.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BookingCreateDomainModel {\n");

        sb.append("  companyId: ").append(companyId).append("\n");
        sb.append("  bookingDate: ").append(bookingDate).append("\n");
        sb.append("  bookingTime: ").append(bookingTime).append("\n");
        sb.append("  carId: ").append(carId).append("\n");
        sb.append("  sum: ").append(sum).append("\n");
        sb.append("  duration: ").append(duration).append("\n");
        sb.append("  servicesResult: ").append(servicesResult).append("\n");
        sb.append("  servicesAvailable: ").append(servicesAvailable).append("\n");
        sb.append("  carName: ").append(carName).append("\n");
        sb.append("}\n");
        return sb.toString();
    }


    public static class Builder {
        private Integer companyId = null;
        private Date bookingDate = null;
        private String bookingTime = null;
        private Integer carId = null;
        private String carName = null;
        private Integer duration = 0;
        private Double sum = 0d;
        private List<Integer> servicesResult = null;
        private List<CompanyServiceModel> servicesAvailable = null;

        public Builder setCompanyId(Integer companyId) {
            this.companyId = companyId;
            return this;
        }


        public Builder setBookingDate(Date bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }


        public Builder setBookingTime(String bookingTime) {
            this.bookingTime = bookingTime;
            return this;
        }

        public Builder setCarId(Integer carId) {
            this.carId = carId;
            return this;
        }

        public Builder setCarName(String carName) {
            this.carName = carName;
            return this;
        }

        public Builder setServicesResult(List<Integer> servicesResult) {
            this.servicesResult = servicesResult;
            return this;
        }

        public Builder setServicesAvailable(List<CompanyServiceModel> servicesAvailable) {
            this.servicesAvailable = servicesAvailable;
            return this;
        }

        public Builder setDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder setSum(Double sum) {
            this.sum = sum;
            return this;
        }

        public BookingCreateDomainModel build() {
            return new BookingCreateDomainModel(companyId, bookingDate, bookingTime, carId, carName,
                    servicesResult, servicesAvailable, sum, duration);
        }
    }
}
