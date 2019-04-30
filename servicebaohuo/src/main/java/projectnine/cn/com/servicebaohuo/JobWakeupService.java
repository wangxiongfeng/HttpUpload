package projectnine.cn.com.servicebaohuo;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * Created by wang on 2018/4/11.
 * 5.0以上
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeupService extends JobService {

    private final int JOBWakeup = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启一个轮询
        JobInfo.Builder jobBuild = new JobInfo.Builder(JOBWakeup, new ComponentName(this, JobWakeupService.class));
        jobBuild.setPeriodic(2000);

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobBuild.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务 看messageservice有没有被杀死    杀死启动 轮询
        //判断服务有没有在运行
        boolean messageserviceAlive = isServiceWork(MessageService.class.getName());
        if (!messageserviceAlive) {
            startService(new Intent(this, MessageService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    private boolean isServiceWork(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


}
