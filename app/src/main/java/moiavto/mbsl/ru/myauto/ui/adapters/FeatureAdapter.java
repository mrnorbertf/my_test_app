package moiavto.mbsl.ru.myauto.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyFeatureModel;
import moiavto.mbsl.ru.myauto.databinding.ItemCompanyFeaturesBinding;

import java.util.List;

/**
 * Created by Fedor on 07.07.2017.
 */

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {

    private final List<CompanyFeatureModel> list;

    public FeatureAdapter(List<CompanyFeatureModel> list) {
        this.list = list;
    }

    @Override
    public FeatureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCompanyFeaturesBinding binding = ItemCompanyFeaturesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCompanyFeaturesBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }


        void bindItem(CompanyFeatureModel model) {
            binding.setFeature(model);
        }
    }


}
