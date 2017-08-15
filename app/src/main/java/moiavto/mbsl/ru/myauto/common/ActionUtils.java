package moiavto.mbsl.ru.myauto.common;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;

import java.util.List;

/**
 * Created by Fedor on 14.07.2017.
 */

public class ActionUtils {

    public static void callToCompany(String companyPhone) {
        Intent intent_call = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", companyPhone, null));
        MyAutoApp.getAppComponent().getContext().startActivity(intent_call);
    }

    public static void navigateToCompany(Double lat, Double lng, String address) {
        //try yandex
        // Создаем интент для построения маршрута
        Intent intent = new Intent("ru.yandex.yandexnavi.action.BUILD_ROUTE_ON_MAP");
        intent.setPackage("ru.yandex.yandexnavi");

        PackageManager pm = MyAutoApp.getAppComponent().getContext().getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);

        // Проверяем, установлен ли Яндекс.Навигатор
        if (infos == null || infos.size() == 0) {
            //открываем  гугл картах
            Uri gmmIntentUri = Uri.parse("geo:<" + lat + ">,<" + lng + ">?q=<" + lat + ">,<" + lng + ">(" + address + ")");
            intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        } else {
            // открываем в навигаторе
            intent.putExtra("lat_to", String.valueOf(lat));
            intent.putExtra("lon_to", String.valueOf(lng));
        }

        // Запускаем нужную Activity
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyAutoApp.getAppComponent().getContext().startActivity(intent);
    }
}
