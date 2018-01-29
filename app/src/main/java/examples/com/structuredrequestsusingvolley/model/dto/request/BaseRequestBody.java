package examples.com.structuredrequestsusingvolley.model.dto.request;


import com.google.gson.Gson;

/**
 * put what ever fields you want here that will be sent in all requests
 */
public class BaseRequestBody {
    public String toJsonString() {
        return new Gson().toJson(this);
    }


}
