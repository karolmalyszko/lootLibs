package io.loot.lootsdk;

import android.arch.lifecycle.MutableLiveData;

import io.loot.lootsdk.networking.ApiResponse;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApiUtils {

    public static final String EXTRACTED_ERROR = "EXTRACTED_ERROR";
    public static final String JSON_ERROR = "{\"error\": \""+ EXTRACTED_ERROR + "\"}";
    public static final int ERROR_CODE = 404;

    public static final DummyDataClass dataClass = new DummyDataClass("success_content");

    public static MutableLiveData<ApiResponse<DummyDataClass>> fakeSuccessApiCall() {
        MutableLiveData<ApiResponse<DummyDataClass>> liveData = new MutableLiveData<>();
        ApiResponse<DummyDataClass> successResponse = new ApiResponse<>(Response.success(dataClass));

        liveData.setValue(successResponse);

        return liveData;
    }

    public static MutableLiveData<ApiResponse<DummyDataClass>> fakeErrorCall() {
        MutableLiveData<ApiResponse<DummyDataClass>> liveData = new MutableLiveData<>();

        Response<DummyDataClass> error = Response.error(ERROR_CODE, ResponseBody.create(MediaType.parse("Application/Json"), JSON_ERROR));
        ApiResponse<DummyDataClass> errorResponse = new ApiResponse<>(error);

        liveData.setValue(errorResponse);

        return liveData;
    }

    public static MutableLiveData<ApiResponse<DummyDataClass>> fakeFailureCall() {
        MutableLiveData<ApiResponse<DummyDataClass>> liveData = new MutableLiveData<>();
        ApiResponse<DummyDataClass> errorResponse = new ApiResponse<>(new Throwable("SAMPLE_THROWABLE_FROM_NETWORK"));

        liveData.setValue(errorResponse);
        return liveData;
    }

}
