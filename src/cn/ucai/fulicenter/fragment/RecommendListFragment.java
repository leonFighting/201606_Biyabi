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
import java.util.List;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.BiyabiMainActivity;
import cn.ucai.fulicenter.adapter.RecommendAdapter;
import cn.ucai.fulicenter.bean.RecommendListBean;
import cn.ucai.fulicenter.data.ApiParams;
import cn.ucai.fulicenter.data.GsonRequest;
import cn.ucai.fulicenter.widget.flashview.FlashView;

public class RecommendListFragment extends Fragment {
    public static final String TAG = RecommendListFragment.class.getName();
    BiyabiMainActivity mContext;
    private FlashView flashView;
    private ArrayList<String> imageUrls;
    ArrayList<RecommendListBean> mRecommendList;
    RecommendAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    TextView mtvHint;
    String path;
    private int action = I.ACTION_DOWNLOAD;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = (BiyabiMainActivity) getActivity();
        View layout = inflater.inflate(R.layout.fragment_recommend_list, container, false);
        Log.e(TAG, "onCreateView");
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {
        try {
            path = new ApiParams()
                    .with("infoNation","0")
                    .with("mallUrl","")
                    .getUrl("http://mws1.biyabi.com/WebService.asmx/HomePageShow");
            Log.e(TAG, "path=" + path);
            mContext.executeRequest(new GsonRequest<RecommendListBean>(path,
                    RecommendListBean.class, responseDownloadRecommendListener(),
                    mContext.errorListener()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<RecommendListBean> responseDownloadRecommendListener() {
        return new Response.Listener<RecommendListBean>() {
            @Override
            public void onResponse(RecommendListBean resultBeen) {
                Log.e(TAG,"123");
                if (resultBeen != null) {
                    Log.e(TAG,"000000000");
                    List<RecommendListBean.Resultbean> result = resultBeen.getResult();
                    for (int i = 0; i < result.size(); i++) {
                        List<RecommendListBean.Resultbean.ResultBean> result1 = result.get(i).getResult();
                        for (int j = 0; j < result1.size(); j++) {
                            String proImage = result1.get(j).getProImage();
                            if (proImage != null) {
                                Log.e(TAG, "proImage=" + proImage);
                                imageUrls.add(proImage);
                            }
                        }
                    }
                    flashView.setImageUris(imageUrls);
                }
            }
        };
    }


    private void initView(View layout) {
        flashView = (FlashView) layout.findViewById(R.id.flash_view);
        imageUrls=new ArrayList<String>();
    }


}
