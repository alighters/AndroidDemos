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

package com.lighters.demos.service.test;

import android.content.Intent;
import android.os.Bundle;
import butterknife.OnClick;
import com.lighters.demos.R;
import com.lighters.demos.app.base.BaseActivity;

/**
 * Created by david on 16/9/14.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class TestIntentActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intent_service_test;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        startService(new Intent(this, TestIntentService.class));
    }

    @OnClick(R.id.btn_stop_service)
    public void stopTheService() {
        stopService(new Intent(this, TestIntentService.class));
    }
}
