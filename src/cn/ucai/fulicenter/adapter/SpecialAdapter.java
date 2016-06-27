package cn.ucai.fulicenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.SpecialBean;
import cn.ucai.fulicenter.data.RequestManager;
import cn.ucai.fulicenter.widget.FooterViewHolder;

/**
 * Created by leon on 2016/6/27.
 */
public class SpecialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = SpecialAdapter.class.getName();

    Context context;
    ArrayList<SpecialBean> mSpecialList;

    public SpecialAdapter(Context context, ArrayList<SpecialBean> list) {
        this.context = context;
        this.mSpecialList = list;
    }

    SpecialViewHolder specialViewHolder;
    String footerText;
    boolean isMore;

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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case I.TYPE_FOOTER:
                holder = new FooterViewHolder(inflater.inflate(R.layout.item_footer, parent, false));
                break;
            case I.TYPE_ITEM:
                holder = new SpecialViewHolder(inflater.inflate(R.layout.item_special, parent, false));
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
        if (position == mSpecialList.size()) {
            return;
        }
        if (holder instanceof SpecialViewHolder) {
            specialViewHolder = (SpecialViewHolder) holder;
            final SpecialBean specialBean = mSpecialList.get(position);

            String path = specialBean.getStrSpecialImage();
            Log.e(TAG, "getStrSpecialImage.path=" + path);
            specialViewHolder.networkImageView.setImageUrl(path, RequestManager.getImageLoader());
            specialViewHolder.networkImageView.setDefaultImageResId(R.drawable.nopic);
            specialViewHolder.networkImageView.setErrorImageResId(R.drawable.nopic);
        }
    }

    @Override
    public int getItemCount() {
        return mSpecialList == null ? 1 : mSpecialList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        } else {
            return I.TYPE_ITEM;
        }
    }

    public void initItems(ArrayList<SpecialBean> list) {
        if (list != null ) {
            mSpecialList.clear();
        }
        mSpecialList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<SpecialBean> list) {
        mSpecialList.addAll(list);
        notifyDataSetChanged();
    }

    class SpecialViewHolder extends RecyclerView.ViewHolder {
        NetworkImageView networkImageView;

        public SpecialViewHolder(View itemView) {
            super(itemView);
            networkImageView = (NetworkImageView) itemView.findViewById(R.id.niv_item_special);
        }
    }
}
