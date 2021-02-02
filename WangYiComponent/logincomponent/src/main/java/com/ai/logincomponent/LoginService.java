package com.ai.logincomponent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.ai.componentlib.ILoginService;

public class LoginService implements ILoginService {
    @Override
    public void launch(Context context) {
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public Fragment getFragment(FragmentManager manager, int container, Bundle bundle) {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(bundle);
        manager.beginTransaction().add(container,fragment).commit();
        return fragment;
    }
}
