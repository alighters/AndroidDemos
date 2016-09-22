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

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by david on 16/9/14.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class TestIntentService extends IntentService {

    Thread mThread = null;

    public TestIntentService() {
        this("TestIntentService");
    }

    public TestIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mThread = Thread.currentThread();
        int i = 0;
        while (true) {
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("TestIntentService", i + "");
        }
    }

    @Override
    public boolean stopService(Intent name) {
        boolean result = super.stopService(name);
        if (mThread != null) {
            mThread.interrupt();
        }
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mThread != null) {
            mThread.interrupt();
        }
    }
}
