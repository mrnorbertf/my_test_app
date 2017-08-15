package moiavto.mbsl.ru.myauto.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardMethod;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardingOnTouchListener;
import moiavto.mbsl.ru.myauto.common.viewUtils.PhoneMaskedTextChangedListener;
import moiavto.mbsl.ru.myauto.data.domainData.AccountCreateRequestDomainModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentSignInBinding;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.SignInPresenter;
import moiavto.mbsl.ru.myauto.ui.views.SignInView;

/**
 * Created by Fedor on 22.06.2017.
 */

public class SignInFragment extends BaseFragment implements SignInView {
    public static final String TAG_FACTORY = "SignInFragment";
    public static final String TAG = SignInFragment.class.getSimpleName();

    @InjectPresenter
    SignInPresenter presenter;

    FragmentSignInBinding binding;

    private AccountCreateRequestDomainModel requestModel = new AccountCreateRequestDomainModel("", false);

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        binding.setRequestUser(requestModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUI();
    }

    private void initializeUI() {
        binding.rootView.setOnTouchListener(new HideKeyboardingOnTouchListener(() -> HideKeyboardMethod.hideKeyboard(getActivity())));

        PhoneMaskedTextChangedListener listener = new PhoneMaskedTextChangedListener(binding.phoneET);
        binding.phoneET.addTextChangedListener(listener);
        binding.phoneET.setOnFocusChangeListener(listener);
        binding.phoneET.setHint(listener.placeholder());

        Observable<CharSequence> phoneSub = RxTextView.textChanges(binding.phoneET);
        Observable<Boolean> conditionsSub = RxCompoundButton.checkedChanges(binding.conditionRulesCB);

        Disposable zip = Observable.combineLatest(phoneSub, conditionsSub, (phoneNumber, isConditionAccept) -> {
            requestModel.phoneNumber.set(phoneNumber.toString());
            requestModel.isConditionsAccept.set(isConditionAccept);
            return isConditionAccept;
        }).subscribe(aBoolean -> binding.continueButton.setEnabled(aBoolean));

        unsubscribeOnDestroy(zip);

        Disposable checkPhone = RxView.clicks(binding.continueButton)
                .subscribe(o -> presenter.attemptLogin(binding.getRequestUser()));

        unsubscribeOnDestroy(checkPhone);

        Disposable conditions = RxView.clicks(binding.conditionRulesTV)
                .subscribe(o -> startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.terms_of_use_and_privacy_policy_link))))
                );
        unsubscribeOnDestroy(conditions);
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
    public void showMsg(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFieldError(Integer fieldError) {
        binding.phoneET.setError(fieldError != null ? getString(fieldError) : null);
    }

    @Override
    public void startSignInVerify() {
        fragmentSelector.onFragmentSelected(SignInVerifyFragment.TAG_FACTORY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.progressBar.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }
}
