package com.stambulo.learningassistant.repository

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.stambulo.learningassistant.view.MainActivity
import com.stambulo.learningassistant.view.fragments.home.ClassesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Inject
import javax.inject.Singleton

class ImageLoader{
    fun loadInto(source: String, container: ImageView){
        Glide.with(container.context)
            .asBitmap()
            .load(source)
            .into(container)
    }
}
