package moiavto.mbsl.ru.myauto.common.map;


import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterItem;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;

/**
 * Created by Fedor on 08.08.2017.
 */

public class MyClusterItem implements ClusterItem {
    private final LatLng mPosition;
    private final CompanyModel company;
    private MarkerOptions marker;

    public MyClusterItem(CompanyModel companyModel) {
        Gson gson = new Gson();
        this.mPosition = new LatLng(companyModel.getLat(), companyModel.getLng());
        this.company = companyModel;
        setMarker(new MarkerOptions()
                .position(new LatLng(companyModel.getLat(), companyModel.getLng()))
                .snippet(gson.toJson(companyModel))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_map_marker))
                .flat(true)
        );
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public void setMarker(MarkerOptions marker) {
        this.marker = marker;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return company.getName();
    }

    @Override
    public String getSnippet() {
        return marker.getSnippet();
    }

    public CompanyModel getCompany() {
        return company;
    }
}
