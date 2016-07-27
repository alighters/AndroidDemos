package com.lighters.demos;

import android.content.Intent;
import android.os.Bundle;
import butterknife.OnClick;
import com.lighters.demos.anim.FirstActivity;
import com.lighters.demos.app.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_screen_anim)
    void gotoAnimScreen() {
        startActivity(new Intent(this, FirstActivity.class));
    }
}
