package kz.mycrm.android.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import kz.mycrm.android.api.ApiResponse;
import kz.mycrm.android.util.AppExecutors;

public abstract class DatabaseRequest<ResultType> extends NetworkBoundResource<ResultType, ApiResponse> {


    DatabaseRequest(AppExecutors appExecutors) {
        super(appExecutors);
    }

    @Override
    protected void saveCallResult(@NonNull ApiResponse item) {}

    @Override
    protected boolean shouldFetch(@Nullable Object data) {
        return false;
    }

    @NonNull
    @Override
    protected LiveData<ApiResponse<ApiResponse>> createCall() { return null; }

    @NonNull
    @Override
    protected abstract LiveData<ResultType> loadFromDb();
}