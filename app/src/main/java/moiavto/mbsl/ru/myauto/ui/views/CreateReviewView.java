package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

/**
 * Created by Fedor on 12.07.2017.
 */

public interface CreateReviewView extends BaseView {
    void hideEmptyError();

    @StateStrategyType(SkipStrategy.class)
    void reviewSendSuccessful();

    void showEmptyError();
}
