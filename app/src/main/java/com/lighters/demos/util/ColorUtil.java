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

package com.lighters.demos.util;

/**
 * Created by david on 16/7/27.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class ColorUtil {

    /**
     * Get the percent color between the startColor and endColor
     *
     * @param startColor The start color.
     * @param endColor   The end color.
     * @param percent    The percent transform value which is between 0 and 1.
     * @return the result color value.
     */
    public static int getInterColor(int startColor, int endColor, float percent) {
        int maskColor = (1 << 8) - 1;
        int index = 0;
        int interColor = 0;
        int startValue;
        int endValue;
        while (index < 4) {
            startValue = (startColor >> (index * 8)) & maskColor;
            endValue = (endColor >> (index * 8)) & maskColor;
            interColor = interColor | ((int) (startValue + (endValue - startValue) * percent) << (index * 8));
            index++;
        }
        return interColor;
    }
}
