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

package net.bingosoft.oss.linksdk.sso.credentials;

import java.util.HashMap;
import java.util.Map;

/**
 * 凭证—授权码
 */

public class AuthorizationCodeCredentials extends CredentialsBase {

    protected String authorizationCode;

    public AuthorizationCodeCredentials(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    @Override
    public Map<String, String> credentialsToMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("grant_type","authorization_code");
        result.put("code", authorizationCode);
        return result;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

}
