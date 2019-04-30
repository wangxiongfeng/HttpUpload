package projectnine.cn.com.bannerviewpager;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by wang on 2018/5/30.
 */
public class BannerAdapterImpl extends BannerAdapter {

    private Context mContext;

    BannerAdapterImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public String getBannerDesc(int mCurrentPosition) {
        return "广告的描述" + mCurrentPosition;
    }


}
