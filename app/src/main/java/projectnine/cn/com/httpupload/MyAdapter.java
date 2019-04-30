package projectnine.cn.com.httpupload;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wang on 2018/3/12.
 */

public class MyAdapter extends DelegateAdapter.Adapter<MyAdapter.MainViewHolder> {


    private ArrayList<HashMap<String, Object>> listItem; // 用于存放数据列表
    private Context context;
    private LayoutHelper layoutHelper;
    private RecyclerView.LayoutParams layoutParams;
    private int count = 0;

    private MyItemClickListener myItemClickListener;

    public MyAdapter(Context context, LayoutHelper layoutHelper, int count, ArrayList<HashMap<String, Object>> listItem) {
        this(context, layoutHelper, count, new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300), listItem);
    }

    public MyAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull RecyclerView.LayoutParams layoutParams, ArrayList<HashMap<String, Object>> listItem) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.count = count;
        this.layoutParams = layoutParams;
        this.listItem = listItem;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.text.setText(listItem.get(position).get("ItemTitle") + "");
        holder.image.setImageResource((Integer) listItem.get(position).get("ItemImage"));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        myItemClickListener = listener;
    }

    interface MyItemClickListener {
        void onItemClick(View v, int position);
    }


    class MainViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView image;

        public MainViewHolder(View root) {

            super(root);

            text = (TextView) root.findViewById(R.id.Item);
            image = (ImageView) root.findViewById(R.id.Image);

            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                    if (myItemClickListener != null)
                        myItemClickListener.onItemClick(v, getPosition());
                }
            });

        }

        public TextView gettext() {
            return text;

        }
    }

}
