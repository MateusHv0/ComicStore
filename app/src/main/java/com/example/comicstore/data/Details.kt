package com.example.comicstore.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.comicstore.api.Credentials
import com.squareup.moshi.JsonClass
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Comic::class,
            parentColumns = ["id"],
            childColumns = ["comicId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@JsonClass(generateAdapter = true)
class Details(){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String? = null
    var banner: String? = null
    var description: String? = null
    @ColumnInfo(index = true)
    var comicId : Int?  = null
    fun getContentDetails(): String {
        val description = description
        return when {
            description?.isNotEmpty() == true -> description
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
}

