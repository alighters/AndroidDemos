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

package com.lighters.robolectric;

import android.content.Context;
import android.widget.Toast;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

/**
 * Created by david on 16/9/6.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
@Implements(Toast.class)
public class CustomShadowToast {

    private static boolean mIsShown;

    public void __constructor__(Context context) {
    }

    @Implementation
    public void show() {
        mIsShown = true;
    }

    public static boolean isToastShowInvoked() {
        return mIsShown;
    }
}
