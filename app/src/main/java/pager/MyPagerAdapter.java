package pager;

/**
 * Created by 鼎鈞 on 2016/6/25.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyPagerAdapter extends PagerAdapter {

    private List<View> mList;

    public MyPagerAdapter(Context context, List<String> mPaths) {
        mList = new ArrayList<View>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < mPaths.size(); i++) {
            ImageView mImageView = new ImageView(context);

            Bitmap bitmap = BitmapFactory.decodeFile(mPaths.get(i));
            mImageView.setImageBitmap(bitmap);
            mImageView.setLayoutParams(mParams);
            //mImageView.setBackgroundResource(list.get(i));
            mList.add(mImageView);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    //销毁上一页
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        position = position % mList.size();
        container.removeView(mList.get(position));
    }

    //添加下一页
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        position = position % mList.size();
        container.addView(mList.get(position));
        return mList.get(position);
    }

}
