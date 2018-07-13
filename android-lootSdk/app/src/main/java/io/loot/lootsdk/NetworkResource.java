package io.loot.lootsdk;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.utils.AppExecutors;

public abstract class NetworkResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

        result.setValue(Resource.<ResultType>loading(null));
        fetchFromNetwork();
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork() {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> requestTypeApiResponse) {
                result.removeSource(apiResponse);

                if (requestTypeApiResponse != null && requestTypeApiResponse.isSuccessful() && requestTypeApiResponse.getBody() != null) {
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            ResultType proceededData = proceedData(requestTypeApiResponse.getBody());
                            result.postValue(Resource.success(proceededData, requestTypeApiResponse.getCode()));
                        }
                    });
                } else if (requestTypeApiResponse != null && requestTypeApiResponse.isSuccessful() && requestTypeApiResponse.getBody() == null ) {
                        appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            ResultType proceededData = proceedData(requestTypeApiResponse.getBody());
                            result.postValue(Resource.success(proceededData, requestTypeApiResponse.getCode()));
                        }
                    });
                } else {
                    onFetchFailed();

                    String error = "UNEXPECTED_ERROR";
                    int code = -1;
                    if (requestTypeApiResponse != null && !requestTypeApiResponse.getErrorMessage().isEmpty()) {
                        error = requestTypeApiResponse.getRawErrorMessage();
                        code = requestTypeApiResponse.getCode();
                    }

                    Resource<ResultType> resource = Resource.error(error, code, null);
                    setValue(resource);
                }
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    protected abstract ResultType proceedData(@NonNull RequestType item);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();



}