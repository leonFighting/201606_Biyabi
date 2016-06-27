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
import cn.ucai.fulicenter.adapter.SpecialAdapter;
import cn.ucai.fulicenter.bean.SpecialBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.utils.Utils;

public class SpecialGoodFragment extends Fragment {
    public static final String TAG = SpecialGoodFragment.class.getName();
    BiyabiMainActivity mContext;
    ArrayList<SpecialBean> mSpecialList;
    SpecialAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView mtvHint;
    String path;
    int p_iPageIndex = 1;
    private int action = I.ACTION_DOWNLOAD;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = (BiyabiMainActivity) getActivity();
        View layout = inflater.inflate(R.layout.fragment_special_good,container,false);
        Log.e(TAG, "onCreateView");
        mSpecialList = new ArrayList<SpecialBean>();
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
            getPath(p_iPageIndex);
            mContext.executeRequest(new GsonRequest<SpecialBean[]>(path,
                    SpecialBean[].class,responseDownloadSpecialListener(),
                    mContext.errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<SpecialBean[]> responseDownloadSpecialListener() {
        return new Response.Listener<SpecialBean[]>() {
            @Override
            public void onResponse(SpecialBean[] specialBeen) {
                if (specialBeen != null) {
                    Log.e(TAG, "onResponse,action=" + action + "specialBeen.length=" + specialBeen.length);
                    Log.e(TAG, "specialBeen=" + specialBeen.length);
                    mAdapter.setMore(true);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mtvHint.setVisibility(View.GONE);
                    mAdapter.setFooterText(getResources().getString(R.string.load_more));
                    ArrayList<SpecialBean> list = Utils.array2List(specialBeen);
                    if (action == I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initItems(list);
                    } else if (action == I.ACTION_PULL_UP) {
                        mAdapter.addItems(list);
                    }
                    if(specialBeen.length< I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                        mAdapter.setFooterText(getResources().getString(R.string.no_more));
                    }
                }
            }
        };
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl_special);
        mSwipeRefreshLayout.setColorSchemeColors(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        mtvHint = (TextView) layout.findViewById(R.id.tv_refresh_hint);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv_special);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new SpecialAdapter(mContext, mSpecialList);
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
                                p_iPageIndex++;
                                getPath(p_iPageIndex);
                                mContext.executeRequest(new GsonRequest<SpecialBean[]>(path,
                                        SpecialBean[].class,responseDownloadSpecialListener(),
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

    private String getPath(int p_iPageIndex) {
        try {
            path = new ApiParams()
                    .with("p_iParentSpecialID", "0")
                    .with("p_btSpecialType", "2")
                    .with("p_iPageIndex", "" + ""+p_iPageIndex)
                    .with("p_iPageSize", "" + ""+I.PAGE_SIZE_DEFAULT)
                    .getUrl("http://mws1.biyabi.com/WebService.asmx/SpecialListQuery");
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
                        p_iPageIndex = 1;
                        action = I.ACTION_PULL_DOWN;
                        getPath(p_iPageIndex);
                        mContext.executeRequest(new GsonRequest<SpecialBean[]>(path,
                                SpecialBean[].class,responseDownloadSpecialListener(),
                                mContext.errorListener()));
                    }
                }
        );
    }


}
