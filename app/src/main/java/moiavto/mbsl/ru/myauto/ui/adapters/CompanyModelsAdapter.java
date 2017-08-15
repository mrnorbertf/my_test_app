package moiavto.mbsl.ru.myauto.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.databinding.ItemCompanyOffersBinding;

import java.util.List;

/**
 * Created by Fedor on 06.07.2017.
 */

public class CompanyModelsAdapter extends RecyclerView.Adapter<CompanyModelsAdapter.ViewHolder> {

    private final Context context;
    private final OnAdapterItemClickListener listener;
    private List<CompanyModel> list;

    public CompanyModelsAdapter(List<CompanyModel> orderList, Context context, OnAdapterItemClickListener listener) {
        list = orderList;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCompanyOffersBinding binding = ItemCompanyOffersBinding.inflate(inflater, parent, false);
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

    public void add(List<CompanyModel> orderList) {
        int size = list.size();
        list.addAll(orderList);
        notifyItemRangeInserted(size, orderList.size());
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public interface OnAdapterItemClickListener {
        void onItemClick(int companyId);

        void onItemEnrollClick(CompanyModel companyId);

        void onItemCallClick(CompanyModel company);

        void onItemNavigateClick(CompanyModel company);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener,
            ServicesAdapter.OnAdapterItemClickListener
    {
        private final ItemCompanyOffersBinding binding;
        private CompanyModel company;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);

            setButtonClickListeners();
        }

        private void setButtonClickListeners() {
            binding.buttonsIncludedLayout.enrollButton.setOnClickListener(v ->
                    listener.onItemEnrollClick(company));
            binding.buttonsIncludedLayout.callButton.setOnClickListener(v ->
                    listener.onItemCallClick(company));
            binding.buttonsIncludedLayout.navigationButton.setOnClickListener(v ->
                    listener.onItemNavigateClick(company));
        }


        void bindItem(CompanyModel company) {
            this.company = company;
            binding.setCompany(company);

            binding.companyPreviewLayout.favoriteImage.setVisibility(company.getInFavorites() ? View.VISIBLE : View.GONE);
            setList(binding.servicesRV, new ServicesAdapter(company.getServices(), this), LinearLayoutManager.VERTICAL);
            binding.servicesRV.setNestedScrollingEnabled(false);
            setList(binding.featuresRV, new FeatureAdapter(company.getFeatures()), LinearLayoutManager.HORIZONTAL);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(company.getCompanyId());
        }

        private void setList(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onItemClick() {
            listener.onItemClick(company.getCompanyId());
        }
    }

}
