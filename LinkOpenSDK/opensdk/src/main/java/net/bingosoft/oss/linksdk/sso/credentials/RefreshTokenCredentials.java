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


import net.bingosoft.oss.linksdk.sso.entity.Token;

import java.util.HashMap;
import java.util.Map;

public class RefreshTokenCredentials extends CredentialsBase {

    private Token refreshToken;

    public RefreshTokenCredentials(Token refreshToken){
        this.refreshToken=refreshToken;
    }

    @Override
    public Map<String, String> credentialsToMap() {
        Map<String, String> result = new HashMap<String, String>();
        result.put("grant_type","refresh_token");
        result.put("refresh_token",refreshToken.getId());
        return result;
    }
}
