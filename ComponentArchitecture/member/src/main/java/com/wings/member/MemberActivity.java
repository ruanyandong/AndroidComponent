package com.wings.member;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wings.annotation.Route;

/**
 * @author AI
 */
@Route(value = "/member/member")
public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
    }
}
