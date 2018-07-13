package io.loot.lootsdk.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

public class LiveDataUtils {

    public static <T> LiveData<T> emit(T data) {
        MutableLiveData<T> liveData = new MutableLiveData<>();
        liveData.setValue(data);

        return liveData;
    }

}
