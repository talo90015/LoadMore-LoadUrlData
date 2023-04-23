package com.talo.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;

import com.talo.test.adapter.MainAdapter;
import com.talo.test.data.MainData;
import com.talo.test.databinding.ActivityMainBinding;
import com.talo.test.viewmodel.MainViewModel;

import java.util.AbstractList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainAdapter adapter;
    private MainViewModel viewModel;
    private LinearLayoutManager manager;

    private int userId = 1;

    //--------------
    /**是否滑動*/

    private Boolean isScrolling = false;

    /**顯示最多數量、總數、欲載入數量
     * 總數算法:顯示最多數量 + 欲載入數量*/

    private int currentItem, totalItems, scrollOutItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        initAdapter();

        new Handler().post(() -> viewModel.getData(userId));

        //建立觀察者
        loadData();

        //刷新
        refresh();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        manager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(manager);
        binding.recycler.setHasFixedSize(true);

        binding.recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItem = manager.findFirstVisibleItemPosition();
                System.out.println("scrollOutItem" + scrollOutItem);

                if (isScrolling && (currentItem + scrollOutItem == totalItems)) {
                    isScrolling = false;
                    loadMore();
                }
            }
        });
    }

    private void initAdapter() {
        adapter = new MainAdapter(getBaseContext());
        binding.recycler.setAdapter(adapter);
    }

    private void refresh() {
        binding.refresh.setOnRefreshListener(() -> {
            binding.refresh.setRefreshing(false);
            new Handler().post(() -> viewModel.getData(1));
            binding.recycler.scrollToPosition(0);
        });

    }

    private void loadData() {
        viewModel.getList().observe(this, new Observer<List<MainData>>() {
            @Override
            public void onChanged(List<MainData> mainData) {
                adapter.setData(mainData);
                adapter.submitList(mainData);
            }
        });
    }



    private void loadMore() {
        userId += 1 ;
        binding.progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            viewModel.getData(userId);
            binding.progressBar.setVisibility(View.GONE);
            binding.recycler.scrollToPosition(0);
        }, 200);
    }

}