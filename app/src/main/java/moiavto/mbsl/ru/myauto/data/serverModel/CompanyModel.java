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
 * Модель компании
 **/
@ApiModel(description = "Модель компании")
public class CompanyModel {

    @SerializedName("companyId")
    private Integer companyId = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("address")
    private String address = null;
    @SerializedName("distance")
    private Integer distance = null;
    @SerializedName("rating")
    private Double rating = null;
    @SerializedName("reviews")
    private Integer reviews = null;
    @SerializedName("lat")
    private Double lat = null;
    @SerializedName("lng")
    private Double lng = null;
    @SerializedName("previewImageUrl")
    private String previewImageUrl = null;
    @SerializedName("inFavorites")
    private Boolean inFavorites = null;
    @SerializedName("detailImagesUrls")
    private List<String> detailImagesUrls = null;
    @SerializedName("description")
    private String description = null;
    @SerializedName("schedule")
    private String schedule = null;
    @SerializedName("isOpen")
    private Boolean isOpen = null;
    @SerializedName("isVacant")
    private Boolean isVacant = null;
    @SerializedName("isAdvertised")
    private Boolean isAdvertised = null;
    @SerializedName("discount")
    private Integer discount = null;
    @SerializedName("phone")
    private String phone = null;
    @SerializedName("priceRange")
    private String priceRange = null;
    @SerializedName("isBookingAvailable")
    private Boolean isBookingAvailable = null;
    @SerializedName("actionDescription")
    private String actionDescription = null;
    @SerializedName("features")
    private List<CompanyFeatureModel> features = null;
    @SerializedName("services")
    private List<CompanyServiceModel> services = null;
    @SerializedName("ReviewListResponseModel")
    private ReviewListResponseModel reviewList = null;


    public CompanyModel() {
    }

    public CompanyModel(Integer companyId, String name, String address, Integer distance, Double rating, Integer reviews, Double lat, Double lng, String previewImageUrl, Boolean inFavorites, List<String> detailImagesUrls, String description, String schedule, Boolean isOpen, Boolean isVacant, Boolean isAdvertised, Integer discount, String phone, String priceRange, Boolean isBookingAvailable, String actionDescription, List<CompanyFeatureModel> features, List<CompanyServiceModel> services, ReviewListResponseModel reviewList) {
        this.companyId = companyId;
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.rating = rating;
        this.reviews = reviews;
        this.lat = lat;
        this.lng = lng;
        this.previewImageUrl = previewImageUrl;
        this.inFavorites = inFavorites;
        this.detailImagesUrls = detailImagesUrls;
        this.description = description;
        this.schedule = schedule;
        this.isOpen = isOpen;
        this.isVacant = isVacant;
        this.isAdvertised = isAdvertised;
        this.discount = discount;
        this.phone = phone;
        this.priceRange = priceRange;
        this.isBookingAvailable = isBookingAvailable;
        this.actionDescription = actionDescription;
        this.features = features;
        this.services = services;
        this.reviewList = reviewList;
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
     * Название компании
     **/
    @ApiModelProperty(required = true, value = "Название компании")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Адрес
     **/
    @ApiModelProperty(required = true, value = "Адрес")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Расстояние в метрах
     **/
    @ApiModelProperty(required = true, value = "Расстояние в метрах")
    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * Рейтинг
     **/
    @ApiModelProperty(required = true, value = "Рейтинг")
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Количество отзывов
     **/
    @ApiModelProperty(value = "Количество отзывов")
    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public ReviewListResponseModel getReviewList() {
        return reviewList;
    }

    public void setReviewList(ReviewListResponseModel reviewList) {
        this.reviewList = reviewList;
    }

    /**
     * Широта
     **/
    @ApiModelProperty(required = true, value = "Широта")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * Долгота
     **/
    @ApiModelProperty(required = true, value = "Долгота")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * URI, по которому можно загрузить уменьшенное изображение компании
     **/
    @ApiModelProperty(required = true, value = "URI, по которому можно загрузить уменьшенное изображение компании")
    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    /**
     * Признак наличия компании в избранном
     **/
    @ApiModelProperty(required = true, value = "Признак наличия компании в избранном")
    public Boolean getInFavorites() {
        return inFavorites;
    }

    public void setInFavorites(Boolean inFavorites) {
        this.inFavorites = inFavorites;
    }

    /**
     * Массив URI, по которым можно загрузить изображения компании
     **/
    @ApiModelProperty(value = "Массив URI, по которым можно загрузить изображения компании")
    public List<String> getDetailImagesUrls() {
        return detailImagesUrls;
    }

    public void setDetailImagesUrls(List<String> detailImagesUrls) {
        this.detailImagesUrls = detailImagesUrls;
    }

    /**
     * Описание компании
     **/
    @ApiModelProperty(value = "Описание компании")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * График работы
     **/
    @ApiModelProperty(value = "График работы")
    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * Признак работы в данное время
     **/
    @ApiModelProperty(value = "Признак работы в данное время")
    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * Признак наличия свободного бокса
     **/
    @ApiModelProperty(value = "Признак наличия свободного бокса")
    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }

