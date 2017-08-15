/**
 * MoiAvto Mobile API
 * ### Сведения о версии API  Версия | Дата | Описание ------ | ---- | -------- 1.0 | 10.04.2017 | Исходная версия 1.1 | 11.06.2017 | Добавлено поле reviews в CompanyModel  ### Конфиденциальность информации Сведения, приведённые в настоящем документе, являются конфиденциальной информацией компании.  ### Общее описание Список функций мобильного приложения, которые поддерживаются данным API: - Регистрация нового пользователя и управление аккаунтом; - Аутентификация; - Личный кабинет; - Просмотр информации о компаниях; - Работа с отзывами; - Запись по времени; - Просмотр помощи.   Для взаимодействия с серверной частью приложение использует протокол на основе REST. Требования к реализации протокола, а также описание форматов сообщений, приведены в соответствующих разделах.  ### Кодирование данных Все данные должны передаваться в кодировке `UTF-8`.   ### Транспортный протокол Все взаимодействие между приложением и сервером должно по возможности осуществляться использованием защищенного соединения HTTPS. Клиент должен самостоятельно проверять валидность SSL-сертификата и немедленно прекращать обмен данными, если эта проверка не прошла.  ### Доступ к ресурсам системы Для доступа к методам API используется протокол на основе RPC. Каждый ресурс идентифицируется уникальным URI.  Например, `https://host/api/mobile/ver/controller/action` вызывает метод `action` контроллера `controller`.  Фрагмент `host` определеяет основной хост сервера API. Фрагмент `ver` определяет версию протокола обмена данными, и для версии 1.0 имеет вид v10.  ### Точка входа Точка входа для текущей версии API представляет собой следующий URI:  ``` https://host/api/mobile/v10/ ```  Для тестирования следует использовать другой URI (внимание, без https):  ``` http://test.host/api/mobile/v10/ ```  ### Формат возвращаемых данных Основным и единственным форматом данных является `application/json`. В этом формате выполняются запросы к методам API и отправляются ответы. В случае наличия тела сообщения, приложение также должно передавать заголовок:  ``` Accept: application/json ```  В случае наличия тела сообщения, приложение также должно передавать заголовок:  ``` Content-Type: application/json; charset=utf-8 ```  Сервер в ответ отправляет:  ``` Content-Type: application/json; charset=utf-8 ```  Если приложение запросило представление, которое не поддерживается сервером, сервер должен возвратить соответствующий код ошибки HTTP.  ### Локализация Для указания языка, на котором сервер должен возвращать стандартные сообщения и сообщения об ошибках, приложение должно передавать заголовок:  ``` Accept-Language: ru ```  Где `ru` – код языка, на котором приложение желает получать сообщения. Если сервер не поддерживает запрошенный язык, сообщения будут приходить на языке по умолчанию.  ### Содержимое возвращаемых данных В случае успешного ответа сервер возвращает код ответа `HTTP: 200 OK` в заголовке, а в теле ответа  соответствующий запросу объект. Например:  ``` HTTP/1.1 200 OK  {   \"foo\": {     \"bar\":\"1\",     \"baz\":\"2\"   } } ```  В случае ошибки сервер возвращает HTTP код ошибки и соответствующее ему описание. В теле ответа в поле `message` содержится текст произошедшей на сервере ошибки на запрошенном языке. Например:  ``` HTTP/1.1 404 Not Found  {   \"message\": \"Компания с запрошенным идентификатором не найдена.\" } ```  ### Использование методов HTTP Все запросы к серверу должны использовать метод POST. Методы GET, PUT, DELETE и прочие считаются ошибочными и игнорируются.  #### POST Метод HTTP POST используется вызова удаленных процедур, например:  ``` POST https://host/api/mobile/ver/controller HTTP 1.1 Host: host ```  Приложение должно использовать представление `application/json` для данных при отправке запроса серверу:  ``` Content-type: application/json; charset=utf-8 ```  Параметры передаются в теле запроса HTTP следующим образом:  ```json {   \"foo\": {     \"bar\":\"1\",     \"baz\":\"2\"   } }  ```  ### Авторизация При выполнении запроса от клиента выполняется его авторизация. Способ авторизации – по цифровой подписи пакетов данных.   JSON-пакет подписывается цифровой подписью, при этом к каждому запросу клиент должен добавить следующие HTTP-заголовки: -  X-Auth-Method – метод вычисления цифровой подписи; -  X-Auth-Signature – цифровая подпись, закодированная с помощью алгоритма base64; -  X-Auth-AppId – идентификатор приложения; - X-Auth-Time – текущее время на мобильном устройстве, с учетом разницы во времени между сервером и мобильным устройством.  В случае если цифровая подпись не пройдет проверку на сервере, сервер вернет ошибку HTTP 401: `HTTP 401 Unauthorized`.  В таком случае надо проверить параметры аутентификации либо алгоритм проверки цифровой подписи.  Методы проверки цифровой подписи  На данный момент поддерживается единственный метод проверки цифровой подписи – `sha512mob`.  В этом случае цифровая подпись высчитывается по следующей формуле: ``` sha512(request_body+secret+time) ```  где: -  sha512 – алгоритм вычисления хеша SHA-2 с длиной дайджеста 512 бит; -  request_body – json-тело запроса; -  secret – md5(appSecret); - time - время, указанное в заголовке X-Auth-Time.  Список методов, которым не требуется цифровая подпись: - /app/ping; - /account/create.   ### Коды ошибок HTTP Сервер использует [коды ошибок HTTP](http://en.wikipedia.org/wiki/List_of_HTTP_status_codes) для того, чтобы сигнализировать о нормальном или неудачном выполнении запроса приложения. Перечень используемых кодов HTTP и их значение приведён в таблице:  Код | Текст --- | ----- **200 OK** | Запрос выполнен успешно. **201 Created** | Запрос выполнен успешно, ресурс создан. **204 Not Modified** | Содержимое затребованного ресурса не изменилось. **400 Bad Request** | Ошибка приложения в запросе. Дополнительная информация может находиться в возвращаемых сервером данных. **401 Unauthorized** | Приложение не подписало вызванный метод API либо подпись неверна ИЛИ Авторизация не проведена. Необходимо провести повторную авторизацию. **402 Payment Required** | Пользователь изменил номер телефона и требуется повторный ввод кода из СМС. Необходимо перенаправить пользователя на страницу ввода СМС-кода.  **403 Forbidden** | Приложение не имеет доступ к запрошенному ресурсу. Например, при попытке выполнить операцию до подтверждения кода по СМС. Необходимо провести повторную авторизацию. **404 Not Found** | Запрашиваемый ресурс отсутствует. **405 Method Not Allowed** | Приложение использует неправильный метод HTTP. **406 Not Acceptable** | Запрошенная операция не может быть выполнена в силу ограничений данных. **415 Unsupported Media Type** | Сервер не поддерживает запрошенное приложением представление возвращаемых данных. **410 Gone** | Время, переданное в заголовке `X-Auth-Time` отличается от времени сервера более чем на 60 секунд в меньшую или большую сторону. На устройстве необходимо повторно выполнить метод `/app/ping`. **500 Internal Server Error** | Ошибка сервера. Дополнительная информация может находиться в возвращаемых сервером данных. **503 Service Unavailable** | В настоящее время сервер не может обслужить запрос, например, находится на обслуживании.  ### Прочие требования Дата и время представляются в формате, определяемым стандартом [ISO 8601](http://en.wikipedia.org/wiki/ISO_8601) `(‘yyyy-MM-dd\\THH:mm:ss\\Z’)`.  При этом даты передаются либо в UTC, либо обязательно нужно передавать смещение часового пояса пользователя (берется из настроек мобильной ОС).  Координаты (долгота и широта) представляются в формате [WGS 84](http://en.wikipedia.org/wiki/World_Geodetic_System), с точностью не менее 6 знаков после десятичной точки.  При записи чисел с десятичной точкой в качестве разделителя используется только точка.
 * <p>
 * OpenAPI spec version: 1.1
 * <p>
 * <p>
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package moiavto.mbsl.ru.myauto.data.serverModel;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Модель ответа запроса списка отзывов
 **/
