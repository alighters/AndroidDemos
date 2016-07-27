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

package com.lighters.demos.anim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import com.lighters.demos.R;
import com.lighters.demos.app.base.BaseActivity;

public class FirstActivity extends BaseActivity {

    @BindView(R.id.view_first) View mViewFirst;
    @BindView(R.id.btn_goto_next_page) Button mBtnGotoNextPage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        mBtnGotoNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
