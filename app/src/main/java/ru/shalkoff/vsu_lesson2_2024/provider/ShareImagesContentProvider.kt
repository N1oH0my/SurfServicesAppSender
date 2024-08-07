package ru.shalkoff.vsu_lesson2_2024.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log


object ImageProvider {
    private var lastImageUri: Uri? = null

    fun updateImage(uri: Uri?) {
        lastImageUri = uri
    }

    fun getImage(): Uri? {
        return lastImageUri
    }
}

class ShareImagesContentProvider: ContentProvider() {

    companion object {
        private const val AUTHORITY = "dev.surf.android.images.provider"
        private const val PATH_STRING = "image"
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
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val match = uriMatcher.match(uri)
        return when (match) {
            TEXT_ID -> {
                val columns = arrayOf("image")
                val cursor = MatrixCursor(columns)
                val lastImageUri = ImageProvider.getImage()
                lastImageUri?.let {
                    cursor.addRow(arrayOf(it.toString()))
                    cursor
                }
            }
            else -> {
                Log.d("ShareImagesContentProvider","Unknown URI: $uri")
                null
            }
        }
    }

    override fun getType(uri: Uri): String? {
        return "text/plain"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    fun updateImage(uri: Uri?) {
        if (uri != null) {
            ImageProvider.updateImage(uri)
            Log.d("ShareImagesContentProvider", "Добавлено изображение: $uri")
        } else {
            Log.e("ShareImagesContentProvider", "Ошибка: URI изображения равен null")
        }
    }

}