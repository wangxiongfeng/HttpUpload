package prjecttwo.cn.wang.collapsingtoolbarlayout;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * 带侧滑
 */
public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingactionbnt;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    /**
     * 侧滑布局
     */
    private DrawerLayout drawerLayout;
    /**
     * 左侧菜单
     */
    private LinearLayout ll;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_main);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        floatingactionbnt = (FloatingActionButton) findViewById(R.id.floatingactionbnt);
        drawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        ll = (LinearLayout) findViewById(R.id.linearll);  //
        editText = (EditText) findViewById(R.id.edittext);
        floatingactionbnt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_light)));
        floatingactionbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ScaleAnimation animation = new ScaleAnimation(1.0f, 0f, 1.0f, 0f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setDuration(2000); //设置动画持续时间
                animation.setRepeatCount(0); //设置重复次数
                animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
                animation.setStartOffset(0); //执行前的等待时间
                editText.startAnimation(animation);
                Snackbar.make(v, "haha", Snackbar.LENGTH_LONG).setAction("Snackbar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Toast", Toast.LENGTH_LONG).show();
                    }
                }).show();
            }
        });
        //给页面设置工具栏
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //必须在setSupportActionBar之后
        //toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_launcher)); //设置返回键icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //返回键
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(ll)) {
                    drawerLayout.closeDrawer(ll);
                } else {
                    drawerLayout.openDrawer(ll);
                }
            }
        });

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(MainActivity.this, "onDrawerOpened", Toast.LENGTH_LONG).show();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(MainActivity.this, "onDrawerClosed", Toast.LENGTH_LONG).show();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
//                Toast.makeText(MainActivity.this,"onDrawerSlide",Toast.LENGTH_LONG).show();
                super.onDrawerSlide(drawerView, slideOffset);
            }
        });

        String tab[] = {"RecyclerView", "ListView", "ScrollView"};
        TabLayout tabLayout = (TabLayout) findViewById(R.id.actTabLayout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tab[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tab[1]));
        tabLayout.addTab(tabLayout.newTab().setText(tab[2]));

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //对AppBarLayout进行监听，判断CollapsingToolbarLayout的状态并实现相应的逻辑
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;// 修改状态标记为展开
                        //设置工具栏标题
                        collapsingToolbarLayout.setTitle("EXPANDED");//设置title为EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        collapsingToolbarLayout.setTitle("折叠");  // 设置title不显示
                        state = CollapsingToolbarLayoutState.COLLAPSED; //   修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERMEDIATES) {
                        collapsingToolbarLayout.setTitle("EXPANDED");//设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERMEDIATES;//修改状态标记为中间
                    }
                }
            }
        });

    }

    private CollapsingToolbarLayoutState state;

    /**
     * 枚举类型
     */
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERMEDIATES
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "set", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            this.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            this.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.primary);
            getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }
}
