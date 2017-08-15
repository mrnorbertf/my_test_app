package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

/**
 * Created by Fedor on 28.06.2017.
 */

public interface SignInVerifyView extends BaseView {
    void showFieldError(Integer error);

    @StateStrategyType(SkipStrategy.class)
    void finishAuth();
}
