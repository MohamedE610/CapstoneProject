/*
 * Copyright 2016.  Dmitry Malkovich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.e610.capstoneproject.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class AnimeContract {

    public static final String CONTENT_AUTHORITY = "3bd.el3al.AnimeSan";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FavouriteAnime = "FavouriteAnime";
    public static final String PATH_FavouriteManga = "FavouriteManga";

    public static final String PATH_CompletedAnime = "CompletedAnime";
    public static final String PATH_CompletedManga = "CompletedManga";

    public static final String PATH_StartWatchAnime = "StartWatchAnime";
    public static final String PATH_StartWatchManga = "StartWatchManga";

    public static final class FavouriteAnimeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FavouriteAnime).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FavouriteAnime;

        public static final String TABLE_NAME = "FavouriteAnime";
        public static final String COLUMN_anime_ID = "anime_id";
        public static final String COLUMN_anime_content = "anime_content";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] Series_COLUMNS = {
                COLUMN_anime_ID, COLUMN_anime_content

        };

        public static final int COL_anime_ID = 1;
        public static final int COL_anime_content = 2;
    }

    public static final class FavouriteMangaEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FavouriteManga).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FavouriteManga;

        public static final String TABLE_NAME = "FavouriteManga";
        public static final String COLUMN_manga_ID = "manga_id";
        public static final String COLUMN_manga_content = "manga_content";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] Series_COLUMNS = {
                COLUMN_manga_ID, COLUMN_manga_content

        };

        public static final int COL_manga_ID = 1;
        public static final int COL_manga_content = 2;
    }


    public static final class CompletedAnimeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CompletedAnime).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CompletedAnime;

        public static final String TABLE_NAME = "CompletedAnime";
        public static final String COLUMN_anime_ID = "anime_id";
        public static final String COLUMN_anime_content = "anime_content";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] Series_COLUMNS = {
                COLUMN_anime_ID, COLUMN_anime_content

        };

        public static final int COL_anime_ID = 1;
        public static final int COL_anime_content = 2;
    }

    public static final class CompletedMangaEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CompletedManga).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CompletedManga;

        public static final String TABLE_NAME = "CompletedManga";
        public static final String COLUMN_manga_ID = "manga_id";
        public static final String COLUMN_manga_content = "manga_content";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] Series_COLUMNS = {
                COLUMN_manga_ID, COLUMN_manga_content

        };

        public static final int COL_manga_ID = 1;
        public static final int COL_manga_content = 2;
    }

    public static final class StartWatchAnimeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_StartWatchAnime).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_StartWatchAnime;

        public static final String TABLE_NAME = "StartWatchAnime";
        public static final String COLUMN_anime_ID = "anime_id";
        public static final String COLUMN_anime_content = "anime_content";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] Series_COLUMNS = {
                COLUMN_anime_ID, COLUMN_anime_content

        };

        public static final int COL_anime_ID = 1;
        public static final int COL_anime_content = 2;
    }

    public static final class StartWatchMangaEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_StartWatchManga).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_StartWatchManga;

        public static final String TABLE_NAME = "StartWatchManga";
        public static final String COLUMN_manga_ID = "manga_id";
        public static final String COLUMN_manga_content = "manga_content";


        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] Series_COLUMNS = {
                COLUMN_manga_ID, COLUMN_manga_content

        };

        public static final int COL_manga_ID = 1;
        public static final int COL_manga_content = 2;
    }

}
