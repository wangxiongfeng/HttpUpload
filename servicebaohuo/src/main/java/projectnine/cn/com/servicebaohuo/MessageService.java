package projectnine.cn.com.servicebaohuo;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by wang on 2018/4/11.
 * <p>
 * QQ聊天通讯 需要轻
 */

public class MessageService extends Service {

    private final int messageId = 1;


    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Log.e("TAG", "等待接受消息");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程的优先级
        startForeground(messageId, new Notification());
        bindService(new Intent(this, GuardService.class), serviceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        checkCallingOrSelfPermission("com.cn.XXXXX")

        return new ProcessConnect.Stub() {
            @Override
            public String getName(String text) throws RemoteException {
                return "";
            }
        };

//        return  new MyBinder();

    }


    ProcessConnect processConnect;

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            processConnect.asBinder().unlinkToDeath(deathRecipient, 0);
            processConnect = null;
            //重新绑定GuardService

        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder ibinder) {
            Toast.makeText(MessageService.this, "建立连接", Toast.LENGTH_LONG).show();
            processConnect = ProcessConnect.Stub.asInterface(ibinder);  //将binder转化服务端接口类型的对象
//            service.linkToDeath(deathRecipient,0); // 给Binder设置死亡代理

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //重新绑定
            startService(new Intent(MessageService.this, GuardService.class));
            bindService(new Intent(MessageService.this, GuardService.class), serviceConnection, Context.BIND_IMPORTANT);
        }

    };

    class MyBinder extends Binder {
        MessageService getService() {
            return new MessageService();
        }
    }


}
