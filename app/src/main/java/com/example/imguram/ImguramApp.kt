package com.example.imguram

import android.app.Application
import android.os.Build.VERSION.SDK_INT
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

class ImguramApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader(object : ImageLoaderFactory{
            val imageLoaderBuilder = ImageLoader.Builder(this@ImguramApp)
                .components {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                }
            override fun newImageLoader() = imageLoaderBuilder.build()

        })
    }
}