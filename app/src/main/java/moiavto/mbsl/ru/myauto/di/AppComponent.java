package moiavto.mbsl.ru.myauto.di;

import android.content.Context;
import dagger.Component;
import moiavto.mbsl.ru.myauto.common.map.CustomInfoWindowAdapter;
import moiavto.mbsl.ru.myauto.di.modules.CheckInternetModule;
import moiavto.mbsl.ru.myauto.di.modules.ContextModule;
import moiavto.mbsl.ru.myauto.di.modules.DataManagerModule;
import moiavto.mbsl.ru.myauto.di.modules.MyAutoModule;
import moiavto.mbsl.ru.myauto.network.NetworkDataProvider;
import moiavto.mbsl.ru.myauto.services.MessageReceivingService;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.FilterFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.*;

import javax.inject.Singleton;

/**
 * Created by Fedor on 14.06.2017.
 */

@Singleton
@Component(modules = {ContextModule.class, MyAutoModule.class, DataManagerModule.class, CheckInternetModule.class,})
public interface AppComponent {
    Context getContext();

    void inject(MainPresenter mainPresenter);

    void inject(SignInPresenter signInPresenter);

    void inject(NetworkDataProvider dataProvider);

    void inject(SignInVerifyPresenter signInVerifyPresenter);

    void inject(MapPageFragmentPresenter mapPageFragmentPresenter);

    void inject(ListPagePresenter listPagePresenter);

    void inject(CompanyDetailPresenter companyDetailPresenter);

    void inject(FilterFragment filterFragment);

    void inject(ReviewsFragmentPresenter reviewsFragmentPresenter);

    void inject(CreateReviewPresenter createReviewPresenter);

    void inject(FavoritesPresenter favoritesPresenter);

    void inject(BookingsPresenter bookingsPresenter);

    void inject(BookingPresenter bookingPresenter);

    void inject(BaseFragment helpContactFragment);

    void inject(HelpPresenter helpPresenter);

    void inject(SettingsPresenter settingsPresenter);

    void inject(AutoDetailPresenter autoDetailPresenter);

    void inject(MessageReceivingService messageReceivingService);

    void inject(CustomInfoWindowAdapter customInfoWindowAdapter);
}
