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

package net.bingosoft.oss.linksdk.openapi;

import android.content.Context;
import android.content.Intent;

import net.bingosoft.oss.linksdk.modelbase.BaseReq;
import net.bingosoft.oss.linksdk.modelbase.BaseResp;

public interface ILinkAPI {

    void registerClient(String clientId);

    void handleIntent(Intent intent,ILinkAPIEventHandler handler);

    boolean isLinkInstalled(Context context,String packageName);

    void sendReq(Context context,BaseReq req);

    void sendResp(Context context,BaseResp resp);

}
