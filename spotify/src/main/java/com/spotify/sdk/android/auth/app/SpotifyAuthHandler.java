/*
 * Copyright (c) 2015-2016 Spotify AB
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.sdk.android.auth.app;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.spotify.sdk.android.auth.AuthorizationHandler;
import com.spotify.sdk.android.auth.AuthorizationRequest;

public class SpotifyAuthHandler implements AuthorizationHandler {

    private SpotifyNativeAuthUtil mSpotifyNativeAuthUtil;

    @Override
    public boolean start(Activity contextActivity, AuthorizationRequest request) {
        mSpotifyNativeAuthUtil = new SpotifyNativeAuthUtil(
                contextActivity,
                request,
                new Sha1HashUtil()
        );
        return mSpotifyNativeAuthUtil.startAuthActivity();
    }

    @Override
    public void stop() {
        if (mSpotifyNativeAuthUtil != null) {
            mSpotifyNativeAuthUtil.stopAuthActivity();
        }
    }

    @Override
    public void setOnCompleteListener(@Nullable OnCompleteListener listener) {
        // no-op
    }
}
