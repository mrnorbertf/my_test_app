package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

import java.util.List;

/**
 * Created by Fedor on 05.07.2017.
 */

public interface ListView extends BaseView {
    void setCompanyList(List<CompanyModel> companyModels);

    void setEmptyList();

    void hideEmptyList();

    @StateStrategyType(SkipStrategy.class)
    void callToCompany(CompanyModel company);

    @StateStrategyType(SkipStrategy.class)
    void navigateToCompany(CompanyModel company);

    void updateLocation();
}
