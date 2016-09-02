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

package com.lighters.demos.robolectric;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import com.lighters.demos.BuildConfig;
import com.lighters.demos.MainActivity;
import com.lighters.demos.R;
import com.lighters.demos.anim.FirstActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by david on 16/8/26.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
@RunWith(RobolectricTestRunner.class)
//@Config(manifest = "/src/main/AndroidManifest.xml", sdk = Build.VERSION_CODES.JELLY_BEAN
//,resourceDir = "/src/main/res")
//@Config(constants = BuildConfig.class)ee
public class MainActivityTest {

    @Test
    public void testClickButton() {
        Activity activity = Robolectric.setupActivity(MainActivity.class);
        Button button = (Button) activity.findViewById(R.id.btn_screen_anim);
        button.performClick();

        Intent expectedIntent = new Intent(activity, FirstActivity.class);
        assertThat(Shadows.shadowOf(activity).getNextStartedActivity(), equalTo(expectedIntent));
    }
}
