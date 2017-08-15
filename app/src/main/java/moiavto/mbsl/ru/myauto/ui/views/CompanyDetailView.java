package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

/**
 * Created by Fedor on 07.07.2017.
 */

public interface CompanyDetailView extends BaseView {
    void showData(CompanyModel companyModel);

    void addToFavorite();

    void removeFromFavorite();

    @StateStrategyType(SkipStrategy.class)
    void enrollService(CompanyIdModel companyId);

    @StateStrategyType(SkipStrategy.class)
    void callToCompany(CompanyModel company);

    @StateStrategyType(SkipStrategy.class)
    void navigateToCompany(CompanyModel company);
}
