package moiavto.mbsl.ru.myauto.ui.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.DataManager;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardMethod;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardingOnTouchListener;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyFeatureModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyListRequestModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentFilterBinding;
import moiavto.mbsl.ru.myauto.ui.adapters.CheckableFeatureAdapter;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fedor on 07.07.2017.
 */

public class FilterFragment extends BaseFragment {
    public static final String TAG_FACTORY = "FilterFragment";
    public static final String TAG = FilterFragment.class.getSimpleName();

    @Inject
    DataManager dataManager;

//    @BindView(R.id.priceSeekBar)
//    CrystalRangeSeekbar priceSeekBar;
//    @BindView(R.id.ratingSeekBar)
//    CrystalRangeSeekbar ratingSeekBar;

    private FragmentFilterBinding binding;

    private CompanyListRequestModel filters = new CompanyListRequestModel();
    private CheckableFeatureAdapter adapter;

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyAutoApp.getAppComponent().inject(this);
    }


    private void loadData() {
        filters = dataManager.getDataClass(CompanyListRequestModel.class);
        if (filters == null)
            filters = new CompanyListRequestModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ButterKnife.bind(this, view);

        initializeUI();
        loadData();
        showFilters(filters);
    }

    private void initializeUI() {
        setMenu();

        binding.rootView.setOnTouchListener(new HideKeyboardingOnTouchListener(() -> HideKeyboardMethod.hideKeyboard(getActivity())));

        binding.priceSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
                    filters.setMinPrice(minValue.doubleValue());
                    filters.setMaxPrice(maxValue.doubleValue());
                    binding.setFilter(filters);
                }
        );
        binding.ratingSeekBar.setGap(1);
        binding.priceSeekBar.setGap(200);
        binding.ratingSeekBar.setOnRangeSeekbarChangeListener((minValue, maxValue) -> {
                    filters.setMinRating(minValue.doubleValue());
                    filters.setMaxRating(maxValue.doubleValue());
                    binding.setFilter(filters);
                }
        );

        Disposable services = RxTextView.textChanges(binding.serviceET)
                .map(charSequence -> charSequence.length() > 3)
                .subscribe(aBoolean -> {
                    binding.serviceET.setTextColor(aBoolean ? Color.BLACK : Color.RED);
                    filters.setServiceName(binding.serviceET.getText().toString());
                });

        unsubscribeOnDestroy(services);

        Disposable onlyFree = RxCompoundButton.checkedChanges(binding.onlyFreeCB)
                .subscribe(isVacant -> filters.setIsVacantOnly(isVacant));
        unsubscribeOnDestroy(onlyFree);

        Disposable applyFilter = RxView.clicks(binding.applyFilterButton)
                .subscribe(__ -> updateChanges());

        unsubscribeOnDestroy(applyFilter);
    }

    private void setMenu() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.filter));
    }

    private void updateChanges() {
        filters.setFeatures(adapter.getSelectedItems());
        filters.setMaxRating(binding.ratingSeekBar.getSelectedMaxValue().doubleValue());
        filters.setMinRating(binding.ratingSeekBar.getSelectedMinValue().doubleValue());
        filters.setMaxPrice(binding.priceSeekBar.getSelectedMaxValue().doubleValue());
        filters.setMinRating(binding.priceSeekBar.getSelectedMinValue().doubleValue());
        dataManager.saveDataClass(filters, CompanyListRequestModel.class);
        showMsg(R.string.msg_Filters_were_applied);

        backPressedListener.onDoBackPressed();
    }

    private List<CompanyFeatureModel> generateAllFeature() {
        List<CompanyFeatureModel> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new CompanyFeatureModel(i));
        }
        return list;
    }

    private void setList(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    public void showFilters(CompanyListRequestModel requestModel) {
        binding.setFilter(requestModel);
        adapter = new CheckableFeatureAdapter(generateAllFeature(), requestModel.getFeatures());
        setList(binding.featuresRV, adapter, LinearLayoutManager.HORIZONTAL);
        binding.priceSeekBar
                .setMaxStartValue(requestModel.getMaxPrice() != null ? requestModel.getMaxPrice().floatValue() : 10000)
                .setMinStartValue(requestModel.getMinPrice() != null ? requestModel.getMinPrice().floatValue() : 0)
                .apply();
        binding.ratingSeekBar
                .setMaxStartValue(requestModel.getMaxRating() != null ? requestModel.getMaxRating().floatValue() : 5f)
                .setMinStartValue(requestModel.getMinRating() != null ? requestModel.getMinRating().floatValue() : 0f)
                .apply();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.unbind();
    }

}
