package projectnine.cn.com.eventbustest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cn.eventbean.MyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * eventbus是一款针对android优化的发布订阅时间总线
 * 主要功能十替代handler broadcast 在fragment activity service线性之间传递消息
 * 将发送者和接收者解耦
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }


    /**
     * EventBus3.0有以下四种ThreadMode：
     * POSTING（默认）：如果使用事件处理函数指定了线程模型为POSTING，那么该事件在哪个线程发布出来的，事件处理函数就会在这个线程中运行，
     * 也就是说发布事件和接收事件在同一个线程。在线程模型为POSTING的事件处理函数中尽量避免执行耗时操作，因为它会阻塞事件的传递，甚至有可能会引起ANR。
     * MAIN:
     * 事件的处理会在UI线程中执行。事件处理时间不能太长，长了会ANR的。
     * BACKGROUND：如果事件是在UI线程中发布出来的，那么该事件处理函数就会在新的线程中运行，如果事件本来就是子线程中发布出来的，
     * 那么该事件处理函数直接在发布事件的线程中执行。在此事件处理函数中禁止进行UI更新操作。
     * ASYNC：无论事件在哪个线程发布，该事件处理函数都会在新建的子线程中执行，同样，此事件处理函数中禁止进行UI更新操作。
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 200,sticky = false)
    public void test1(MyEvent event) {
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(event.getMessage());
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }








}
