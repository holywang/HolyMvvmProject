package com.mvvm.holyandroid.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mvvm.core.fragment.BaseFragment;
import com.mvvm.holyandroid.R;
import com.mvvm.holyandroid.activity.GooglePlayBillingActivity;
import com.mvvm.holyandroid.activity.LoginActivity;
import com.mvvm.holyandroid.activity.PayActivity;
import com.mvvm.holyandroid.activity.ThirdPartyLoginActivity;
import com.mvvm.holyandroid.adapter.MainRefreshAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFragment extends BaseFragment implements MainRefreshAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private MainRefreshAdapter mAdapter;
    private List<String> mData = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsLoadingMore = false;
    private int mCurrentPage = 1;

    public MainFragment() {}

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        // 初始化RecyclerView
        initRecyclerView();
        // 设置下拉刷新
        setPullToRefresh();
        // 设置上拉加载
        setPushLoadData();
        // 加载第一页数据
        loadData(mCurrentPage);
       return view;
    }

    // 初始化RecyclerView
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainRefreshAdapter(getActivity(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    // 设置下拉刷新
    private void setPullToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // 下拉刷新
            refreshData();
        });
    }

    // 设置上拉加载
    private void setPushLoadData() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 如果滚动到了最后一个item，并且没有在加载更多，则开始加载更多
                    if (!mIsLoadingMore && isLastItemVisible()) {
                        loadMoreData();
                    }
                }
            }
        });
    }

    // 加载数据
    @SuppressLint("NotifyDataSetChanged")
    private void loadData(int page) {
        // TODO: 加载数据
        mData.addAll(Arrays.asList(getResources().getStringArray(R.array.mainList)));
        mAdapter.notifyDataSetChanged();
    }

    // 刷新数据
    @SuppressLint("NotifyDataSetChanged")
    private void refreshData() {
        // TODO: 刷新数据
        mData.clear();
        mData.addAll(Arrays.asList(getResources().getStringArray(R.array.mainList)));
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
    // 加载更多数据
    private void loadMoreData() {
        // TODO: 加载更多数据
    }
    // 判断是否滚动到了最后一个item
    private boolean isLastItemVisible() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        return lastVisibleItemPosition == itemCount - 1;
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0: jumpActivity(getActivity(), LoginActivity.class);break;
            case 1: jumpActivity(getActivity(), ThirdPartyLoginActivity.class);break;
            case 2: jumpActivity(getActivity(), PayActivity.class);break;
            case 3: jumpActivity(getActivity(), GooglePlayBillingActivity.class);break;
            default:
        }

    }
}