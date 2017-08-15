package moiavto.mbsl.ru.myauto.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyServiceModel;
import moiavto.mbsl.ru.myauto.databinding.ItemBookingServiceBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fedor on 07.07.2017.
 */

public class CheckableServicesAdapter extends RecyclerView.Adapter<CheckableServicesAdapter.ViewHolder> {

    private final List<CompanyServiceModel> list;
    private final OnAdapterItemClickListener listener;
    private List<Integer> resultIdsList;

    public CheckableServicesAdapter(List<CompanyServiceModel> list, OnAdapterItemClickListener listener) {
        this.listener = listener;
        this.list = list;
        this.resultIdsList = new ArrayList<>();
    }

    @Override
    public CheckableServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBookingServiceBinding binding = ItemBookingServiceBinding.inflate(inflater, parent, false);
        return new CheckableServicesAdapter.ViewHolder(binding.getRoot());
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

    public interface OnAdapterItemClickListener {
        void onItemClick(List<Integer> resultIdsList);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemBookingServiceBinding binding;
        private CompanyServiceModel item;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            binding = DataBindingUtil.bind(itemView);
        }


        void bindItem(CompanyServiceModel item) {
            this.item = item;
            binding.setService(item);
        }


        @Override
        public void onClick(View v) {
            int index = resultIdsList.indexOf(item.getCompanyServiceId());
            if (index == -1) {
                resultIdsList.add(item.getCompanyServiceId());
                binding.addServiceCB.setChecked(true);
            } else {
                resultIdsList.remove(index);
                binding.addServiceCB.setChecked(false);
            }
            listener.onItemClick(resultIdsList);
        }

    }
}
