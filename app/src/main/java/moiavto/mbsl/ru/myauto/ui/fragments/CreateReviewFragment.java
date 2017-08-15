package moiavto.mbsl.ru.myauto.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardMethod;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardingOnTouchListener;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentReviewCreateBinding;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.CreateReviewPresenter;
import moiavto.mbsl.ru.myauto.ui.views.CreateReviewView;

/**
 * Created by Fedor on 12.07.2017.
 */

public class CreateReviewFragment extends BaseFragment implements CreateReviewView {
    public static final String TAG_FACTORY = "CreateReviewFragment";
    public static final String TAG = CreateReviewFragment.class.getSimpleName();

    private static final String ARG_DATA_KEY = "Company_ID_model";
    @InjectPresenter
    CreateReviewPresenter presenter;
    private FragmentReviewCreateBinding binding;

    public static CreateReviewFragment newInstance(CompanyIdModel companyIdModel) {
        CreateReviewFragment fragment = new CreateReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA_KEY, companyIdModel);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter(type = PresenterType.LOCAL)
    CreateReviewPresenter provideCreateReviewPresenter() {
        CreateReviewPresenter presenter = new CreateReviewPresenter();
        CompanyIdModel companyId = (CompanyIdModel) getArguments().getSerializable(ARG_DATA_KEY);
        presenter.setCompanyId(companyId);
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_create, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUI();
    }

    private void initializeUI() {
        binding.rootView.setOnTouchListener(new HideKeyboardingOnTouchListener(() -> HideKeyboardMethod.hideKeyboard(getActivity())));

        binding.reviewET.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1000) });

        Disposable addReview = RxView.clicks(binding.addReviewButton)
                .map(__ -> binding.reviewET.getText().toString())
                .map(text -> !TextUtils.isEmpty(text) && (text.trim().length() > 0))
                .subscribe(isValid -> {
                    if (isValid)
                        presenter.createReview(binding.reviewET.getText().toString(),
                                Math.round(binding.ratingBar.getRating()));
                    else
                        presenter.showEmptyError();
                });

        unsubscribeOnDestroy(addReview);
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
    public void hideEmptyError() {
        binding.reviewET.setError(null);
    }

    @Override
    public void showEmptyError() {
        binding.reviewET.setError(getString(R.string.msg_this_field_is_required));
    }

    @Override
    public void reviewSendSuccessful() {
        backPressedListener.onDoBackPressed();
    }

}
