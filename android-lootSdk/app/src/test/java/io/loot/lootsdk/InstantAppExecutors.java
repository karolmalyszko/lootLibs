package io.loot.lootsdk;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import io.loot.lootsdk.utils.AppExecutors;

public class InstantAppExecutors extends AppExecutors {

    private static Executor instantExecutor = new Executor() {
        @Override
        public void execute(@NonNull Runnable command) {
            command.run();;
        }
    };

    public InstantAppExecutors() {
        super(instantExecutor, instantExecutor, instantExecutor);

    }
}
