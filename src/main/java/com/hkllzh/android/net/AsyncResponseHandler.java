package com.hkllzh.android.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.hkllzh.android.util.log.LogHandler;


public abstract class AsyncResponseHandler implements ResponseInterface {

    private static final String LOG_TAG = "AsyncResponseHandler";
    private LogHandler log;

    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;

    private Handler handler;

    /**
     * Creates a new AsyncResponseHandler
     */
    public AsyncResponseHandler() {
        this(null);
    }

    /**
     * Creates a new AsyncResponseHandler with a user-supplied looper. If
     * the passed looper is null, the looper attached to the current thread will
     * be used.
     *
     * @param looper The looper to work with
     */
    public AsyncResponseHandler(Looper looper) {

        log = new LogHandler();
        handler = new ResponderHandler(this, Looper.myLooper());
        Handler handler2 = new Handler();
        Handler handler3 = new Handler();
    }

    /**
     * Avoid leaks by using a non-anonymous handler class.
     */
    private static class ResponderHandler extends Handler {
        private final AsyncResponseHandler mResponder;

        ResponderHandler(AsyncResponseHandler mResponder, Looper looper) {
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
                    success(success);
                    break;
                case FAILURE_MESSAGE:
                    String failedMessage = (String) message.obj;
                    failed(failedMessage);
                    break;
                case START_MESSAGE:
                    start();
                    break;
                case FINISH_MESSAGE:
                    finish();
                    break;
            }
        } catch (Throwable error) {
            onUserException(error);
        }
    }

    protected void sendMessage(Message msg) {
        handleMessage(msg);
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
