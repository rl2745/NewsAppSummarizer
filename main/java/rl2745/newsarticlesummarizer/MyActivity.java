package rl2745.newsarticlesummarizer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "rl2745.newsarticlesummarizer.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }


    /**
     * Called when the user clicks the Send button
     */
    public void sendSummary(View view) throws Exception {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String urlStr = editText != null ? editText.getText().toString() : null;
        callAPI aylien = new callAPI();
        aylien.execute(urlStr);
        String response = aylien.get();
        JSONObject myObj = new JSONObject(response);
        String summary = myObj.getString("sentences").replaceAll("\",\"", " ")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");
        summary = summary.substring(1, summary.length()-1);
        intent.putExtra(EXTRA_MESSAGE, summary);
        startActivity(intent);
    }


    /**
     * Queries the text API for the text summary
     *
     */
    private class callAPI extends AsyncTask<String, String, String> {

        private String response;

        @Override
        protected String doInBackground(String[] params) {
            try {
                response = sendGet(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    private String sendGet(String urlStr) throws Exception {

        String url = "https://api.aylien.com/api/v1/summarize?url="
                + urlStr;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request headers
        con.setRequestProperty("Accept", "*/*");
        con.setRequestProperty("X-AYLIEN-TextAPI-Application-Key",
                "KEY");
        con.setRequestProperty("X-AYLIEN-TextAPI-Application-ID", "ID");
        con.setRequestProperty("accept-encoding", "gzip");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //return result
        return response.toString();

    }
}


