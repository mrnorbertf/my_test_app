package moiavto.mbsl.ru.myauto.application;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import io.fabric.sdk.android.Fabric;
import moiavto.mbsl.ru.myauto.BuildConfig;
import moiavto.mbsl.ru.myauto.di.AppComponent;
import moiavto.mbsl.ru.myauto.di.DaggerAppComponent;
import moiavto.mbsl.ru.myauto.di.modules.ContextModule;
import timber.log.Timber;

/**
 * Created by Fedor on 14.06.2017.
 */
//public class MyAutoApp extends Application {
public class MyAutoApp extends MultiDexApplication {

    private static MyAutoApp sInstance;
    private static AppComponent sAppComponent;
    private RefWatcher refWatcher;

    public static MyAutoApp getInstance() {
        return sInstance;
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    public static RefWatcher getRefWatcher(Context context) {
        return sInstance.refWatcher;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        turnOnStrictMode();
        sInstance = this;

        initFabric();
        initTimber();
        initDagger();

        refWatcher = LeakCanary.install(this);
    }

    private void turnOnStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .permitDiskWrites()
                    .permitDiskReads()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    //                    .penaltyDeath()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }

    private void initDagger() {
        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    /**
     * this function disable crash logging in debug mode
     */
    private void initFabric() {
        CrashlyticsCore core = new CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        }
        Timber.plant(new CrashlyticsTree());
    }
}
