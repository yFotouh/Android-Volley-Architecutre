package examples.com.structuredrequestsusingvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import examples.com.structuredrequestsusingvolley.model.dto.request.CreatePostBody;
import examples.com.structuredrequestsusingvolley.model.dto.response.PostResponse;
import examples.com.structuredrequestsusingvolley.network.ServerRequest;

public class MainActivity extends AppCompatActivity {
    private static final String RESPONSE_TAG = "RESPONSE_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        demonstration of post
         */
        CreatePostBody createPostBody = new CreatePostBody();
        createPostBody.body = "test body";
        createPostBody.title = "test title";
        createPostBody.userId = 1;
        ServerRequest.get().createPosts(createPostBody, null, new Response.Listener<PostResponse>() {
            @Override
            public void onResponse(PostResponse response) {
                Log.d(RESPONSE_TAG, response.toGsonString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
 /*
        demonstration of get
         */
        ServerRequest.get().getPost(1, null, new Response.Listener<PostResponse>() {
            @Override
            public void onResponse(PostResponse response) {
                Log.d(RESPONSE_TAG, response.toGsonString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
