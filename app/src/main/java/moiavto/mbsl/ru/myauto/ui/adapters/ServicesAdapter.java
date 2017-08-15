package moiavto.mbsl.ru.myauto.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyServiceModel;
import moiavto.mbsl.ru.myauto.databinding.ItemCompanyServiceBinding;

import java.util.List;

/**
 * Created by Fedor on 07.07.2017.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private final List<CompanyServiceModel> list;
    private final OnAdapterItemClickListener listener;

    public ServicesAdapter(List<CompanyServiceModel> list, OnAdapterItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCompanyServiceBinding binding = ItemCompanyServiceBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding.getRoot());
    }


    @Override
    public void onBindViewHolder(ServicesAdapter.ViewHolder holder, int position) {
        holder.bindItem(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnAdapterItemClickListener {
        void onItemClick();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final ItemCompanyServiceBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }


        void bindItem(CompanyServiceModel model) {
            binding.setService(model);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onItemClick();
        }
    }
}
