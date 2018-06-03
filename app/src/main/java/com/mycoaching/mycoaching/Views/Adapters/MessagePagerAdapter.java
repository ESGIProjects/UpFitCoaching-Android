package com.mycoaching.mycoaching.Views.Adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by kevin on 20/05/2018.
 */
public class MessagePagerAdapter extends PagerAdapter {

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return 1;
    }

}
