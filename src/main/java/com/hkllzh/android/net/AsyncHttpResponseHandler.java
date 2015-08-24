//package com.hkllzh.android.net;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//
//import com.hkllzh.android.util.log.LogHandler;
//
//import java.net.URI;
//
//public abstract class AsyncHttpResponseHandler implements ResponseInterface {
//
//    private static final String LOG_TAG = "AsyncHttpResponseHandler";
//    private LogHandler log;
//
//    protected static final int SUCCESS_MESSAGE = 0;
//    protected static final int FAILURE_MESSAGE = 1;
//    protected static final int START_MESSAGE = 2;
//    protected static final int FINISH_MESSAGE = 3;
//
//    // protected static final int PROGRESS_MESSAGE = 4;
//    // protected static final int RETRY_MESSAGE = 5;
//    // protected static final int CANCEL_MESSAGE = 6;
//
//    protected static final int BUFFER_SIZE = 4096;
//
//    public static final String DEFAULT_CHARSET = "UTF-8";
//    public static final String UTF8_BOM = "\uFEFF";
//    private String responseCharset = DEFAULT_CHARSET;
//    private Handler handler;
//    private Looper looper = null;
//
//    /**
//     * Creates a new AsyncHttpResponseHandler
//     */
//    public AsyncHttpResponseHandler() {
//        this(null);
//    }
//
//    /**
//     * Creates a new AsyncHttpResponseHandler with a user-supplied looper. If
//     * the passed looper is null, the looper attached to the current thread will
//     * be used.
//     *
//     * @param looper The looper to work with
//     */
//    public AsyncHttpResponseHandler(Looper looper) {
//
//        log = new LogHandler();
//
//        this.looper = looper == null ? Looper.myLooper() : looper;
//        handler = new ResponderHandler(this, looper);
//    }
//
//    /**
//     * Avoid leaks by using a non-anonymous handler class.
//     */
//    private static class ResponderHandler extends Handler {
//        private final AsyncHttpResponseHandler mResponder;
//
//        ResponderHandler(AsyncHttpResponseHandler mResponder, Looper looper) {
//            super(looper);
//            this.mResponder = mResponder;
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            mResponder.handleMessage(msg);
//        }
//    }
//
//    /**
//     * Sets the charset for the response string. If not set, the default is UTF-8.
//     *
//     * @param charset to be used for the response string.
//     * @see <a href="http://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html">Charset</a>
//     */
//    public void setCharset(final String charset) {
//        this.responseCharset = charset;
//    }
//
//    public String getCharset() {
//        return this.responseCharset == null ? DEFAULT_CHARSET : this.responseCharset;
//    }
//
//    /**
//     * Fired when the request progress, override to handle in your own code
//     *
//     * @param bytesWritten offset from start of file
//     * @param totalSize    total size of file
//     */
//    public void onProgress(long bytesWritten, long totalSize) {
//        log.v(LOG_TAG, String.format("Progress %d from %d (%2.0f%%)", bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
//    }
//
//    /**
//     * Fired when the request is started, override to handle in your own code
//     */
//    public void onStart() {
//        // default log warning is not necessary, because this method is just optional notification
//    }
//
//    /**
//     * Fired in all cases when the request is finished, after both success and failure, override to
//     * handle in your own code
//     */
//    public void onFinish() {
//        // default log warning is not necessary, because this method is just optional notification
//    }
//
//
//    /**
//     * Fired when a request returns successfully, override to handle in your own code
//     *
//     * @param statusCode   the status code of the response
//     * @param headers      return headers, if any
//     * @param responseBody the body of the HTTP response from the server
//     */
//    public abstract void onSuccess(int statusCode, Header[] headers, byte[] responseBody);
//
//    /**
//     * Fired when a request fails to complete, override to handle in your own code
//     *
//     * @param statusCode   return HTTP status code
//     * @param headers      return headers, if any
//     * @param responseBody the response body, if any
//     * @param error        the underlying cause of the failure
//     */
//    public abstract void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error);
//
//    /**
//     * Fired when a retry occurs, override to handle in your own code
//     *
//     * @param retryNo number of retry
//     */
//    public void onRetry(int retryNo) {
//        Log.d(LOG_TAG, String.format("Request retry no. %d", retryNo));
//    }
//
//    public void onCancel() {
//        log.d(LOG_TAG, "Request got cancelled");
//    }
//
//    public void onUserException(Throwable error) {
//        log.e(LOG_TAG, "User-space exception detected!", error);
//        throw new RuntimeException(error);
//    }
//
//    final public void sendProgressMessage(long bytesWritten, long bytesTotal) {
//        sendMessage(obtainMessage(PROGRESS_MESSAGE, new Object[]{bytesWritten, bytesTotal}));
//    }
//
//    final public void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBytes) {
//        sendMessage(obtainMessage(SUCCESS_MESSAGE, new Object[]{statusCode, headers, responseBytes}));
//    }
//
//    final public void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
//        sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{statusCode, headers, responseBody, throwable}));
//    }
//
//    final public void sendStartMessage() {
//        sendMessage(obtainMessage(START_MESSAGE, null));
//    }
//
//    final public void sendFinishMessage() {
//        sendMessage(obtainMessage(FINISH_MESSAGE, null));
//    }
//
//    @Override
//    final public void sendRetryMessage(int retryNo) {
//        sendMessage(obtainMessage(RETRY_MESSAGE, new Object[]{retryNo}));
//    }
//
//    @Override
//    final public void sendCancelMessage() {
//        sendMessage(obtainMessage(CANCEL_MESSAGE, null));
//    }
//
//    // Methods which emulate android's Handler and Message methods
//    protected void handleMessage(Message message) {
//        Object[] response;
//
//        try {
//            switch (message.what) {
//                case SUCCESS_MESSAGE:
//                    response = (Object[]) message.obj;
//                    if (response != null && response.length >= 3) {
//                        onSuccess((Integer) response[0], (Header[]) response[1], (byte[]) response[2]);
//                    } else {
//                        Log.e(LOG_TAG, "SUCCESS_MESSAGE didn't got enough params");
//                    }
//                    break;
//                case FAILURE_MESSAGE:
//                    response = (Object[]) message.obj;
//                    if (response != null && response.length >= 4) {
//                        onFailure((Integer) response[0], (Header[]) response[1], (byte[]) response[2], (Throwable) response[3]);
//                    } else {
//                        Log.e(LOG_TAG, "FAILURE_MESSAGE didn't got enough params");
//                    }
//                    break;
//                case START_MESSAGE:
//                    onStart();
//                    break;
//                case FINISH_MESSAGE:
//                    onFinish();
//                    break;
//                case PROGRESS_MESSAGE:
//                    response = (Object[]) message.obj;
//                    if (response != null && response.length >= 2) {
//                        try {
//                            onProgress((Long) response[0], (Long) response[1]);
//                        } catch (Throwable t) {
//                            Log.e(LOG_TAG, "custom onProgress contains an error", t);
//                        }
//                    } else {
//                        Log.e(LOG_TAG, "PROGRESS_MESSAGE didn't got enough params");
//                    }
//                    break;
//                case RETRY_MESSAGE:
//                    response = (Object[]) message.obj;
//                    if (response != null && response.length == 1) {
//                        onRetry((Integer) response[0]);
//                    } else {
//                        Log.e(LOG_TAG, "RETRY_MESSAGE didn't get enough params");
//                    }
//                    break;
//                case CANCEL_MESSAGE:
//                    onCancel();
//                    break;
//            }
//        } catch(Throwable error) {
//            onUserException(error);
//        }
//    }
//
//    protected void sendMessage(Message msg) {
//        if (getUseSynchronousMode() || handler == null) {
//            handleMessage(msg);
//        } else if (!Thread.currentThread().isInterrupted()) { // do not send messages if request has been cancelled
//            Utils.asserts(handler != null, "handler should not be null!");
//            handler.sendMessage(msg);
//        }
//    }
//
//    /**
//     * Helper method to send runnable into local handler loop
//     *
//     * @param runnable runnable instance, can be null
//     */
//    protected void postRunnable(Runnable runnable) {
//        if (runnable != null) {
//            if (getUseSynchronousMode() || handler == null) {
//                // This response handler is synchronous, run on current thread
//                runnable.run();
//            } else {
//                // Otherwise, run on provided handler
//                handler.post(runnable);
//            }
//        }
//    }
//
//    /**
//     * Helper method to create Message instance from handler
//     *
//     * @param responseMessageId   constant to identify Handler message
//     * @param responseMessageData object to be passed to message receiver
//     * @return Message instance, should not be null
//     */
//    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
//        return Message.obtain(handler, responseMessageId, responseMessageData);
//    }
//
//    @Override
//    public void sendResponseMessage(HttpResponse response) throws IOException {
//        // do not process if request has been cancelled
//        if (!Thread.currentThread().isInterrupted()) {
//            StatusLine status = response.getStatusLine();
//            byte[] responseBody;
//            responseBody = getResponseData(response.getEntity());
//            // additional cancellation check as getResponseData() can take non-zero time to process
//            if (!Thread.currentThread().isInterrupted()) {
//                if (status.getStatusCode() >= 300) {
//                    sendFailureMessage(status.getStatusCode(), response.getAllHeaders(), responseBody, new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()));
//                } else {
//                    sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody);
//                }
//            }
//        }
//    }
//
//    /**
//     * Returns byte array of response HttpEntity contents
//     *
//     * @param entity can be null
//     * @return response entity body or null
//     * @throws java.io.IOException if reading entity or creating byte array failed
//     */
//    byte[] getResponseData(HttpEntity entity) throws IOException {
//        byte[] responseBody = null;
//        if (entity != null) {
//            InputStream instream = entity.getContent();
//            if (instream != null) {
//                long contentLength = entity.getContentLength();
//                if (contentLength > Integer.MAX_VALUE) {
//                    throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
//                }
//                int buffersize = (contentLength <= 0) ? BUFFER_SIZE : (int) contentLength;
//                try {
//                    ByteArrayBuffer buffer = new ByteArrayBuffer(buffersize);
//                    try {
//                        byte[] tmp = new byte[BUFFER_SIZE];
//                        long count = 0;
//                        int l;
//                        // do not send messages if request has been cancelled
//                        while ((l = instream.read(tmp)) != -1 && !Thread.currentThread().isInterrupted()) {
//                            count += l;
//                            buffer.append(tmp, 0, l);
//                            sendProgressMessage(count, (contentLength <= 0 ? 1 : contentLength));
//                        }
//                    } finally {
//                        AsyncHttpClient.silentCloseInputStream(instream);
//                        AsyncHttpClient.endEntityViaReflection(entity);
//                    }
//                    responseBody = buffer.toByteArray();
//                } catch (OutOfMemoryError e) {
//                    System.gc();
//                    throw new IOException("File too large to fit into available memory");
//                }
//            }
//        }
//        return responseBody;
//    }
//}
