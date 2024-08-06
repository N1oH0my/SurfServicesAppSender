package ru.shalkoff.vsu_lesson2_2024.provider


import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.database.MatrixCursor

class ShareSecretKeyContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "dev.surf.android.provider"
        private const val PATH_STRING = "text"

        private const val TEXT_ID = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, PATH_STRING, TEXT_ID)
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        // Обработка запроса на получение данных
        if (uriMatcher.match(uri) == TEXT_ID) {
            // Если URI совпадает с ожидаемым, возвращаем строку
            val columns = arrayOf("text")
            val cursor = MatrixCursor(columns)
            cursor.addRow(arrayOf("Secret Key: 1234567890"))
            return cursor
        }
        return null
    }

        override fun getType(uri: Uri): String? {
        // Возвращаем MIME-тип данных
        return "text/plain"
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}

