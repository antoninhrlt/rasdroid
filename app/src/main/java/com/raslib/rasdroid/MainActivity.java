package com.raslib.rasdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String repoUrl = "https://github.com/antoninhrlt/rasdroid/";
    public static String githubUrl = repoUrl + "blob/main/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonListener();
    }

    private void buttonListener() {
        onConnectionBtn();
        onCreditsBtn();
        onHelpText();
        onGithubText();
    }

    private void openLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData( Uri.parse(link) );
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    /**
     * Open ConnectionActivity when connection's button is clicked
     */
    private void onConnectionBtn() {
        Button connectionBtn = findViewById(R.id.connection_btn);
        connectionBtn.setOnClickListener(v -> {
            startActivity( new Intent( getApplicationContext(), ConnectionActivity.class ) );
        });
    }

    /**
     * Open new Internet tab when credits button is clicked to CREDITS.txt in github's repo of
     * rasdroid. Call to openLink() method to open link
     */
    private void onCreditsBtn() {
        Button creditsBtn = findViewById(R.id.credits_btn);
        creditsBtn.setOnClickListener(v -> {
            openLink(githubUrl + "CREDITS.txt");
        });
    }

    /**
     * Open new Internet tab when credits button is clicked to CONNECTION.txt in github's repo of
     * rasdroid. Call to openLink() method to open link
     */
    private void onHelpText() {
        TextView helpText = findViewById(R.id.help_txt);
        helpText.setClickable(true);
        helpText.setOnClickListener(v -> {
            openLink(githubUrl + "README.md");
        });
    }

    /**
     * Open new Internet tab when text button is clicked to github repository of rasdroid. Call to
     * openLink() method to open link
     */
    private void onGithubText() {
        TextView githubText = findViewById(R.id.github_txt);
        githubText.setClickable(true);
        githubText.setOnClickListener(v -> {
            openLink(repoUrl);
        });
    }
}