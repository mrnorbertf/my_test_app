package moiavto.mbsl.ru.myauto.ui.views;

import moiavto.mbsl.ru.myauto.data.serverModel.HelpModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

import java.util.List;

/**
 * Created by Fedor on 14.07.2017.
 */

public interface HelpListView extends BaseView {
    void showData(List<HelpModel> list);

    void setEmptyList();

    void hideEmptyList();
}
