package com.talo.test.viewmodel;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.talo.test.data.ApiClient;
import com.talo.test.data.MainData;
import com.talo.test.data.MainInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private MainInterface mainInterface;

    private MutableLiveData<List<MainData>> list = new MutableLiveData<>();

    public void getData(int userId) {
        mainInterface = ApiClient.getApiData().create(MainInterface.class);
        Call<List<MainData>> call = mainInterface.getData(userId);

        call.enqueue(new Callback<List<MainData>>() {
            @Override
            public void onResponse(@NonNull Call<List<MainData>> call, @NonNull Response<List<MainData>> response) {
                list.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<MainData>> call, Throwable t) {
                System.out.println("fail " + t.getMessage());
            }
        });
    }

    public MutableLiveData<List<MainData>> getList() {
        return list;
    }

    public void setList(MutableLiveData<List<MainData>> list) {
        this.list = list;
    }
}
