package com.beginner.micromaster.flashcardsapp.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by praxis on 12/04/17.
 */

public class DataContract {
    public static final String CONTENT_AUTHORITY = "com.beginner.micromaster.flashcardsapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TODO = "todo";
    public static final String QUERY_EQUAL = "=?";

    public static final class TodoEntry implements BaseColumns {
        public static final String TABLE_CARDS = "cards";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_ANSWER = "answer";

        public static final String WHERE_TODO_ID = _ID + QUERY_EQUAL;
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODO).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TODO;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TODO;

        public static Uri buildTodoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
