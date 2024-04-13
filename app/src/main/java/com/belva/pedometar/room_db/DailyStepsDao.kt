package com.belva.pedometar.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// Data Access Object (DAO) sučelje
// Ovo DAO sučelje koristi se za interakciju sa SQLite bazom podataka koristeći Room, Androidovu library itd

@Dao
interface DailyStepsDao {

    //Upituje sve retke iz tablice DailyStepsEntity gdje je vrijednost stupca epochDay veća od 0 i vraća ih kao flow of list objekata DailyStepsEntity.

    @Query("SELECT * FROM DailyStepsEntity WHERE epochDay > 0")
    fun getAllStepsFlow(): Flow<List<DailyStepsEntity>>

    //Upituje vrijednost stupca koraka iz tablice DailyStepsEntity za navedeni dan i vraća je kao flow Long vrijednosti
    @Query("SELECT steps FROM DailyStepsEntity WHERE epochDay = :day")
    fun getStepsFlow(day: Long): Flow<Long?>

    //Upituje vrijednost stupca koraka iz tablice DailyStepsEntity za navedeni dan i vraća je kaoLong vrijednost  i moguci NULL




    @Query("SELECT steps FROM DailyStepsEntity WHERE epochDay = :day")
    fun getSteps(day: Long): Long?



    //Ažurira stupce koraka i timeStamp u ta blici DailyStepsEntity za zadnji unos (redak s maksimalnom vrijednošću epochDay) dodavanjem navedene vrijednosti koraka i ažuriranjem timeStampa s navedenom vrijednošću.

    @Query("UPDATE DailyStepsEntity SET steps = steps + :steps, timeStamp = :timeStamp WHERE epochDay=(SELECT MAX(epochDay) FROM DailyStepsEntity)")
    fun addToLastEntry(steps: Long, timeStamp: String)

    //Umeće jedan ili više objekata DailyStepsEntity u tablicu DailyStepsEntity.
    // Parametar onConflict postavljen je na OnConflictStrategy.REPLACE, što pokazuje da će se, ako dođe do unosa s istim Kljucem postojeći unos zamijeniti novim.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg dailySteps: DailyStepsEntity)


}