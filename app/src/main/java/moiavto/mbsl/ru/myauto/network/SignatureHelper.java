package moiavto.mbsl.ru.myauto.network;

import moiavto.mbsl.ru.myauto.common.DataManager;
import timber.log.Timber;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static moiavto.mbsl.ru.myauto.network.ApiConstants.*;

/**
 * Created by Fedor on 27.06.2017.
 */

public class SignatureHelper {
    private static final String CRYPTO_METHOD_MD5 = "MD5";
    private static final String CRYPTO_METHOD_SHA512 = "SHA-512";

    public static Map<String, String> getHeaders(DataManager dataManager) {
        return getHeaders(null, dataManager);
    }

    public static Map<String, String> getHeaders(String body, DataManager dataManager) {
        Map<String, String> map = new HashMap<>();
        String serverTime = getServerTime(dataManager.getAuthTime());
        map.put(X_AUTH_SIGNATURE, getSignature(body, dataManager.getAppSecret(), serverTime));
        map.put(X_AUTH_APP_ID, dataManager.getAppId());
        map.put(X_AUTH_TIME, serverTime);
        return map;
    }

    private static String getSignature(String body, String appSecret, String serverTime) {
        String signature = body + getCryptoMethod(appSecret, CRYPTO_METHOD_MD5) + serverTime;
        return getCryptoMethod(signature, CRYPTO_METHOD_SHA512);
    }

    private static String getServerTime(Long authTime) {
        return serverTimeFormat(new Date(authTime + System.currentTimeMillis()));
    }

    private static String serverTimeFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);
        return formatter.format(date.getTime());
    }

    // ================= Crypto algorithms =====================
    private static String getCryptoMethod(String input, String method) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(method);
            digest.update(input.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Timber.w("Could not load MessageDigest: method", e);
            e.printStackTrace();
        }
        return "";
    }

}
