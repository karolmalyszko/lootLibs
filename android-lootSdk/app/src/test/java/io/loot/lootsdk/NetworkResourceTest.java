package io.loot.lootsdk;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.networking.ErrorExtractor;
import io.loot.lootsdk.networking.ApiResponse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class NetworkResourceTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void successTest() throws Exception {
        LiveData<Resource<DummyDataClass>> result = new NetworkResource<DummyDataClass, DummyDataClass>(new InstantAppExecutors()) {
            @Override
            protected DummyDataClass proceedData(@NonNull DummyDataClass item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DummyDataClass>> createCall() {
                return ApiUtils.fakeSuccessApiCall();
            }
        }.asLiveData();

        Observer observer = mock(Observer.class);
        result.observeForever(observer);

        verify(observer).onChanged(Resource.loading(null));
        verify(observer).onChanged(Resource.success(ApiUtils.dataClass));
        verifyNoMoreInteractions(observer);
    }

    @Test
    public void errorTest() throws Exception {
        LiveData<Resource<DummyDataClass>> result = new NetworkResource<DummyDataClass, DummyDataClass>(new InstantAppExecutors()) {
            @Override
            protected DummyDataClass proceedData(@NonNull DummyDataClass item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DummyDataClass>> createCall() {
                return ApiUtils.fakeErrorCall();
            }
        }.asLiveData();

        Observer observer = mock(Observer.class);
        result.observeForever(observer);

        verify(observer).onChanged(Resource.loading(null));
        verify(observer).onChanged(Resource.error(ApiUtils.JSON_ERROR, ApiUtils.ERROR_CODE, null));
        verifyNoMoreInteractions(observer);
    }

    @Test
    public void failureTest() throws Exception {
        LiveData<Resource<DummyDataClass>> result = new NetworkResource<DummyDataClass, DummyDataClass>(new InstantAppExecutors()) {
            @Override
            protected DummyDataClass proceedData(@NonNull DummyDataClass item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<DummyDataClass>> createCall() {
                return ApiUtils.fakeFailureCall();
            }
        }.asLiveData();

        Observer observer = mock(Observer.class);
        result.observeForever(observer);

        verify(observer).onChanged(Resource.loading(null));
        verify(observer).onChanged(Resource.error(ErrorExtractor.UNEXPECTED_ERROR, ApiUtils.ERROR_CODE, null));
        verifyNoMoreInteractions(observer);
    }

}
