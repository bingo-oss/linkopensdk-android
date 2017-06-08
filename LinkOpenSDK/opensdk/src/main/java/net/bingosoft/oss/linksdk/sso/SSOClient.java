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

package net.bingosoft.oss.linksdk.sso;

import net.bingosoft.oss.linksdk.sso.credentials.CredentialsBase;
import net.bingosoft.oss.linksdk.sso.entity.Authentication;
import net.bingosoft.oss.linksdk.sso.entity.SingleSignOnException;
import net.bingosoft.oss.linksdk.sso.entity.Token;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SSOClient {

    private String ssoBaseEndpoint;
    private String clientId;
    private String clientSecret;
    private Token refreshToken;
    private Authentication authentication;
    private OauthTokenHandler oauthTokenHandler;
    private ExecutorService threadExecutor = Executors.newSingleThreadExecutor();

    public SSOClient(String serverUrl, String clientId, String clientSecret) {
        setSsoBaseEndpoint(serverUrl);
        setClientId(clientId);
        setClientSecret(clientSecret);
        setOauthTokenHandler(new OauthTokenHandler(this));
    }

    /**
     * 用户凭证登录：支持用户密码、授权码、刷新令牌方式
     * @param credentials 凭证,支持UserNamePasswordCredentials和AuthorizationCodeCredentials
     * @param listener    监听器,监听授权登录过程
     */
    public void doLogin(final CredentialsBase credentials, final SSOLoginListener listener) {
        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Authentication auth = oauthTokenHandler.login(credentials, getOauthProviderPath());
                    setAuthentication(auth);
                    listener.onSuccess(auth);
                } catch (SingleSignOnException e) {
                    listener.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }

    public void setRefreshToken(Token token) {
        this.refreshToken = token;
    }

    public Token getRefreshToken() {
        return this.refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    private void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    private void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private void setSsoBaseEndpoint(String ssoBaseEndpoint) {
        this.ssoBaseEndpoint = ssoBaseEndpoint;
    }

    public Authentication getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getOauthProviderPath() {
        return ssoBaseEndpoint + "/oauth2/token";
    }

    private void setOauthTokenHandler(OauthTokenHandler oauthTokenHandler) {
        this.oauthTokenHandler = oauthTokenHandler;
    }


}
