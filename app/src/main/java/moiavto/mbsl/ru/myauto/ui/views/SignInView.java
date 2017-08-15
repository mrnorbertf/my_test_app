package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

/**
 * Created by Fedor on 26.06.2017.
 */

public interface SignInView extends BaseView {
    @StateStrategyType(SkipStrategy.class)
    void showMsg(String s);

    void showFieldError(Integer fieldError);

    @StateStrategyType(SkipStrategy.class)
    void startSignInVerify();
}
