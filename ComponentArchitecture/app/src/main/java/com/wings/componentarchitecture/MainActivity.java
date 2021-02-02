package com.wings.componentarchitecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wings.annotation.Route;
import com.wings.common.LiveDataBus;
import com.wings.router.Router;

/**
 * @author AI
 */
@Route(value = "/main/main")
public class MainActivity extends AppCompatActivity {

    LiveDataBus.BusMutableLiveData<String> liveData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         liveData = LiveDataBus.getInstance().with("11", String.class);
        // 能够主动调用回调onChanged的只有setValue和postValue,以及onStateChanged
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("ruanyandong", "onChanged: "+s);
            }
        });
    }

    public void jumpActivity(View view) {
        // postValue在任意线程可以调用
        // setValue在任意线程可以调用
        liveData.postValue("123");
        Router.getInstance().jumpActivity("/login/login",null);
    }
}