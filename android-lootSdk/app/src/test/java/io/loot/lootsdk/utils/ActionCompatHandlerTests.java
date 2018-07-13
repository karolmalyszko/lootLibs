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
import io.loot.lootsdk.models.ActionResult;

import static io.loot.lootsdk.models.networking.ErrorExtractor.CANNOT_PROCEED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.FAILURE;
import static io.loot.lootsdk.models.networking.ErrorExtractor.SESSION_EXPIRED;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ActionCompatHandlerTests {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();
    private DummyListener dummyListener;

    @Before
    public void setUp() throws Exception {
        dummyListener = mock(DummyListener.class);
    }

    private LiveData<ActionResult> emitSuccess() {
        MutableLiveData<ActionResult> liveData = new MutableLiveData<>();
        liveData.setValue(ActionResult.success());
        return liveData;
    }

    private LiveData<ActionResult> emitLoading() {
        MutableLiveData<ActionResult> liveData = new MutableLiveData<>();
        liveData.setValue(ActionResult.loading());
        return liveData;
    }

    private LiveData<ActionResult> emitError(String error) {
        MutableLiveData<ActionResult> liveData = new MutableLiveData<>();
        liveData.setValue(ActionResult.error(error));
        return liveData;
    }

    @Test
    public void successLiveDataShouldInvokeSuccessMethod() throws Exception {
        LiveData<ActionResult> result = emitSuccess();
        final DummyDataClass data = new DummyDataClass();

        new ActionCompatHandler(result, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onSuccess() {
                dummyListener.onLiveDataReceived(data);
            }
        }.handle();

        verify(dummyListener).onLiveDataReceived(data);
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void errorLiveDataShouldReturExtractedError() throws Exception {
        final String error = "EXTRACTED_ERROR";
        LiveData<ActionResult> result = emitError(error);

        new ActionCompatHandler(result, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onSuccess() {
                dummyListener.onLiveDataReceived(new DummyDataClass());
            }
        }.handle();

        verify(dummyListener).onErrorReceived(error);
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void sessionExpiredShouldInvokeProperMethod() throws Exception {
        final String error = SESSION_EXPIRED;
        LiveData<ActionResult> result = emitError(error);

        new ActionCompatHandler(result, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onSuccess() {
                dummyListener.onLiveDataReceived(new DummyDataClass());
            }
        }.handle();

        verify(dummyListener).onSessionExpired();
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void cannotProceedErrorShouldInvokeProperMethod() throws Exception {
        final String error = CANNOT_PROCEED;
        LiveData<ActionResult> result = emitError(error);

        new ActionCompatHandler(result, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onSuccess() {
                dummyListener.onLiveDataReceived(new DummyDataClass());
            }
        }.handle();

        verify(dummyListener).onUserBlocked();
        verifyNoMoreInteractions(dummyListener);
    }

    @Test
    public void failureErrorShouldInvokeProperMethod() throws Exception {
        final String error = FAILURE;
        LiveData<ActionResult> result = emitError(error);

        new ActionCompatHandler(result, dummyListener) {
            @Override
            public void onError(String errorMessage) {
                dummyListener.onErrorReceived(errorMessage);
            }

            @Override
            public void onSuccess() {
                dummyListener.onLiveDataReceived(new DummyDataClass());
            }
        }.handle();

        verify(dummyListener).onFailure(error);
        verifyNoMoreInteractions(dummyListener);
    }

    private interface DummyListener extends GenericFailListener {
        void onLiveDataReceived(DummyDataClass data);

        void onCachedDataReceived(DummyDataClass data);

        void onErrorReceived(String error);
    }

}
