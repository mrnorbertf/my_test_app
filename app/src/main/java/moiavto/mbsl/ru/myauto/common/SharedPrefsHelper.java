package moiavto.mbsl.ru.myauto.common;

import android.content.SharedPreferences;

/**
 * Created by Fedor on 14.06.2017.
 */
public class SharedPrefsHelper {
    static final String PREF_KEY_APP_ID = "app_id";
    static final String PREF_KEY_APP_SECRET = "app_secret";
    static final String PREF_KEY_AUTH_TIME = "x_auth_time";
    static final String PREF_KEY_PUSH_TOKEN = "push_token";
    private SharedPreferences sharedPreferences;

    public SharedPrefsHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void put(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void put(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void put(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void put(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public Integer get(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public Boolean get(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public Long get(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }


    public void deleteAllData() {
        sharedPreferences.edit().clear().apply();
    }

    public void deleteAllDataWithoutPushToken() {
        String pushToken = sharedPreferences.getString(PREF_KEY_PUSH_TOKEN, "");
        deleteAllData();
        put(PREF_KEY_PUSH_TOKEN, pushToken);
    }

    public void deleteSavedData(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}
