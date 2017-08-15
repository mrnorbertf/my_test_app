package moiavto.mbsl.ru.myauto.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardMethod;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardingOnTouchListener;
import moiavto.mbsl.ru.myauto.databinding.FragmentSignInVerifyBinding;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnSignInCompleteListener;
import moiavto.mbsl.ru.myauto.ui.presenters.SignInVerifyPresenter;
import moiavto.mbsl.ru.myauto.ui.views.SignInVerifyView;

/**
 * Created by Fedor on 28.06.2017.
 */

public class SignInVerifyFragment extends BaseFragment implements SignInVerifyView {
    public static final String TAG_FACTORY = "SignInVerifyFragment";
    public static final String TAG = SignInVerifyFragment.class.getSimpleName();

    @InjectPresenter
    SignInVerifyPresenter presenter;

    FragmentSignInVerifyBinding binding;

    private OnSignInCompleteListener onSignInCompleteListener;

    public static SignInVerifyFragment newInstance() {
        return new SignInVerifyFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onSignInCompleteListener = (OnSignInCompleteListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in_verify, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI();
    }

    private void initializeUI() {
        binding.rootView.setOnTouchListener(new HideKeyboardingOnTouchListener(() -> HideKeyboardMethod.hideKeyboard(getActivity())));

        Disposable verifyCodeDisp = RxTextView.textChanges(binding.verifyCodeET)
                .map(charSequence -> charSequence.length() == 4)
                .subscribe(aBoolean -> {
                    binding.verifyCodeET.setTextColor(aBoolean ? Color.BLACK : Color.RED);
                    binding.continueButton.setEnabled(aBoolean);
                });

        unsubscribeOnDestroy(verifyCodeDisp);

        Disposable buttonDisp = RxView.clicks(binding.continueButton)
                .subscribe(o -> presenter.verifyLogin(binding.verifyCodeET.getText().toString()));

        unsubscribeOnDestroy(buttonDisp);
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
    public void showMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFieldError(Integer fieldError) {
        binding.verifyCodeET.setError(fieldError != null ? getString(fieldError) : null);
    }

    @Override
    public void finishAuth() {
        onSignInCompleteListener.onSignInComplete();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.progressBar.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }
}
