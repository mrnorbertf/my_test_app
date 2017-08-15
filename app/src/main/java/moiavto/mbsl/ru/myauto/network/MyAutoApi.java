package moiavto.mbsl.ru.myauto.network;

import io.reactivex.Completable;
import io.reactivex.Observable;
import moiavto.mbsl.ru.myauto.data.serverModel.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Fedor on 14.06.2017.
 */

public interface MyAutoApi {

    //не подписанный метод
    @POST(ApiConstants.APP_PING)
    Observable<AppPingModel> getAppPing();

    //подписанный метод
    @POST(ApiConstants.APP_PING)
    Observable<AppPingModel> getAppPing(
            @HeaderMap Map<String, String> headers);


    ////////////////////////////////////////////
    // -------- Account-------- //
    ////////////////////////////////////////////
    @POST(ApiConstants.API_ACCOUNT_CREATE)
    Observable<AccountCreateResponseModel> getAccountCreate(
            @Body RequestBody requestBody);

    @POST(ApiConstants.API_ACCOUNT_REVALIDATE)
    Completable getAccountRevalidate(@HeaderMap Map<String, String> headers,
                                     @Body RequestBody requestBody);

    @POST(ApiConstants.API_ACCOUNT_REGISTER_DEVICE)
    Completable getAccountRegisterDevice(@HeaderMap Map<String, String> headers,
                                         @Body RequestBody requestBody);

    @POST(ApiConstants.API_ACCOUNT_GET_INFO)
    Observable<AccountInfoModel> getAccountInfo(@HeaderMap Map<String, String> headers);

    @POST(ApiConstants.API_ACCOUNT_EDIT_INFO)
    Completable editAccountInfo(@HeaderMap Map<String, String> headers,
                                @Body RequestBody requestBody);

    @POST(ApiConstants.API_ACCOUNT_LOGOUT)
    Completable logout(@HeaderMap Map<String, String> headers);

    @Multipart
    @POST(ApiConstants.API_ACCOUNT_UPLOAD_AVATAR)
    Observable<AccountUploadAvatarResponseModel> uploadAvatar(@HeaderMap Map<String, String> headers,
                                                              @Part MultipartBody.Part fileExt, @Part MultipartBody.Part fileName, @Part MultipartBody.Part file);

    @Multipart
    @POST(ApiConstants.API_ACCOUNT_UPLOAD_AVATAR)
    Observable<AccountUploadAvatarResponseModel> uploadAvatar(@HeaderMap Map<String, String> headers,
                                                              @Part MultipartBody.Part file);
    ////////////////////////////////////////////
    // -------- Company-------- //
    ////////////////////////////////////////////
    @POST(ApiConstants.API_COMPANY_GET)
    Observable<CompanyModel> getCompany(@HeaderMap Map<String, String> headers,
                                        @Body RequestBody body);

    @POST(ApiConstants.API_COMPANY_LIST)
    Observable<List<CompanyModel>> getCompanyList(@HeaderMap Map<String, String> headers,
                                                  @Body RequestBody body);

    @POST(ApiConstants.API_COMPANY_ADD_TO_FAVORITES)
    Completable addToFavorites(@HeaderMap Map<String, String> headers,
                               @Body RequestBody body);

    @POST(ApiConstants.API_COMPANY_REMOVE_FROM_FAVORITES)
    Completable removeFromFavorites(@HeaderMap Map<String, String> headers,
                                                  @Body RequestBody body);

    @POST(ApiConstants.API_COMPANY_INCREASE_CALLS_COUNTER)
    Completable increaseCallsCounter(@HeaderMap Map<String, String> headers,
                                     @Body RequestBody body);

    @POST(ApiConstants.API_COMPANY_INCREASE_ROUTERS_COUNTER)
    Completable increaseRoutesCounter(@HeaderMap Map<String, String> headers,
                                      @Body RequestBody body);


    ////////////////////////////////////////////
    // -------- Reviews-------- //
    ////////////////////////////////////////////
    @POST(ApiConstants.API_REVIEW_LIST)
    Observable<ReviewListResponseModel> getReviews(@HeaderMap Map<String, String> headers,
                                                   @Body RequestBody body);

    @POST(ApiConstants.API_REVIEW_CREATE)
    Completable createReview(@HeaderMap Map<String, String> headers,
                             @Body RequestBody body);

    ////////////////////////////////////////////
    // -------- Bookings-------- //
    ////////////////////////////////////////////
    @POST(ApiConstants.API_BOOKING_PREPARE)
    Observable<BookingPrepareResponseModel> getBookingPrepare(@HeaderMap Map<String, String> headers,
                                                              @Body RequestBody body);


    @POST(ApiConstants.API_BOOKING_CREATE)
    Observable<BookingCreateResponseModel> bookingCreate(@HeaderMap Map<String, String> headers,
                                                         @Body RequestBody body);

    @POST(ApiConstants.API_BOOKING_LIST)
    Observable<List<BookingModel>> getBookings(@HeaderMap Map<String, String> headers);

    @POST(ApiConstants.API_BOOKING_CANCEL)
    Completable cancelBooking(@HeaderMap Map<String, String> headers,
                              @Body RequestBody body);


    ////////////////////////////////////////////
    // -------- Bookings-------- //
    ////////////////////////////////////////////
    @POST(ApiConstants.API_HELP_LIST)
    Observable<List<HelpModel>> getHelpList(@HeaderMap Map<String, String> headers);

    ////////////////////////////////////////////
    // -------- Auto-------- //
    ////////////////////////////////////////////
    @POST(ApiConstants.API_AUTO_GET)
    Observable<AutoModel> getAuto(@HeaderMap Map<String, String> headers,
                                  @Body RequestBody body);

    @POST(ApiConstants.API_AUTO_LIST)
    Observable<List<AutoListResponseModel>> getAutoList(@HeaderMap Map<String, String> headers);

    @POST(ApiConstants.API_AUTO_ADD)
    Completable addAuto(@HeaderMap Map<String, String> headers,
                        @Body RequestBody body);

    @POST(ApiConstants.API_AUTO_EDIT)
    Completable editAuto(@HeaderMap Map<String, String> headers,
                         @Body RequestBody body);

    @POST(ApiConstants.API_AUTO_DELETE)
    Completable deleteAuto(@HeaderMap Map<String, String> headers,
                           @Body RequestBody body);

    @POST(ApiConstants.API_AUTO_LIST_MARKS)
    Observable<List<AutoMarkModel>> getAutoListMarks(@HeaderMap Map<String, String> headers);

    @POST(ApiConstants.API_AUTO_LIST_MODELS)
    Observable<List<AutoModelModel>> getAutoListModels(@HeaderMap Map<String, String> headers,
                                                       @Body RequestBody body);

    @POST(ApiConstants.API_AUTO_LIST_BODY_TYPES)
    Observable<List<AutoBodyTypeModel>> getAutoListBodyTypes(@HeaderMap Map<String, String> headers,
                                                             @Body RequestBody body);

    @POST(ApiConstants.API_AUTO_LIST_COLORS)
    Observable<List<AutoColorModel>> getAutoListColors(@HeaderMap Map<String, String> headers);


}
