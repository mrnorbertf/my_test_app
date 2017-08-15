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

import java.util.Date;
import java.util.List;

/**
 * Модель бронирования
 **/
@ApiModel(description = "Модель бронирования")
public class BookingModel {

    @SerializedName("bookingId")
    private Integer bookingId = null;
    @SerializedName("companyId")
    private Integer companyId = null;
    @SerializedName("name")
    private String name = null;
    @SerializedName("address")
    private String address = null;
    @SerializedName("phone")
    private String phone = null;
    @SerializedName("lat")
    private Double lat = null;
    @SerializedName("lng")
    private Double lng = null;
    @SerializedName("previewImageUrl")
    private String previewImageUrl = null;
    @SerializedName("bookingDate")
    private Date bookingDate = null;
    @SerializedName("bookingTime")
    private String bookingTime = null;
    @SerializedName("carId")
    private Integer carId = null;
    @SerializedName("carName")
    private String carName = null;
    @SerializedName("isCancellable")
    private Boolean isCancellable = null;
    @SerializedName("duration")
    private Integer duration = null;
    @SerializedName("price")
    private Double price = null;
    @SerializedName("boxName")
    private String boxName = null;
    @SerializedName("services")
    private List<CompanyServiceModel> services = null;

    /**
     * Идентификатор бронирования
     **/
    @ApiModelProperty(required = true, value = "Идентификатор бронирования")
    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
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
     * Адрес компании
     **/
    @ApiModelProperty(required = true, value = "Адрес компании")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Номер телефона компании
     **/
    @ApiModelProperty(value = "Номер телефона компании")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
     * Дата бронирования
     **/
    @ApiModelProperty(required = true, value = "Дата бронирования")
    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * Время бронирования в формате \"HH:MM\"
     **/
    @ApiModelProperty(required = true, value = "Время бронирования в формате \"HH:MM\"")
    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    /**
     * Идентификатор автомобиля
     **/
    @ApiModelProperty(required = true, value = "Идентификатор автомобиля")
    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    /**
     * Наименование автомобиля
     **/
    @ApiModelProperty(required = true, value = "Наименование автомобиля")
    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    /**
     * Признак возможности отмены бронирования
     **/
    @ApiModelProperty(required = true, value = "Признак возможности отмены бронирования")
    public Boolean getIsCancellable() {
        return isCancellable;
    }

    public void setIsCancellable(Boolean isCancellable) {
        this.isCancellable = isCancellable;
    }

    /**
     * Длительность услуги
     **/
    @ApiModelProperty(value = "Длительность услуги")
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Стоимость
     **/
    @ApiModelProperty(value = "Стоимость")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Наименование бокса
     **/
    @ApiModelProperty(value = "Наименование бокса")
    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
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
        BookingModel bookingModel = (BookingModel) o;
        return (this.bookingId == null ? bookingModel.bookingId == null : this.bookingId.equals(bookingModel.bookingId)) &&
                (this.companyId == null ? bookingModel.companyId == null : this.companyId.equals(bookingModel.companyId)) &&
                (this.name == null ? bookingModel.name == null : this.name.equals(bookingModel.name)) &&
                (this.address == null ? bookingModel.address == null : this.address.equals(bookingModel.address)) &&
                (this.phone == null ? bookingModel.phone == null : this.phone.equals(bookingModel.phone)) &&
                (this.lat == null ? bookingModel.lat == null : this.lat.equals(bookingModel.lat)) &&
                (this.lng == null ? bookingModel.lng == null : this.lng.equals(bookingModel.lng)) &&
                (this.previewImageUrl == null ? bookingModel.previewImageUrl == null : this.previewImageUrl.equals(bookingModel.previewImageUrl)) &&
                (this.bookingDate == null ? bookingModel.bookingDate == null : this.bookingDate.equals(bookingModel.bookingDate)) &&
                (this.bookingTime == null ? bookingModel.bookingTime == null : this.bookingTime.equals(bookingModel.bookingTime)) &&
                (this.carId == null ? bookingModel.carId == null : this.carId.equals(bookingModel.carId)) &&
                (this.carName == null ? bookingModel.carName == null : this.carName.equals(bookingModel.carName)) &&
                (this.isCancellable == null ? bookingModel.isCancellable == null : this.isCancellable.equals(bookingModel.isCancellable)) &&
                (this.duration == null ? bookingModel.duration == null : this.duration.equals(bookingModel.duration)) &&
                (this.price == null ? bookingModel.price == null : this.price.equals(bookingModel.price)) &&
                (this.boxName == null ? bookingModel.boxName == null : this.boxName.equals(bookingModel.boxName)) &&
                (this.services == null ? bookingModel.services == null : this.services.equals(bookingModel.services));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.bookingId == null ? 0 : this.bookingId.hashCode());
        result = 31 * result + (this.companyId == null ? 0 : this.companyId.hashCode());
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        result = 31 * result + (this.address == null ? 0 : this.address.hashCode());
        result = 31 * result + (this.phone == null ? 0 : this.phone.hashCode());
        result = 31 * result + (this.lat == null ? 0 : this.lat.hashCode());
        result = 31 * result + (this.lng == null ? 0 : this.lng.hashCode());
        result = 31 * result + (this.previewImageUrl == null ? 0 : this.previewImageUrl.hashCode());
        result = 31 * result + (this.bookingDate == null ? 0 : this.bookingDate.hashCode());
        result = 31 * result + (this.bookingTime == null ? 0 : this.bookingTime.hashCode());
        result = 31 * result + (this.carId == null ? 0 : this.carId.hashCode());
        result = 31 * result + (this.carName == null ? 0 : this.carName.hashCode());
        result = 31 * result + (this.isCancellable == null ? 0 : this.isCancellable.hashCode());
        result = 31 * result + (this.duration == null ? 0 : this.duration.hashCode());
        result = 31 * result + (this.price == null ? 0 : this.price.hashCode());
        result = 31 * result + (this.boxName == null ? 0 : this.boxName.hashCode());
        result = 31 * result + (this.services == null ? 0 : this.services.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BookingDomainModel {\n");

        sb.append("  bookingId: ").append(bookingId).append("\n");
        sb.append("  companyId: ").append(companyId).append("\n");
        sb.append("  name: ").append(name).append("\n");
        sb.append("  address: ").append(address).append("\n");
        sb.append("  phone: ").append(phone).append("\n");
        sb.append("  lat: ").append(lat).append("\n");
        sb.append("  lng: ").append(lng).append("\n");
        sb.append("  previewImageUrl: ").append(previewImageUrl).append("\n");
        sb.append("  bookingDate: ").append(bookingDate).append("\n");
        sb.append("  bookingTime: ").append(bookingTime).append("\n");
        sb.append("  carId: ").append(carId).append("\n");
        sb.append("  carName: ").append(carName).append("\n");
        sb.append("  isCancellable: ").append(isCancellable).append("\n");
        sb.append("  duration: ").append(duration).append("\n");
        sb.append("  price: ").append(price).append("\n");
        sb.append("  boxName: ").append(boxName).append("\n");
        sb.append("  services: ").append(services).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
