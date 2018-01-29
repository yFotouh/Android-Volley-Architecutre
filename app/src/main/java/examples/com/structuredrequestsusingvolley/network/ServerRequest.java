package examples.com.structuredrequestsusingvolley.network;

import com.android.volley.Response;

import examples.com.structuredrequestsusingvolley.AppClass;
import examples.com.structuredrequestsusingvolley.model.dto.request.CreatePostBody;
import examples.com.structuredrequestsusingvolley.model.dto.response.PostResponse;


/**
 * Created by youssef on 02-Nov-15.
 */
public class ServerRequest extends BaseRest {
    private static final String posts = "posts/";
    static ServerRequest serverRequest;

    public static final ServerRequest get() {
        if (serverRequest == null)
            return new ServerRequest();
        else
            return serverRequest;
    }

    public ServerRequest() {
        super();
    }


    public void createPosts(CreatePostBody body, RequestOptions requestOptions, Response.Listener<PostResponse> responseListener, Response.ErrorListener errorListener) {
        genericPostRequest(posts, body, PostResponse.class, requestOptions, responseListener, errorListener);
    }


    public void getPost(long userId, RequestOptions requestOptions, Response.Listener<PostResponse> responseListener, Response.ErrorListener errorListener) {
        genericGetRequest(posts, PostResponse.class, requestOptions, responseListener, errorListener, 1);
    }


    public void cancelRequestsByTag(String tag) {
        AppClass.getInstance().getRequestQueue().cancelAll(tag);
    }
}
