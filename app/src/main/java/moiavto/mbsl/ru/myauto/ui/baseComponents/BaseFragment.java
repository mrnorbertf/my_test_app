package moiavto.mbsl.ru.myauto.ui.baseComponents;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.squareup.leakcanary.RefWatcher;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;

import javax.inject.Inject;

/**
 * Created by Fedor on 22.06.2017.
 */

public abstract class BaseFragment extends MvpAppCompatFragment {

    protected OnFragmentSelectorListener fragmentSelector;
    protected OnBackPressedListener backPressedListener;
    protected Context context;
    @Inject
    public Context appContext;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyAutoApp.getAppComponent().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
        backPressedListener = (OnBackPressedListener) context;
        fragmentSelector = (OnFragmentSelectorListener) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = MyAutoApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void showMsg(String msg) {
        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showMsg(int msg) {
        Toast.makeText(appContext, getString(msg), Toast.LENGTH_SHORT).show();
    }

}
