package moiavto.mbsl.ru.myauto.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.text.TextUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import timber.log.Timber;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by Fedor on 30.05.2017.
 */

public class MessageReceivingService extends Service {
    @Inject
    DataManager dataManager;
    private GoogleCloudMessaging gcm;

    public void onCreate() {
        super.onCreate();
        MyAutoApp.getAppComponent().inject(this);
        gcm = GoogleCloudMessaging.getInstance(getBaseContext());

        register();
    }

    private void register() {
        GetAmazonID task = new GetAmazonID();
        task.execute(null, null, null);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    private class GetAmazonID extends AsyncTask<Boolean, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Boolean... params) {
            String token;
            try {
                token = gcm.register(getString(R.string.project_number));
                if (!TextUtils.isEmpty(token)) {
                    dataManager.savePushToken(token);
                }
                Timber.i("registrationId", token);
            } catch (IOException e) {
                Timber.i("Registration Error", e.getMessage());
            }
            return true;
        }
    }
}
