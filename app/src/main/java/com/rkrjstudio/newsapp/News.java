package com.rkrjstudio.newsapp;


class News {

    private String mTitle;

    private String mAuthor;

    private String mTime;

    private String mDescription;

    private String mImage;

    private String mUrl;

    News(String vTitle, String vAuthor, String vTime, String vDescription, String vImage, String vUrl) {
        mTitle = vTitle;
        mAuthor = vAuthor;
        mTime = vTime;
        mDescription = vDescription;
        mImage = vImage;
        mUrl = vUrl;
    }

    String getTitle() {
        return mTitle;
    }

    String getAuthor() {
        return mAuthor;
    }

    String getTime() {
        return mTime;
    }

    String getDescription() {
        return mDescription;
    }

    String getImage() {
        return mImage;
    }

    String getUrl() {
        return mUrl;
    }

}
