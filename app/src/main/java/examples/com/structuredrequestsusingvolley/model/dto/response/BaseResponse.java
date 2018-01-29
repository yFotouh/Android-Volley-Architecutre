package examples.com.structuredrequestsusingvolley.model.dto.response;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

/**
 * put what ever fields here that you will have in all responses
 */
public class BaseResponse {
    @SerializedName("Message")
    public String Message;
    //    public boolean isSuccess;
    @SerializedName("ErrorCode")
    public String ErrorCode;
    @SerializedName("TimeStamp")
    public long TimeStamp;
    public String fullResponse;

    public String toGsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    public static final BaseResponse getFromString(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, BaseResponse.class);
    }
}
