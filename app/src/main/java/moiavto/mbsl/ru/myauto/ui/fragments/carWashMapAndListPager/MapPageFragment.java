package moiavto.mbsl.ru.myauto.ui.fragments.carWashMapAndListPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.maps.android.clustering.ClusterManager;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.map.CustomInfoWindowAdapter;
import moiavto.mbsl.ru.myauto.common.map.MyClusterItem;
import moiavto.mbsl.ru.myauto.common.map.MyIconRender;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentPagerMapBinding;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnLocationRequestListener;
import moiavto.mbsl.ru.myauto.ui.fragments.CompanyDetailFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.MapPageFragmentPresenter;
import moiavto.mbsl.ru.myauto.ui.views.MapPageView;
import timber.log.Timber;

import java.util.List;

/**
 * Created by Fedor on 22.03.2017.
 */
public class MapPageFragment extends BaseFragment implements
        MapPageView,
        OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    public final static String TAG = MapPageFragment.class.getSimpleName();
    private static final int REQUEST_LOCATION = 101;

    @InjectPresenter
    MapPageFragmentPresenter presenter;

    private FragmentPagerMapBinding binding;

    private GoogleMap googleMap;
    private ClusterManager<MyClusterItem> clusterManager;
    private OnLocationRequestListener locationListener;

    public static MapPageFragment newInstance() {
        return new MapPageFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationListener = (OnLocationRequestListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager_map, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMap();
    }

    private void setMap() {
        if (googleMap == null) {
            SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
            FragmentTransaction fragmentTransaction =
                    getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_map, mMapFragment);
            fragmentTransaction.commit();
            mMapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        setClusterManager();

        setMapUI(googleMap);

        Location location = locationListener.getLastLocation();
        presenter.setLocation(location);

        VisibleRegion visibleRegion = googleMap.getProjection().getVisibleRegion();
        presenter.loadData(visibleRegion);
    }

    private void setClusterManager() {
        clusterManager = new ClusterManager<>(appContext, googleMap);
        clusterManager.setRenderer(new MyIconRender(
                appContext, googleMap, clusterManager));
        clusterManager.setOnClusterItemInfoWindowClickListener(myClusterItem ->
                fragmentSelector.onFragmentSelected(CompanyDetailFragment.TAG_FACTORY,
                        new CompanyIdModel(myClusterItem.getCompany().getCompanyId())));
        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowAdapter());
    }

    private void setMapUI(GoogleMap googleMap) {
        googleMap.setOnInfoWindowClickListener(clusterManager);
        googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setOnCameraIdleListener(this);
        googleMap.setOnMarkerClickListener(clusterManager);

        setMyLocationButton(googleMap);
    }

    @Override
    public void showUserLocation(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }


    @Override
    public void addMarkersOnMap(List<CompanyModel> companyModels) {
        clusterManager.clearItems();
        for (CompanyModel companyModel :
                companyModels) {
            clusterManager.addItem(new MyClusterItem(companyModel));
        }

        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowAdapter());
        clusterManager.cluster();
    }

    private void setMyLocationButton(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            Timber.i(TAG,
                    "Location permission has already been granted. Displaying location preview.");
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Timber.e(TAG, "permission denied, show dialog");
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
            }
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            Timber.i("Received response for Location permission request.");
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Timber.i("Location permission has now been granted. Showing preview.");
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Timber.i("Location permission was NOT granted.");
                Toast.makeText(getContext(), getString(R.string.msg_permission),
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void startLoading() {
        binding.progressBar.defaultProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishLoading() {
        binding.progressBar.defaultProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finishLoading();
        binding.unbind();
    }

    @Override
    public void onCameraIdle() {
        presenter.loadData(googleMap.getProjection().getVisibleRegion());
    }
}
