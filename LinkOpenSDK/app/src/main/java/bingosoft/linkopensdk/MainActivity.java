/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package bingosoft.linkopensdk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import net.bingosoft.oss.linksdk.openapi.AuthReq;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    BaseApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnLoginWithLink).setOnClickListener(this);

        app = (BaseApplication) getApplication();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btnLoginWithLink:
                //向Link发起授权登录请求
                AuthReq req = new AuthReq();
                req.setLinkAppId("com.bingo.sled");
                app.ilinkapi.sendReq(this, req);
                break;
        }
    }
}
