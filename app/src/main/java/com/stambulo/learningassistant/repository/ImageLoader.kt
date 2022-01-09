package com.stambulo.learningassistant.repository

import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class ImageLoader{
    fun loadInto(source: String, container: ImageView){
        Glide.with(container.context)
            .asBitmap()
            .load(source)
            .into(container)
    }
}
