package com.example.comicstore.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.comicstore.api.Credentials
import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

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
class Image(){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String? = null
    var banner: String? = null
    var description: String? = null
    @ColumnInfo(index = true)
    var comicId : Int?  = null

    private fun getFullPath(): String {
        return banner ?: ""
    }

    fun getCarouselImage(): CarouselItem {
        return CarouselItem(imageUrl = getFullPath())
    }
}

