package moiavto.mbsl.ru.myauto.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import moiavto.mbsl.ru.myauto.data.domainData.BookingDomainModel;
import moiavto.mbsl.ru.myauto.data.serverModel.BookingIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyIdModel;
import moiavto.mbsl.ru.myauto.data.serverModel.CompanyModel;
import moiavto.mbsl.ru.myauto.databinding.ItemBookingBinding;

import java.util.Date;
import java.util.List;

/**
 * Created by Fedor on 06.07.2017.
 */

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {

    private final Context context;
    private final OnAdapterItemClickListener listener;
    private List<BookingDomainModel> list;

    public BookingsAdapter(List<BookingDomainModel> list, Context context, OnAdapterItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBookingBinding binding = ItemBookingBinding.inflate(inflater, parent, false);
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

    public void add(List<BookingDomainModel> orderList) {
        int size = list.size();
        list.addAll(orderList);
        notifyItemRangeInserted(size, orderList.size());
    }

    public void remove(int pos) {
        if (pos < list.size()) {
            list.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void remove(BookingIdModel bookingIdModel) {
        int pos = findByBookingId(bookingIdModel);
        if (pos != -1) {
            list.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    private int findByBookingId(BookingIdModel bookingIdModel) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBooking().getBookingId().equals(bookingIdModel.getBookingId()))
                return i;
        }
        return -1;
    }


    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public interface OnAdapterItemClickListener {
        void onItemClick(CompanyIdModel model);

        void onItemDismissClick(BookingIdModel model);

        void onItemCallClick(CompanyModel model);

        void onItemNavigateClick(CompanyModel model);

        void onItemReviewClick(CompanyIdModel model);
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener,
            ServicesAdapter.OnAdapterItemClickListener {
        private final ItemBookingBinding binding;
        private BookingDomainModel item;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);

            setButtonClickListeners();
        }

        private void setButtonClickListeners() {
            binding.buttonsIncludedLayout.dismissButton.setOnClickListener(v ->
                    listener.onItemDismissClick(new BookingIdModel(item.getBooking().getBookingId())));
            binding.buttonsIncludedLayout.callButton.setOnClickListener(v ->
                    listener.onItemCallClick(item.getCompany()));
            binding.buttonsIncludedLayout.navigationButton.setOnClickListener(v ->
                    listener.onItemNavigateClick(item.getCompany()));
            binding.addReviewButton.setOnClickListener(v ->
                    listener.onItemReviewClick(new CompanyIdModel(item.getBooking().getCompanyId())));
        }


        void bindItem(BookingDomainModel item) {
            this.item = item;
            binding.setBooking(item.getBooking());
            binding.setCompany(item.getCompany());

            if (item.getBooking() != null && item.getBooking().getBookingDate() != null) {
                Date bookingDate = item.getBooking().getBookingDate();
                binding.addReviewButton.setVisibility(bookingDate.getTime() < System.currentTimeMillis() ? View.VISIBLE : View.GONE);
            }

            binding.companyPreviewLayout.favoriteImage.setVisibility(View.GONE);
            setList(binding.servicesRV, new ServicesAdapter(item.getBooking().getServices(), this), LinearLayoutManager.VERTICAL);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(new CompanyIdModel(item.getBooking().getCompanyId()));
        }

        private void setList(RecyclerView recyclerView, RecyclerView.Adapter adapter, int orientation) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onItemClick() {
            listener.onItemClick(new CompanyIdModel(item.getBooking().getCompanyId()));
        }
    }

}
