package com.cn.binary;

import java.io.OutputStream;

/**
 * Created by wang on 2018/3/14.
 */

public interface Binary {


    String getFileName();

    String getMimeType();

    long getBinaryLength();

    void  onWriteBinary(OutputStream outputStream);

}
