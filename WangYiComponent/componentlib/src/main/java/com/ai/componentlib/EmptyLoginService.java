package com.ai.componentlib;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class EmptyLoginService implements ILoginService{
    @Override
    public void launch(Context context) {

    }

    @Override
    public Fragment getFragment(FragmentManager manager, int container, Bundle bundle) {
        return null;
    }
}
