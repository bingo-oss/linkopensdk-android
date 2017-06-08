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
import net.bingosoft.oss.linksdk.sso.utils.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class OauthTokenHandler {

    private SSOClient ssoClient;

    public OauthTokenHandler(SSOClient ssoClient) {
        this.ssoClient = ssoClient;
    }

    public Authentication login(CredentialsBase credentials, String reqUrl) throws SingleSignOnException {
        Authentication authentication = null;
        Map<String, String> params = credentials.credentialsToMap();
        params.put("client_id", ssoClient.getClientId());
        params.put("client_secret", ssoClient.getClientSecret());
        HttpRequest request = HttpRequest
                .post(reqUrl, params, true)
                .trustAllCerts().trustAllHosts()
                .header("X-Requested-With", "XMLHttpRequest");
        int code = request.code();
        String body = request.body();

        try {
            JSONObject responseData = new JSONObject(body);
            if (code == 200) {
                authentication = parseAuthentication(responseData);
            } else {
                throw new SingleSignOnException(responseData.getString("error"), responseData.getString("error_description"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return authentication;
    }

    private Authentication parseAuthentication(JSONObject obj) {
        Authentication authentication = new Authentication();
        try {
            Token accessToken = new Token(obj.getString("access_token"), obj.getLong("expires_in"));
            authentication.accessToken = accessToken;
            authentication.tokenType = obj.getString("token_type");
            Token refreshToken = new Token(obj.getString("refresh_token"), 3600); //协议中未返回过期时间
            authentication.refreshToken = refreshToken;
            authentication.uid = obj.getString("uid");
            authentication.enabled = obj.getInt("enabled");
            authentication.status = obj.getInt("status");
            authentication.eCode = obj.getString("eCode");
            ssoClient.setRefreshToken(refreshToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return authentication;
    }
}
