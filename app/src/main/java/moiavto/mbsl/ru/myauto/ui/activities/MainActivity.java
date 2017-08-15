package moiavto.mbsl.ru.myauto.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.viewUtils.BottomNavigationViewHelper;
import moiavto.mbsl.ru.myauto.services.MessageReceivingService;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnBackPressedListener;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnFragmentSelectorListener;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnLocationRequestListener;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnLogoutListener;
import moiavto.mbsl.ru.myauto.ui.fragments.BookingsFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.FavoritesFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.HelpFragments.HelpFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.SettingsFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.carWashMapAndListPager.CarWashesPagerFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.MainPresenter;
import moiavto.mbsl.ru.myauto.ui.presenters.MainRouterPresenter;
import moiavto.mbsl.ru.myauto.ui.views.MainView;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView,
        OnLocationRequestListener,
        OnBackPressedListener,
        OnLogoutListener,
        OnFragmentSelectorListener,
        BottomNavigationView.OnNavigationItemSelectedListener,
        FragmentManager.OnBackStackChangedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String ARG_FACTORY_TAG = "hot_start_factory_tag";
    private static final String ARG_INNER_TAG = "hot_start_inner_data";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNav;
    @BindView(R.id.defaultToolbar)
    Toolbar toolbar;
    @InjectPresenter
    MainPresenter presenter;
    @InjectPresenter
    MainRouterPresenter routerPresenter;
    private FragmentManager fragmentManager;

    private GoogleApiClient client;
    private Location lastLocation;

    public static Intent newInstance(Context context) {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return mainActivityIntent;
    }

    public static Intent newInstancePush(Context context, String fragmentTag, Object arguments) {
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return mainActivityIntent;
    }

    @ProvidePresenter(type = PresenterType.LOCAL)
    MainPresenter provideMainPresenter() {
        MainPresenter presenter = new MainPresenter();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String factoryTag = extras.getString(ARG_FACTORY_TAG);
            Object innerData = extras.getSerializable(ARG_INNER_TAG);
            if (factoryTag != null)
                presenter.setStartFragment(factoryTag, innerData);
        }
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        registerPushService();

        buildGoogleApiClient();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        initializeUI();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (client != null) client.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (client != null && client.isConnected()) client.disconnect();
    }

    private void registerPushService() {
        startService(new Intent(this, MessageReceivingService.class));
    }

    private void buildGoogleApiClient() {
        try {
            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        } catch (Exception e) {
            Timber.e("client build failed");
            client.connect();
        }
    }

    private void initializeUI() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public void startUserRegistration() {
        startActivity(LoginActivity.newInstance(this));
        finish();
    }

    @Override
    public void showCurrentFragment(Fragment desireFragment, String tag) {
        boolean isPop = fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (!isPop) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main_fragment_container, desireFragment, tag);
            ft.addToBackStack(tag);
            ft.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Timber.i("bottom Item selected: ", item.getItemId());
        clearFragmentBackStack();
        switch (item.getItemId()) {
            case R.id.action_washes:
                routerPresenter.showSupposedFragment(CarWashesPagerFragment.TAG, null);
                break;
            case R.id.action_favorites:
                routerPresenter.showSupposedFragment(FavoritesFragment.TAG, null);
                break;
            case R.id.action_enrolls:
                routerPresenter.showSupposedFragment(BookingsFragment.TAG, null);
                break;
            case R.id.action_profile:
                routerPresenter.showSupposedFragment(SettingsFragment.TAG, null);
                break;
            case R.id.action_help:
                routerPresenter.showSupposedFragment(HelpFragment.TAG, null);
                break;
            default:
                routerPresenter.showSupposedFragment(CarWashesPagerFragment.TAG, null);
        }
        return true;
    }

    private void clearFragmentBackStack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackStackChanged() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (fragmentManager.getBackStackEntryCount() > 1) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.icon_back_white_arrow);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }


    @Override
    public void onFragmentSelected(String tag, Object data) {
        routerPresenter.showSupposedFragment(tag, data);
    }


    @Override
    public void onFragmentSelected(String tag) {
        onFragmentSelected(tag, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MessageReceivingService.class));
    }

    @Override
    public void logout() {
        presenter.startLogin();
    }

    @Override
    public void onDoBackPressed() {
        onBackPressed();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.i("GoogleApiClient connection has suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.i("GoogleApiClient connection has failed");
        Timber.e(connectionResult.toString());
        Timber.e("error message " + connectionResult.getErrorMessage());
        client.connect();
    }

    @Override
    public Location getLastLocation() {
        try {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);
        } catch (Exception e){
            Timber.e(e.getMessage());
            e.printStackTrace();
        }
        Timber.i("LastLocation: ", lastLocation != null ? lastLocation : null);
        return lastLocation;
    }
}
