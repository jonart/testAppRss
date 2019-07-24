package ru.kamishnikov.testapprss.data.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull
import java.io.Serializable

@Entity(tableName = "news")
class NewsEntity(
    @param:NonNull
    @field:ColumnInfo(name = "title")
    @get:NonNull
    var title: String?
) : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}