    /**
     * Признак рекламируемой компании
     **/
    @ApiModelProperty(value = "Признак рекламируемой компании")
    public Boolean getIsAdvertised() {
        return isAdvertised;
    }

    public void setIsAdvertised(Boolean isAdvertised) {
        this.isAdvertised = isAdvertised;
    }

    /**
     * Скидка в процентах
     **/
    @ApiModelProperty(value = "Скидка в процентах")
    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    /**
     * Номер телефона
     **/
    @ApiModelProperty(value = "Номер телефона")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Диапазон цен
     **/
    @ApiModelProperty(value = "Диапазон цен")
    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    /**
     * Возможность бронирования
     **/
    @ApiModelProperty(value = "Возможность бронирования")
    public Boolean getIsBookingAvailable() {
        return isBookingAvailable;
    }

    public void setIsBookingAvailable(Boolean isBookingAvailable) {
        this.isBookingAvailable = isBookingAvailable;
    }

    /**
     * Описание действующей акции
     **/
    @ApiModelProperty(value = "Описание действующей акции")
    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    /**
     * Список характеристик
     **/
    @ApiModelProperty(value = "Список характеристик")
    public List<CompanyFeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(List<CompanyFeatureModel> features) {
        this.features = features;
    }

    /**
     * Список услуг
     **/
    @ApiModelProperty(value = "Список услуг")
    public List<CompanyServiceModel> getServices() {
        return services;
    }

