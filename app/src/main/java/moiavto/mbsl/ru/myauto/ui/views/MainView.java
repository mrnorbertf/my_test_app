package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseActivityVew;

/**
 * Created by Fedor on 22.06.2017.
 */

public interface MainView extends BaseActivityVew {
    @StateStrategyType(SkipStrategy.class)
    void startUserRegistration();
}
