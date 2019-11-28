package com.league.foorballleagueapp.RoomDatabase;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.league.foorballleagueapp.Model.Team;
import java.util.List;
import io.reactivex.Single;

@Dao
public interface TeamDAO {

    /**
     * Insert Teams in Database
     * @param teamList List of teams
     */
    @Insert
    void insertTeamData(List<Team> teamList);

    /**
     * Select all Teams rows
     * @return list of teams
     */
    @Query("SELECT * FROM Team")
     Single<List<Team>> getTeams();

}
