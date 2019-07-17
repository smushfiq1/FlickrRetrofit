package com.example.shoobs.flickrretrofit.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feed {

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("media")
	@Expose
	private Media media;

	@SerializedName("date_taken")
	@Expose
	private String dateTaken;

	@SerializedName("author")
	@Expose
	private String author;

	@SerializedName("author_id")
	@Expose
	private String author_id;

	@SerializedName("tags")
	@Expose
	private String tags;



	public String getTitle () {
		return title;
	}



	public Media getMedia () {
		return media;
	}



	public String getDateTaken () {
		return dateTaken;
	}



	public String getAuthor () {
		return author;
	}



	public String getAuthorId () {
		return author_id;
	}



	public String getTags () {
		return tags;
	}



	@Override
	public String toString () {
		return "Feed{" +
				"title='" + title + '\'' +
				", media=" + media +
				", dateTaken='" + dateTaken + '\'' +
				", author='" + author + '\'' +
				", author_id='" + author_id + '\'' +
				", tags='" + tags + '\'' +
				'}';
	}

}
