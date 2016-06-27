package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.RecommendBean;
import cn.ucai.fulicenter.data.RequestManager;
import cn.ucai.fulicenter.widget.FooterViewHolder;

/**
 * Created by leon on 2016/6/27.
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = RecommendAdapter.class.getName();
    Context context;
    ArrayList<RecommendBean> mRecommendList;
    int sortBy;
    public RecommendAdapter(Context context, ArrayList<RecommendBean> mRecommendList) {
        this.context = context;
        this.mRecommendList = mRecommendList;
        sortBy = I.SORT_BY_ADDTIME_DESC;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
        sort(sortBy);
        notifyDataSetChanged();
    }

    RecommendViewholder mRecommendViewholder;
    String footerText;
    Boolean isMore;
    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public boolean isMore() {
        return isMore;
    }

    private void sort(final int sortBy){
        Collections.sort(mRecommendList, new Comparator<RecommendBean>() {
            @Override
            public int compare(RecommendBean g1, RecommendBean g2) {
                DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm");
                long time1 = 0;
                long time2 = 0;
                try {
                    Date date1 = fmt.parse(g1.getUpdateTime());
                    time1 = date1.getTime();
                    Date date2 = fmt.parse(g2.getUpdateTime());
                    time2 = date2.getTime();
                    Log.e(TAG, "time1="+time1 + ",time2="+time2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int result =0;
                switch (sortBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result = (int) (time1-time2);
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result = (int) (time2-time1);
                        break;
                }
                return result;
            }
        });
    }
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(inflater.inflate(R.layout.item_footer, parent, false));
                break;
            case I.TYPE_ITEM:
                holder = new RecommendViewholder(inflater.inflate(R.layout.item_recommendlist, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).tvFooter.setText(footerText);
            ((FooterViewHolder) holder).tvFooter.setVisibility(View.VISIBLE);
        }
        if (position == mRecommendList.size()) {
            return;
        }
        if (holder instanceof RecommendViewholder) {
            mRecommendViewholder = (RecommendViewholder) holder;
            final RecommendBean recommendBean = mRecommendList.get(position);

            String path = recommendBean.getMainImage();
            Log.e(TAG, "getMainImage.path=" + path);
            mRecommendViewholder.nivDiscoverlist.setImageUrl(path, RequestManager.getImageLoader());
            mRecommendViewholder.nivDiscoverlist.setDefaultImageResId(R.drawable.nopic);
            mRecommendViewholder.nivDiscoverlist.setErrorImageResId(R.drawable.nopic);
            mRecommendViewholder.tvTitleDiscoverlist.setText(recommendBean.getInfoTitle());
            mRecommendViewholder.tvPriceDiscoverlist.setText("¥ " + recommendBean.getMinPriceRMB());
            mRecommendViewholder.tvSourcediscoverlist.setText(recommendBean.getMallName());
            if (recommendBean.getMaxPriceRMB() == recommendBean.getOrginalPriceRMB()) {
                mRecommendViewholder.tvOrginalPriceDiscoverlist.setVisibility(View.GONE);
            } else {
                mRecommendViewholder.tvOrginalPriceDiscoverlist.setVisibility(View.VISIBLE);
                mRecommendViewholder.tvOrginalPriceDiscoverlist.setText("¥ " + recommendBean.getOrginalPriceRMB());
                //加删除线
                mRecommendViewholder.tvOrginalPriceDiscoverlist.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            mRecommendViewholder.tvCommentcountDiscoverlist.setText(""+recommendBean.getIsGood());
            if (recommendBean.getInfoReview() > 0) {
                mRecommendViewholder.ivReviewcountDiscoverlist.setVisibility(View.VISIBLE);
                mRecommendViewholder.tvReviewcountDiscoverlist.setText(""+recommendBean.getInfoReview());
            }
            String updateTime = recommendBean.getInfoTime().substring(recommendBean.getInfoTime().lastIndexOf(" ") + 1);
            mRecommendViewholder.tvTimeDiscoverlist.setText(updateTime);
            mRecommendViewholder.tvTagRecommendlist.setText("海外直购");
        }
    }

    @Override
    public int getItemCount() {
        return mRecommendList == null ? 1 : mRecommendList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    public void initItems(ArrayList<RecommendBean> list) {
        if (list != null ) {
            mRecommendList.clear();
        }
        mRecommendList.addAll(list);
        sort(sortBy);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<RecommendBean> list) {
        mRecommendList.addAll(list);
        sort(sortBy);
        notifyDataSetChanged();
    }

    class RecommendViewholder extends RecyclerView.ViewHolder {
        FrameLayout layoutImgDiscoverList;
        NetworkImageView nivDiscoverlist;
        ImageView ivSalestatusRecommendlist;
        TextView tvTitleDiscoverlist;
        TextView tvPriceDiscoverlist;
        TextView tvOrginalPriceDiscoverlist;
        TextView tvSourcediscoverlist;
        ImageView ivGoodDiscoverlist;
        TextView tvCommentcountDiscoverlist;
        ImageView ivReviewcountDiscoverlist;
        TextView tvReviewcountDiscoverlist;
        TextView tvTimeDiscoverlist;
        TextView tvTagRecommendlist;

        public RecommendViewholder(View itemView) {
            super(itemView);
            layoutImgDiscoverList = (FrameLayout) itemView.findViewById(R.id.img_layout_discoverlist);
            nivDiscoverlist = (NetworkImageView) itemView.findViewById(R.id.img_discoverlist);
            ivSalestatusRecommendlist = (ImageView) itemView.findViewById(R.id.salestatus_iv_recommendlist);
            tvTitleDiscoverlist = (TextView) itemView.findViewById(R.id.title_discoverlist);
            tvPriceDiscoverlist = (TextView) itemView.findViewById(R.id.price_discoverlist);
            tvOrginalPriceDiscoverlist = (TextView) itemView.findViewById(R.id.orginalPrice_discoverlist);
            tvSourcediscoverlist = (TextView) itemView.findViewById(R.id.source_discoverlist);
            ivGoodDiscoverlist = (ImageView) itemView.findViewById(R.id.good_iv_discoverlist);
            tvCommentcountDiscoverlist = (TextView) itemView.findViewById(R.id.commentcount_discoverlist);
            ivReviewcountDiscoverlist = (ImageView) itemView.findViewById(R.id.reviewcount_iv_discoverlist);
            tvReviewcountDiscoverlist = (TextView) itemView.findViewById(R.id.reviewcount_discoverlist);
            tvTimeDiscoverlist = (TextView) itemView.findViewById(R.id.time_discoverlist);
            tvTagRecommendlist = (TextView) itemView.findViewById(R.id.tag_tv_recommendlist);
        }
    }
}
