package com.aryomtech.plasmaz.glide

import android.content.ComponentCallbacks2
import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

//custom GlideModule class
@GlideModule
class CustomGlideModuleV4 : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
                RequestOptions().format(DecodeFormat.PREFER_RGB_565)
        )
    }

}