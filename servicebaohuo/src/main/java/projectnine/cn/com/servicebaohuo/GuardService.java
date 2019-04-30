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
import android.widget.Toast;

/**
 * Created by wang on 2018/4/11.
 * <p>
 * 守护进程   双进程通信
 */

public class GuardService extends Service {

    private final int GuardId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级
        startForeground(GuardId, new Notification());
        bindService(new Intent(this, MessageService.class), serviceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnect.Stub() {
            @Override
            public String getName(String text) throws RemoteException {
                return  "";
            }
        };
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(GuardService.this, "建立连接", Toast.LENGTH_LONG).show();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //重新绑定
            startService(new Intent(GuardService.this, MessageService.class));
            bindService(new Intent(GuardService.this, MessageService.class), serviceConnection, Context.BIND_IMPORTANT);
        }
    };


}
