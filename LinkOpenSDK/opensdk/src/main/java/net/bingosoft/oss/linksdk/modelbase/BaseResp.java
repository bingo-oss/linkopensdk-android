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

package net.bingosoft.oss.linksdk.modelbase;

public class BaseResp {

    public int resultCode;
    public String error;
    public String errorDescription;

    public BaseResp() {

    }

    public interface StatusCode {
        int STATUS_OK = 200;
        int STATUS_CANCEL = 100;   // 用户取消授权
        int STATUS_LOAD_CLIENT_FAIL = 101; // 加载客户端失败
        int STATUS_CLIENT_UNABLED = 102; //客户端不可用
        int STATUS_AUTH_REQUEST_FAIL = 103;  //获取授权码失败
        int STATUS_ILLEGAL_ARGUMENT = 104; //不合法的参数
    }
}
