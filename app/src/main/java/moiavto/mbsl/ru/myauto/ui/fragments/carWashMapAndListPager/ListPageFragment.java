package moiavto.mbsl.ru.myauto.ui.fragments.carWashMapAndListPager;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arellomobile.mvp.presenter.InjectPresenter;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.ActionUtils;
import moiavto.mbsl.ru.myauto.common.viewUtils.EndlessRecyclerViewScrollListener;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentListBinding;
import moiavto.mbsl.ru.myauto.ui.adapters.CompanyModelsAdapter;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnLocationRequestListener;
import moiavto.mbsl.ru.myauto.ui.fragments.BookingFragment;
import moiavto.mbsl.ru.myauto.ui.fragments.CompanyDetailFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.ListPagePresenter;
import moiavto.mbsl.ru.myauto.ui.views.ListView;

import java.util.List;

/**
 * Created by Fedor on 28.06.2017.
 */

public class ListPageFragment extends BaseFragment implements ListView,
        CompanyModelsAdapter.OnAdapterItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public final static String TAG = MapPageFragment.class.getSimpleName();

    @InjectPresenter
    ListPagePresenter presenter;

    private FragmentListBinding binding;

    private CompanyModelsAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private OnLocationRequestListener locationListener;
    public static ListPageFragment newInstance() {
        return new ListPageFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationListener = (OnLocationRequestListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI();
    }


    private void initializeUI() {
        binding.refreshLayout.setOnRefreshListener(() -> {
            binding.refreshLayout.post(() -> binding.refreshLayout.setRefreshing(false));
            clearAdapter();
            presenter.refreshOrders();
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        binding.recyclerView.setLayoutManager(layoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //TODO server not realize this function
                //                presenter.loadData();
            }
        };
        binding.recyclerView.addOnScrollListener(scrollListener);
        binding.recyclerView.setAdapter(adapter);
        binding.emptyListTV.setVisibility(View.GONE);
    }

    @Override
    public void setEmptyList() {
        binding.emptyListTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyList() {
        binding.emptyListTV.setVisibility(View.GONE);
    }

    @Override
    public void startLoading() {
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishLoading() {
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setCompanyList(List<CompanyModel> companyModels) {
        if (adapter == null) {
            adapter = new CompanyModelsAdapter(companyModels, getContext(), this);
            binding.recyclerView.setAdapter(adapter);
        } else {
            adapter.add(companyModels);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearAdapter();
    }

    private void clearAdapter() {
        if (adapter != null)
            adapter.clear();
        scrollListener.resetState();
    }

    @Override
    public void onItemClick(int companyId) {
        fragmentSelector.onFragmentSelected(CompanyDetailFragment.TAG_FACTORY, new CompanyIdModel(companyId));
    }

    @Override
    public void onItemEnrollClick(CompanyModel company) {
        if (company.getIsVacant())
            fragmentSelector.onFragmentSelected(BookingFragment.TAG_FACTORY, new CompanyIdModel(company.getCompanyId()));
        else
            showMsg(R.string.msg_no_available_dates);
    }

    @Override
    public void onItemCallClick(CompanyModel company) {
        presenter.callToCompany(company);
    }

    @Override
    public void onItemNavigateClick(CompanyModel company) {
        presenter.navigateToCompany(company);
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
    public void onRefresh() {
        binding.refreshLayout.post(() -> binding.refreshLayout.setRefreshing(false));
        clearAdapter();
        presenter.refreshOrders();
    }

    @Override
    public void updateLocation() {
        presenter.setLocation(locationListener.getLastLocation());
    }
}