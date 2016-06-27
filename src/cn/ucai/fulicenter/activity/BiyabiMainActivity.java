package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.RecommendFragment;
import cn.ucai.fulicenter.fragment.SpecialGoodFragment;

public class BiyabiMainActivity extends BaseActivity {
    public static final String TAG = BiyabiMainActivity.class.getName();
    SpecialGoodFragment mSpecialGoodFragment;
    RecommendFragment mRecommendFragment;
    private Fragment[] mFragments = new Fragment[2];
    private int index;
    private int currentTabIndex;

    TextView mtvCartHint;
    RadioButton mRadioHome;
    RadioButton mRadioSearch;
    RadioButton mRadioShareorder;
    RadioButton mRadioCart;
    RadioButton mRadioUser;

    private RadioButton[] mRadios = new RadioButton[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biyabi_main);
        initView();
        initFragment();
    }

    private void initFragment() {
        mSpecialGoodFragment = new SpecialGoodFragment();
        mRecommendFragment = new RecommendFragment();
        mFragments[0] = mSpecialGoodFragment;
        mFragments[1] = mRecommendFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mSpecialGoodFragment)
                .add(R.id.fragment_container,mRecommendFragment)
                .hide(mRecommendFragment)
                .show(mSpecialGoodFragment)
                .commit();
    }

    private void initView() {
        mtvCartHint = (TextView) findViewById(R.id.tv_CartHint);

        mRadioHome = (RadioButton) findViewById(R.id.layout_home);
        mRadioSearch = (RadioButton) findViewById(R.id.layout_search);
        mRadioShareorder = (RadioButton) findViewById(R.id.layout_shareorder);
        mRadioCart = (RadioButton) findViewById(R.id.layout_cart);
        mRadioUser = (RadioButton) findViewById(R.id.layout_user);

        mRadios[0] = mRadioHome;
        mRadios[1] = mRadioSearch;
        mRadios[2] = mRadioShareorder;
        mRadios[3] = mRadioCart;
        mRadios[4] = mRadioUser;
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_home:
                index = 0;
                break;
            case R.id.layout_search:
                index = 1;
                break;
            case R.id.layout_shareorder:
                index = 2;
                break;
            case R.id.layout_cart:
                index = 3;
                break;
            case R.id.layout_user:
                index = 4;
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.fragment_container, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
            setRadioChecked(index);
            currentTabIndex = index;
        }
    }

    public void setRadioChecked(int index) {
        for (int i = 0; i < mRadios.length; i++) {
            if (i == index) {
                mRadios[i].setChecked(true);
            } else {
                mRadios[i].setChecked(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.fragment_container, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
            setRadioChecked(index);
            currentTabIndex = index;
        }
    }
}
