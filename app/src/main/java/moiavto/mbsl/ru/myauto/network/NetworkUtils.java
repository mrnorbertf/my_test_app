package moiavto.mbsl.ru.myauto.network;


import android.support.annotation.NonNull;
import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

/**
 * Created by Fedor on 27.06.2017.
 */

public class NetworkUtils {

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> CompletableTransformer applyCompletableSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    static long calculatePing(long time) {
        return time - System.currentTimeMillis();
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(File file) {
        return prepareFilePart("file", file);
    }

    @NonNull
    public static MultipartBody.Part prepareTextPart(String field, String text) {
        return MultipartBody.Part.createFormData(field, text);
    }

}
