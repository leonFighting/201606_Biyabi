package cn.ucai.fulicenter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.BiyabiMainActivity;
import cn.ucai.fulicenter.adapter.RecommendAdapter;
import cn.ucai.fulicenter.bean.RecommendBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

public class RecommendFragment extends Fragment {
    public static final String TAG = RecommendFragment.class.getName();
    BiyabiMainActivity mContext;
    ArrayList<RecommendBean> mRecommendList;
    RecommendAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView mtvHint;
    String path;
    int pageIndex = 1;
    int pageSize = 20;
    private int action = I.ACTION_DOWNLOAD;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = (BiyabiMainActivity) getActivity();
        View layout = inflater.inflate(R.layout.fragment_recommend,container,false);
        Log.e(TAG, "onCreateView");
        mRecommendList = new ArrayList<RecommendBean>();
        initView(layout);
        initData();
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownRefreshListener();
        setPullUpRefreshListener();
    }

    private void initData() {
        try {
            getPath(pageIndex);
            mContext.executeRequest(new GsonRequest<RecommendBean[]>(path,
                    RecommendBean[].class, responseDownloadRecommendListener(),
                    mContext.errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<RecommendBean[]> responseDownloadRecommendListener() {
        return new Response.Listener<RecommendBean[]>() {
            @Override
            public void onResponse(RecommendBean[] recommendBeen) {
                if (recommendBeen != null) {
                    Log.e(TAG, "onResponse,action=" + action + "recommendBeen.length=" + recommendBeen.length);
                    Log.e(TAG, "recommendBeen=" + recommendBeen.length);
                    mAdapter.setMore(true);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mtvHint.setVisibility(View.GONE);
                    mAdapter.setFooterText(getResources().getString(R.string.load_more));
                    ArrayList<RecommendBean> list = Utils.array2List(recommendBeen);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initItems(list);
                    } else if (action == I.ACTION_PULL_UP) {
                        mAdapter.addItems(list);
                    }
                    if(recommendBeen.length< I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                        mAdapter.setFooterText(getResources().getString(R.string.no_more));
                    }
                }
            }
        };
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl_recommend);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        mtvHint = (TextView) layout.findViewById(R.id.tv_refresh_hint);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_recommend);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new RecommendAdapter(mContext, mRecommendList);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 上拉刷新事件监听
     */
    private void setPullUpRefreshListener() {
        mRecyclerView.setOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    int lastItemPosition;
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.e(TAG,"setPullUpRefreshListener,newState="+newState);
                        if(newState == RecyclerView.SCROLL_STATE_IDLE &&
                                lastItemPosition == mAdapter.getItemCount()-1){
                            if(mAdapter.isMore()){
                                mSwipeRefreshLayout.setRefreshing(true);
                                action = I.ACTION_PULL_UP;
                                pageIndex++;
                                getPath(pageIndex);
                                mContext.executeRequest(new GsonRequest<RecommendBean[]>(path,
                                        RecommendBean[].class, responseDownloadRecommendListener(),
                                        mContext.errorListener()));
                            }
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        lastItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                        mSwipeRefreshLayout.setEnabled(mLinearLayoutManager
                                .findFirstCompletelyVisibleItemPosition() == 0);
                    }
                }
        );
    }

    private String getPath(int pageIndex) {
        try {
            path = new ApiParams()
                    .with("infoNation", "0")
                    .with("homeShow", "0")
                    .with("brightUrl", "")
                    .with("infoType","5")
                    .with("isTop","1")
                    .with("tagUrl","")
                    .with("catUrl","")
                    .with("keyWord","")
                    .with("exceptMallUrl","")
                    .with("mallUrl","")
                    .with("pageIndex", "" + pageIndex)
                    .with("pageSize", "" + pageSize)
                    .getUrl("http://mws1.biyabi.com/WebService.asmx/GetInfoListWithHomeshowAndIstop_exceptMallUrlJson");
            Log.e(TAG, "path=" + path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 下拉刷新事件监听
     */
    private void setPullDownRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener(){
                    @Override
                    public void onRefresh() {
                        mtvHint.setVisibility(View.VISIBLE);
                        pageIndex = 1;
                        action = I.ACTION_PULL_DOWN;
                        getPath(pageIndex);
                        mContext.executeRequest(new GsonRequest<RecommendBean[]>(path,
                                RecommendBean[].class, responseDownloadRecommendListener(),
                                mContext.errorListener()));
                    }
                }
        );
    }



}
