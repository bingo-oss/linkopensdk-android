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

package net.bingosoft.oss.linksdk.sso.entity;

/**
 * 单点登录异常处理类
 */

public class SingleSignOnException extends RuntimeException {

    public String error;
    public String description;
    public int errorCode;

    public SingleSignOnException() {
    }

    public SingleSignOnException(String message) {
        super(message);
        this.error=message;
    }

    public SingleSignOnException(String error, String description){
        super(error);
        this.error=error;
        this.description=description;
    }

    public SingleSignOnException(int errorCode, String error, String description){
        super(error);
        this.error=error;
        this.description=description;
        this.errorCode=errorCode;
    }


    public SingleSignOnException(Throwable cause) {
        super(cause);
    }

    public SingleSignOnException(String message, Throwable cause) {
        super(message, cause);
        this.error=message;
    }
}
