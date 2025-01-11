package com.adsprovider.combineadinventory.model;

public class News {

    public String newsTitle;
    public String authorName;
    public String newsImageURL;
    public String description;

    public News() {
    }

    public News(String newsTitle, String authorName, String newsImageURL, String description) {
        this.newsTitle = newsTitle;
        this.authorName = authorName;
        this.newsImageURL = newsImageURL;
        this.description = description;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
