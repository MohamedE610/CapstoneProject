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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AnimeProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static final int FavouriteAnimeConst = 300;
    static final int FavouriteMangaConst = 301;

    static final int CompletedAnimeConst = 302;
    static final int CompletedMangaConst = 303;

    static final int StartWatchAnimeConst = 304;
    static final int StartWatchMangaConst = 305;

    private AnimeDBHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AnimeContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, AnimeContract.PATH_FavouriteAnime, FavouriteAnimeConst);
        matcher.addURI(authority, AnimeContract.PATH_FavouriteManga, FavouriteMangaConst);

        matcher.addURI(authority, AnimeContract.PATH_CompletedAnime, CompletedAnimeConst);
        matcher.addURI(authority, AnimeContract.PATH_CompletedManga, CompletedMangaConst);

        matcher.addURI(authority, AnimeContract.PATH_StartWatchAnime, StartWatchAnimeConst);
        matcher.addURI(authority, AnimeContract.PATH_StartWatchManga, StartWatchMangaConst);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new AnimeDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FavouriteAnimeConst: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        AnimeContract.FavouriteAnimeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case FavouriteMangaConst: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        AnimeContract.FavouriteMangaEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case CompletedAnimeConst: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        AnimeContract.CompletedAnimeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case CompletedMangaConst: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        AnimeContract.CompletedMangaEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case StartWatchAnimeConst: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        AnimeContract.StartWatchAnimeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case StartWatchMangaConst: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        AnimeContract.StartWatchMangaEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case FavouriteAnimeConst:
                return AnimeContract.FavouriteAnimeEntry.CONTENT_TYPE;
            case FavouriteMangaConst:
                return AnimeContract.FavouriteMangaEntry.CONTENT_TYPE;
            case CompletedAnimeConst:
                return AnimeContract.CompletedAnimeEntry.CONTENT_TYPE;
            case CompletedMangaConst:
                return AnimeContract.CompletedMangaEntry.CONTENT_TYPE;
            case StartWatchAnimeConst:
                return AnimeContract.StartWatchAnimeEntry.CONTENT_TYPE;
            case StartWatchMangaConst:
                return AnimeContract.StartWatchMangaEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case FavouriteAnimeConst: {
                long id = db.insert(AnimeContract.FavouriteAnimeEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = AnimeContract.FavouriteAnimeEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case FavouriteMangaConst: {
                long id = db.insert(AnimeContract.FavouriteMangaEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = AnimeContract.FavouriteAnimeEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case CompletedAnimeConst: {
                long id = db.insert(AnimeContract.CompletedAnimeEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = AnimeContract.CompletedAnimeEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case CompletedMangaConst: {
                long id = db.insert(AnimeContract.CompletedMangaEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = AnimeContract.CompletedMangaEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case StartWatchAnimeConst: {
                long id = db.insert(AnimeContract.StartWatchAnimeEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = AnimeContract.StartWatchAnimeEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case StartWatchMangaConst: {
                long id = db.insert(AnimeContract.StartWatchMangaEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = AnimeContract.StartWatchMangaEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) {
            selection = "1";
        }
        switch (match) {
            case FavouriteAnimeConst:
                rowsDeleted = db.delete(
                        AnimeContract.FavouriteAnimeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FavouriteMangaConst:
                rowsDeleted = db.delete(
                        AnimeContract.FavouriteMangaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CompletedAnimeConst:
                rowsDeleted = db.delete(
                        AnimeContract.CompletedAnimeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CompletedMangaConst:
                rowsDeleted = db.delete(
                        AnimeContract.CompletedMangaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case StartWatchAnimeConst:
                rowsDeleted = db.delete(
                        AnimeContract.StartWatchAnimeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case StartWatchMangaConst:
                rowsDeleted = db.delete(
                        AnimeContract.StartWatchMangaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case FavouriteAnimeConst:
                rowsUpdated = db.update(AnimeContract.FavouriteAnimeEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case FavouriteMangaConst:
                rowsUpdated = db.update(AnimeContract.FavouriteMangaEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CompletedAnimeConst:
                rowsUpdated = db.update(AnimeContract.CompletedAnimeEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case CompletedMangaConst:
                rowsUpdated = db.update(AnimeContract.CompletedMangaEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case StartWatchAnimeConst:
                rowsUpdated = db.update(AnimeContract.StartWatchAnimeEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case StartWatchMangaConst:
                rowsUpdated = db.update(AnimeContract.StartWatchMangaEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
