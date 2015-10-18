package ark.com.ibotta.cloud;
/*
    Listener Interface to handle Volley async callbacks.
 */
public interface ResponseListener {
    public void onResponseReceived(Object result);
    public void onResponseFailed(Exception errorMessage);

}
