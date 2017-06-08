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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.bingosoft.oss.linksdk.modelbase.BaseReq;
import net.bingosoft.oss.linksdk.modelbase.BaseResp;
import net.bingosoft.oss.linksdk.openapi.AuthResp;
import net.bingosoft.oss.linksdk.openapi.ILinkAPIEventHandler;
import net.bingosoft.oss.linksdk.sso.SSOClient;
import net.bingosoft.oss.linksdk.sso.SSOLoginListener;
import net.bingosoft.oss.linksdk.sso.credentials.AuthorizationCodeCredentials;
import net.bingosoft.oss.linksdk.sso.entity.Authentication;
import net.bingosoft.oss.linksdk.sso.entity.SingleSignOnException;

public class LinkEntryActivity extends AppCompatActivity implements ILinkAPIEventHandler {

    TextView tvResult;
    StringBuilder str;
    BaseApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_entry);

        tvResult = (TextView) findViewById(R.id.tvResult);

        app = (BaseApplication) getApplication();
        app.ilinkapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        app.ilinkapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        int resultCode = resp.resultCode;
        switch (resultCode) {
            case BaseResp.StatusCode.STATUS_OK:
                //获取授权码
                String authCode = ((AuthResp) resp).authCode;

                //通过SSOClient 获取认证信息Authentication，包含了AccessToken
                String serverUrl = getString(R.string.ssoendpoint);
                String clientId = getString(R.string.clientId);
                String clientSecret = getString(R.string.clientSecret);

                SSOClient ssoClient = new SSOClient(serverUrl, clientId, clientSecret);
                AuthorizationCodeCredentials credentials = new AuthorizationCodeCredentials(authCode);
                ssoClient.doLogin(credentials, new SSOLoginListener() {
                    @Override
                    public void onSuccess(Authentication auth) {
                        str = new StringBuilder();
                        str.append("accessToken:" + auth.accessToken.getId() + " expires:" + auth.accessToken.getExpires() + "\n");
                        str.append("refreshToken:" + auth.refreshToken.getId() + " expires:" + auth.refreshToken.getExpires() + "\n");
                        str.append("tokenType:" + auth.tokenType + "\n");
                        str.append("uid:" + auth.uid + "\n");
                        str.append("eCode:" + auth.eCode + "\n");
                        str.append("status:" + auth.status + "\n");
                        str.append("enabled:" + auth.enabled + "\n");
                        showMsg(str.toString());
                    }

                    @Override
                    public void onError(SingleSignOnException ex) {
                        str = new StringBuilder();
                        str.append("获取Authentication失败\n");
                        str.append("errorCode:" + ex.errorCode + "\n");
                        str.append("error:" + ex.error + "\n");
                        str.append("description:" + ex.description);
                        showMsg(str.toString());
                    }
                });

                break;
            case BaseResp.StatusCode.STATUS_CANCEL:
                str = new StringBuilder();
                str.append("用户取消授权");
                showMsg(str.toString());
                break;

            case BaseResp.StatusCode.STATUS_AUTH_REQUEST_FAIL:
                str = new StringBuilder();
                str.append("获取授权码失败");
                showMsg(str.toString());
                break;
            case BaseResp.StatusCode.STATUS_ILLEGAL_ARGUMENT:
                str = new StringBuilder();
                str.append("不合法的参数");
                showMsg(str.toString());
                break;
            case BaseResp.StatusCode.STATUS_CLIENT_UNABLED:
                str = new StringBuilder();
                str.append("clientId不可用");
                showMsg(str.toString());
                break;
            case BaseResp.StatusCode.STATUS_LOAD_CLIENT_FAIL:
                str = new StringBuilder();
                str.append("加载客户端失败");
                showMsg(str.toString());
                break;
        }
    }

    private void showMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvResult.setText(msg);
            }
        });
    }
}
