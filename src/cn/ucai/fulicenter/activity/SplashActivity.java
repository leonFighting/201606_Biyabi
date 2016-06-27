package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.ucai.fulicenter.DemoHXSDKHelper;
import cn.ucai.fulicenter.R;

/**
 * 开屏页
 *
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getName();
	private RelativeLayout rootLayout;
	private TextView versionText;
    Context mContext;
	
	private static final int sleepTime = 2000;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_splash);
		super.onCreate(arg0);
        mContext = this;

		rootLayout = (RelativeLayout) findViewById(R.id.splash_root);

		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		rootLayout.startAnimation(animation);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (DemoHXSDKHelper.getInstance().isLogined()) {
            Log.e(TAG,"isLogined");
		}

		new Thread(new Runnable() {
			public void run() {
				if (DemoHXSDKHelper.getInstance().isLogined()) {
				}else {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
				}
				startActivity(new Intent(SplashActivity.this, BiyabiMainActivity.class));
				finish();
			}
		}).start();

	}
	
	/**
	 * 获取当前应用程序的版本号
	 */
	private String getVersion() {
		String st = getResources().getString(R.string.Version_number_is_wrong);
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return st;
		}
	}
}
