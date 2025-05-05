package com.malabar.malabarmoviesapp.domain.data.images

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_ORIGINAL

data class MovieImageItem(
    @SerializedName("aspect_ratio") val aspect_ratio: Double,
    @SerializedName("height") val height: Int,
    @SerializedName("file_path") val file_path: String,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Int,
    @SerializedName("width") val width: Int
){
    fun createImagePath(): String {
        return "$MOVIE_IMAGE_ORIGINAL$file_path"
    }
}