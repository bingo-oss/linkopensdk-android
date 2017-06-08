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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import net.bingosoft.oss.linksdk.modelbase.BaseReq;
import net.bingosoft.oss.linksdk.modelbase.BaseResp;

public class LinkApiImplV3 implements ILinkAPI {

    private String clientId;
    private Context context;

    public LinkApiImplV3(Context context) {
        this.context=context;
    }

    @Override
    public void registerClient(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void handleIntent(Intent intent, ILinkAPIEventHandler handler) {
        Bundle bundle = intent.getExtras();
        int resultCode = bundle.getInt("resultCode");
        String code = bundle.getString("code");
        String error = bundle.getString("error");
        String error_description = bundle.getString("error_description");
        AuthResp resp = new AuthResp();
        resp.resultCode = resultCode;
        if (code != null) {
            resp.authCode = code;
        }
        resp.error = error;
        resp.errorDescription = error_description;
        handler.onResp(resp);
    }

    @Override
    public boolean isLinkInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void sendReq(Context context, BaseReq req) {
        Intent intent = new Intent();
        intent.setPackage(req.getLinkAppId());
        intent.setAction(req.getLinkAppId() + ".action.authorize_login");
        intent.putExtra("package_name", context.getPackageName());
        intent.putExtra("activity_action_name", "net.bingosoft.oss.linkauth");
        intent.putExtra("client_id", this.clientId);
        context.startActivity(intent);
    }

    @Override
    public void sendResp(Context context, BaseResp resp) {
        //// TODO: 2017/5/17
    }
}
