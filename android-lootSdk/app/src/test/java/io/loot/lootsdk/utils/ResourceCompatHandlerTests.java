package io.loot.lootsdk.utils;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import io.loot.lootsdk.DummyDataClass;
import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.Resource;

import static io.loot.lootsdk.models.networking.ErrorExtractor.FAILURE;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ResourceCompatHandlerTests {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
    private DummyListener dummyListener;

    @Before
    public void setUp() throws Exception {
        dummyListener = mock(DummyListener.class);
    }

    private LiveData<Resource<DummyDataClass>> emitLiveData(Resource<DummyDataClass> content) {
        LiveData<Resource<DummyDataClass>> liveData = new MutableLiveData<>();
        ((MutableLiveData<Resource<DummyDataClass>>) liveData).setValue(content);

        return liveData;
    }

    private LiveData<DummyDataClass> emitDummyLiveData(DummyDataClass content) {
        LiveData<DummyDataClass> liveData = new MutableLiveData<>();
        ((MutableLiveData<DummyDataClass>) liveData).setValue(content);

        return liveData;
    }

    @Test
    public void compatHandlerShouldInvokeCachedOnCachedResource() throws Exception {
        final DummyDataClass dummyData = new DummyDataClass("test");
        LiveData<Resource<DummyDataClass>> liveData = emitLiveData(Resource.loading(dummyData));
        new ResourceCompatHandler<DummyDataClass>(liveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener, times(1)).onCachedDataReceived(dummyData);
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void compatHandlerShouldInvokeLiveOnLiveResource() throws Exception {
        final DummyDataClass dummyData = new DummyDataClass("test");
        LiveData<Resource<DummyDataClass>> liveData = emitLiveData(Resource.success(dummyData));
        new ResourceCompatHandler<DummyDataClass>(liveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener, times(1)).onLiveDataReceived(any(DummyDataClass.class));
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void compatHandlerShouldInvokeErrorOnErrorResource() throws Exception {
        final DummyDataClass dummyData = new DummyDataClass("test");
        LiveData<Resource<DummyDataClass>> liveData = emitLiveData(Resource.error("{\"error\": \"ERROR\"}", new DummyDataClass()));
        new ResourceCompatHandler<DummyDataClass>(liveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener, times(1)).onErrorReceived("ERROR");
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void compatHandlerShouldInvokeFailureOnUnexpectedError() throws Exception {
        LiveData<Resource<DummyDataClass>> liveData = emitLiveData(Resource.error("{\"error\": \"FAILURE\"}", new DummyDataClass()));
        new ResourceCompatHandler<DummyDataClass>(liveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener, times(1)).onFailure(anyString());
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void compatHandlerShouldInvokeSessionExpiredOnSessionExpiredError() throws Exception {
        LiveData<Resource<DummyDataClass>> liveData = emitLiveData(Resource.error("{\"error\": \"SESSION_EXPIRED\"}", new DummyDataClass()));
        new ResourceCompatHandler<DummyDataClass>(liveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener, times(1)).onSessionExpired();
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void compatHandlerShouldInvokeUserBlockedOnCannotProceedError() throws Exception {
        LiveData<Resource<DummyDataClass>> liveData = emitLiveData(Resource.error("{\"error\": \"CANNOT_PROCEED\"}", new DummyDataClass()));
        new ResourceCompatHandler<DummyDataClass>(liveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener, times(1)).onUserBlocked();
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void nullableResourceOnCompatHandlerShouldInvokeUnexpectedError() throws Exception {
        LiveData<Resource<DummyDataClass>> liveData = emitLiveData(null);
        new ResourceCompatHandler<DummyDataClass>(liveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener, times(1)).onErrorReceived("UNEXPECTED_ERROR");
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void afterSuccessNoMoreInteractions() throws Exception {
        LiveData<Resource<DummyDataClass>> dummyLiveData = new MutableLiveData<>();
        new ResourceCompatHandler<DummyDataClass>(dummyLiveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.success(new DummyDataClass("FIRST_ATTEMPT")));

        verify(dummyListener, times(1)).onLiveDataReceived(any(DummyDataClass.class));

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.success(new DummyDataClass("SECOND_ATTEMPT")));
        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.loading(new DummyDataClass("THIRD_ATTEMPT")));
        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.error("{\"error\": \"CANNOT_PROCEED\"}", 422, new DummyDataClass("FOURTH_ATTEMPT")));


        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void afterCachedDataShouldCallLiveWhenAvailable() throws Exception {
        LiveData<Resource<DummyDataClass>> dummyLiveData = new MutableLiveData<>();
        new ResourceCompatHandler<DummyDataClass>(dummyLiveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.loading(new DummyDataClass("FIRST_ATTEMPT")));

        verify(dummyListener, times(1)).onCachedDataReceived(any(DummyDataClass.class));

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.success(new DummyDataClass("SECOND_ATTEMPT")));

        verify(dummyListener, times(1)).onLiveDataReceived(any(DummyDataClass.class));

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.loading(new DummyDataClass("THIRD_ATTEMPT")));
        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.error("{\"error\": \"CANNOT_PROCEED\"}", 422, new DummyDataClass("FOURTH_ATTEMPT")));


        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void afterErrorNoMoreInteractions() throws Exception {
        LiveData<Resource<DummyDataClass>> dummyLiveData = new MutableLiveData<>();
        new ResourceCompatHandler<DummyDataClass>(dummyLiveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.error("{\"error\": \"SESSION_EXPIRED\"}", 422, new DummyDataClass("FIRST_ATTEMPT")));

        verify(dummyListener, times(1)).onSessionExpired();

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.success(new DummyDataClass("SECOND_ATTEMPT")));
        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.loading(new DummyDataClass("THIRD_ATTEMPT")));
        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.loading(new DummyDataClass("FOURTH_ATTEMPT")));


        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void onFailureShouldBeInvoked() throws Exception {
        LiveData<Resource<DummyDataClass>> dummyLiveData = new MutableLiveData<>();
        new ResourceCompatHandler<DummyDataClass>(dummyLiveData, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onCachedDataReceived(DummyDataClass data) {
                dummyListener.onCachedDataReceived(data);
            }

            @Override
            public void onLiveDataReceived(DummyDataClass data) {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        ((MutableLiveData<Resource<DummyDataClass>>) dummyLiveData).setValue(Resource.error(FAILURE, 422, new DummyDataClass("FIRST_ATTEMPT")));

        verify(dummyListener, times(1)).onFailure(anyString());

        verifyNoMoreInteractions(dummyListener);
    }

    private interface DummyListener extends GenericFailListener {
        void onLiveDataReceived(DummyDataClass data);

        void onCachedDataReceived(DummyDataClass data);

        void onErrorReceived(String error);
    }

}
