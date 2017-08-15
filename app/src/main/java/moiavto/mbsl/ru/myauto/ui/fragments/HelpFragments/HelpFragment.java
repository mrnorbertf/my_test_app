package moiavto.mbsl.ru.myauto.ui.fragments.HelpFragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arellomobile.mvp.presenter.InjectPresenter;
import moiavto.mbsl.ru.myauto.BR;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.data.serverModel.HelpModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentListBinding;
import moiavto.mbsl.ru.myauto.ui.adapters.RecyclerBindingAdapter;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.HelpPresenter;
import moiavto.mbsl.ru.myauto.ui.views.HelpListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fedor on 06.07.2017.
 */

public class HelpFragment extends BaseFragment implements HelpListView {
    public static final String TAG_FACTORY = "HelpFragment";
    public static final String TAG = HelpFragment.class.getSimpleName();
    @InjectPresenter
    HelpPresenter presenter;
    private FragmentListBinding binding;

    public static HelpFragment newInstance() {
        return new HelpFragment();
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
        setMenu();
        binding.refreshLayout.setBackgroundResource(R.color.colorWhite);
        binding.refreshLayout.setRefreshing(false);
        binding.refreshLayout.setEnabled(false);
        binding.emptyListTV.setTextColor(R.color.colorBlack);
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

    @Override
    public void startLoading() {
        binding.refreshLayout.post(() -> binding.refreshLayout.setRefreshing(false));
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishLoading() {
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void showData(List<HelpModel> list) {
        ArrayList<HelpModel> helps = (ArrayList<HelpModel>) list;
        RecyclerBindingAdapter<HelpModel> adapter = new RecyclerBindingAdapter<>(R.layout.item_help, BR.help, helps);
        adapter.setOnItemClickListener((position, item)
                -> fragmentSelector.onFragmentSelected(HelpDetailFragment.TAG_FACTORY, item));
        setList(adapter);
    }

    private void setList(RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        binding.includedProgressDialog.defaultProgressBar.setVisibility(View.GONE);
        binding.unbind();
    }
}
