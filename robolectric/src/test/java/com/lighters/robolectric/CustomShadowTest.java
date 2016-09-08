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
import android.widget.Button;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by david on 16/9/6.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = { CustomShadowToast.class })
public class CustomShadowTest {

    @Test
    public void testToast() {
        Activity activity = Robolectric.setupActivity(TestToastActivity.class);

        Button button = (Button) activity.findViewById(R.id.btn_test_main);
        button.performClick();

        assertThat(CustomShadowToast.isToastShowInvoked(), is(true));
        assertThat(shadowOf(RuntimeEnvironment.application).getShownToasts().size() == 0, is(true));
    }
}
