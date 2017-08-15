package moiavto.mbsl.ru.myauto.ui.views;

import android.location.Location;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

import java.util.List;

/**
 * Created by Fedor on 28.06.2017.
 */

public interface MapPageView extends BaseView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void addMarkersOnMap(List<CompanyModel> companyModels);

    @StateStrategyType(SkipStrategy.class)
    void showUserLocation(Location location);
}
