package moiavto.mbsl.ru.myauto.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import butterknife.ButterKnife;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.Utils.DateConverter;
import moiavto.mbsl.ru.myauto.data.domainData.BookingCreateDomainModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingCarModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingDateModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyServiceModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentBookingBinding;
import moiavto.mbsl.ru.myauto.ui.adapters.CheckableServicesAdapter;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.BookingPresenter;
import moiavto.mbsl.ru.myauto.ui.views.BookingView;

import java.util.List;

/**
 * Created by Fedor on 07.07.2017.
 */

public class BookingFragment extends BaseFragment implements BookingView, CheckableServicesAdapter.OnAdapterItemClickListener {
    public static final String TAG_FACTORY = "BookingFragment";
    public static final String TAG = BookingFragment.class.getSimpleName();

    private static final String ARG_DATA_KEY = "Company_model";

    @InjectPresenter
    BookingPresenter presenter;
    private FragmentBookingBinding binding;
    private AlertDialog dialog;

    public static BookingFragment newInstance(CompanyIdModel companyIdModel) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA_KEY, companyIdModel);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter(type = PresenterType.LOCAL)
    BookingPresenter provideBookingPresenter() {
        BookingPresenter presenter = new BookingPresenter();
        CompanyIdModel companyId = (CompanyIdModel) getArguments().getSerializable(ARG_DATA_KEY);
        presenter.setCompanyId(companyId);
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_booking, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        initializeUI();
    }

    private void initializeUI() {
        binding.dateTV.setOnClickListener(__ ->
                presenter.showDateDialog());
        binding.timeTV.setOnClickListener(__ ->
                presenter.showTimeDialog());
        binding.carTV.setOnClickListener(__ ->
                presenter.showCarDialog());

        binding.bookButton.setOnClickListener(__ ->
                presenter.book());
    }


    @Override
    public void showData(BookingCreateDomainModel resultBooking) {
        binding.setBooking(resultBooking);

        updateServiceList(resultBooking.getServicesAvailable());
    }

    @Override
    public void updateData(BookingCreateDomainModel resultBooking) {
        binding.setBooking(resultBooking);
    }

    @Override
    public void updateServiceList(List<CompanyServiceModel> services) {
        if (services.isEmpty()) {
            binding.emptyServiceTV.setVisibility(View.VISIBLE);
            binding.servicesRV.setVisibility(View.GONE);
        } else {
            binding.emptyServiceTV.setVisibility(View.GONE);
            setList(new CheckableServicesAdapter(services, this));
        }
    }

    private void setList(RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.servicesRV.setLayoutManager(layoutManager);
        binding.servicesRV.setAdapter(adapter);
    }

    @Override
    public void showDateDialog(List<BookingDateModel> bookingDates) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item);

        for (BookingDateModel item :
                bookingDates) {
            arrayAdapter.add(DateConverter.shortDate(item.getDate()));
        }

        dialog = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle)
                .setTitle(R.string.message_choose_date)
                .setAdapter(arrayAdapter, (dialog, which) ->
                        presenter.updateDate(bookingDates.get(which).getDate())
                )
                .show();
    }


    @Override
    public void showTimeDialog(List<String> times) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, times);

        dialog = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle)
                .setTitle(R.string.message_choose_time)
                .setAdapter(arrayAdapter, (dialog, which) ->
                        presenter.updateTime(times.get(which)))
                .show();

    }

    @Override
    public void showCarDialog(List<BookingCarModel> cars) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item);

        for (BookingCarModel item :
                cars) {
            arrayAdapter.add(item.getName());
        }

        dialog = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle)
                .setTitle(R.string.message_choose_date)
                .setAdapter(arrayAdapter, (dialog, which) ->
                        presenter.updateCar(cars.get(which).getCarId()))
                .show();
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
    public void bookSuccessful() {
        backPressedListener.onDoBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        binding.unbind();
    }

    @Override
    public void onItemClick(List<Integer> resultIdsList) {
        presenter.updateServices(resultIdsList);
    }
}
