package com.dicoding.picodiploma.githubapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.dicoding.picodiploma.githubapp.R

internal class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val widgetItems = ArrayList<Bitmap>()

    companion object {

        private const val AUTHORITY = "com.dicoding.picodiploma.githubapp"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "favorite_user_table"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
    }

    override fun onCreate() {
        TODO("Not yet implemented")
    }

    override fun onDataSetChanged() {

        val indentityToken = Binder.clearCallingIdentity()

        val cursor = context.contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null,
            null
        )

        Binder.restoreCallingIdentity(indentityToken)
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int = widgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)

        rv.setImageViewBitmap(R.id.imageWidget, widgetItems[position])

        val extras = bundleOf(FavUserWidget.EXTRA_ITEM to position)

        val fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageWidget, fillIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(p0: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}