package com.hkllzh.android.net.impl;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.hkllzh.android.net.ResponseInterface;
import com.hkllzh.android.util.log.LogHandler;


/**
 * 参考了开源项目（<a href="https://github.com/loopj/android-async-http">android-async-http</a>）中的<a href="https://github.com/loopj/android-async-http/blob/master/library/src/main/java/com/loopj/android/http/AsyncHttpResponseHandler.java">AsyncHttpResponseHandler</a>的一些实现
 * 删除了一些细节，之后会慢慢加上。
 */
public abstract class AbstractAsyncResponseImpl {// implements ResponseInterface {

    private ResponseInterface mResponseInterface;

    private static final String LOG_TAG = "AbstractAsyncResponseImpl";
    private LogHandler log;

    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;

    private Handler handler;

    public AbstractAsyncResponseImpl(ResponseInterface responseInterface) {
        mResponseInterface = responseInterface;
        log = LogHandler.getInstance();
        handler = new ResponderHandler(this, Looper.myLooper());
    }

    /**
     * Avoid leaks by using a non-anonymous handler class.
     */
    private static class ResponderHandler extends Handler {
        private final AbstractAsyncResponseImpl mResponder;

        ResponderHandler(AbstractAsyncResponseImpl mResponder, Looper looper) {
            super(looper);
            this.mResponder = mResponder;
        }

        @Override
        public void handleMessage(Message msg) {
            mResponder.handleMessage(msg);
        }
    }

    public void onUserException(Throwable error) {
        log.e(LOG_TAG, "User-space exception detected!", error);
        throw new RuntimeException(error);
    }

    final public void sendSuccessMessage(String response) {
        handler.sendMessage(obtainMessage(SUCCESS_MESSAGE, response));
    }

    final public void sendFailureMessage(String failedMessage) {
        handler.sendMessage(obtainMessage(FAILURE_MESSAGE, failedMessage));
    }

    final public void sendStartMessage() {
        handler.sendMessage(obtainMessage(START_MESSAGE, null));
    }

    final public void sendFinishMessage() {
        handler.sendMessage(obtainMessage(FINISH_MESSAGE, null));
    }


    // Methods which emulate android's Handler and Message methods
    protected void handleMessage(Message message) {
        try {
            switch (message.what) {
                case SUCCESS_MESSAGE:
                    String success = (String) message.obj;
                    mResponseInterface.success(success);
                    break;
                case FAILURE_MESSAGE:
                    String failedMessage = (String) message.obj;
                    mResponseInterface.failed(failedMessage);
                    break;
                case START_MESSAGE:
                    mResponseInterface.start();
                    break;
                case FINISH_MESSAGE:
                    mResponseInterface.finish();
                    break;
            }
        } catch (Throwable error) {
            onUserException(error);
        }
    }

    /**
     * Helper method to create Message instance from handler
     *
     * @param responseMessageId   constant to identify Handler message
     * @param responseMessageData object to be passed to message receiver
     * @return Message instance, should not be null
     */
    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return Message.obtain(handler, responseMessageId, responseMessageData);
    }

}
