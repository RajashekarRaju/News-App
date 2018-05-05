package com.rkrjstudio.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String mUrls;

    NewsLoader(Context context, String url) {
        super(context);
        mUrls = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrls == null) {
            return null;
        }

        List<News> news = QueryUtils.fetchNewsData(mUrls);
        return news;
    }
}