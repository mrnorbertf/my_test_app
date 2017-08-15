package moiavto.mbsl.ru.myauto.ui.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import moiavto.mbsl.ru.myauto.data.domainData.AutoDomainModel;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseView;

import java.util.List;

/**
 * Created by Fedor on 17.07.2017.
 */

public interface AutoDetailView extends BaseView {
    void updateMenu(boolean isEdit);

    <T> void showDialog(List<T> list);

    void showData(AutoDomainModel autoDomainModel);

    void complete();

    @StateStrategyType(SkipStrategy.class)
    void showAutoNumberFieldError(boolean isShow);
}
