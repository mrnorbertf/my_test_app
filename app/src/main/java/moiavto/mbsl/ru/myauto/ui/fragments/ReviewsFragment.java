package moiavto.mbsl.ru.myauto.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.disposables.Disposable;
import moiavto.mbsl.ru.myauto.R;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.ReviewListResponseModel;
import moiavto.mbsl.ru.myauto.databinding.FragmentReviewsListBinding;
import moiavto.mbsl.ru.myauto.ui.adapters.ReviewsAdapter;
import moiavto.mbsl.ru.myauto.ui.baseComponents.BaseFragment;
import moiavto.mbsl.ru.myauto.ui.presenters.ReviewsFragmentPresenter;
import moiavto.mbsl.ru.myauto.ui.views.ReviewsView;

/**
 * Created by Fedor on 11.07.2017.
 */

public class ReviewsFragment extends BaseFragment implements ReviewsView {
    public static final String TAG_FACTORY = "ReviewsFragment";
    public static final String TAG = ReviewsFragment.class.getSimpleName();

    private static final String ARG_DATA_KEY = "Company_ID_model";

    @InjectPresenter
    ReviewsFragmentPresenter presenter;

    private FragmentReviewsListBinding binding;

    public static ReviewsFragment newInstance(CompanyIdModel companyIdModel) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATA_KEY, companyIdModel);
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter(type = PresenterType.LOCAL)
    ReviewsFragmentPresenter provideReviewsPresenter() {
        ReviewsFragmentPresenter presenter = new ReviewsFragmentPresenter();
        CompanyIdModel companyId = (CompanyIdModel) getArguments().getSerializable(ARG_DATA_KEY);
        presenter.setCompanyId(companyId);
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reviews_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeUI();
    }

    private void initializeUI() {
        setMenu();

        Disposable button = RxView.clicks(binding.addReviewButton)
                .subscribe(o -> presenter.createReview());

        unsubscribeOnDestroy(button);
    }

    private void setMenu() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.reviews));
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_reviews, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_review:
                presenter.createReview();
        }
        return super.onOptionsItemSelected(item);
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
    public void showData(ReviewListResponseModel reviews) {
        binding.setReviews(reviews);
        setList(binding.reviewsRV, new ReviewsAdapter(reviews.getReviews()));
    }


    private void setList(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void createReview(CompanyIdModel companyId) {
        fragmentSelector.onFragmentSelected(CreateReviewFragment.TAG_FACTORY, companyId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unbind();
    }
}
