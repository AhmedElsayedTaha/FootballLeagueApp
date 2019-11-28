package com.league.foorballleagueapp.Network;

import com.league.foorballleagueapp.Model.LeagueTeams;
import com.league.foorballleagueapp.Model.TeamDetailsModel.TeamDetails;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    /**
     * Request to get Teams
     * @param id league ID
     * @return List Of Teams
     */
    @GET("competitions/{competition_id}/teams")
    Single<LeagueTeams> getTeams(@Path("competition_id") int id);

    /**
     * Get list of team details contain Squad List
     * @param teamID team ID
     * @return List of team details
     */
    @GET("teams/{teamID}")
    Single<TeamDetails> getSquad(@Path("teamID")int teamID);

}
