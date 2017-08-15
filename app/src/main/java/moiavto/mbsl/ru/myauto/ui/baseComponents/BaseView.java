package moiavto.mbsl.ru.myauto.ui.baseComponents;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Fedor on 27.06.2017.
 */

public interface BaseView extends MvpView {
    void startLoading();

    void finishLoading();

    @StateStrategyType(SkipStrategy.class)
    void showMsg(String msg);

    @StateStrategyType(SkipStrategy.class)
    void showMsg(int msg);
}
