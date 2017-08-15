package moiavto.mbsl.ru.myauto.ui.presenters;

import com.arellomobile.mvp.InjectViewState;
import moiavto.mbsl.ru.myauto.common.FragmentFactory;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.views.MainView;
import timber.log.Timber;

/**
 * Created by Fedor on 04.07.2017.
 */

@InjectViewState
public class MainRouterPresenter extends BasePresenter<MainView> {
    private static final String TAG = MainRouterPresenter.class.getSimpleName();

    private FragmentFactory factory;

    public MainRouterPresenter() {
        factory = new FragmentFactory();
    }

    public void showSupposedFragment(String tag, Object argsData) {
        updateData();

        getViewState().showCurrentFragment(factory.createFragment(tag, argsData), tag);
    }

    private void updateData() {
        Timber.d(TAG, "updateData");
    }
}
