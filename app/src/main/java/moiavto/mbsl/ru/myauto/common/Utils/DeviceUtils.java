package moiavto.mbsl.ru.myauto.common.Utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.provider.Settings;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;

/**
 * Created by Fedor on 19.06.2017.
 */

public class DeviceUtils {

    @SuppressLint("HardwareIds")
    public static String getDeviceId() {
        return Settings.Secure.getString(MyAutoApp.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }
}
