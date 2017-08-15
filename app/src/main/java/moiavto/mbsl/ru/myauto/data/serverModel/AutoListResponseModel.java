package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Модель ответа запроса списка автомобилей
 **/
@ApiModel(description = "Модель ответа запроса списка автомобилей")
public class AutoListResponseModel {

    @SerializedName("autoId")
    private Integer autoId = null;
    @SerializedName("number")
    private String number = null;
    @SerializedName("mark")
    private String mark = null;
    @SerializedName("model")
    private String model = null;
    @SerializedName("bodyType")
    private String bodyType = null;
    @SerializedName("color")
    private String color = null;
    @SerializedName("rgb")
    private String rgb = null;
    @SerializedName("year")
    private Integer year = null;

    /**
     * Идентификатор автомобиля
     **/
    @ApiModelProperty(required = true, value = "Идентификатор автомобиля")
    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    /**
     * Номер автомобиля
     **/
    @ApiModelProperty(required = true, value = "Номер автомобиля")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Марка автомобиля
     **/
    @ApiModelProperty(required = true, value = "Марка автомобиля")
    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    /**
     * Модель автомобиля
     **/
    @ApiModelProperty(required = true, value = "Модель автомобиля")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Тип кузова автомобиля
     **/
    @ApiModelProperty(required = true, value = "Тип кузова автомобиля")
    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * Идентификатор цвета автомобиля
     **/
    @ApiModelProperty(required = true, value = "Идентификатор цвета автомобиля")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Цвет автомобиля в формате
     **/
    @ApiModelProperty(required = true, value = "Цвет автомобиля в формате")
    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    /**
     * Год выпуска
     **/
    @ApiModelProperty(value = "Год выпуска")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AutoListResponseModel autoListResponseModel = (AutoListResponseModel) o;
        return (this.autoId == null ? autoListResponseModel.autoId == null : this.autoId.equals(autoListResponseModel.autoId)) &&
                (this.number == null ? autoListResponseModel.number == null : this.number.equals(autoListResponseModel.number)) &&
                (this.mark == null ? autoListResponseModel.mark == null : this.mark.equals(autoListResponseModel.mark)) &&
                (this.model == null ? autoListResponseModel.model == null : this.model.equals(autoListResponseModel.model)) &&
                (this.bodyType == null ? autoListResponseModel.bodyType == null : this.bodyType.equals(autoListResponseModel.bodyType)) &&
                (this.color == null ? autoListResponseModel.color == null : this.color.equals(autoListResponseModel.color)) &&
                (this.rgb == null ? autoListResponseModel.rgb == null : this.rgb.equals(autoListResponseModel.rgb)) &&
                (this.year == null ? autoListResponseModel.year == null : this.year.equals(autoListResponseModel.year));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.autoId == null ? 0 : this.autoId.hashCode());
        result = 31 * result + (this.number == null ? 0 : this.number.hashCode());
        result = 31 * result + (this.mark == null ? 0 : this.mark.hashCode());
        result = 31 * result + (this.model == null ? 0 : this.model.hashCode());
        result = 31 * result + (this.bodyType == null ? 0 : this.bodyType.hashCode());
        result = 31 * result + (this.color == null ? 0 : this.color.hashCode());
        result = 31 * result + (this.rgb == null ? 0 : this.rgb.hashCode());
        result = 31 * result + (this.year == null ? 0 : this.year.hashCode());
        return result;
    }

    public String result(){
        return  mark + " " + model;
    }

    @Override
    public String toString() {
        return "class AutoListResponseModel {\n" +
                "  autoId: " + autoId + "\n" +
                "  number: " + number + "\n" +
                "  mark: " + mark + "\n" +
                "  model: " + model + "\n" +
                "  bodyType: " + bodyType + "\n" +
                "  color: " + color + "\n" +
                "  rgb: " + rgb + "\n" +
                "  year: " + year + "\n" +
                "}\n";
    }
}
