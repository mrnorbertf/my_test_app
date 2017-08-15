package moiavto.mbsl.ru.myauto.data.domainData;

import com.google.gson.annotations.SerializedName;
import moiavto.mbsl.ru.myauto.data.serverModel.*;

/**
 * Created by Fedor on 18.07.2017.
 */

public class AutoDomainModel {
    @SerializedName("autoId")
    private Integer autoId = null;
    @SerializedName("number")
    private String number = null;
    @SerializedName("autoMarkId")
    private Integer autoMarkId = null;
    @SerializedName("autoMarkName")
    private String autoMarkName = null;
    @SerializedName("autoModelId")
    private Integer autoModelId = null;
    @SerializedName("autoModelName")
    private String autoModelName = null;
    @SerializedName("autoBodyTypeId")
    private Integer autoBodyTypeId = null;
    @SerializedName("autoBodyTypeName")
    private String autoBodyTypeName = null;
    @SerializedName("autoColorId")
    private Integer autoColorId = null;
    @SerializedName("autoColorName")
    private String autoColorName = null;
    @SerializedName("year")
    private Integer year = null;

    public AutoDomainModel() {
    }

    public AutoDomainModel(moiavto.mbsl.ru.myauto.data.serverModel.AutoModel serverModel) {
        this.autoId = serverModel.getAutoId();
        this.number = serverModel.getNumber();
        this.autoMarkId = serverModel.getAutoMarkId();
        this.autoModelId = serverModel.getAutoModelId();
        this.autoBodyTypeId = serverModel.getAutoBodyTypeId();
        this.autoColorId = serverModel.getAutoColorId();
        this.year = serverModel.getYear();
    }

    public Integer getAutoId() {
        return autoId;
    }

    public void setAutoId(Integer autoId) {
        this.autoId = autoId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getAutoModelId() {
        return autoModelId;
    }

    public void setAutoModelId(Integer autoModelId) {
        this.autoModelId = autoModelId;
    }

    public Integer getAutoMarkId() {
        return autoMarkId;
    }

    public void setAutoMarkId(Integer autoMarkId) {
        this.autoMarkId = autoMarkId;
    }

    public String getAutoMarkName() {
        return autoMarkName;
    }

    public void setAutoMarkName(String autoMarkName) {
        this.autoMarkName = autoMarkName;
    }

    public String getAutoModelName() {
        return autoModelName;
    }

    public void setAutoModelName(String autoModelName) {
        this.autoModelName = autoModelName;
    }

    public Integer getAutoBodyTypeId() {
        return autoBodyTypeId;
    }

    public void setAutoBodyTypeId(Integer autoBodyTypeId) {
        this.autoBodyTypeId = autoBodyTypeId;
    }

    public String getAutoBodyTypeName() {
        return autoBodyTypeName;
    }

    public void setAutoBodyTypeName(String autoBodyTypeName) {
        this.autoBodyTypeName = autoBodyTypeName;
    }

    public Integer getAutoColorId() {
        return autoColorId;
    }

    public void setAutoColorId(Integer autoColorId) {
        this.autoColorId = autoColorId;
    }

    public String getAutoColorName() {
        return autoColorName;
    }

    public void setAutoColorName(String autoColorName) {
        this.autoColorName = autoColorName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public void setAutoMark(AutoMarkModel mark) {
        this.autoMarkId = mark.getAutoMarkId();
        this.autoMarkName = mark.getName();
        this.autoModelId = null;
        this.autoModelName = null;
        this.autoBodyTypeId = null;
        this.autoBodyTypeName = null;
        this.autoColorId = null;
        this.autoColorName = null;
    }

    public void setAutoModel(AutoModelModel autoModel) {
        this.autoModelId = autoModel.getAutoModelId();
        this.autoModelName = autoModel.getName();
        this.autoBodyTypeId = null;
        this.autoBodyTypeName = null;
        this.autoColorId = null;
        this.autoColorName = null;
    }

    public void setAutoBody(AutoBodyTypeModel body) {
        this.autoBodyTypeId = body.getAutoBodyTypeId();
        this.autoBodyTypeName = body.getName();
        this.autoColorId = null;
        this.autoColorName = null;
    }

    public void setAutoColor(AutoColorModel color) {
        this.autoColorId = color.getAutoColorId();
        this.autoColorName = color.getName();
    }

    public AutoModel convert() {
        return new AutoModel(autoId, number, autoMarkId, autoModelId, autoBodyTypeId, autoColorId, year);
    }
}
