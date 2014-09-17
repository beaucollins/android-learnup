package com.automattic.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String TAG = "Learnup";

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setSubtitle(R.string.subtitle);
        }

        View membersButton = findViewById(R.id.members_button);
        membersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMembersList();
            }
        });
    }

    private void showMembersList() {
        Intent intent = new Intent(this, MembersActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (BuildConfig.DEBUG)
            Log.d(TAG, "onResume");

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (BuildConfig.DEBUG)
            Log.d(TAG, "onPostResume");
    }

    @Override
    protected void onPause() {

        if (BuildConfig.DEBUG)
            Log.d(TAG, "onPause");

        super.onPause();

    }
}
