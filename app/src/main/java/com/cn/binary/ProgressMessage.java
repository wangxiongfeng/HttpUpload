package com.cn.binary;

/**
 * Created by wang on 2018/3/14.
 */

public class ProgressMessage implements Runnable {

    public static final int CMD_PROGRESS = 2;
    public static final int CMD_ERROR = 1;
    private int cmd;
    private int what;
    private int progress;
    private OnBinaryProgressListener onBinaryProgressListener;

    public ProgressMessage(int cmd, int what, int progress, OnBinaryProgressListener onBinaryProgressListener) {
        this.cmd = cmd;
        this.progress = progress;
        this.onBinaryProgressListener = onBinaryProgressListener;
        this.what = what;
    }

    public ProgressMessage(int cmd, int what, OnBinaryProgressListener onBinaryProgressListener) {
        this.cmd = cmd;
        this.what = what;
        this.onBinaryProgressListener = onBinaryProgressListener;
    }

    @Override
    public void run() {
        switch (cmd) {
            case CMD_ERROR:
                if (onBinaryProgressListener != null) {
                    onBinaryProgressListener.onError(what);
                }
                break;
            case CMD_PROGRESS:
                if (onBinaryProgressListener != null) {
                    onBinaryProgressListener.onProgress(what, progress);
                }
                break;
        }

    }
}
