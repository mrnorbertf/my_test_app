package moiavto.mbsl.ru.myauto.common;

import com.google.gson.Gson;

import static moiavto.mbsl.ru.myauto.common.SharedPrefsHelper.*;

/**
 * Created by Fedor on 14.06.2017.
 */
public class DataManager {
    private Gson gson;

    private SharedPrefsHelper sharedPrefsHelper;

    public DataManager(SharedPrefsHelper sharedPrefsHelper, Gson gson) {
        this.sharedPrefsHelper = sharedPrefsHelper;
        this.gson = gson;
    }


    public void saveAppId(String appId) {
        sharedPrefsHelper.put(PREF_KEY_APP_ID, appId);
    }

    public String getAppId() {
        return sharedPrefsHelper.get(PREF_KEY_APP_ID, null);
    }

    ////////////////////////
    public void saveAppSecret(String appSecret) {
        sharedPrefsHelper.put(PREF_KEY_APP_SECRET, appSecret);
    }

    public String getAppSecret() {
        return sharedPrefsHelper.get(PREF_KEY_APP_SECRET, null);
    }

    //////////////////////////////////
    public void saveAuthTime(long authTime) {
        sharedPrefsHelper.put(PREF_KEY_AUTH_TIME, authTime);
    }

    public Long getAuthTime() {
        return sharedPrefsHelper.get(PREF_KEY_AUTH_TIME, System.currentTimeMillis());
    }

    ///////////////////////
    public <T> void saveDataClass(Object dataObject, Class<T> dataClass) {
        String body = gson.toJson(dataObject);
        sharedPrefsHelper.put(dataClass.getSimpleName(), body);
    }

    public <T> T getDataClass(Class<T> dataClass) {
        String body = sharedPrefsHelper.get(dataClass.getSimpleName(), null);
        return gson.fromJson(body, dataClass);
    }


    //////////////////////////////////
    public void savePushToken(String pushToken) {
        sharedPrefsHelper.put(PREF_KEY_PUSH_TOKEN, pushToken);
    }

    public String getPushToken() {
        return sharedPrefsHelper.get(PREF_KEY_PUSH_TOKEN, "");
    }

    public void deleteAllData() {
        sharedPrefsHelper.deleteAllData();
    }

    public void logout() {
        sharedPrefsHelper.deleteAllDataWithoutPushToken();
    }

}
