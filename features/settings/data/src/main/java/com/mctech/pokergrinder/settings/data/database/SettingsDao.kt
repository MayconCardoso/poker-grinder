package com.mctech.pokergrinder.settings.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface SettingsDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun save(settings: SettingsRoomEntity)

  @Query("SELECT * from settings WHERE settingKey = :key")
  public fun observe(key: String): Flow<SettingsRoomEntity?>

  @Query("SELECT * from settings")
  public fun observeAll(): Flow<List<SettingsRoomEntity>>
}
