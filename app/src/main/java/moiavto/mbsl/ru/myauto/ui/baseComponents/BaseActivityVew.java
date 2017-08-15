package moiavto.mbsl.ru.myauto.ui.baseComponents;

import android.support.v4.app.Fragment;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Fedor on 22.06.2017.
 */

public interface BaseActivityVew extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void showCurrentFragment(Fragment fragment, String tag);
}
