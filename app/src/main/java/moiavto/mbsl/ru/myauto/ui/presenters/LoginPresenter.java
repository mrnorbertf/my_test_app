package moiavto.mbsl.ru.myauto.ui.presenters;

import android.support.v4.app.Fragment;
import com.arellomobile.mvp.InjectViewState;
import moiavto.mbsl.ru.myauto.common.FragmentFactory;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BasePresenter;
import moiavto.mbsl.ru.myauto.ui.fragments.SignInFragment;
import moiavto.mbsl.ru.myauto.ui.views.LoginView;

/**
 * Created by Fedor on 22.06.2017.
 */
@InjectViewState
public class LoginPresenter extends BasePresenter<LoginView> {
    private static final String TAG = LoginPresenter.class.getSimpleName();

    private FragmentFactory factory;

    public LoginPresenter() {
        factory = new FragmentFactory();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        showSupposedFragment(SignInFragment.TAG_FACTORY, null);
    }

    public void onFragmentSelected(Fragment fragment, String tag) {
        getViewState().showCurrentFragment(fragment, tag);
    }

    public void showSupposedFragment(String tag, Object data) {
        Fragment fragment = factory.createFragment(tag, data);
        getViewState().showCurrentFragment(fragment, tag);
    }
}
