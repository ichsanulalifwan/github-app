package com.dicoding.picodiploma.githubapp.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.githubapp.R
import com.dicoding.picodiploma.githubapp.helper.MappingHelper

internal class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val widgetItems = ArrayList<String?>()

    companion object {
        private const val AUTHORITY = "com.dicoding.picodiploma.githubapp"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "favorite_user_table"
    }

    override fun onCreate() {
    }

    override fun onDataSetChanged() {

        val uri: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        val indentityToken = Binder.clearCallingIdentity()

        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )

        val list = MappingHelper.mapCursorToArrayList(cursor)

        if (cursor != null && cursor.count > 0) {
            widgetItems.clear()
            for (user in list) {
                widgetItems.add(user.avatar)
            }
        }

        Binder.restoreCallingIdentity(indentityToken)
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = widgetItems.size

    override fun getViewAt(position: Int): RemoteViews {

        val rv = RemoteViews(context.packageName, R.layout.widget_item)

        val bitmap: Bitmap = Glide.with(context)
            .asBitmap()
            .load(widgetItems[position])
            .submit(420, 420)
            .get()

        rv.setImageViewBitmap(R.id.imageWidget, bitmap)

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