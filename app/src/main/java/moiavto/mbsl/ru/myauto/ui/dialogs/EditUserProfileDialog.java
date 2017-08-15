package moiavto.mbsl.ru.myauto.ui.dialogs;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.squareup.leakcanary.RefWatcher;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.application.MyAutoApp;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardMethod;
import moiavto.mbsl.ru.myauto.common.viewUtils.HideKeyboardingOnTouchListener;
import moiavto.mbsl.ru.myauto.common.viewUtils.PhoneMaskedTextChangedListener;
import moiavto.mbsl.ru.myauto.data.domainData.AccountData;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountInfoModel;
import moiavto.mbsl.ru.myauto.databinding.DialogUserProfileBinding;

/**
 * Created by Fedor on 17.07.2017.
 */

public class EditUserProfileDialog extends DialogFragment {
    private static final String ARG_DATA_KEY = "auto_model";
    private DialogUserProfileBinding binding;
    private AccountData user;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static EditUserProfileDialog newInstance(AccountInfoModel info) {
        EditUserProfileDialog frag = new EditUserProfileDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA_KEY, info);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountInfoModel user = (AccountInfoModel) getArguments().getSerializable(ARG_DATA_KEY);
        this.user = new AccountData(user.getUsername(), user.getPhone());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_user_profile, null, false);


        binding.rootView.setOnTouchListener(new HideKeyboardingOnTouchListener(() -> HideKeyboardMethod.hideKeyboard(getActivity())));

        binding.setUser(user);

        PhoneMaskedTextChangedListener listener = new PhoneMaskedTextChangedListener(binding.userPhoneET);
        binding.userPhoneET.addTextChangedListener(listener);
        binding.userPhoneET.setOnFocusChangeListener(listener);
        binding.userPhoneET.setHint(listener.placeholder());

        Disposable nameSub = RxTextView.textChanges(binding.userNameET)
                .map(CharSequence::toString)
                .subscribe(result -> {
                    if (result.length() == 0)
                        binding.userNameET.setError(getString(R.string.msg_this_field_is_required));
                    else {
                        binding.userNameET.setError(null);
                        user.username.set(result);
                    }
                });

        unsubscribeOnDestroy(nameSub);

        Disposable phoneSub = RxTextView.textChanges(binding.userPhoneET)
                .map(CharSequence::toString)
                .subscribe(result -> {
                    if (result.length() == 0)
                        binding.userPhoneET.setError(getString(R.string.msg_this_field_is_required));
                    else {
                        binding.userPhoneET.setError(null);
                        user.phone.set(result);
                    }
                });

        unsubscribeOnDestroy(phoneSub);

        return new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle)
                .setView(binding.getRoot())
                .setPositiveButton(getString(R.string.Edit), (dialog, which) -> attemptSendBack())
                .setNegativeButton(getString(R.string.Cancel), (dialog, which) -> dialog.dismiss())
                .create();
    }

    private void attemptSendBack() {
        if (binding.userNameET.getText().length() != 0 || binding.userPhoneET.length() > 11) {
            sendBackResult();
        }
    }

    public void sendBackResult() {
        EditUserDialogListener listener = (EditUserDialogListener) getTargetFragment();
        listener.onFinishEditDialog(binding.getUser());
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = MyAutoApp.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    protected void unsubscribeOnDestroy(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public interface EditUserDialogListener {
        void onFinishEditDialog(AccountData user);
    }
}
