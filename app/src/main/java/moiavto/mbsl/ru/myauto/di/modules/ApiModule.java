package moiavto.mbsl.ru.myauto.di.modules;

import dagger.Module;
import dagger.Provides;
import moiavto.mbsl.ru.myauto.network.MyAutoApi;
import retrofit2.Retrofit;

import javax.inject.Singleton;

/**
 * Created by Fedor on 09.04.2017.
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public MyAutoApi provideApi(Retrofit retrofit) {
        return retrofit.create(MyAutoApi.class);
    }
}
