/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lighters.demos.token;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import butterknife.OnClick;
import com.lighters.demos.R;
import com.lighters.demos.app.base.BaseActivity;
import com.lighters.demos.token.http.GlobalToken;
import com.lighters.demos.token.http.RetrofitUtil;
import com.lighters.demos.token.http.api.IApiService;
import com.lighters.demos.token.http.api.ResultModel;
import com.lighters.demos.token.http.api.TokenModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by david on 16/8/21.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class TokenTestActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_token_test;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.btn_token_get)
    public void onGetTokenClick(View v) {
        RetrofitUtil.getInstance()
            .get(IApiService.class)
            .getToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<TokenModel>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(TokenModel model) {
                    if (model != null && !TextUtils.isEmpty(model.token)) {
                        GlobalToken.updateToken(model.token);
                    }
                }
            });
    }

    @OnClick(R.id.btn_request)
    public void onRequestClick(View v) {
        for (int i = 0; i < 5; i++) {
            RetrofitUtil.getInstance()
                .getProxy(IApiService.class)
                .getResult(GlobalToken.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultModel model) {

                    }
                });
        }
    }
}
