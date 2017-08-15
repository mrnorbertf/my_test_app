package moiavto.mbsl.ru.myauto.ui.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import gun0912.tedbottompicker.TedBottomPicker;
import moiavto.mbsl.ru.myauto.BR;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.data.domainData.AccountData;
import moiavto.mbsl.ru.myauto.data.domainData.ChangeableAutoIdModel;
import moiavto.mbsl.ru.myauto.data.domainData.SettingsData;
import moiavto.mbsl.ru.myauto.data.serverModel.AccountInfoModel;
import moiavto.mbsl.ru.myauto.data.serverModel.AutoIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.AutoListResponseModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentSettingsBinding;
import moiavto.mbsl.ru.myauto.ui.adapters.RecyclerBindingAdapter;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.baseComponents.OnLogoutListener;
import moiavto.mbsl.ru.myauto.ui.dialogs.EditUserProfileDialog;
import moiavto.mbsl.ru.myauto.ui.presenters.SettingsPresenter;
import moiavto.mbsl.ru.myauto.ui.views.SettingsView;
import timber.log.Timber;

import java.util.ArrayList;

/**
 * Created by Fedor on 06.07.2017.
 */

public class SettingsFragment extends BaseFragment
        implements SettingsView,
        EditUserProfileDialog.EditUserDialogListener {
    public static final String TAG_FACTORY = "SettingsFragment";
    public static final String TAG = SettingsFragment.class.getSimpleName();

    @InjectPresenter
    SettingsPresenter presenter;
    Uri selectedUri;
    private FragmentSettingsBinding binding;
    private AlertDialog logoutDialog;
    private OnLogoutListener logoutListener;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logoutListener = (OnLogoutListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
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
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.settings));
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                presenter.attemptLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setButtonClickListeners() {
        binding.userLayout.profileLayout.setOnClickListener(v -> presenter.showProfileEditDialog());
        binding.userLayout.userCircleImage.setOnClickListener(v -> presenter.showPhotoDialog());
        binding.addCarButton.setOnClickListener(v ->
                presenter.addCar());
    }

    @Override
    public void showData(SettingsData data) {
        binding.setSettings(data);

        ArrayList<AutoListResponseModel> cars = (ArrayList<AutoListResponseModel>) data.getCars();
        RecyclerBindingAdapter<AutoListResponseModel> adapter = new RecyclerBindingAdapter<>(R.layout.item_help, BR.auto, cars);
        adapter.setOnItemClickListener((position, item)
                -> presenter.editCar(new AutoIdModel(item.getAutoId())));
        setList(binding.carsRV, adapter, LinearLayoutManager.VERTICAL);
    }

    private void setList(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startAddingCar() {
        fragmentSelector.onFragmentSelected(AutoDetailFragment.TAG_FACTORY, new ChangeableAutoIdModel(false, null));
    }

    @Override
    public void startEditCar(AutoIdModel autoIdModel) {
        fragmentSelector.onFragmentSelected(AutoDetailFragment.TAG_FACTORY, new ChangeableAutoIdModel(true, autoIdModel));
    }

    @Override
    public void showProfileEditDialog(AccountInfoModel user) {
        FragmentManager fm = getChildFragmentManager();
        EditUserProfileDialog editNameDialogFragment = EditUserProfileDialog.newInstance(user);
        editNameDialogFragment.setTargetFragment(SettingsFragment.this, 300);
        editNameDialogFragment.show(fm, "fragment_edit_user_data");
    }

    @Override
    public void showPhotoDialog() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getContext())
                        .setTitle(getString(R.string.choose_photo))
                        .setOnImageSelectedListener(uri -> {
                            Timber.d("TedBottomPicker", "uri: " + uri);
                            Timber.d("TedBottomPicker", "uri.getPath(): " + uri.getPath());
                            selectedUri = uri;
                            presenter.setServerPhoto(uri);

                        })
                        .setSelectedUri(selectedUri)
                        .setPeekHeight(1200)
                        .create();
                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager());
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                showMsg(R.string.Msg_no_photo_permission);
            }
        };
        new TedPermission(getContext())
                .setPermissionListener(permissionListener)
                .setDeniedMessage(R.string.msg_permission)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
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
    public void showLogoutMessage() {
        logoutDialog = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle)
                .setTitle(R.string.app_name)
                .setMessage(getString(R.string.msg_logout_message))
                .setPositiveButton(R.string.Logout, (dialog1, which) -> presenter.logout())
                .setNegativeButton(R.string.Cancel, (dialog, which) -> presenter.hideLogoutDialog())
                .setOnCancelListener(dialogInterface -> presenter.hideLogoutDialog())
                .show();
    }

    @Override
    public void startLogin() {
        logoutListener.logout();
    }

    @Override
    public void hideLogoutDialog() {
        if (logoutDialog != null && logoutDialog.isShowing()) {
            logoutDialog.hide();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.hideLogoutDialog();
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }

    @Override
    public void onFinishEditDialog(AccountData user) {
        presenter.editUserData(user);
    }
}
