package com.example.githubqueryapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Activity {

    EditText mSearchBoxEditText;
    TextView mUrlDisplayTextView;
    TextView mSearchResultTextView;

    TextView errormessageTextView;
    private View mErrorMessageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultTextView = (TextView) findViewById(R.id.tv_github_search_result_json);

        errormessageTextView =(TextView) findViewById( R.id.error_message_display );
    }
    private void makeGithubSearchQuery(){
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText( githubSearchUrl.toString());
        String githubSearchResults = null;
       new GithubQueryTask().execute(githubSearchUrl);


    }

    private void showJsonDataView(){
        mErrorMessageDisplay.setVisibility( View.INVISIBLE);
        mSearchResultTextView.setVisibility( View.VISIBLE );
    }

    private void showErrorMessage(){
        mSearchResultTextView.setVisibility( View.INVISIBLE );
        mErrorMessageDisplay.setVisibility( View.VISIBLE);
    }

    public class GithubQueryTask extends AsyncTask<URL,Void,String>{
        protected String doInBackground(URL params){
            String githubSearchResults = null;
            try {
                URL searchUrl = null;
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e){
                e.printStackTrace();
            }
            return githubSearchResults;


        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            if ((githubSearchResults != null && !githubSearchResults.equals( "" )))
                mSearchResultTextView.setText( githubSearchResults );
            else {
                showErrorMessage();
            }
        }

        @Override
        protected String doInBackground(URL... urls) {
            return null;
        }

    }



    @Override
    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public boolean onOptionItemSelected(MenuItem item){
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search){
            makeGithubSearchQuery();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
