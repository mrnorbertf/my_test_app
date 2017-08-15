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
 * Модель запроса информации о компаниях
 **/
@ApiModel(description = "Модель запроса информации о компаниях")
public class CompanyListRequestModel {

    @SerializedName("lat")
    private Double lat = null;
    @SerializedName("lng")
    private Double lng = null;
    @SerializedName("minLat")
    private Double minLat = null;
    @SerializedName("maxLat")
    private Double maxLat = null;
    @SerializedName("minLng")
    private Double minLng = null;
    @SerializedName("maxLng")
    private Double maxLng = null;
    @SerializedName("minPrice")
    private Double minPrice = null;
    @SerializedName("maxPrice")
    private Double maxPrice = null;
    @SerializedName("minRating")
    private Double minRating = null;
    @SerializedName("maxRating")
    private Double maxRating = null;
    @SerializedName("features")
    private List<Integer> features = null;
    @SerializedName("isVacantOnly")
    private Boolean isVacantOnly = null;
    @SerializedName("serviceName")
    private String serviceName = null;
    @SerializedName("isFavorites")
    private Boolean isFavorites = null;


    public CompanyListRequestModel() {
    }

    public CompanyListRequestModel(Boolean isFavorites) {
        this.isFavorites = isFavorites;
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

    /**
     * Нижнее ограничение широты
     **/
    @ApiModelProperty(value = "Нижнее ограничение широты")
    public Double getMinLat() {
        return minLat;
    }

    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }

