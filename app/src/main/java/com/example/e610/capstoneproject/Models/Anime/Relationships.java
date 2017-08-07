
package com.example.e610.capstoneproject.Models.Anime;

import java.io.Serializable;

public class Relationships implements Serializable {

    public Genres genres;
    public Categories categories;
    public Castings castings;
    public Installments installments;
    public Mappings mappings;
    public Reviews reviews;
    public MediaRelationships mediaRelationships;
    public Episodes episodes;
    public StreamingLinks streamingLinks;
    public AnimeProductions animeProductions;
    public AnimeCharacters animeCharacters;
    public AnimeStaff animeStaff;
    private final static long serialVersionUID = -7838424388742766528L;

}
