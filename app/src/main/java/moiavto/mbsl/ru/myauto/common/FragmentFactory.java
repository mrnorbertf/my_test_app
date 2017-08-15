package moiavto.mbsl.ru.myauto.common;

import android.support.v4.app.Fragment;
import moiavto.mbsl.ru.myauto.data.domainData.ChangeableAutoIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.HelpModel;
import moiavto.mbsl.ru.myauto.ui.fragments.*;
import moiavto.mbsl.ru.myauto.ui.fragments.HelpFragments.HelpDetailFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.HelpFragments.HelpFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.carWashMapAndListPager.CarWashesPagerFragment;
import timber.log.Timber;

/**
 * Created by Fedor on 04.07.2017.
 */

public class FragmentFactory {
    public Fragment createFragment(String tag, Object argsData) {
        Timber.d("tag: " + tag + "argsData: ", argsData);
        switch (tag) {
            case CarWashesPagerFragment.TAG_FACTORY:
                return CarWashesPagerFragment.newInstance();
            case CompanyDetailFragment.TAG_FACTORY:
                return CompanyDetailFragment.newInstance((CompanyIdModel) argsData);
            case ReviewsFragment.TAG_FACTORY:
                return ReviewsFragment.newInstance((CompanyIdModel) argsData);
            case CreateReviewFragment.TAG_FACTORY:
                return CreateReviewFragment.newInstance((CompanyIdModel) argsData);
            case BookingFragment.TAG_FACTORY:
                return BookingFragment.newInstance((CompanyIdModel) argsData);
            case SignInFragment.TAG_FACTORY:
                return SignInFragment.newInstance();
            case SignInVerifyFragment.TAG_FACTORY:
                return SignInVerifyFragment.newInstance();
            case FilterFragment.TAG_FACTORY:
                return FilterFragment.newInstance();
            case FavoritesFragment.TAG_FACTORY:
                return FavoritesFragment.newInstance();
            case BookingsFragment.TAG_FACTORY:
                return BookingsFragment.newInstance();
            case HelpFragment.TAG_FACTORY:
                return HelpFragment.newInstance();
            case HelpDetailFragment.TAG_FACTORY:
                return HelpDetailFragment.newInstance((HelpModel) argsData);
            case AutoDetailFragment.TAG_FACTORY:
                return AutoDetailFragment.newInstance((ChangeableAutoIdModel) argsData);
            case SettingsFragment.TAG_FACTORY:
                return SettingsFragment.newInstance();
            default:
                return CarWashesPagerFragment.newInstance();
        }
    }
}