    /**
     * Верхнее ограничение широты
     **/
    @ApiModelProperty(value = "Верхнее ограничение широты")
    public Double getMaxLat() {
        return maxLat;
    }

    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }

    /**
     * Нижнее ограничение долготы
     **/
    @ApiModelProperty(value = "Нижнее ограничение долготы")
    public Double getMinLng() {
        return minLng;
    }

    public void setMinLng(Double minLng) {
        this.minLng = minLng;
    }

    /**
     * Верхнее ограничение долготы
     **/
    @ApiModelProperty(value = "Верхнее ограничение долготы")
    public Double getMaxLng() {
        return maxLng;
    }

    public void setMaxLng(Double maxLng) {
        this.maxLng = maxLng;
    }

    /**
     * Минимальная стоимость
     **/
    @ApiModelProperty(value = "Минимальная стоимость")
    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * Максимальная стоимость
     **/
    @ApiModelProperty(value = "Максимальная стоимость")
    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Минимальный рейтинг
     **/
    @ApiModelProperty(value = "Минимальный рейтинг")
    public Double getMinRating() {
        return minRating;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }

    /**
     * Максимальный рейтинг
     **/
    @ApiModelProperty(value = "Максимальный рейтинг")
    public Double getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(Double maxRating) {
        this.maxRating = maxRating;
    }

    /**
     * Фильтр по характеристикам. Параметр может отсутствовать или содержать список идентификаторов характеристик компаний
     **/
    @ApiModelProperty(value = "Фильтр по характеристикам. Параметр может отсутствовать или содержать список идентификаторов характеристик компаний")
    public List<Integer> getFeatures() {
        return features;
    }

    public void setFeatures(List<Integer> features) {
        this.features = features;
    }

    /**
     * Показывать только свободные мойки
     **/
    @ApiModelProperty(value = "Показывать только свободные мойки")
    public Boolean getIsVacantOnly() {
        return isVacantOnly;
    }

    public void setIsVacantOnly(Boolean isVacantOnly) {
        this.isVacantOnly = isVacantOnly;
    }

    /**
     * Часть строки с названием услуги для поиска
     **/
    @ApiModelProperty(value = "Часть строки с названием услуги для поиска")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Фильтр по избранному. При указании отображаются только компании, которые находятся в избранном у пользователя
     **/
    @ApiModelProperty(value = "Фильтр по избранному. При указании отображаются только компании, которые находятся в избранном у пользователя")
    public Boolean getIsFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(Boolean isFavorites) {
        this.isFavorites = isFavorites;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyListRequestModel companyListRequestModel = (CompanyListRequestModel) o;
        return (this.lat == null ? companyListRequestModel.lat == null : this.lat.equals(companyListRequestModel.lat)) &&
                (this.lng == null ? companyListRequestModel.lng == null : this.lng.equals(companyListRequestModel.lng)) &&
                (this.minLat == null ? companyListRequestModel.minLat == null : this.minLat.equals(companyListRequestModel.minLat)) &&
                (this.maxLat == null ? companyListRequestModel.maxLat == null : this.maxLat.equals(companyListRequestModel.maxLat)) &&
                (this.minLng == null ? companyListRequestModel.minLng == null : this.minLng.equals(companyListRequestModel.minLng)) &&
                (this.maxLng == null ? companyListRequestModel.maxLng == null : this.maxLng.equals(companyListRequestModel.maxLng)) &&
                (this.minPrice == null ? companyListRequestModel.minPrice == null : this.minPrice.equals(companyListRequestModel.minPrice)) &&
                (this.maxPrice == null ? companyListRequestModel.maxPrice == null : this.maxPrice.equals(companyListRequestModel.maxPrice)) &&
                (this.minRating == null ? companyListRequestModel.minRating == null : this.minRating.equals(companyListRequestModel.minRating)) &&
                (this.maxRating == null ? companyListRequestModel.maxRating == null : this.maxRating.equals(companyListRequestModel.maxRating)) &&
                (this.features == null ? companyListRequestModel.features == null : this.features.equals(companyListRequestModel.features)) &&
                (this.isVacantOnly == null ? companyListRequestModel.isVacantOnly == null : this.isVacantOnly.equals(companyListRequestModel.isVacantOnly)) &&
                (this.serviceName == null ? companyListRequestModel.serviceName == null : this.serviceName.equals(companyListRequestModel.serviceName)) &&
                (this.isFavorites == null ? companyListRequestModel.isFavorites == null : this.isFavorites.equals(companyListRequestModel.isFavorites));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.lat == null ? 0 : this.lat.hashCode());
        result = 31 * result + (this.lng == null ? 0 : this.lng.hashCode());
        result = 31 * result + (this.minLat == null ? 0 : this.minLat.hashCode());
        result = 31 * result + (this.maxLat == null ? 0 : this.maxLat.hashCode());
        result = 31 * result + (this.minLng == null ? 0 : this.minLng.hashCode());
        result = 31 * result + (this.maxLng == null ? 0 : this.maxLng.hashCode());
        result = 31 * result + (this.minPrice == null ? 0 : this.minPrice.hashCode());
        result = 31 * result + (this.maxPrice == null ? 0 : this.maxPrice.hashCode());
        result = 31 * result + (this.minRating == null ? 0 : this.minRating.hashCode());
        result = 31 * result + (this.maxRating == null ? 0 : this.maxRating.hashCode());
        result = 31 * result + (this.features == null ? 0 : this.features.hashCode());
        result = 31 * result + (this.isVacantOnly == null ? 0 : this.isVacantOnly.hashCode());
        result = 31 * result + (this.serviceName == null ? 0 : this.serviceName.hashCode());
        result = 31 * result + (this.isFavorites == null ? 0 : this.isFavorites.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CompanyListRequestModel {\n");

        sb.append("  lat: ").append(lat).append("\n");
        sb.append("  lng: ").append(lng).append("\n");
        sb.append("  minLat: ").append(minLat).append("\n");
        sb.append("  maxLat: ").append(maxLat).append("\n");
        sb.append("  minLng: ").append(minLng).append("\n");
        sb.append("  maxLng: ").append(maxLng).append("\n");
        sb.append("  minPrice: ").append(minPrice).append("\n");
        sb.append("  maxPrice: ").append(maxPrice).append("\n");
        sb.append("  minRating: ").append(minRating).append("\n");
        sb.append("  maxRating: ").append(maxRating).append("\n");
        sb.append("  features: ").append(features).append("\n");
        sb.append("  isVacantOnly: ").append(isVacantOnly).append("\n");
        sb.append("  serviceName: ").append(serviceName).append("\n");
        sb.append("  isFavorites: ").append(isFavorites).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
