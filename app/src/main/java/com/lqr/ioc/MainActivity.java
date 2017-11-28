package com.lqr.ioc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.lqr.ioc.anno.BindView;
import com.lqr.ioc.anno.CheckNet;
import com.lqr.ioc.anno.ClickView;
import com.lqr.ioc.utils.ViewUtil;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_hello)
    Button mBtnHello;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtil.inject(this);
        initViewPager();
    }

    @CheckNet
    @ClickView(R.id.btn_hello)
    public void sayHello() {
        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
    }

    private void initViewPager() {
        final Fragment[] fragments = new Fragment[]{new MyFragment(1), new MyFragment(2)};
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });
    }
}
