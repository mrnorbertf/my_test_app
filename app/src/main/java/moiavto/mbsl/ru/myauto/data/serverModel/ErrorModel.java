package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Модель ошибки
 **/
@ApiModel(description = "Модель ошибки")
public class ErrorModel {

    @SerializedName("message")
    private String message = null;

    /**
     * Сообщение об ошибке
     **/
    @ApiModelProperty(value = "Сообщение об ошибке")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorModel errorModel = (ErrorModel) o;
        return (this.message == null ? errorModel.message == null : this.message.equals(errorModel.message));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.message == null ? 0 : this.message.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "class ErrorModel {\n" +
                "  message: " + message + "\n" +
                "}\n";
    }
}
