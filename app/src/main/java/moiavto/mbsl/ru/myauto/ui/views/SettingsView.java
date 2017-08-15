package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.data.domainData.SettingsData;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountInfoModel;
import moiavto.mbsl.ru.myauto.data.serverModel.AutoIdModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

/**
 * Created by Fedor on 17.07.2017.
 */

public interface SettingsView extends BaseView {
    void showData(SettingsData data);

    @StateStrategyType(SkipStrategy.class)
    void startAddingCar();

    @StateStrategyType(SkipStrategy.class)
    void startEditCar(AutoIdModel autoIdModel);

    void showProfileEditDialog(AccountInfoModel user);

    void showPhotoDialog();

    void showLogoutMessage();

    void startLogin();

    @StateStrategyType(SkipStrategy.class)
    void hideLogoutDialog();
}
