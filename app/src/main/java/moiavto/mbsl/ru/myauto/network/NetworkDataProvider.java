package moiavto.mbsl.ru.myauto.network;

import android.text.TextUtils;
import com.google.gson.Gson;
import io.reactivex.Completable;
import io.reactivex.Observable;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.data.serverModel.*;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;
import java.util.List;

import static moiavto.mbsl.ru.myauto.network.ApiConstants.APPLICATION_JSON_CHARSET_UTF;

/**
 * Created by Fedor on 14.06.2017.
 */

public class NetworkDataProvider {
    @Inject
    DataManager dataManager;

    @Inject
    Gson gson;

    private MyAutoApi appApi;

    public NetworkDataProvider(MyAutoApi appApi) {
        MyAutoApp.getAppComponent().inject(this);
        this.appApi = appApi;
    }

    Observable<AppPingModel> getAppPing() {
        if (TextUtils.isEmpty(dataManager.getAppId())) {
            return appApi.getAppPing();
        } else {
            return appApi.getAppPing(SignatureHelper.getHeaders(dataManager));
        }
    }


    ////////////////////////////////////////////
    // -------- Account-------- //
    ////////////////////////////////////////////

    public Observable<AccountCreateResponseModel> getAccountCreate(AccountCreateRequestModel body) {
        return appApi.getAccountCreate(getBody(getBodyString(body)));
    }

    public Completable getAccountRevalidate(AccountConfirmRequestModel body) {
        String bodyString = getBodyString(body);
        return appApi.getAccountRevalidate(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }


    public Completable getAccountRegisterDevice(AccountRegisterDeviceModel body) {
        String bodyString = getBodyString(body);
        return appApi.getAccountRegisterDevice(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }


    public Observable<AccountInfoModel> getAccountInfo() {
        return appApi.getAccountInfo(SignatureHelper.getHeaders(dataManager));
    }


    public Completable editAccountInfo(AccountInfoModel body) {
        String bodyString = getBodyString(body);
        return appApi.editAccountInfo(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable logout() {
        return appApi.logout(SignatureHelper.getHeaders(dataManager));
    }

    public Observable<AccountUploadAvatarResponseModel> uploadAvatar(MultipartBody.Part fileExt, MultipartBody.Part fileName, MultipartBody.Part file) {
        return appApi.uploadAvatar(SignatureHelper.getHeaders(dataManager), fileExt, fileName, file);
    }

    public Observable<AccountUploadAvatarResponseModel> uploadAvatar(MultipartBody.Part file) {
        return appApi.uploadAvatar(SignatureHelper.getHeaders(dataManager), file);
    }

    ////////////////////////////////////////////
    // -------- Company-------- //
    ////////////////////////////////////////////

    public Observable<CompanyModel> getCompany(CompanyIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.getCompany(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Observable<List<CompanyModel>> getCompanyList(CompanyListRequestModel body) {
        String bodyString = getBodyString(body);
        return appApi.getCompanyList(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable addToFavorites(CompanyIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.addToFavorites(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable removeFromFavorites(CompanyIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.removeFromFavorites(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable increaseCallsCounter(CompanyIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.increaseCallsCounter(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable increaseRoutesCounter(CompanyIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.increaseRoutesCounter(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    ////////////////////////////////////////////
    // -------- Review-------- //
    ////////////////////////////////////////////
    public Observable<ReviewListResponseModel> getReviews(CompanyIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.getReviews(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable createReview(ReviewCreateRequestModel body) {
        String bodyString = getBodyString(body);
        return appApi.createReview(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    ////////////////////////////////////////////
    // -------- Booking-------- //
    ////////////////////////////////////////////
    public Observable<List<BookingModel>> getBookings() {
        return appApi.getBookings(SignatureHelper.getHeaders(dataManager));
    }

    public Completable cancelBooking(BookingIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.cancelBooking(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Observable<BookingPrepareResponseModel> getBookingPrepare(CompanyIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.getBookingPrepare(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Observable<BookingCreateResponseModel> bookingCreate(BookingCreateRequestModel body) {
        String bodyString = getBodyString(body);
        return appApi.bookingCreate(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }


    ////////////////////////////////////////////
    // -------- Help-------- //
    ////////////////////////////////////////////
    public Observable<List<HelpModel>> getHelpList() {
        return appApi.getHelpList(SignatureHelper.getHeaders(dataManager));
    }


    ////////////////////////////////////////////
    // -------- Auto-------- //
    ////////////////////////////////////////////
    public Observable<AutoModel> getAuto(AutoIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.getAuto(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Observable<List<AutoListResponseModel>> getAutoList() {
        return appApi.getAutoList(SignatureHelper.getHeaders(dataManager));
    }

    public Completable addAuto(AutoModel body) {
        String bodyString = getBodyString(body);
        return appApi.addAuto(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable editAuto(AutoModel body) {
        String bodyString = getBodyString(body);
        return appApi.editAuto(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Completable deleteAuto(AutoIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.deleteAuto(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Observable<List<AutoMarkModel>> getAutoListMarks() {
        return appApi.getAutoListMarks(SignatureHelper.getHeaders(dataManager));
    }

    public Observable<List<AutoModelModel>> getAutoListModels(AutoMarkIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.getAutoListModels(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Observable<List<AutoBodyTypeModel>> getAutoListBodyTypes(AutoModelIdModel body) {
        String bodyString = getBodyString(body);
        return appApi.getAutoListBodyTypes(SignatureHelper.getHeaders(bodyString, dataManager), getBody(bodyString));
    }

    public Observable<List<AutoColorModel>> getAutoListColors() {
        return appApi.getAutoListColors(SignatureHelper.getHeaders(dataManager));
    }


    ////////////////////////////////////////////
    ///////// -------- BODY -------- ////////
    ////////////////////////////////////////////
    private String getBodyString(Object body) {
        return gson.toJson(body);
    }

    private RequestBody getBody(String body) {
        MediaType JSON
                = MediaType.parse(APPLICATION_JSON_CHARSET_UTF);
        return RequestBody.create(JSON, body);
    }


}
