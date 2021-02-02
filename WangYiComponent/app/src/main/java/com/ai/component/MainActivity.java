package com.ai.component;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.ai.componentlib.ServiceFactory;

/**
 * 组件化需要注意的地方：
 *      1、各个模块的gradle用到的公共变量定义到gradle.properties
 *      2、注意application和library的切换
 *      3、library不能有applicationId
 *      4、library和application的manifest文件是不同的
 *      5、app主模块依赖其他模块的时候，注意其他模块是否是library，application是不能依赖的
 *      6、各个模块的application初始化，需要在app主模块的application中利用反射进行
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceFactory.getInstance().getLoginService().launch(MainActivity.this);
            }
        });

        findViewById(R.id.mine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceFactory.getInstance().getMineService().launch(MainActivity.this,1);
            }
        });

        findViewById(R.id.showUI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceFactory.getInstance().getLoginService().getFragment(getSupportFragmentManager(),R.id.container,new Bundle());
            }
        });

    }
}
