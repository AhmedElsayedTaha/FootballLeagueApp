package com.league.foorballleagueapp.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.league.foorballleagueapp.Model.Team;

/**
 * Get a Single Instance of Database
 */
@Database(entities = {Team.class},version = 1,exportSchema = false)
public abstract class TeamDatabase extends RoomDatabase {
    public abstract TeamDAO teamDAO();
    private static TeamDatabase teamDatabaseInstance;

    public static TeamDatabase getTeamDatabaseInstance(Context context){
        if(teamDatabaseInstance==null){
            teamDatabaseInstance = Room.databaseBuilder(context,TeamDatabase.class,"TeamDB").build();
        }
        return teamDatabaseInstance;
    }


}
