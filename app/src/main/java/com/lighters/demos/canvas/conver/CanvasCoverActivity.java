package com.lighters.demos.canvas.conver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.lighters.demos.R;

public class CanvasCoverActivity extends AppCompatActivity {

    private boolean mHandled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_cover);
        final RoundPointView roundPointView = (RoundPointView) findViewById(R.id.rpv_normal);
        roundPointView.setHandled(true);
        roundPointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandled = !mHandled;
                roundPointView.setHandled(mHandled);
                roundPointView.invalidate();
            }
        });
    }
}
