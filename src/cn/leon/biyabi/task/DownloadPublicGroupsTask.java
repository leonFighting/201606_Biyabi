package cn.leon.biyabi.task;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import java.util.ArrayList;

import cn.leon.biyabi.I;
import cn.leon.biyabi.BiyabiApplication;
import cn.leon.biyabi.activity.BaseActivity;
import cn.leon.biyabi.bean.Group;
import cn.leon.biyabi.data.ApiParams;
import cn.leon.biyabi.data.GsonRequest;
import cn.leon.biyabi.data.OkHttpUtils;

/**
 * Created by leon on 2016/5/22.
 */
public class DownloadPublicGroupsTask extends BaseActivity {
    private final static String TAG = DownloadPublicGroupsTask.class.getName();
    Context mContext;
    String userName;
    int pageId;
    int pageSize;
    String downloadPublicGroupsUrl;

    public DownloadPublicGroupsTask(Context mContext, String userName, int pageId, int pageSize) {
        this.mContext = mContext;
        this.userName = userName;
        this.pageId = pageId;
        this.pageSize = pageSize;
        initDownloadPublicGroupsUrl();
    }
//    http://10.0.2.2:8080/SuperWeChatServer/Server?request=download_public_groups&m_user_name=&page_id=&page_size=
    public void initDownloadPublicGroupsUrl() {
        try {
            downloadPublicGroupsUrl = new ApiParams()
                    .with(I.User.USER_NAME, userName)
                    .with(I.PAGE_ID, pageId + "")
                    .with(I.PAGE_SIZE, pageSize + "")
                    .getRequestUrl(I.REQUEST_FIND_PUBLIC_GROUPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        executeRequest(new GsonRequest<Group[]>(downloadPublicGroupsUrl,Group[].class,
                ResponseDownloadPublicGroupsTaskListener(),errorListener()));
    }

    private Response.Listener<Group[]> ResponseDownloadPublicGroupsTaskListener() {
        return new Response.Listener<Group[]>() {
            @Override
            public void onResponse(Group[] groups) {
                Log.e(TAG,"ResponseDownloadContactListListener,groups="+groups);
                if (groups == null) {
                    return;
                }
                ArrayList<Group> list = OkHttpUtils.array2List(groups);
                BiyabiApplication instance = BiyabiApplication.getInstance();
                ArrayList<Group> publicGroupList = instance.getPublicGroupList();
                publicGroupList.addAll(list);
                mContext.sendStickyBroadcast(new Intent("update_public_group"));
            }
        };
    }

}
