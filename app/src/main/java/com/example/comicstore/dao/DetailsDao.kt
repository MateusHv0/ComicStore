    package com.example.comicstore.dao

    import androidx.room.Dao
    import androidx.room.Query
    import androidx.room.Transaction
    import com.example.comicstore.data.Details

    @Dao
    abstract class DetailsDao: BaseDao<Details> {
        @Transaction
        @Query("SELECT * FROM details")
        abstract suspend fun getAllDetails() : List<Details>?
        @Transaction
        @Query("SELECT * FROM details WHERE comicId=:comicId")
        abstract suspend fun getDetails(comicId: Int): Details?
        @Transaction
        open suspend fun insertDetails(details: Details){
            insert(details)
        }
        @Transaction
        @Query("DELETE from details")
        abstract suspend fun clearDetailsData()
    }