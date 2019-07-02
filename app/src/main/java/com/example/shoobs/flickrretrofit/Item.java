package com.example.shoobs.flickrretrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("date_taken")
    @Expose
    private String dateTaken;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("published")
    @Expose
    private String published;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("author_id")
    @Expose
    private String authorId;
    @SerializedName("tags")
    @Expose
    private String tags;

//    public Item() {
//    }
//
//    /**
//     *
//     * @param tags
//     * @param author
//     * @param dateTaken
//     * @param title
//     * @param description
//     * @param link
//     * @param published
//     * @param media
//     * @param authorId
//     */
//    public Item(String title, String link, Media media, String dateTaken, String description, String published, String author, String authorId, String tags) {
//        super();
//        this.title = title;
//        this.link = link;
//        this.media = media;
//        this.dateTaken = dateTaken;
//        this.description = description;
//        this.published = published;
//        this.author = author;
//        this.authorId = authorId;
//        this.tags = tags;
//    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public Media getMedia() {
        return media;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public String getDescription() {
        return description;
    }

    public String getPublished() {
        return published;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }


    public String getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", media=" + media +
                ", dateTaken='" + dateTaken + '\'' +
                ", description='" + description + '\'' +
                ", published='" + published + '\'' +
                ", author='" + author + '\'' +
                ", authorId='" + authorId + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
