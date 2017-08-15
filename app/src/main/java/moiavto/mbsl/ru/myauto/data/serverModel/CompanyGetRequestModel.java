package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Модель идентификатора компании
 **/
@ApiModel(description = "Модель идентификатора компании")
public class CompanyGetRequestModel {

    @SerializedName("companyId")
    private Integer companyId = null;
    @SerializedName("lat")
    private Double lat = null;
    @SerializedName("lng")
    private Double lng = null;

    /**
     * Идентификатор компании
     **/
    @ApiModelProperty(required = true, value = "Идентификатор компании")
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * Широта
     **/
    @ApiModelProperty(value = "Широта")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * Долгота
     **/
    @ApiModelProperty(value = "Долгота")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyGetRequestModel companyGetRequestModel = (CompanyGetRequestModel) o;
        return (this.companyId == null ? companyGetRequestModel.companyId == null : this.companyId.equals(companyGetRequestModel.companyId)) &&
                (this.lat == null ? companyGetRequestModel.lat == null : this.lat.equals(companyGetRequestModel.lat)) &&
                (this.lng == null ? companyGetRequestModel.lng == null : this.lng.equals(companyGetRequestModel.lng));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.companyId == null ? 0 : this.companyId.hashCode());
        result = 31 * result + (this.lat == null ? 0 : this.lat.hashCode());
        result = 31 * result + (this.lng == null ? 0 : this.lng.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "class CompanyGetRequestModel {\n" +
                "  companyId: " + companyId + "\n" +
                "  lat: " + lat + "\n" +
                "  lng: " + lng + "\n" +
                "}\n";
    }
}
