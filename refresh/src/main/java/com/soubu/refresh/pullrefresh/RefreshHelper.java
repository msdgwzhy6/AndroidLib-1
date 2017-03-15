//package com.soubu.refresh.pullrefresh;
//
//import android.content.Context;
//import android.database.Observable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.soubu.refresh.pullrefresh.core.OnPullListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * 作者：余天然 on 2016/10/18 下午9:25
// */
//public class RefreshHelper<T> {
//
//    public RefreshLayout viewRefresh;
//    public RefreshInterface<T> refreshInterface;
//    public int layoutId;
//
//    public Context context;
//    public RecyclerView rv;
//    public List<T> data;
//
//    public SingleAdapter<T> adapter;
//    public int curPage = 1;//当前页码
//    public int countNum = 10;
//    public boolean isRefresh = false;//是否正在刷新
//    public boolean isLoadMore = false;//是否正在加载更多
//    public boolean isBgRefresh = false;//是否是后台静默刷新
//
//    public boolean isShowLoading = true;//是否显示loading
//
//    public RefreshHelper(RefreshLayout viewRefresh, RefreshInterface<T> refreshInterface, int layoutId) {
//        this.viewRefresh = viewRefresh;
//        this.refreshInterface = refreshInterface;
//        this.layoutId = layoutId;
//
//        context = viewRefresh.getContext();
//        rv = (RecyclerView) viewRefresh.getChildAt(0);
//        data = new ArrayList<>();
//
//        initRv();
//        initRefresh();
//    }
//
//    public void detachView() {
//        this.viewRefresh = null;
//        this.refreshInterface = null;
//        this.context = null;
//        this.rv = null;
//        this.data = null;
//    }
//
//    private void initRv() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        rv.setLayoutManager(layoutManager);
//        rv.setHasFixedSize(true);
//        adapter = new SingleAdapter<T>(context, layoutId) {
//            @Override
//            protected void bindData(BaseViewHolder holder, T item, int position) {
//                refreshInterface.bindData(holder, item, position);
//            }
//
//            @Override
//            protected void bindListener(BaseViewHolder holder, T item, int position) {
//                refreshInterface.bindListener(holder, item, position);
//            }
//
//            @Override
//            public void onItemClick(BaseViewHolder holder, T item, int position) {
//                refreshInterface.onItemClick(holder, item, position);
//            }
//        };
//        rv.setAdapter(adapter);
//    }
//
//    private void initRefresh() {
//        viewRefresh.setOnPullListener(new OnPullListener() {
//            @Override
//            public void onRefresh() {
//                LogUtil.print("");
//                isRefresh = true;
//                curPage = 1;
//                loadData();
//            }
//
//            @Override
//            public void onLoadMore() {
//                LogUtil.print("");
//                isLoadMore = true;
//                curPage++;
//                loadData();
//            }
//        });
//    }
//
//    /**
//     * 后台刷新
//     */
//    public void bgRefresh() {
//        isBgRefresh = true;
//        curPage = 1;
//        data = new ArrayList<>();
//        loadData();
//    }
//
//    /**
//     * 刷新
//     */
//    public void refresh() {
//        isBgRefresh = false;
//        curPage = 1;
//        data = new ArrayList<>();
//        loadData();
//    }
//
//    public void clearData() {
//        data = new ArrayList<>();
//    }
//
//    public void loadData() {
//        if (isRefresh || isLoadMore || isBgRefresh) {
//            isShowLoading = false;
//        } else {
//            isShowLoading = true;
//        }
//        refreshInterface.getData(curPage)
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        if (isShowLoading) {
//                            DialogHelper.showProgressDialog();
//                        }
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())//在主线程显示进度条
//                .subscribe(new Action1<List<T>>() {
//                    @Override
//                    public void call(List<T> list) {
//                        boolean hideLoadMore = false;
//                        LogUtil.print("list.size=" + list.size());
//                        if (isRefresh) {
//                            data = new ArrayList<>();
//                            isRefresh = false;
//                            viewRefresh.stopRefresh(true);
//                        }
//                        if (isLoadMore) {
//                            isLoadMore = false;
//                            viewRefresh.stopLoadMore(true);
//                            if (list.isEmpty()) {
//                                ShowWidgetUtil.showShort("没有更多数据了");
//                            }
//                        }
//                        if (isBgRefresh) {
//                            isBgRefresh = false;
//                        }
//                        data.addAll(list);
//                        if (data.isEmpty()) {
//                            rv.setVisibility(View.GONE);
//                            viewRefresh.setCanLoadMore(false);
//                        } else {
//                            rv.setVisibility(View.VISIBLE);
//                            viewRefresh.setCanLoadMore(true);
//                        }
//                        if (data.isEmpty() && dataInterface != null) {
//                            dataInterface.onData(ViewType.ERROR_EMPTY);
//                            return;
//                        }
//                        adapter.setData(data);
//                        if (dataInterface != null) {
//                            dataInterface.onData(ViewType.CONTENT);
//                        }
//                        if (hideLoadMore) {
//                            viewRefresh.setCanLoadMore(false);
//                        } else {
//                            viewRefresh.setCanLoadMore(true);
//                        }
//
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        if (throwable instanceof BaseException) {
//                            //TODO 细化应用的异常处理机制
//                            BaseException exception = (BaseException) throwable;
//                            if (exception.getErrorCode() == -2) {
//                                if (dataInterface != null) {
//                                    dataInterface.onData(ViewType.ERROR_INTERNET);
//                                }
//                            }
//                            if (exception.getErrorCode() == 404) {
//                                if (dataInterface != null) {
//                                    dataInterface.onData(ViewType.ERROR_SERVER);
//                                }
//                            }
//                            if (isRefresh) {
//                                isRefresh = false;
//                                viewRefresh.stopRefresh(false);
//                            }
//                            if (isLoadMore) {
//                                isLoadMore = false;
//                                viewRefresh.stopLoadMore(false);
//                            }
//                            if (isBgRefresh) {
//                                isBgRefresh = false;
//                            }
//                        }
//                    }
//                });
//    }
//
//    private DataInterface dataInterface;
//
//    public void setDataInterface(DataInterface dataInterface) {
//        this.dataInterface = dataInterface;
//    }
//
//    public interface DataInterface {
//        void onData(ViewType type);
//    }
//
//    public interface RefreshInterface<T> {
//
//        Observable<List<T>> getData(int curPage);
//
//        void bindData(BaseViewHolder holder, T item, int position);
//
//        void bindListener(BaseViewHolder holder, T item, int position);
//
//        void onItemClick(BaseViewHolder holder, T item, int position);
//    }
//
//    public List<T> getData() {
//        return data;
//    }
//
//    public SingleAdapter<T> getAdapter() {
//        return adapter;
//    }
//}
