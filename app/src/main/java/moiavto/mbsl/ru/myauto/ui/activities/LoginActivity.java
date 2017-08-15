package moiavto.mbsl.ru.myauto.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnBackPressedListener;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnFragmentSelectorListener;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnSignInCompleteListener;
import moiavto.mbsl.ru.myauto.ui.presenters.LoginPresenter;
import moiavto.mbsl.ru.myauto.ui.views.LoginView;

/**
 * Created by Fedor on 19.06.2017.
 */
public class LoginActivity extends MvpAppCompatActivity implements LoginView,
        OnBackPressedListener,
        OnFragmentSelectorListener,
        OnSignInCompleteListener {

    @InjectPresenter
    LoginPresenter presenter;

    private FragmentManager fragmentManager;

    public static Intent newInstance(Context context) {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return loginIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.LoginTheme);
        setContentView(R.layout.activity_login);

        fragmentManager = getSupportFragmentManager();
    }


    @Override
    public void showCurrentFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(tag);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragmentManager.getBackStackEntryCount() == 0) {
            finish();
        }
    }

    @Override
    public void onSignInComplete() {
        startActivity(MainActivity.newInstance(this));
        finish();
    }

    @Override
    public void onFragmentSelected(String tag, Object data) {
        presenter.showSupposedFragment(tag, data);
    }

    @Override
    public void onFragmentSelected(String tag) {
        onFragmentSelected(tag, null);
    }

    @Override
    public void onDoBackPressed() {

    }
}
