package projectnine.cn.com.bannerviewpager;

import android.view.View;

/**
 * Created by wang on 2018/5/30.
 *
 * 定义一个接口
 */

public interface BannerInterface {

    /**
     * 根据位置获取viewpager里面的子view
     *
     *  @param position
     * @return
     */
    View getView(int position, View convertView);

    int getCount();

    String getBannerDesc(int mCurrentPosition);

}
