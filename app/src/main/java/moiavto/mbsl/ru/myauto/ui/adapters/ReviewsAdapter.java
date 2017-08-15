package moiavto.mbsl.ru.myauto.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.data.serverModel.ReviewModel;
import moiavto.mbsl.ru.myauto.databinding.ItemCompanyReviewBinding;

import java.util.List;

/**
 * Created by Fedor on 07.07.2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final List<ReviewModel> list;

    public ReviewsAdapter(List<ReviewModel> list) {
        this.list = list;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCompanyReviewBinding binding = ItemCompanyReviewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }


    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemCompanyReviewBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }


        void bindItem(ReviewModel model) {
            binding.setReview(model);
            binding.ratingBar.setRating(model.getRating());
        }
    }
}
