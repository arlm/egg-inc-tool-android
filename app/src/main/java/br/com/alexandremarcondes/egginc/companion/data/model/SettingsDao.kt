package br.com.alexandremarcondes.egginc.companion.data.model

import androidx.room.*

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings")
    fun getAll(): List<Settings>

    @Query("SELECT * FROM settings WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Settings>

    @Query("SELECT * FROM settings WHERE egg_inc_id LIKE :eggIncId LIMIT 1")
    fun findByEggIncId(eggIncId: String): Settings

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg settings: Settings)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(settings: Settings)

    @Delete
    fun delete(settings: Settings)
}