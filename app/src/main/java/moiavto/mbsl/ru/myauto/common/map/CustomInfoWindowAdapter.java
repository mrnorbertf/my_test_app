package moiavto.mbsl.ru.myauto.common.map;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.databinding.MarkerInfoWindowBinding;

import javax.inject.Inject;

/**
 * Created by Fedor on 05.07.2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    @Inject
    Context context;
    private final MarkerInfoWindowBinding binding;

    public CustomInfoWindowAdapter() {
        MyAutoApp.getAppComponent().inject(this);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = MarkerInfoWindowBinding.inflate(inflater);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return bindView(marker);
    }

    private View bindView(Marker marker) {
        Gson gson = new Gson();
        CompanyModel model = gson.fromJson(marker.getSnippet(), CompanyModel.class);

        if (model != null && !TextUtils.isEmpty(model.getName())) {
            updateView(model);
            return binding.getRoot();
        }
        return null;
    }

    private void updateView(CompanyModel model) {
        binding.washNameTV.setText(model.getName());
        binding.washAddressTV.setText(model.getAddress());
        binding.statusTV.setText(model.getIsVacant() ? context.getString(R.string.open) : context.getString(R.string.close));
        binding.statusTV.setTextColor(model.getIsVacant() ? ContextCompat.getColor(context, R.color.colorGreen) : ContextCompat.getColor(context, R.color.colorRed));
        binding.ratingBar.setRating(model.getRating() != null ? model.getRating().floatValue() : 5);
        binding.priceContentLayout.setVisibility(TextUtils.isEmpty(model.getPriceRange()) ? View.GONE : View.VISIBLE);
        binding.priceCountTv.setText(TextUtils.isEmpty(model.getPriceRange()) ? "" : model.getPriceRange());
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
