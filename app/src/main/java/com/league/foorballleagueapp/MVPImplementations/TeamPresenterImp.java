package com.league.foorballleagueapp.MVPImplementations;

import android.widget.TextView;

import com.league.foorballleagueapp.Interfaces.MVPInterface;
import com.league.foorballleagueapp.Model.Team;
import com.league.foorballleagueapp.Model.TeamDetailsModel.TeamDetails;

import java.util.List;

/**
 * The type Team presenter imp.
 *
 */
public class TeamPresenterImp implements MVPInterface.TeamInteractor.TeamListner,MVPInterface.TeamPresenter {
   private MVPInterface.TeamView teamView;
   private MVPInterface.TeamInteractor teamInteractor;

    /**
     * Instantiates a new Team presenter imp.
     *
     * @param teamView       the team view interface
     * @param teamInteractor the team interactor interface
     */
    public TeamPresenterImp(MVPInterface.TeamView teamView, MVPInterface.TeamInteractor teamInteractor) {
        this.teamView = teamView;
        this.teamInteractor = teamInteractor;
    }

    /**
     * This function implementation to pass the competionID to the getTeams() function
     * @param competionID integer ID
     */
    @Override
    public void requestTeams(int competionID) {
        teamInteractor.getTeams(this,competionID);
    }

    /**
     * This function implementation to pass the teamID to the getSquad() function
     * @param teamID teamID
     */
    @Override
    public void requestSquads(int teamID, TextView playesNameTv) {
        teamInteractor.getSquad(this,teamID,playesNameTv);
    }

    /**
     * This function to listen if request of get Team list done success
     * then pass the list to the view
     * @param teamList list of teams
     */
    @Override
    public void onFinishedRequestTeams(List<Team> teamList) {
        if(teamView!=null){
            teamView.getTeamSuccess(teamList);
        }
    }

    /**
     * This function to listen if request of get Team list failed
     * and pass the error message to the view
     * @param t error
     */
    @Override
    public void onFailedRequestTeams(Throwable t) {
        if(teamView!=null){
            teamView.failedGetTeam(t);
        }
    }

    /**
     * This function to listen if request of get TeamDetails contain squad list done success
     * then pass the list to the view
     * @param teamDetails squadList
     */
    @Override
    public void onFinishedRequestSquads(TeamDetails teamDetails, TextView playesNameTv) {
        if(teamView!=null){
            teamView.getSquadSuccessfully(teamDetails,playesNameTv);
        }
    }

    /**
     * This function to listen if request of get Squad list failed
     * and pass the error message to the view
     * @param t error
     */
    @Override
    public void onFailedRequestSquads(Throwable t) {
        if(teamView!=null){
            teamView.failedGetSquad(t);
        }
    }
}
