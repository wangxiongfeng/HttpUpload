package com.cn.okhttpprogress;

/**
 * Created by wang on 2018/7/16.
 */

public interface UploadProgressListener  {

    void  uploadProgress(long currentLength,long contextLength);
}
