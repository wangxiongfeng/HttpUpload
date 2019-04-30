package projectnine.cn.com.sockettest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by wang on 2018/8/30.
 */

public class TCPServerService extends Service {


    private boolean mIsServiceDestoryed=false;

    private String[] mDefinedMessagees=new String[]{
            "11111",
            "22222",
            "33333",
            "44444"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        mIsServiceDestoryed=true;
        super.onDestroy();
    }

    private class TcpServer implements  Runnable{
        @Override
        public void run() {
            ServerSocket serverSocket;
            try{
                serverSocket=new ServerSocket(8688);
                while (!mIsServiceDestoryed){
                    final Socket client=serverSocket.accept();
                    System.out.println("accept");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            responseClient(client);
                        }
                    }).start();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private void responseClient(Socket client) {
        //用于接收客户端信息
        try {
            BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
            out.print("--------");
            while (!mIsServiceDestoryed){
                String str=in.readLine();
                System.out.print("msg from client :"+str);
                if(str==null){
                    break;
                }
                int i=new Random().nextInt(mDefinedMessagees.length);
                String msg=mDefinedMessagees[i];
                out.println(msg);
                System.out.print("send:"+msg);
            }
            System.out.println("client quit.");
            out.close();
            in.close();
            client.close();

        }catch (Exception e){

        }



    }


}
