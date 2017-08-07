
package com.example.e610.capstoneproject.Models.Manga;

import java.io.Serializable;

public class Attributes implements Serializable {

    public String createdAt;
    public String updatedAt;
    public String slug;
    public String synopsis;
    public Integer coverImageTopOffset;
    public Titles titles;
    public String canonicalTitle;
    public Object abbreviatedTitles;
    public String averageRating;
    public RatingFrequencies ratingFrequencies;
    public Integer userCount;
    public Integer favoritesCount;
    public String startDate;
    public String endDate;
    public Integer popularityRank;
    public Integer ratingRank;
    public Object ageRating;
    public Object ageRatingGuide;
    public String subtype;
    public String status;
    public Object tba;
    public PosterImage posterImage;
    public CoverImage coverImage;
    public Integer chapterCount;
    public Integer volumeCount;
    public String serialization;
    public String mangaType;
    private final static long serialVersionUID = -7786353009234813485L;

}
