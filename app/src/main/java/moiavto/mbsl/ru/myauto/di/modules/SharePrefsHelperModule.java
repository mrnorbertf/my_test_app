package moiavto.mbsl.ru.myauto.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import moiavto.mbsl.ru.myauto.common.SharedPrefsHelper;

import javax.inject.Singleton;

/**
 * Created by Fedor on 22.06.2017.
 */
@Module
public class SharePrefsHelperModule {
    @Provides
    @Singleton
    public SharedPrefsHelper provideSharedPrefsHelper(SharedPreferences sharedPreferences) {
        return new SharedPrefsHelper(sharedPreferences);
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return context.getSharedPreferences("MyAuto_preferences", Context.MODE_PRIVATE);
    }
}
