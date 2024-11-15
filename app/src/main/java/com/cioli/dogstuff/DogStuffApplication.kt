package com.cioli.dogstuff

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.util.DebugLogger

class DogStuffApplication: Application(), SingletonImageLoader.Factory {
	override fun newImageLoader(context: PlatformContext): ImageLoader {
		return ImageLoader.Builder(context)
			.memoryCachePolicy(CachePolicy.ENABLED)
			.memoryCache {
				MemoryCache.Builder()
					.maxSizePercent(context,0.25)
					.strongReferencesEnabled(true)
					.build()
			}
			.diskCachePolicy(CachePolicy.ENABLED)
			.diskCache {
				DiskCache.Builder()
					.directory(this.cacheDir.resolve("dog_image_cache"))
					.maxSizePercent(0.02)
					.build()
			}
			.logger(DebugLogger())
			.build()
	}
}