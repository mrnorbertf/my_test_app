package moiavto.mbsl.ru.myauto.ui.baseComponents;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.google.gson.Gson;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.data.serverModel.ErrorModel;
import retrofit2.HttpException;

import java.io.IOException;

/**
 * Created by Fedor on 22.03.2017.
 */
public abstract class BasePresenter<View extends MvpView> extends MvpPresenter<View> {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    protected boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyAutoApp.getAppComponent().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    protected void isNetworkConnected(InternetSuccessListener listener, BaseView view) {
        if (isNetworkConnected())
            listener.internetSuccessListener();
        else
            view.showMsg(R.string.error_no_internet_connection);
    }

    protected String getThrowableMessage(Throwable throwable) {
        if (throwable instanceof HttpException) {
            try {
                ErrorModel errorModel = (new Gson()).fromJson(((HttpException) throwable).response().errorBody().string(), ErrorModel.class);
                return errorModel.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return throwable.getLocalizedMessage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


    @FunctionalInterface
    public interface InternetSuccessListener {
        void internetSuccessListener();
    }
}
