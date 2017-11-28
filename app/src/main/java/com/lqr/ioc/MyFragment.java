package com.lqr.ioc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lqr.ioc.anno.BindView;
import com.lqr.ioc.anno.CheckNet;
import com.lqr.ioc.anno.ClickView;
import com.lqr.ioc.utils.ViewUtil;

public class MyFragment extends Fragment {

    int index;
    private View mRootView;

    @BindView(R.id.tv_index)
    TextView mTvIndex;

    public MyFragment(int index) {
        this.index = index;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_my, null, false);
            ViewUtil.inject(this, mRootView);
            initView();
        }
        return mRootView;
    }

    private void initView() {
        mTvIndex.setText(String.valueOf(index));
        switch (index) {
            case 1:
                mRootView.setBackgroundColor(Color.parseColor("#FFEBCD"));
                break;
            case 2:
                mRootView.setBackgroundColor(Color.parseColor("#DCDCDC"));
                break;
        }
    }

    @CheckNet
    @ClickView(R.id.tv_index)
    public void index() {
        mTvIndex.setText(index + " test success");
    }
}
