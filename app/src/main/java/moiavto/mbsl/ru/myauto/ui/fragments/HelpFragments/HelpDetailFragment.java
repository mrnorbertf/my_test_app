package moiavto.mbsl.ru.myauto.ui.fragments.HelpFragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.data.serverModel.HelpModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentHelpDetailBinding;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;

/**
 * Created by Fedor on 06.07.2017.
 */

public class HelpDetailFragment extends BaseFragment {
    public static final String TAG_FACTORY = "HelpDetailFragment";
    public static final String TAG = HelpDetailFragment.class.getSimpleName();

    private static final String ARG_DATA_KEY = "HelpModel";

    private FragmentHelpDetailBinding binding;
    private HelpModel helpModel;

    public static HelpDetailFragment newInstance(HelpModel helpModel) {
        HelpDetailFragment fragment = new HelpDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA_KEY, helpModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpModel = (HelpModel) getArguments().getSerializable(ARG_DATA_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_help_detail, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setMenu();
        binding.setHelp(helpModel);
    }

    private void setMenu() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.help));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.unbind();
    }
}
