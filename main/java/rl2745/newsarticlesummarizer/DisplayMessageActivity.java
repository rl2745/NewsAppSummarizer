package rl2745.newsarticlesummarizer;

/**
 * Created by Richard Lopez on 3/22/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String summary = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(summary);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
        layout.addView(textView);
    }

}