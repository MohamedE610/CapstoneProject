
package com.example.e610.capstoneproject.Models.Manga;

import java.io.Serializable;

public class Relationships implements Serializable {

    public Genres genres;
    public Categories categories;
    public Castings castings;
    public Installments installments;
    public Mappings mappings;
    public Reviews reviews;
    public MediaRelationships mediaRelationships;
    public Chapters chapters;
    public MangaCharacters mangaCharacters;
    public MangaStaff mangaStaff;
    private final static long serialVersionUID = -4250632840008796926L;

}
