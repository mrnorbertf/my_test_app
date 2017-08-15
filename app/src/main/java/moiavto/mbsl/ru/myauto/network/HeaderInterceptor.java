package moiavto.mbsl.ru.myauto.network;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import static moiavto.mbsl.ru.myauto.network.ApiConstants.*;

/**
 * Created by Fedor on 28.06.2017.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}