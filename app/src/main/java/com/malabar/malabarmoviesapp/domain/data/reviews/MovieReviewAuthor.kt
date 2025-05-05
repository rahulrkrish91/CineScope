package com.malabar.malabarmoviesapp.domain.data.reviews

import com.google.gson.annotations.SerializedName
import com.malabar.core.AppConstants.Companion.MOVIE_IMAGE_W_500

data class MovieReviewAuthor(
    @SerializedName("name") var name: String? = "",
    @SerializedName("username") var username: String? = "",
    @SerializedName("avatar_path") var avatar_path: String? = "",
    @SerializedName("rating") var rating: Int? = 0
){
    fun createAvathar() = "$MOVIE_IMAGE_W_500$avatar_path"
}
