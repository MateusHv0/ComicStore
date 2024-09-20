    package com.example.comicstore.data

    import androidx.room.Entity
    import androidx.room.PrimaryKey
    import com.squareup.moshi.JsonClass

    @Entity
    @JsonClass(generateAdapter = true)
    class Comic () {
        @PrimaryKey
        var id: Int? = null
        var overview: String? = null
        var name: String? = null
        var poster: String? = null
        fun getContent(): String {
            val overview = overview
            return when {
                overview?.isNotEmpty() == true -> overview
                else -> "Conteudo nao disponivel"
            }
        }
        fun getComicName(): String {
            val name = name
            return when {
                name?.isNotEmpty() == true -> name
                else -> "titulo nao disponivel"
            }
        }
        fun getImage() : String? = poster
    }
