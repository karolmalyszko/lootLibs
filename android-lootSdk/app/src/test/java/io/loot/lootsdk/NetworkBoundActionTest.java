package io.loot.lootsdk;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.networking.ApiResponse;

import static io.loot.lootsdk.models.networking.ErrorExtractor.UNEXPECTED_ERROR;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class NetworkBoundActionTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void successApiCallShouldReturnResourceWithContent() throws Exception {
        LiveData<ActionResult> liveData = new NetworkBoundAction<DummyDataClass>(new InstantAppExecutors()) {
            @Override
            protected void saveCallResult(@NonNull DummyDataClass item) {
                // nothing here
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DummyDataClass>> createCall() {
                return ApiUtils.fakeSuccessApiCall();
            }
        }.asLiveData();


        Observer observer = mock(Observer.class);
        liveData.observeForever(observer);

        verify(observer).onChanged(ActionResult.loading());
        verify(observer).onChanged(ActionResult.success());
        verifyNoMoreInteractions(observer);
    }

    @Test
    public void errorApiCallShouldReturnExtractedError() throws Exception {
        LiveData<ActionResult> liveData = new NetworkBoundAction<DummyDataClass>(new InstantAppExecutors()) {
            @Override
            protected void saveCallResult(@NonNull DummyDataClass item) {
                // nothing here
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DummyDataClass>> createCall() {
                return ApiUtils.fakeErrorCall();
            }
        }.asLiveData();


        Observer observer = mock(Observer.class);
        liveData.observeForever(observer);

        verify(observer).onChanged(ActionResult.loading());
        verify(observer).onChanged(ActionResult.error(ApiUtils.EXTRACTED_ERROR));
        verifyNoMoreInteractions(observer);
    }

    @Test
    public void failureFromNetworkShouldReturnFailureErrorMessage() throws Exception {
        LiveData<ActionResult> liveData = new NetworkBoundAction<DummyDataClass>(new InstantAppExecutors()) {
            @Override
            protected void saveCallResult(@NonNull DummyDataClass item) {
                // nothing here
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DummyDataClass>> createCall() {
                return ApiUtils.fakeFailureCall();
            }
        }.asLiveData();

        Observer observer = mock(Observer.class);
        liveData.observeForever(observer);

        verify(observer).onChanged(ActionResult.loading());
        verify(observer).onChanged(ActionResult.error(UNEXPECTED_ERROR));

        verifyNoMoreInteractions(observer);
    }

}