    public void setServices(List<CompanyServiceModel> services) {
        this.services = services;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyModel companyModel = (CompanyModel) o;
        return (this.companyId == null ? companyModel.companyId == null : this.companyId.equals(companyModel.companyId)) &&
                (this.name == null ? companyModel.name == null : this.name.equals(companyModel.name)) &&
                (this.address == null ? companyModel.address == null : this.address.equals(companyModel.address)) &&
                (this.distance == null ? companyModel.distance == null : this.distance.equals(companyModel.distance)) &&
                (this.rating == null ? companyModel.rating == null : this.rating.equals(companyModel.rating)) &&
                (this.reviews == null ? companyModel.reviews == null : this.reviews.equals(companyModel.reviews)) &&
                (this.lat == null ? companyModel.lat == null : this.lat.equals(companyModel.lat)) &&
                (this.lng == null ? companyModel.lng == null : this.lng.equals(companyModel.lng)) &&
                (this.previewImageUrl == null ? companyModel.previewImageUrl == null : this.previewImageUrl.equals(companyModel.previewImageUrl)) &&
                (this.inFavorites == null ? companyModel.inFavorites == null : this.inFavorites.equals(companyModel.inFavorites)) &&
                (this.detailImagesUrls == null ? companyModel.detailImagesUrls == null : this.detailImagesUrls.equals(companyModel.detailImagesUrls)) &&
                (this.description == null ? companyModel.description == null : this.description.equals(companyModel.description)) &&
                (this.schedule == null ? companyModel.schedule == null : this.schedule.equals(companyModel.schedule)) &&
                (this.isOpen == null ? companyModel.isOpen == null : this.isOpen.equals(companyModel.isOpen)) &&
                (this.isVacant == null ? companyModel.isVacant == null : this.isVacant.equals(companyModel.isVacant)) &&
                (this.isAdvertised == null ? companyModel.isAdvertised == null : this.isAdvertised.equals(companyModel.isAdvertised)) &&
                (this.discount == null ? companyModel.discount == null : this.discount.equals(companyModel.discount)) &&
                (this.phone == null ? companyModel.phone == null : this.phone.equals(companyModel.phone)) &&
                (this.priceRange == null ? companyModel.priceRange == null : this.priceRange.equals(companyModel.priceRange)) &&
                (this.isBookingAvailable == null ? companyModel.isBookingAvailable == null : this.isBookingAvailable.equals(companyModel.isBookingAvailable)) &&
                (this.actionDescription == null ? companyModel.actionDescription == null : this.actionDescription.equals(companyModel.actionDescription)) &&
                (this.features == null ? companyModel.features == null : this.features.equals(companyModel.features)) &&
                (this.services == null ? companyModel.services == null : this.services.equals(companyModel.services)) &&
                (this.reviewList == null ? companyModel.reviewList == null : this.reviewList.equals(companyModel.reviewList));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.companyId == null ? 0 : this.companyId.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        result = 31 * result + (this.address == null ? 0 : this.address.hashCode());
        result = 31 * result + (this.distance == null ? 0 : this.distance.hashCode());
        result = 31 * result + (this.rating == null ? 0 : this.rating.hashCode());
        result = 31 * result + (this.reviews == null ? 0 : this.reviews.hashCode());
        result = 31 * result + (this.lat == null ? 0 : this.lat.hashCode());
        result = 31 * result + (this.lng == null ? 0 : this.lng.hashCode());
        result = 31 * result + (this.previewImageUrl == null ? 0 : this.previewImageUrl.hashCode());
        result = 31 * result + (this.inFavorites == null ? 0 : this.inFavorites.hashCode());
        result = 31 * result + (this.detailImagesUrls == null ? 0 : this.detailImagesUrls.hashCode());
        result = 31 * result + (this.description == null ? 0 : this.description.hashCode());
        result = 31 * result + (this.schedule == null ? 0 : this.schedule.hashCode());
        result = 31 * result + (this.isOpen == null ? 0 : this.isOpen.hashCode());
        result = 31 * result + (this.isVacant == null ? 0 : this.isVacant.hashCode());
        result = 31 * result + (this.isAdvertised == null ? 0 : this.isAdvertised.hashCode());
        result = 31 * result + (this.discount == null ? 0 : this.discount.hashCode());
        result = 31 * result + (this.phone == null ? 0 : this.phone.hashCode());
        result = 31 * result + (this.priceRange == null ? 0 : this.priceRange.hashCode());
        result = 31 * result + (this.isBookingAvailable == null ? 0 : this.isBookingAvailable.hashCode());
        result = 31 * result + (this.actionDescription == null ? 0 : this.actionDescription.hashCode());
        result = 31 * result + (this.features == null ? 0 : this.features.hashCode());
        result = 31 * result + (this.services == null ? 0 : this.services.hashCode());
        result = 31 * result + (this.reviewList == null ? 0 : this.reviewList.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CompanyModel {\n");

        sb.append("  companyId: ").append(companyId).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  address: ").append(address).append("\n");
        sb.append("  distance: ").append(distance).append("\n");
        sb.append("  rating: ").append(rating).append("\n");
        sb.append("  reviews: ").append(reviews).append("\n");
        sb.append("  lat: ").append(lat).append("\n");
        sb.append("  lng: ").append(lng).append("\n");
        sb.append("  previewImageUrl: ").append(previewImageUrl).append("\n");
        sb.append("  inFavorites: ").append(inFavorites).append("\n");
        sb.append("  detailImagesUrls: ").append(detailImagesUrls).append("\n");
        sb.append("  description: ").append(description).append("\n");
        sb.append("  schedule: ").append(schedule).append("\n");
        sb.append("  isOpen: ").append(isOpen).append("\n");
        sb.append("  isVacant: ").append(isVacant).append("\n");
        sb.append("  isAdvertised: ").append(isAdvertised).append("\n");
        sb.append("  discount: ").append(discount).append("\n");
        sb.append("  phone: ").append(phone).append("\n");
        sb.append("  priceRange: ").append(priceRange).append("\n");
        sb.append("  isBookingAvailable: ").append(isBookingAvailable).append("\n");
        sb.append("  actionDescription: ").append(actionDescription).append("\n");
        sb.append("  features: ").append(features).append("\n");
        sb.append("  services: ").append(services).append("\n");
        sb.append("  reviewList: ").append(reviewList).append("\n");
        sb.append("}\n");
        return sb.toString();
    }


    public static class Builder {

        @SerializedName("companyId")
        private Integer companyId = null;
        @SerializedName("name")
        private String name = null;
        @SerializedName("address")
        private String address = null;
        @SerializedName("distance")
        private Integer distance = null;
        @SerializedName("rating")
        private Double rating = null;
        @SerializedName("reviews")
        private Integer reviews = null;
        @SerializedName("lat")
        private Double lat = null;
        @SerializedName("lng")
        private Double lng = null;
        @SerializedName("previewImageUrl")
        private String previewImageUrl = null;
        @SerializedName("inFavorites")
        private Boolean inFavorites = null;
        @SerializedName("detailImagesUrls")
        private List<String> detailImagesUrls = null;
        @SerializedName("description")
        private String description = null;
        @SerializedName("schedule")
        private String schedule = null;
        @SerializedName("isOpen")
        private Boolean isOpen = null;
        @SerializedName("isVacant")
        private Boolean isVacant = null;
        @SerializedName("isAdvertised")
        private Boolean isAdvertised = null;
        @SerializedName("discount")
        private Integer discount = null;
        @SerializedName("phone")
        private String phone = null;
        @SerializedName("priceRange")
        private String priceRange = null;
        @SerializedName("isBookingAvailable")
        private Boolean isBookingAvailable = null;
        @SerializedName("actionDescription")
        private String actionDescription = null;
        @SerializedName("features")
        private List<CompanyFeatureModel> features = null;
        @SerializedName("services")
        private List<CompanyServiceModel> services = null;
        @SerializedName("ReviewListResponseModel")
        private ReviewListResponseModel reviewList = null;

        public Builder setCompanyId(Integer companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setDistance(Integer distance) {
            this.distance = distance;
            return this;
        }

        public Builder setRating(Double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setReviews(Integer reviews) {
            this.reviews = reviews;
            return this;
        }

        public Builder setLat(Double lat) {
            this.lat = lat;
            return this;
        }

        public Builder setLng(Double lng) {
            this.lng = lng;
            return this;
        }

        public Builder setPreviewImageUrl(String previewImageUrl) {
            this.previewImageUrl = previewImageUrl;
            return this;
        }

        public Builder setInFavorites(Boolean inFavorites) {
            this.inFavorites = inFavorites;
            return this;
        }

        public Builder setDetailImagesUrls(List<String> detailImagesUrls) {
            this.detailImagesUrls = detailImagesUrls;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setSchedule(String schedule) {
            this.schedule = schedule;
            return this;
        }

        public Builder setOpen(Boolean open) {
            isOpen = open;
            return this;
        }

        public Builder setVacant(Boolean vacant) {
            isVacant = vacant;
            return this;
        }

        public Builder setAdvertised(Boolean advertised) {
            isAdvertised = advertised;
            return this;
        }

        public Builder setDiscount(Integer discount) {
            this.discount = discount;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setPriceRange(String priceRange) {
            this.priceRange = priceRange;
            return this;
        }

        public Builder setBookingAvailable(Boolean bookingAvailable) {
            isBookingAvailable = bookingAvailable;
            return this;
        }

        public Builder setActionDescription(String actionDescription) {
            this.actionDescription = actionDescription;
            return this;
        }

        public Builder setFeatures(List<CompanyFeatureModel> features) {
            this.features = features;
            return this;
        }

        public Builder setServices(List<CompanyServiceModel> services) {
            this.services = services;
            return this;
        }

        public Builder setReviewList(ReviewListResponseModel reviewList) {
            this.reviewList = reviewList;
            return this;
        }


        public CompanyModel build() {
            return new CompanyModel(companyId, name, address, distance, rating, reviews, lat, lng, previewImageUrl, inFavorites, detailImagesUrls, description, schedule, isOpen,
                    isVacant, isAdvertised, discount, phone, priceRange, isBookingAvailable, actionDescription, features, services, reviewList);
        }
    }

}
