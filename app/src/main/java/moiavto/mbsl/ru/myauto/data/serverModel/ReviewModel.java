package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Модель отзыва
 **/
@ApiModel(description = "Модель отзыва")
public class ReviewModel {

    @SerializedName("reviewId")
    private Integer reviewId = null;
    @SerializedName("dateCreate")
    private Date dateCreate = null;
    @SerializedName("userName")
    private String userName = null;
    @SerializedName("userAvatarUrl")
    private String userAvatarUrl = null;
    @SerializedName("rating")
    private Integer rating = null;
    @SerializedName("text")
    private String text = null;

    /**
     * Идентификатор отзыва
     **/
    @ApiModelProperty(required = true, value = "Идентификатор отзыва")
    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * Дата создания отзыва
     **/
    @ApiModelProperty(required = true, value = "Дата создания отзыва")
    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    /**
     * Имя пользователя
     **/
    @ApiModelProperty(required = true, value = "Имя пользователя")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * URI, по которому можно загрузить изображение аватара пользователя
     **/
    @ApiModelProperty(required = true, value = "URI, по которому можно загрузить изображение аватара пользователя")
    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    /**
     * Рейтинг
     **/
    @ApiModelProperty(required = true, value = "Рейтинг")
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
        ReviewModel reviewModel = (ReviewModel) o;
        return (this.reviewId == null ? reviewModel.reviewId == null : this.reviewId.equals(reviewModel.reviewId)) &&
                (this.dateCreate == null ? reviewModel.dateCreate == null : this.dateCreate.equals(reviewModel.dateCreate)) &&
                (this.userName == null ? reviewModel.userName == null : this.userName.equals(reviewModel.userName)) &&
                (this.userAvatarUrl == null ? reviewModel.userAvatarUrl == null : this.userAvatarUrl.equals(reviewModel.userAvatarUrl)) &&
                (this.rating == null ? reviewModel.rating == null : this.rating.equals(reviewModel.rating)) &&
                (this.text == null ? reviewModel.text == null : this.text.equals(reviewModel.text));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.reviewId == null ? 0 : this.reviewId.hashCode());
        result = 31 * result + (this.dateCreate == null ? 0 : this.dateCreate.hashCode());
        result = 31 * result + (this.userName == null ? 0 : this.userName.hashCode());
        result = 31 * result + (this.userAvatarUrl == null ? 0 : this.userAvatarUrl.hashCode());
        result = 31 * result + (this.rating == null ? 0 : this.rating.hashCode());
        result = 31 * result + (this.text == null ? 0 : this.text.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "class ReviewModel {\n" +
                "  reviewId: " + reviewId + "\n" +
                "  dateCreate: " + dateCreate + "\n" +
                "  userName: " + userName + "\n" +
                "  userAvatarUrl: " + userAvatarUrl + "\n" +
                "  rating: " + rating + "\n" +
                "  text: " + text + "\n" +
                "}\n";
    }
}
