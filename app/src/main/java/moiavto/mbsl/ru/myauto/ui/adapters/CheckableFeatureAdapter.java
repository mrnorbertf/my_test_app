package moiavto.mbsl.ru.myauto.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyFeatureModel;
import moiavto.mbsl.ru.myauto.databinding.ItemCompanyFeaturesBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fedor on 07.07.2017.
 */

public class CheckableFeatureAdapter extends RecyclerView.Adapter<CheckableFeatureAdapter.ViewHolder> {

    private final List<CompanyFeatureModel> list;
    private List<Integer> resultIdsList;

    public CheckableFeatureAdapter(List<CompanyFeatureModel> list, List<Integer> features) {
        this.list = list;
        resultIdsList = features;
        if (resultIdsList == null)
            resultIdsList = new ArrayList<>();
    }

    @Override
    public CheckableFeatureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCompanyFeaturesBinding binding = ItemCompanyFeaturesBinding.inflate(inflater, parent, false);
        return new CheckableFeatureAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<Integer> getSelectedItems() {
        return resultIdsList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final static float ALPHA_NOT_CONTAINED = 0.5f;
        private final static float ALPHA_CONTAINED = 1f;
        private final ItemCompanyFeaturesBinding binding;
        private CompanyFeatureModel feature;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            binding = DataBindingUtil.bind(itemView);
        }


        void bindItem(CompanyFeatureModel model) {
            this.feature = model;
            binding.setFeature(model);
            binding.featureImage.setAlpha(isContains() != -1 ? ALPHA_CONTAINED : ALPHA_NOT_CONTAINED);
        }


        @Override
        public void onClick(View v) {
            setFilter();
        }

        private void setFilter() {
            int index = isContains();
            if (index == -1) {
                setColorFilter(ALPHA_CONTAINED);
                resultIdsList.add(feature.getCompanyFeatureId());
            } else {
                setColorFilter(ALPHA_NOT_CONTAINED);
                resultIdsList.remove(index);
            }
        }

        private int isContains() {
            return resultIdsList.indexOf(feature.getCompanyFeatureId());
        }

        private void setColorFilter(float alpha) {
            binding.featureImage.setAlpha(alpha);
        }

    }
}
