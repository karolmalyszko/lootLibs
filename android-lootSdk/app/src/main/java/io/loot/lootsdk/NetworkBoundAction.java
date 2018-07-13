package io.loot.lootsdk;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import java.util.Objects;

import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.utils.AppExecutors;

import static io.loot.lootsdk.models.networking.ErrorExtractor.UNEXPECTED_ERROR;

/**
 * Class responsible for making API Call
 * and dispatching ActionResult as a LiveData.
 *
 * @param <RequestType> - Request type of API Call
 */

public abstract class NetworkBoundAction<RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<ActionResult> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundAction(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

        result.setValue(ActionResult.loading());
        makeApiCall();
    }


    @MainThread
    private void setValue(ActionResult newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void makeApiCall() {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> requestTypeApiResponse) {
                result.removeSource(apiResponse);

                if (requestTypeApiResponse != null && requestTypeApiResponse.isSuccessful()) {
                    appExecutors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            saveCallResult(processResponse(requestTypeApiResponse));
                            appExecutors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    setValue(ActionResult.success());
                                }
                            });
                        }
                    });
                } else {
                    onApiCallFailed();

                    String errorMessage = UNEXPECTED_ERROR;
                    if (requestTypeApiResponse != null && requestTypeApiResponse.getErrorMessage() != null && !requestTypeApiResponse.getErrorMessage().isEmpty()) {
                        errorMessage = requestTypeApiResponse.getErrorMessage();
                    }

                    setValue(ActionResult.error(errorMessage));
                }
            }
        });
    }


    protected void onApiCallFailed() {
    }

    public LiveData<ActionResult> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.getBody();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}