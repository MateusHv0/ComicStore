    package com.example.comicstore.dao

    import androidx.room.Dao
    import androidx.room.Query
    import androidx.room.Transaction
    import com.example.comicstore.data.Comic
    import com.example.comicstore.data.Image

    @Dao
    abstract class ImagesDao: BaseDao<Image> {
        @Transaction
        @Query("SELECT * FROM image")
        abstract suspend fun getAllImages(): List<Image>?

        @Transaction
        @Query("SELECT * FROM image WHERE id=:comicId")
        abstract suspend fun getImage(comicId: Int): Image?
        @Transaction
        open suspend fun insertImage(image: Image){
            insert(image)
        }
        @Transaction
        @Query("DELETE from image")
        abstract suspend fun clearImagesData()

        @Transaction
        open suspend fun insertImageList(imageList: List<Image>){
            imageList.forEach{insertImage(it)}
        }
    }