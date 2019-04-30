package projectnine.cn.com.httpupload;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wang on 2018/3/12.
 */

public class VlayoutActivity extends Activity implements MyAdapter.MyItemClickListener {

    RecyclerView recyclerView;
    MyAdapter Adapter_linearLayout, Adapter_GridLayout, Adapter_FixLayout,
            Adapter_ScrollFixLayout, Adapter_FloatLayout, Adapter_ColumnLayout,
            Adapter_SingleLayout, Adapter_onePlusNLayout, Adapter_StickyLayout,
            Adapter_StaggeredGridLayout;
    private ArrayList<HashMap<String, Object>> listItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alibaba_main);


        /**
         *
         * 步骤1：创建RecyclerView & VirtualLayoutManager 对象并进行绑定
         */
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // 创建RecyclerView对象

        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        // 创建VirtualLayoutManager对象
        // 同时内部会创建一个LayoutHelperFinder对象，用来后续的LayoutHelper查找

        recyclerView.setLayoutManager(layoutManager); // 将VirtualLayoutManager绑定到recyclerView

        /** * 步骤2：设置组件复用回收池 * */
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);


        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemImage", R.mipmap.ic_launcher);
            listItem.add(map);
        }

        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();

        linearLayoutHelper.setItemCount(4);// 设置布局里Item个数
        linearLayoutHelper.setPadding(20, 20, 20, 20);
        // 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        linearLayoutHelper.setMargin(20, 20, 20, 20);
        // 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        linearLayoutHelper.setBgColor(Color.GRAY);
        // 设置背景颜色
        linearLayoutHelper.setAspectRatio(6);
        // 设置设置布局内每行布局的宽与高的比
        // linearLayoutHelper特有属性
        linearLayoutHelper.setDividerHeight(10);
        // 设置间隔高度
        // 设置的间隔会与RecyclerView的addItemDecoration（）添加的间隔叠加.
        linearLayoutHelper.setMarginBottom(100);
        // 设置布局底部与下个布局的间隔


        Adapter_linearLayout = new MyAdapter(this, linearLayoutHelper, 4, listItem) {
            // 参数2:绑定绑定对应的LayoutHelper
            // 参数3:传入该布局需要显示的数据个数
            // 参数4:传入需要绑定的数据
            // 通过重写onBindViewHolder()设置更丰富的布局效果
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 为了展示效果,将布局的第一个数据设置为linearLayout
                if (position == 0) {
                    holder.text.setText("Linear");
                }
                // 为了展示效果,将布局里不同位置的Item进行背景颜色设置
                if (position < 2) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 6) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };
        Adapter_linearLayout.setOnItemClickListener(VlayoutActivity.this);
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        adapters.add(Adapter_linearLayout);

        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        delegateAdapter.setAdapters(adapters);


        recyclerView.setAdapter(delegateAdapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(5, 5, 5, 5);
            }
        });


    }

    @Override
    public void onItemClick(View v, int position) {
        Toast.makeText(this, (String) listItem.get(position).get("ItemTitle"), Toast.LENGTH_SHORT).show();

    }
}
