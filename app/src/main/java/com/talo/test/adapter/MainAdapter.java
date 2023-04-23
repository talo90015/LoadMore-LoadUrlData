package com.talo.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.talo.test.data.MainData;
import com.talo.test.databinding.LayoutItemBinding;

import java.util.List;
import java.util.Objects;

public class MainAdapter extends ListAdapter<MainData, MainAdapter.ViewHolder> {

    private Context context;
    private List<MainData> list;

    public MainAdapter(Context context) {
        super(DiffCallback);
        this.context = context;
    }

    public void setData(List<MainData> dataList) {
        this.list = dataList;
        list.addAll(dataList);
    }

    private static final DiffUtil.ItemCallback<MainData> DiffCallback = new DiffUtil.ItemCallback<MainData>() {
        @Override
        public boolean areItemsTheSame(@NonNull MainData oldItem, @NonNull MainData newItem) {
            return true;
        }

        @Override
        public boolean areContentsTheSame(@NonNull MainData oldItem, @NonNull MainData newItem) {
            return Objects.equals(oldItem.getTitle(), newItem.getTitle()) &&
                    Objects.equals(oldItem.getBody(), newItem.getBody());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutItemBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.txtUserId.setText("USER ID " + list.get(position).getUserId());
        holder.binding.txtNumber.setText(String.valueOf(position));
        holder.binding.txtTitle.setText(list.get(position).getTitle());
        holder.binding.txtContent.setText(list.get(position).getBody());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

         LayoutItemBinding binding;

        public ViewHolder(@NonNull LayoutItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
