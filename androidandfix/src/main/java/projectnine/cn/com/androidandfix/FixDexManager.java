package projectnine.cn.com.androidandfix;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by wang on 2018/6/20.
 */
public class FixDexManager {

    private static final String TAG = "FixDexManager";
    private Context mContext;
    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext = context;
        // 获取应用可以访问的dex目录
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath
     */
    public void fixDex(String fixDexPath) throws Exception {
        // 2 获取下载好的补丁的dexElement
        // 2.1 移动到系统能访问的 dex目录下  ClassLoader
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath);
        }
        File destFile = new File(mDexDir, srcFile.getName()); // dex目录下
        if (destFile.exists()) {
            Log.d(TAG, "patch [" + fixDexPath + "] has be loaded.");
            return;
        }

        // 拷贝文件到dex目录下
        copyFile(srcFile, destFile);

        // 2.2  ClassLoader读取dex路径 为什么加入集合  因为可能一启动就要修复
        List<File> fixdexFiles = new ArrayList<>();
        fixdexFiles.add(destFile);

        //  修复dex
        fixdexfiles(fixdexFiles);

    }

    /**
     * 把dexElement注入到classloader中
     *
     * @param classLoader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {
        //先获取Pathlist
        Field pathListfield = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListfield.setAccessible(true);
        Object pathlist = pathListfield.get(classLoader);
        // pathlist里面的dexElements
        Field dexElementsField = pathlist.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        dexElementsField.set(pathlist, dexElements);
    }


    /**
     * 从classloader 中获取dexElements  反射
     *
     * @param classLoader
     * @return
     */
    private Object getdexElementByClassLoader(ClassLoader classLoader) throws Exception {
        //先获取Pathlist
        Field pathListfield = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListfield.setAccessible(true);
        Object pathlist = pathListfield.get(classLoader);
        //pathlist里面的dexElements
        Field dexElementsField = pathlist.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        return dexElementsField.get(pathlist);
    }


    /**
     * 合并数组
     *
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; ++k) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - j));
            }
        }
        return result;
    }


    /**
     * copy file
     *
     * @param src  source file
     * @param dest target file
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }


    /**
     * 加载全部的修复包 （之前已经拷贝进去了）
     */
    public void loadFixDex() throws Exception {
        // dex目录下所有文件
        File[] dexfiles = mDexDir.listFiles();
        List<File> fixdexfile = new ArrayList<>();
        for (File dexfile : dexfiles) {
            if (dexfile.getName().endsWith(".dex")) {
                fixdexfile.add(dexfile);
            }
        }
        fixdexfiles(fixdexfile);
    }


    /**
     * 修复dex
     *
     * @param fixdexFiles
     */
    private void fixdexfiles(List<File> fixdexFiles) throws Exception {

        // 1 先获取已经运行的dexElement
        ClassLoader applicationClassLoader = mContext.getClassLoader();
        Object applicationdexElements = getdexElementByClassLoader(applicationClassLoader);

        //解压文件
        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }

        //修复
        for (File fixdexFile : fixdexFiles) {
            Log.e("path", fixdexFile.getAbsolutePath() + "  " + fixdexFiles.size());
            //dexpath  dex路径
            //optimizedDirectory 解压路径
            //librarySearchPath  .so文件位置
            //parent 父的ClassLoader
            ClassLoader fixdexclassloader = new BaseDexClassLoader(  // DexClassLoader  解压路径
                    fixdexFile.getAbsolutePath(), // dex路径  必须要在应用目录下的dex文件中 所以要拷贝(前面已经考进来了)
                    optimizedDirectory,   //  解压路径
                    null,   // .so的位置
                    applicationClassLoader  //  父加载器
            );
            Object fixDexElements = getdexElementByClassLoader(fixdexclassloader);
            //3 把补丁的dexElement插到已经运行的dexElement的最前面
            //applicationClassLoader 数组  合并fixDexElements 数组
            //3.1合并完成
            applicationdexElements = combineArray(fixDexElements, applicationdexElements);
        }

        // 3.2 把合并的数组 注入到原来的类中 applicationClassLoader
        injectDexElements(applicationClassLoader, applicationdexElements);
    }


}
