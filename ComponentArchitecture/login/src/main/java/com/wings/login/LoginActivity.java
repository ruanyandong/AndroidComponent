package com.wings.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wings.annotation.Route;
import com.wings.router.Router;

/**
 * @author AI
 */
@Route(value = "/login/login")
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void jumpActivity(View view) {
        // 不能跳转的真正原因是什么？不能得到目的地的引用
        // 路由表 装的就是我们需要的引用
        // apt作用：生成文件
        // 编译时技术作用：用来生成代码文件
        //startActivity(new Intent(LoginActivity.this,MemberActivity.class));
        Router.getInstance().jumpActivity("/member/member",null);
    }
}
