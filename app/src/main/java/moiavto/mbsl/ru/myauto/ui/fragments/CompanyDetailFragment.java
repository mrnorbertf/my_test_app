package moiavto.mbsl.ru.myauto.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.ActionUtils;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentCompanyDetailBinding;
import moiavto.mbsl.ru.myauto.ui.adapters.FeatureAdapter;
import moiavto.mbsl.ru.myauto.ui.adapters.ServicesAdapter;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.CompanyDetailPresenter;
import moiavto.mbsl.ru.myauto.ui.views.CompanyDetailView;

/**
 * Created by Fedor on 06.07.2017.
 */

public class CompanyDetailFragment extends BaseFragment implements CompanyDetailView {
    public static final String TAG_FACTORY = "CompanyDetailFragment";
    public static final String TAG = CompanyDetailFragment.class.getSimpleName();

    private static final String ARG_DATA_KEY = "Company_model";

    @InjectPresenter
    CompanyDetailPresenter presenter;

    private FragmentCompanyDetailBinding binding;
    private MenuItem favoriteItem;
    private ActionBar actionBar;
    private CompanyIdModel companyId;

    public static CompanyDetailFragment newInstance(CompanyIdModel companyIdModel) {
        CompanyDetailFragment fragment = new CompanyDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA_KEY, companyIdModel);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter(type = PresenterType.LOCAL)
    CompanyDetailPresenter provideCompanyDetailPresenter() {
        CompanyDetailPresenter presenter = new CompanyDetailPresenter();
        CompanyIdModel companyId = (CompanyIdModel) getArguments().getSerializable(ARG_DATA_KEY);
        presenter.setCompanyId(companyId);
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companyId = (CompanyIdModel) getArguments().getSerializable(ARG_DATA_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUI();
    }

    private void initializeUI() {
        setMenu();
        setButtonClickListeners();
    }

    private void setMenu() {
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.washes));
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_favorite, menu);

        favoriteItem = menu.getItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                presenter.toggleFavorite();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setButtonClickListeners() {
        binding.buttonsIncludeLayout.enrollButton.setOnClickListener(v ->
                presenter.enrollService());
        binding.buttonsIncludeLayout.callButton.setOnClickListener(v ->
                presenter.callToCompany());
        binding.buttonsIncludeLayout.navigationButton.setOnClickListener(v ->
                presenter.navigateToCompany());

        binding.reviewsButton.setOnClickListener(v ->
                fragmentSelector.onFragmentSelected(ReviewsFragment.TAG_FACTORY, companyId)
        );
    }

    @Override
    public void showData(CompanyModel company) {
        toggleFavoriteMenuIcon(company.getInFavorites());
        binding.setCompany(company);
        actionBar.setTitle(company.getName());

        setList(binding.servicesRV, new ServicesAdapter(company.getServices(), null), LinearLayoutManager.VERTICAL);
        setList(binding.featuresRV, new FeatureAdapter(company.getFeatures()), LinearLayoutManager.HORIZONTAL);
    }

    @Override
    public void addToFavorite() {
        toggleFavoriteMenuIcon(true);
    }

    @Override
    public void removeFromFavorite() {
        toggleFavoriteMenuIcon(false);
    }

    private void toggleFavoriteMenuIcon(boolean isFavorite) {
        favoriteItem.setIcon(isFavorite ? R.drawable.icon_favorite_active : R.drawable.icon_favorite_not_active);
    }

    private void setList(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startLoading() {
        binding.progressBar.defaultProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishLoading() {
        binding.progressBar.defaultProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void enrollService(CompanyIdModel companyId) {
        fragmentSelector.onFragmentSelected(BookingFragment.TAG_FACTORY, companyId);
    }

    @Override
    public void callToCompany(CompanyModel company) {
        ActionUtils.callToCompany(company.getPhone());
    }

    @Override
    public void navigateToCompany(CompanyModel company) {
        ActionUtils.navigateToCompany(company.getLat(), company.getLng(), company.getAddress());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.progressBar.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }
}
