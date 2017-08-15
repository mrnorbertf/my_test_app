package moiavto.mbsl.ru.myauto.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardMethod;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardingOnTouchListener;
import moiavto.mbsl.ru.myauto.data.domainData.AutoDomainModel;
import moiavto.mbsl.ru.myauto.data.domainData.ChangeableAutoIdModel;
import moiavto.mbsl.ru.myauto.data.domainData.Naming;
import moiavto.mbsl.ru.myauto.databinding.FragmentAutoDetailBinding;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.AutoDetailPresenter;
import moiavto.mbsl.ru.myauto.ui.views.AutoDetailView;

import java.util.List;

/**
 * Created by Fedor on 06.07.2017.
 */

public class AutoDetailFragment extends BaseFragment implements AutoDetailView {
    public static final String TAG_FACTORY = "AutoDetailFragment";
    public static final String TAG = AutoDetailFragment.class.getSimpleName();

    private static final String ARG_DATA_KEY = "auto_model";

    @InjectPresenter
    AutoDetailPresenter presenter;

    private FragmentAutoDetailBinding binding;
    private AlertDialog dialog;

    public static AutoDetailFragment newInstance(ChangeableAutoIdModel autoIdModel) {
        AutoDetailFragment fragment = new AutoDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA_KEY, autoIdModel);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter(type = PresenterType.LOCAL)
    AutoDetailPresenter provideAutoDetailPresenter() {
        AutoDetailPresenter presenter = new AutoDetailPresenter();
        ChangeableAutoIdModel autoIdModel = (ChangeableAutoIdModel) getArguments().getSerializable(ARG_DATA_KEY);
        presenter.setAutoId(autoIdModel);
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auto_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUI();
    }

    private void initializeUI() {
        binding.rootView.setOnTouchListener(new HideKeyboardingOnTouchListener(() -> HideKeyboardMethod.hideKeyboard(getActivity())));

        Disposable nameDisposable = RxTextView.textChanges(binding.autoNumberET)
                .map(CharSequence::toString)
                .subscribe(autoNumber -> presenter.changeNumber(autoNumber));
        unsubscribeOnDestroy(nameDisposable);

        Disposable markDisposable = RxView.clicks(binding.markLayout)
                .subscribe(__ -> presenter.showMarkDialog());
        unsubscribeOnDestroy(markDisposable);

        Disposable modelDisposable = RxView.clicks(binding.modelLayout)
                .subscribe(__ -> presenter.showModelDialog());
        unsubscribeOnDestroy(modelDisposable);

        Disposable bodyDisposable = RxView.clicks(binding.bodyLayout)
                .subscribe(__ -> presenter.showBodyDialog());
        unsubscribeOnDestroy(bodyDisposable);

        Disposable colorDisposable = RxView.clicks(binding.colorLayout)
                .subscribe(__ -> presenter.showColorDialog());
        unsubscribeOnDestroy(colorDisposable);

        Disposable yearDisposable = RxTextView.textChanges(binding.yearET)
                .filter(charSequence -> charSequence.length() > 0)
                .map(CharSequence::toString)
                .map(Integer::parseInt)
                .subscribe(autoNumber -> presenter.changeYear(autoNumber));
        unsubscribeOnDestroy(yearDisposable);

        Disposable addDisposable = RxView.clicks(binding.addButton)
                .subscribe(__ -> presenter.changeAuto());
        unsubscribeOnDestroy(addDisposable);

        Disposable deleteDisposable = RxView.clicks(binding.deleteButton)
                .subscribe(__ -> presenter.deleteAuto());
        unsubscribeOnDestroy(deleteDisposable);
    }

    @Override
    public void showData(AutoDomainModel autoDomainModel) {
        binding.setAuto(autoDomainModel);
    }

    @Override
    public void updateMenu(boolean isEdit) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(isEdit ? getString(R.string.settings_edit) : getString(R.string.settings_add));
        }
    }

    @Override
    public <T> void showDialog(List<T> list) {
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item);

        for (Object aList : list) {
            Naming name = (Naming) aList;
            arrayAdapter.add(name.getName());
        }

        dialog = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle)
                .setCancelable(true)
                .setAdapter(arrayAdapter, (dialog, which) ->
                        presenter.updateField(list.get(which)))
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
    public void complete() {
        backPressedListener.onDoBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null)
            dialog.dismiss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }

    @Override
    public void showAutoNumberFieldError(boolean isShow) {
        binding.autoNumberET.setError(isShow ? getString(R.string.msg_this_field_is_required) : null);
    }
}
