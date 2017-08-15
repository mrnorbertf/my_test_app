package moiavto.mbsl.ru.myauto.ui.fragments.carWashMapAndListPager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.databinding.FragmentPageContainerBinding;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.FilterFragment;

/**
 * Created by Fedor on 28.06.2017.
 */

public class CarWashesPagerFragment extends BaseFragment {
    public static final String TAG_FACTORY = "CarWashesPagerFragment";
    public static final String TAG = CarWashesPagerFragment.class.getSimpleName();

    private static final int numOfPages = 2;

    private FragmentPageContainerBinding binding;

    public static CarWashesPagerFragment newInstance() {
        return new CarWashesPagerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page_container, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUI();
    }

    private void initializeUI() {
        setMenu();
        setPager();
    }

    private void setMenu() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.washes));
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                fragmentSelector.onFragmentSelected(FilterFragment.TAG_FACTORY);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setPager() {
        String[] pageTitle = new String[]{getString(R.string.List), getString(R.string.Map)};
        for (int i = 0; i < numOfPages; i++) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(pageTitle[i]));
        }

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        binding.viewPager.setAdapter(pagerAdapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        binding.tabLayout.addOnTabSelectedListener(onTabSelectedListener(binding.viewPager));
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }

    private static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return MapPageFragment.newInstance();
                default:
                    return ListPageFragment.newInstance();
            }
        }
    }
}