@ApiModel(description = "Модель ответа запроса списка отзывов")
public class ReviewListResponseModel {

    @SerializedName("companyName")
    private String companyName = null;
    @SerializedName("rating")
    private Double rating = null;
    @SerializedName("count")
    private Integer count = null;
    @SerializedName("canWriteReview")
    private Boolean canWriteReview = null;
    @SerializedName("reviews")
    private List<ReviewModel> reviews = null;

    /**
     * Название компании
     **/
    @ApiModelProperty(required = true, value = "Название компании")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Рейтинг компании
     **/
    @ApiModelProperty(required = true, value = "Рейтинг компании")
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Количество отзывов
     **/
    @ApiModelProperty(required = true, value = "Количество отзывов")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * Признак того что пользователь может писать отзыв для этой компании
     **/
    @ApiModelProperty(value = "Признак того что пользователь может писать отзыв для этой компании")
    public Boolean getCanWriteReview() {
        return canWriteReview;
    }

    public void setCanWriteReview(Boolean canWriteReview) {
        this.canWriteReview = canWriteReview;
    }

    /**
     * Список отзывов
     **/
    @ApiModelProperty(required = true, value = "Список отзывов")
    public List<ReviewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewModel> reviews) {
        this.reviews = reviews;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReviewListResponseModel reviewListResponseModel = (ReviewListResponseModel) o;
        return (this.companyName == null ? reviewListResponseModel.companyName == null : this.companyName.equals(reviewListResponseModel.companyName)) &&
                (this.rating == null ? reviewListResponseModel.rating == null : this.rating.equals(reviewListResponseModel.rating)) &&
                (this.count == null ? reviewListResponseModel.count == null : this.count.equals(reviewListResponseModel.count)) &&
                (this.canWriteReview == null ? reviewListResponseModel.canWriteReview == null : this.canWriteReview.equals(reviewListResponseModel.canWriteReview)) &&
                (this.reviews == null ? reviewListResponseModel.reviews == null : this.reviews.equals(reviewListResponseModel.reviews));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.companyName == null ? 0 : this.companyName.hashCode());
        result = 31 * result + (this.rating == null ? 0 : this.rating.hashCode());
        result = 31 * result + (this.count == null ? 0 : this.count.hashCode());
        result = 31 * result + (this.canWriteReview == null ? 0 : this.canWriteReview.hashCode());
        result = 31 * result + (this.reviews == null ? 0 : this.reviews.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ReviewListResponseModel {\n");

        sb.append("  companyName: ").append(companyName).append("\n");
        sb.append("  rating: ").append(rating).append("\n");
        sb.append("  count: ").append(count).append("\n");
        sb.append("  canWriteReview: ").append(canWriteReview).append("\n");
        sb.append("  reviews: ").append(reviews).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
