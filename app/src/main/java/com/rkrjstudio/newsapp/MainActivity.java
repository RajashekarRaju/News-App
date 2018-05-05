package com.rkrjstudio.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {


    // TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;


    // Adapter for the list of news articles
    private NewsAdapter mAdapter;


    // Tag for the log messages
    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    // Constant value for the news articles loader ID.
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = findViewById(R.id.list_item);

        // Create a new adapter that takes an empty list of news articles as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news articles.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current news article that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                assert currentNews != null;
                Uri newUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news article URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Find a reference to the to display text in the layout
        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        // initializing ConnectivityManager to class
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.e(LOG_TAG, "init problem");
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet);
        }

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        // Building URI Builder to fetch data from custom URL
        return new NewsLoader(this, uriBuilder());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newList) {

        // Clear the adapter of previous news article data
        mAdapter.clear();

        // If there is a valid list of news articles, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newList != null && !newList.isEmpty()) {
            mAdapter.addAll(newList);
        }

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No News Articles found."
        mEmptyStateTextView.setText(R.string.no_news);

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /**
     * URL builder to fetch dta from URL dataset
     */
    private static String uriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("newsapi.org")
                .appendPath("v2")
                .appendPath("everything")
                .appendQueryParameter("sources", "the-wall-street-journal")
                .appendQueryParameter("apiKey", "d9f1fd005526451b918a0328a7c89473");
        return builder.build().toString();
    }

}
