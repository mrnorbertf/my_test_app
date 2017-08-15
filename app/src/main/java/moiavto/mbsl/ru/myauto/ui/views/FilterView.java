package moiavto.mbsl.ru.myauto.ui.views;

import moiavto.mbsl.ru.myauto.data.serverModel.CompanyListRequestModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

/**
 * Created by Fedor on 07.07.2017.
 */

public interface FilterView extends BaseView {
    void showFilters(CompanyListRequestModel requestModel);
}
