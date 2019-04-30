package com.cn.binary;

import android.os.Looper;
import android.webkit.MimeTypeMap;

import com.cn.util.Poster;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by wang on 2018/3/14.
 */

public class FileBinary implements Binary {

    private OnBinaryProgressListener onBinaryProgressListener;
    private File file;
    private int what;//不同文件的进度

    public FileBinary(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("the file is not exist");
        }
        this.file = file;
    }

    /**
     * 设置上传进度监听
     *
     * @param onBinaryProgressListener
     */
    public void setProgressListener(int what, OnBinaryProgressListener onBinaryProgressListener) {
        this.what = what;
        this.onBinaryProgressListener = onBinaryProgressListener;
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public String getMimeType() {
        String mimeType = "application/octet-stream";
        if (MimeTypeMap.getSingleton().hasExtension(getFileName())) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(getFileName());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return mimeType;
    }

    @Override
    public long getBinaryLength() {
        return file.length();
    }

    @Override
    public void onWriteBinary(OutputStream outputStream) {
        long allLength = getBinaryLength();
        long hasUploadLength = 0;
        try {
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                hasUploadLength += len;
                int progress = (int) (hasUploadLength / allLength * 100);
                //上传进度
                Poster.getinstance().post(new ProgressMessage(ProgressMessage.CMD_PROGRESS, what, progress, onBinaryProgressListener));
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
