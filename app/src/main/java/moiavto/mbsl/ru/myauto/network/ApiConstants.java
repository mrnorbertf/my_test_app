package moiavto.mbsl.ru.myauto.network;

/**
 * Created by Fedor on 09.04.2017.
 * constants for server
 */
public class ApiConstants {
    // -------- BASE -------- //
    public static final String API_BASE_URL = "http://api.moiavto.kz/mobile/v10/";
    //    public static final String API_BASE_URL = "http://test.moiavto.ru/api/mobile/v10/";
    // -------- Headers -------- //
    public static final String APPLICATION_JSON_CHARSET_UTF = "application/json; charset=utf-8";
    public static final String X_AUTH_TIME = "X-Auth-Time";
    public static final String X_AUTH_SIGNATURE = "X-Auth-Signature";
    public static final String X_AUTH_METHOD = "X-Auth-Method";
    public static final String X_AUTH_APP_ID = "X-Auth-AppId";
    public static final String AUTH_METHOD = "sha512mob";//
    public static final String ACCEPT_APPLICATION_JSON = "Accept: application/json";
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ORIGINAL_FILE_NAME = "originalFileName";
    public static final String FILE_EXT = "fileExt";
    ////////////////////////////////////////////
    // -------- Application Management-------- //
    ////////////////////////////////////////////
    public static final String APP_PING = "app/ping";
    ////////////////////////////////////////////
    // -------- Application Management-------- //
    ////////////////////////////////////////////
    private static final String API_ACCOUNT = "account";
    static final String API_ACCOUNT_CREATE = API_ACCOUNT + "/create";
    static final String API_ACCOUNT_CONFIRM = API_ACCOUNT + "/confirm";
    static final String API_ACCOUNT_REVALIDATE = API_ACCOUNT + "/revalidate";
    static final String API_ACCOUNT_REGISTER_DEVICE = API_ACCOUNT + "/registerDevice";
    static final String API_ACCOUNT_LOGOUT = API_ACCOUNT + "/logout";
    static final String API_ACCOUNT_DELETE = API_ACCOUNT + "/delete";
    static final String API_ACCOUNT_GET_INFO = API_ACCOUNT + "/getInfo";
    static final String API_ACCOUNT_EDIT_INFO = API_ACCOUNT + "/editInfo";
    static final String API_ACCOUNT_UPLOAD_AVATAR = API_ACCOUNT + "/uploadAvatar";
    ////////////////////////////////////////////
    // -------- COMPANY -------- //
    ////////////////////////////////////////////
    private static final String API_COMPANY = "company";
    static final String API_COMPANY_GET = API_COMPANY + "/get";
    static final String API_COMPANY_LIST = API_COMPANY + "/list";
    static final String API_COMPANY_LIST_FEATURES = API_COMPANY + "/listFeatures";
    static final String API_COMPANY_ADD_TO_FAVORITES = API_COMPANY + "/addToFavorites";
    static final String API_COMPANY_REMOVE_FROM_FAVORITES = API_COMPANY + "/removeFromFavorites";
    static final String API_COMPANY_INCREASE_CALLS_COUNTER = API_COMPANY + "/increaseCallsCounter";
    static final String API_COMPANY_INCREASE_ROUTERS_COUNTER = API_COMPANY + "/increaseRoutesCounter";
    ////////////////////////////////////////////
    // -------- REVIEW -------- //
    ////////////////////////////////////////////
    private static final String API_REVIEW = "review";
    static final String API_REVIEW_LIST = API_REVIEW + "/list";
    static final String API_REVIEW_CREATE = API_REVIEW + "/create";
    ////////////////////////////////////////////
    // -------- BOOKING -------- //
    ////////////////////////////////////////////
    private static final String API_BOOKING = "booking";
    static final String API_BOOKING_PREPARE = API_BOOKING + "/prepare";
    static final String API_BOOKING_CREATE = API_BOOKING + "/create";
    static final String API_BOOKING_LIST = API_BOOKING + "/list";
    static final String API_BOOKING_CANCEL = API_BOOKING + "/cancel";
    ////////////////////////////////////////////
    // -------- BOOKING -------- //
    ////////////////////////////////////////////
    private static final String API_AUTO = "auto";
    static final String API_AUTO_GET = API_AUTO + "/get";
    static final String API_AUTO_LIST = API_AUTO + "/list";
    static final String API_AUTO_ADD = API_AUTO + "/add";
    static final String API_AUTO_EDIT = API_AUTO + "/edit";
    static final String API_AUTO_DELETE = API_AUTO + "/delete";
    static final String API_AUTO_LIST_MARKS = API_AUTO + "/listMarks";
    static final String API_AUTO_LIST_MODELS = API_AUTO + "/listModels";
    static final String API_AUTO_LIST_BODY_TYPES = API_AUTO + "/listBodyTypes";
    static final String API_AUTO_LIST_COLORS = API_AUTO + "/listColors";
    ////////////////////////////////////////////
    // -------- BOOKING -------- //
    ////////////////////////////////////////////
    private static final String API_HELP = "help";
    static final String API_HELP_LIST = API_HELP + "/list";
    static final String API_HELP_GET = API_HELP + "/get";

}
