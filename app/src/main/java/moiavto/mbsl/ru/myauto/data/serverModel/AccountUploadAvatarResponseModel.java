package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Модель ответа загрузки аватара
 **/
@ApiModel(description = "Модель ответа загрузки аватара")
public class AccountUploadAvatarResponseModel {

    @SerializedName("avatarUrl")
    private String avatarUrl = null;

    /**
     * Url изображения аватара пользователя
     **/
    @ApiModelProperty(required = true, value = "Url изображения аватара пользователя")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountUploadAvatarResponseModel accountUploadAvatarResponseModel = (AccountUploadAvatarResponseModel) o;
        return (this.avatarUrl == null ? accountUploadAvatarResponseModel.avatarUrl == null : this.avatarUrl.equals(accountUploadAvatarResponseModel.avatarUrl));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.avatarUrl == null ? 0 : this.avatarUrl.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "class AccountUploadAvatarResponseModel {\n" +
                "  avatarUrl: " + avatarUrl + "\n" +
                "}\n";
    }
}
