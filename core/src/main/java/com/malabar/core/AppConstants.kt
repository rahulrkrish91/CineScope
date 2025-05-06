package com.malabar.core

class AppConstants {
    companion object {
        const val MOVIE_BASE_URL = "https://api.themoviedb.org/3/"
        const val MOVIE_HEADER =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNzdjMmJiNmQ5YTdlNmVkZTY4MWU2ZmU3ZTU0ZjMxNCIsIm5iZiI6MTczMjI2NjQ3NS41Niwic3ViIjoiNjc0MDQ5ZWI0YTNkYzE3ODE3M2QzMmFiIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.mnT7TNxr9AmW4bTOb4dhA4K5r91-moAZdRs_x5c9Ep8"

        const val MOVIE_IMAGE_ORIGINAL = "https://image.tmdb.org/t/p/original/"
        const val MOVIE_IMAGE_W_500 = "https://image.tmdb.org/t/p/w500/"
        const val MOVIE_DEFAULT_LANG = "en-US"
        const val MOVIE_DEFAULT_REGION = "in"

        const val GOOGLE_AD = "ca-app-pub-1657553758550660~2089927936"
        const val GOOGLE_PUB_SUB = "ca-app-pub-1657553758550660/5520254091"

        fun getVideThumbnail(key: String) = "https://img.youtube.com/vi/$key/mqdefault.jpg"
    }
}