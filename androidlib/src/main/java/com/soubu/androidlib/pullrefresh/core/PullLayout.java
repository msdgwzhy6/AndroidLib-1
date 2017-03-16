package com.soubu.androidlib.pullrefresh.core;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 下拉上拉的布局
 */
public abstract class PullLayout extends InterceptLayout {
    // 事件监听接口
    private OnPullListener listener;
    // Layout状态
    private PullStatus status = PullStatus.DEFAULT;
    //阻尼系数
    private float damp = 0.5f;
    //恢复动画的执行时间
    public int SCROLL_TIME = 300;
    //是否刷新完成
    private boolean isRefreshSuccess = false;
    //是否加载完成
    private boolean isLoadSuccess = false;



    private int dySum = 0;//记录整体的y偏移量

    public void setOnPullListener(OnPullListener listener) {
        this.listener = listener;
    }

    public PullLayout(Context context) {
        super(context);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isLoading == true) {
            //正在加载中时，直接处理掉子控件的手势
            return true;
        }

        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                // 计算本次滑动的Y轴增量(距离)
                int dy = y - lastYMove;
                dySum += dy;

                // 如果dy>0，即下拉操作
                if (dySum > 0) {
                    if (header != null && canPullRefresh == true) {
                        performDownScroll(dy);
                    }
                }
                // 如果dy<0，即上拉操作
                else if (dySum < 0) {
                    if (footer != null && canLoadMore == true) {
                        performUpScroll(dy);
                    }
                }
                // 记录y坐标
                lastYMove = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                dySum = 0;
                // 判断本次触摸系列事件结束时,Layout的状态
                switch (status) {
                    //下拉刷新
                    case DOWN_BEFORE:
                        scrolltoDefaultStatus(PullStatus.REFRESH_CANCEL_SCROLLING);
                        break;
                    case DOWN_AFTER:
                        scrolltoRefreshStatus();
                        break;
                    //上拉加载更多
                    case UP_BEFORE:
                        scrolltoDefaultStatus(PullStatus.LOADMORE_CANCEL_SCROLLING);
                        break;
                    case UP_AFTER:
                        scrolltoLoadStatus();
                        break;
                    default:
                        break;
                }
            }
        }
        lastYIntercept = 0;
        postInvalidate();
        return true;
    }

    private void performUpScroll(int dy) {
        // 进行Y轴上的滑动
        performScroll(dy);
        if (getScrollY() >= bottomScroll + footer.getMeasuredHeight()) {
            updateStatus(PullStatus.UP_AFTER);
        } else {
            updateStatus(PullStatus.UP_BEFORE);
        }
    }

    private void performDownScroll(int dy) {
        // 进行Y轴上的滑动
        performScroll(dy);
        if (Math.abs(getScrollY()) > header.getMeasuredHeight()) {
            updateStatus(PullStatus.DOWN_AFTER);
        } else {
            updateStatus(PullStatus.DOWN_BEFORE);
        }
    }

    //执行滑动
    public void performScroll(int dy) {
        scrollBy(0, (int) (-dy * damp));
    }

    //刷新状态
    private void updateStatus(PullStatus status) {
        this.status = status;
        int scrollY = getScrollY();
        // 判断本次触摸系列事件结束时,Layout的状态
        switch (status) {
            //默认状态
            case DEFAULT:
                onDefault();
                break;
            //下拉刷新
            case DOWN_BEFORE:
                pullHeader.onDownBefore(scrollY);
                break;
            case DOWN_AFTER:
                pullHeader.onDownAfter(scrollY);
                break;
            case REFRESH_SCROLLING:
                pullHeader.onRefreshScrolling(scrollY);
                break;
            case REFRESH_DOING:
                isLoading = true;
                pullHeader.onRefreshDoing(scrollY);
                if (listener != null) {
                    listener.onRefresh();
                }
                break;
            case REFRESH_COMPLETE_SCROLLING:
                pullHeader.onRefreshCompleteScrolling(scrollY, isRefreshSuccess);
                break;
            case REFRESH_CANCEL_SCROLLING:
                pullHeader.onRefreshCancelScrolling(scrollY);
                break;
            //上拉加载更多
            case UP_BEFORE:
                pullFooter.onUpBefore(scrollY);
                break;
            case UP_AFTER:
                pullFooter.onUpAfter(scrollY);
                break;
            case LOADMORE_SCROLLING:
                pullFooter.onLoadScrolling(scrollY);
                break;
            case LOADMORE_DOING:
                isLoading = true;
                pullFooter.onLoadDoing(scrollY);
                if (listener != null) {
                    listener.onLoadMore();
                }
                break;
            case LOADMORE_COMPLETE_SCROLLING:
                pullFooter.onLoadCompleteScrolling(scrollY, isLoadSuccess);
                break;
            case LOADMORE_CANCEL_SCROLLING:
                pullFooter.onLoadCancelScrolling(scrollY);
                break;
        }
    }

    //默认状态
    private void onDefault() {
        isRefreshSuccess = false;
        isLoadSuccess = false;
        isLoading = false;
    }

    //滚动到加载状态
    private void scrolltoLoadStatus() {
        int start = getScrollY();
        int end = footer.getMeasuredHeight() + bottomScroll;
        performAnim(start, end, new AnimListener() {
            @Override
            public void onDoing() {
                updateStatus(PullStatus.LOADMORE_SCROLLING);
            }

            @Override
            public void onEnd() {
                updateStatus(PullStatus.LOADMORE_DOING);
            }
        });
    }

    //滚动到刷新状态
    private void scrolltoRefreshStatus() {
        int start = getScrollY();
        int end = -header.getMeasuredHeight();
        performAnim(start, end, new AnimListener() {
            @Override
            public void onDoing() {
                updateStatus(PullStatus.REFRESH_SCROLLING);
            }

            @Override
            public void onEnd() {
                updateStatus(PullStatus.REFRESH_DOING);
            }
        });
    }

    //滚动到默认状态
    private void scrolltoDefaultStatus(final PullStatus startStatus) {
        int start = getScrollY();
        int end = 0;
        performAnim(start, end, new AnimListener() {
            @Override
            public void onDoing() {
                updateStatus(startStatus);
            }

            @Override
            public void onEnd() {
                updateStatus(PullStatus.DEFAULT);
            }
        });
    }

    //停止刷新
    public void stopRefresh(boolean isSuccess) {
        isRefreshSuccess = isSuccess;
        scrolltoDefaultStatus(PullStatus.REFRESH_COMPLETE_SCROLLING);
    }

    //停止加载更多
    public void stopLoadMore(boolean isSuccess) {
        isLoadSuccess = isSuccess;
        scrolltoDefaultStatus(PullStatus.LOADMORE_COMPLETE_SCROLLING);
    }

    //执行动画
    private void performAnim(int start, int end, final AnimListener listener) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(SCROLL_TIME).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollTo(0, value);
                postInvalidate();
                listener.onDoing();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    interface AnimListener {
        void onDoing();

        void onEnd();
    }


}
