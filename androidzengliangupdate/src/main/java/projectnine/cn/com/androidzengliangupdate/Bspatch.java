package projectnine.cn.com.androidzengliangupdate;

/**
 * Created by wang on 2018/7/7.
 */

public class Bspatch {

    public static native void bsPatch(String oldPatch, String newPath, String patchPath);

    static {
        System.load("timBsPatch");
    }


}
