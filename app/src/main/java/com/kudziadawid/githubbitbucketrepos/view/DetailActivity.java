package com.kudziadawid.githubbitbucketrepos.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kudziadawid.githubbitbucketrepos.R;

public class DetailActivity extends AppCompatActivity {

    private TextView repoNameTV;
    private TextView ownerNameTV;
    private TextView repoDescriptionTV;
    private ImageView avatarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        repoNameTV = findViewById(R.id.repoNameTV);
        ownerNameTV = findViewById(R.id.ownerNameTV);
        repoDescriptionTV = findViewById(R.id.repoDescriptionTV);
        avatarImage = findViewById(R.id.avatarImage);

        Intent intent = getIntent();
        repoNameTV.setText(intent.getStringExtra("repoName"));
        ownerNameTV.setText(intent.getStringExtra("ownerName"));
        repoDescriptionTV.setText(intent.getStringExtra("repoDescription"));
        Glide.with(this).load(intent.getStringExtra("avatarUrl")).into(avatarImage);

        if (intent.getBooleanExtra("isBitbucket", false)) {
            repoNameTV.setTextColor(Color.RED);
            ownerNameTV.setTextColor(Color.RED);
            repoDescriptionTV.setTextColor(Color.RED);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
