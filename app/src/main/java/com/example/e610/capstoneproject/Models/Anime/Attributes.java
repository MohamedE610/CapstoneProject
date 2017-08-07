
package com.example.e610.capstoneproject.Models.Anime;

import java.io.Serializable;
import java.util.List;

public class Attributes implements Serializable {

    public String createdAt;
    public String updatedAt;
    public String slug;
    public String synopsis;
    public long coverImageTopOffset;
    public Titles titles;
    public String canonicalTitle;
    public List<String> abbreviatedTitles = null;
    public String averageRating;
    public RatingFrequencies ratingFrequencies;
    public long userCount;
    public long favoritesCount;
    public String startDate;
    public String endDate;
    public long popularityRank;
    public long ratingRank;
    public String ageRating;
    public String ageRatingGuide;
    public String subtype;
    public String status;
    public Object tba;
    public PosterImage posterImage;
    public CoverImage coverImage;
    public long episodeCount;
    public long episodeLength;
    public String youtubeVideoId;
    public String showType;
    public boolean nsfw;
    private final static long serialVersionUID = -4405113118845406521L;

}
