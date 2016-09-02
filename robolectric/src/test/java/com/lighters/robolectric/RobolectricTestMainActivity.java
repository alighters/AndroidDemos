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

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by david on 16/8/29.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class RobolectricTestMainActivity {

    @Test
    public void test() {
        Activity activity = Robolectric.setupActivity(TestMainActivity.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);

        Button button = (Button) activity.findViewById(R.id.btn_test_main);
        TextView textView = (TextView) activity.findViewById(R.id.tv_test_main);

        button.performClick();
        assertThat(textView.getText().toString(), equalTo("Hello"));

        Intent intent = new Intent(activity, TestFirstActivity.class);
        activity.startActivity(intent);
        assertThat(shadowActivity.getNextStartedActivity(), equalTo(intent));
    }
}
