package moiavto.mbsl.ru.myauto.common.map;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Fedor on 08.08.2017.
 */

public class MyIconRender extends DefaultClusterRenderer<MyClusterItem> {

    public MyIconRender(Context context, GoogleMap map,
                           ClusterManager<MyClusterItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyClusterItem item,
                                               MarkerOptions markerOptions) {
        markerOptions.icon(item.getMarker().getIcon());
    }

}