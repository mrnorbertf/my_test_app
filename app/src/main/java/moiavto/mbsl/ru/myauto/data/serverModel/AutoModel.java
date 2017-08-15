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

import java.io.Serializable;

/**
 * Модель автомобиля
 **/
@ApiModel(description = "Модель автомобиля")
public class AutoModel implements Serializable {

    @SerializedName("autoId")
    private Integer autoId = null;
    @SerializedName("number")
    private String number = null;
    @SerializedName("autoMarkId")
    private Integer autoMarkId = null;
    @SerializedName("autoModelId")
    private Integer autoModelId = null;
    @SerializedName("autoBodyTypeId")
    private Integer autoBodyTypeId = null;
    @SerializedName("autoColorId")
    private Integer autoColorId = null;
    @SerializedName("year")
    private Integer year = null;

    public AutoModel() {
    }

    public AutoModel(Integer autoId, String number, Integer autoMarkId, Integer autoModelId, Integer autoBodyTypeId, Integer autoColorId, Integer year) {
        this.autoId = autoId;
        this.number = number;
        this.autoMarkId = autoMarkId;
        this.autoModelId = autoModelId;
        this.autoBodyTypeId = autoBodyTypeId;
        this.autoColorId = autoColorId;
        this.year = year;
    }

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


    public Integer getAutoMarkId() {
        return autoMarkId;
    }

    public void setAutoMarkId(Integer autoMarkId) {
        this.autoMarkId = autoMarkId;
    }

    /**
     * Идентификатор модели автомобиля
     **/
    @ApiModelProperty(required = true, value = "Идентификатор модели автомобиля")
    public Integer getAutoModelId() {
        return autoModelId;
    }

    public void setAutoModelId(Integer autoModelId) {
        this.autoModelId = autoModelId;
    }

    /**
     * Идентификатор типа кузова автомобиля
     **/
    @ApiModelProperty(required = true, value = "Идентификатор типа кузова автомобиля")
    public Integer getAutoBodyTypeId() {
        return autoBodyTypeId;
    }

    public void setAutoBodyTypeId(Integer autoBodyTypeId) {
        this.autoBodyTypeId = autoBodyTypeId;
    }

    /**
     * Идентификатор цвета автомобиля
     **/
    @ApiModelProperty(required = true, value = "Идентификатор цвета автомобиля")
    public Integer getAutoColorId() {
        return autoColorId;
    }

    public void setAutoColorId(Integer autoColorId) {
        this.autoColorId = autoColorId;
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
        AutoModel autoModel = (AutoModel) o;
        return (this.autoId == null ? autoModel.autoId == null : this.autoId.equals(autoModel.autoId)) &&
                (this.number == null ? autoModel.number == null : this.number.equals(autoModel.number)) &&
                (this.autoMarkId == null ? autoModel.autoMarkId == null : this.autoMarkId.equals(autoModel.autoMarkId)) &&
                (this.autoModelId == null ? autoModel.autoModelId == null : this.autoModelId.equals(autoModel.autoModelId)) &&
                (this.autoBodyTypeId == null ? autoModel.autoBodyTypeId == null : this.autoBodyTypeId.equals(autoModel.autoBodyTypeId)) &&
                (this.autoColorId == null ? autoModel.autoColorId == null : this.autoColorId.equals(autoModel.autoColorId)) &&
                (this.year == null ? autoModel.year == null : this.year.equals(autoModel.year));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.autoId == null ? 0 : this.autoId.hashCode());
        result = 31 * result + (this.number == null ? 0 : this.number.hashCode());
        result = 31 * result + (this.autoMarkId == null ? 0 : this.autoMarkId.hashCode());
        result = 31 * result + (this.autoModelId == null ? 0 : this.autoModelId.hashCode());
        result = 31 * result + (this.autoBodyTypeId == null ? 0 : this.autoBodyTypeId.hashCode());
        result = 31 * result + (this.autoColorId == null ? 0 : this.autoColorId.hashCode());
        result = 31 * result + (this.year == null ? 0 : this.year.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AutoDomainModel {\n");

        sb.append("  autoId: ").append(autoId).append("\n");
        sb.append("  number: ").append(number).append("\n");
        sb.append("  autoMarkId: ").append(autoMarkId).append("\n");
        sb.append("  autoModelId: ").append(autoModelId).append("\n");
        sb.append("  autoBodyTypeId: ").append(autoBodyTypeId).append("\n");
        sb.append("  autoColorId: ").append(autoColorId).append("\n");
        sb.append("  year: ").append(year).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}