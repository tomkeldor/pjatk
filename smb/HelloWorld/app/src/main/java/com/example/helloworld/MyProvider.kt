package com.example.helloworld

import android.content.*
import android.database.Cursor
import android.net.Uri

class MyProvider : ContentProvider() {
    private lateinit var productDao: ProductDao
    
    private val authority = "com.example.helloworld.provider"
    private val path = "product_table"
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(authority, path, 1)
        addURI(authority, "$path/#", 2)
    }

    override fun onCreate(): Boolean {
        productDao = ProductDB.getDatabase((this.context) as Context).productDao()
        return false
    }

    @Throws(IllegalArgumentException::class)
    override fun query(uri: Uri, projection: Array<out String?>?, selection: String?, selectionArgs: Array<out String?>?, sortOrder: String?): Cursor? {
        val cursor: Cursor
        when (uriMatcher.match(uri)) {
            1 -> {
                cursor = productDao.findAll()
                if (context != null) {
                    cursor.setNotificationUri(context!!
                            .contentResolver, uri)
                    return cursor
                }
                throw IllegalArgumentException("Unknown URI: $uri")
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    @Throws(IllegalArgumentException::class)
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            1 -> {
                if (context != null) {

                    val id: Long = productDao.insertP(Product.fromContentValues(values))
                    if (id != 0L) {
                        context!!.contentResolver
                                .notifyChange(uri, null)
                        return ContentUris.withAppendedId(uri, id)
                    }
                }
                throw IllegalArgumentException("Invalid URI: Insert failed$uri")
            }
            2 -> throw IllegalArgumentException("Invalid URI: Insert failed$uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @Throws(IllegalArgumentException::class)
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String?>?): Int {
        when (uriMatcher.match(uri)) {
            1 -> throw java.lang.IllegalArgumentException("Invalid uri: cannot delete")
            2 -> {
                if (context != null) {
                    val count: Int = productDao.deleteP(ContentUris.parseId(uri))
                    context!!.contentResolver
                            .notifyChange(uri, null)
                    return count
                }
                throw IllegalArgumentException("Unknown URI:$uri")
            }
            else -> throw IllegalArgumentException("Unknown URI:$uri")
        }
    }

    @Throws(IllegalArgumentException::class)
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String?>?): Int {
        when (uriMatcher.match(uri)) {
            1 -> {
                if (context != null) {
                    val count: Int = productDao.updateP(Product.fromContentValues(values))
                    if (count != 0) {
                        context!!.contentResolver
                                .notifyChange(uri, null)
                        return count
                    }
                }
                throw IllegalArgumentException("Invalid URI:  cannot update")
            }
            2 -> throw IllegalArgumentException("Invalid URI:  cannot update")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}