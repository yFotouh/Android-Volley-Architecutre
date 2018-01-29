package examples.com.structuredrequestsusingvolley.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import examples.com.structuredrequestsusingvolley.AppClass;
import examples.com.structuredrequestsusingvolley.model.dto.request.BaseRequestBody;
import examples.com.structuredrequestsusingvolley.model.dto.response.BaseResponse;


/**
 * Created by youssef on 03-Nov-15.
 */
public class BaseRest {
    public static final int defualtNumberOfRetries = 1;
    public static final int defualtTimeOut = 30000;
    public static final String UI_DEPENDANT_REQUEST_TAG = "UI_DEPENDANT_REQUEST_TAG";
    public static final String NON_UI_DEPENDANT_REQUEST_TAG = "NON_UI_DEPENDANT_REQUEST_TAG";
    Context ctx;

    public BaseRest() {
        this.ctx = AppClass.getInstance();
    }

    public static final String BASEURL = "https://jsonplaceholder.typicode.com/";

    public void fireRequest(Request request, RequestOptions requestOptions) {
        request.setTag(UI_DEPENDANT_REQUEST_TAG);
        if (requestOptions != null) {
            int timeOut = defualtTimeOut;
            int maxRetries = defualtNumberOfRetries;
            if (requestOptions.noOfRetries != null)
                maxRetries = requestOptions.noOfRetries;
            if (requestOptions.timeOut != null)
                timeOut = requestOptions.timeOut;
            if (requestOptions.tag != null) {
                request.setTag(requestOptions.tag);
            }
//            }
            request.setRetryPolicy(new DefaultRetryPolicy(
                    timeOut,
                    maxRetries,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            if (requestOptions.requestType != null) {
                if (requestOptions.requestType == RequestType.now) {
                    AppClass.getInstance().getRequestQueue().add(request);
                } else if (requestOptions.requestType == RequestType.whenInternet) {
                    AppClass.getInstance().getRequestQueue().add(request);
                }
            } else
                AppClass.getInstance().getRequestQueue().add(request);

        } else {
            request.setRetryPolicy(new DefaultRetryPolicy(
                    defualtTimeOut,
                    defualtNumberOfRetries,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppClass.getInstance().getRequestQueue().add(request);

        }


    }

    public <T extends BaseResponse> void fireRequest(Request request, RequestFuture<T> requestFuture, RequestOptions requestOptions, Response.Listener listener, Response.ErrorListener errorListener) {

        request.setTag(UI_DEPENDANT_REQUEST_TAG);
        if (requestOptions != null) {
            int timeOut = defualtTimeOut;
            int maxRetries = defualtNumberOfRetries;
            if (requestOptions.noOfRetries != null)
                maxRetries = requestOptions.noOfRetries;
            if (requestOptions.timeOut != null)
                timeOut = requestOptions.timeOut;
            if (requestOptions.tag != null) {
                request.setTag(requestOptions.tag);
            }


            request.setRetryPolicy(new DefaultRetryPolicy(
                    defualtTimeOut,
                    defualtNumberOfRetries,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleyError volleyError = new VolleyError();

            AppClass.getInstance().getRequestQueue().add(request);
            try {
                BaseResponse response = null;
                try {
                    response = requestFuture.get(10, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    volleyError.initCause(e);
                    errorListener.onErrorResponse(volleyError);
                    e.printStackTrace();

                }
                listener.onResponse(response);
            } catch (InterruptedException e) {
                volleyError.initCause(e);
                errorListener.onErrorResponse(volleyError);
            } catch (ExecutionException e) {
                volleyError.initCause(e);
                errorListener.onErrorResponse(volleyError);
            }
        }


    }

    public <T extends BaseResponse> void genericPostRequest(String api, BaseRequestBody body, Class responseClass, RequestOptions requestOptions, Response.Listener responseListener, Response.ErrorListener errorListener, Object... params) {
        if (!ConnectionUtils.isNetworkAvailable(ctx)) {
//            NotificationUtils.noInternet(ctx);
            return;
        }
        String fullUrl;
        if (requestOptions != null && requestOptions.isFullUrl) {
            fullUrl = api;
        } else {
            fullUrl = BASEURL + api;
        }
        Map<String, String> headers = new HashMap<String, String>();

        if (requestOptions != null && requestOptions.async == false) {
            RequestFuture<T> future = RequestFuture.newFuture();
            GenericRequest<T> request = new GenericRequest<T>(fullUrl, responseClass,
                    future, future, headers);

            fireRequest(request, future, requestOptions, responseListener, errorListener);
        } else {
            GenericRequest<T> request = new GenericRequest<T>(Request.Method.POST, responseClass, fullUrl, body.toJsonString(),
                    responseListener, errorListener, headers);
            fireRequest(request, requestOptions);
        }
    }


    public <T extends BaseResponse> void genericPostRequest(String api, String body, Class responseClass, RequestOptions requestOptions, Response.Listener responseListener, Response.ErrorListener errorListener, Object... params) {
        if (!ConnectionUtils.isNetworkAvailable(ctx)) {
            return;
        }
        String fullUrl;
        fullUrl = BASEURL + api;
//        for(int i=0;i<params.length;i++)
//            fullUrl+=params[i];
        Map<String, String> headers = new HashMap<String, String>();
        GenericRequest<T> request = new GenericRequest<T>(Request.Method.POST, responseClass, fullUrl, body,
                responseListener, errorListener, headers);
        if (requestOptions == null) {

        }
        fireRequest(request, requestOptions);
    }

    public <T extends BaseResponse> void genericPostTestRequest(String api, String body, RequestOptions requestOptions, Response.Listener responseListener, Response.ErrorListener errorListener, Object... params) {
        if (!ConnectionUtils.isNetworkAvailable(ctx)) {
//            NotificationUtils.noInternet(ctx);
            return;
        }
        String fullUrl;
        fullUrl = BASEURL + api;
        Map<String, String> headers = new HashMap<String, String>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, fullUrl, body,
                responseListener, errorListener);
        if (requestOptions == null) {

        }
        fireRequest(request, requestOptions);
    }

    public <T extends BaseResponse> void genericGetRequest(String api, Class responseClass, RequestOptions requestOptions, Response.Listener responseListener, Response.ErrorListener errorListener, Object... params) {
        if (!ConnectionUtils.isNetworkAvailable(ctx)) {
//            NotificationUtils.noInternet(ctx);
            return;
        }
        String fullUrl;
        fullUrl = BASEURL + api;
        if (fullUrl.endsWith("/"))
            fullUrl = fullUrl.substring(0, fullUrl.length() - 1);
        for (int i = 0; i < params.length; i++) {
            fullUrl += "/";
            fullUrl += params[i];
        }
        Map<String, String> headers = new HashMap<String, String>();
        GenericRequest<T> request = new GenericRequest<T>(fullUrl, responseClass,
                responseListener, errorListener, headers);
        fireRequest(request, requestOptions);
    }

    public static final BaseResponse getErrorMessage(VolleyError volleyError) {
        if (volleyError.networkResponse != null) {
            String errorMessage = new String(volleyError.networkResponse.data);
            return BaseResponse.getFromString(errorMessage);
        }
        return null;
    }
}
