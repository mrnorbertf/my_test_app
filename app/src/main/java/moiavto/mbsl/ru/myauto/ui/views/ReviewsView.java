package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.ReviewListResponseModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

/**
 * Created by Fedor on 11.07.2017.
 */

public interface ReviewsView extends BaseView {
    void showData(ReviewListResponseModel reviews);

    @StateStrategyType(SkipStrategy.class)
    void createReview(CompanyIdModel companyId);


}
