package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Модель запроса создания отзыва
 **/
@ApiModel(description = "Модель запроса создания отзыва")
public class ReviewCreateRequestModel {

    @SerializedName("companyId")
    private Integer companyId = null;
    @SerializedName("rating")
    private Integer rating = null;
    @SerializedName("text")
    private String text = null;


    public ReviewCreateRequestModel(Integer companyId, Integer rating, String text) {
        this.companyId = companyId;
        this.rating = rating;
        this.text = text;
    }

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
     * Рейтинг (1-5)
     **/
    @ApiModelProperty(required = true, value = "Рейтинг (1-5)")
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Текст отзыва
     **/
    @ApiModelProperty(required = true, value = "Текст отзыва")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReviewCreateRequestModel reviewCreateRequestModel = (ReviewCreateRequestModel) o;
        return (this.companyId == null ? reviewCreateRequestModel.companyId == null : this.companyId.equals(reviewCreateRequestModel.companyId)) &&
                (this.rating == null ? reviewCreateRequestModel.rating == null : this.rating.equals(reviewCreateRequestModel.rating)) &&
                (this.text == null ? reviewCreateRequestModel.text == null : this.text.equals(reviewCreateRequestModel.text));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.companyId == null ? 0 : this.companyId.hashCode());
        result = 31 * result + (this.rating == null ? 0 : this.rating.hashCode());
        result = 31 * result + (this.text == null ? 0 : this.text.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "class ReviewCreateRequestModel {\n" +
                "  companyId: " + companyId + "\n" +
                "  rating: " + rating + "\n" +
                "  text: " + text + "\n" +
                "}\n";
    }
}
