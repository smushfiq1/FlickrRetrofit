package com.example.shoobs.flickrretrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class FlickerData {

@SerializedName("title")
@Expose
private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("generator")
    @Expose
    private String generator;
    @SerializedName("items")
    @Expose
    //private List<Item> items = null;
    private ArrayList<Item> items;

//    public FlickerData() {
//    }
//
//    /**
//     *
//     * @param title
//     * @param items
//     * @param description
//     * @param link
//     * @param generator
//     * @param modified
//     */
//    public FlickerData(String title, String link, String description, String modified, String generator, List<Item> items) {
//        super();
//        this.title = title;
//        this.link = link;
//        this.description = description;
//        this.modified = modified;
//        this.generator = generator;
//        this.items = items;
//    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public String getGenerator() {
        return generator;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "FlickerData{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", modified='" + modified + '\'' +
                ", generator='" + generator + '\'' +
                ", items=" + items +
                '}';
    }
}

