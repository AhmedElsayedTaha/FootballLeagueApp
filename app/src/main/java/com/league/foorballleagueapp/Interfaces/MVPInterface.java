package com.league.foorballleagueapp.Interfaces;

import android.widget.TextView;

import com.league.foorballleagueapp.Model.Team;
import com.league.foorballleagueapp.Model.TeamDetailsModel.TeamDetails;

import java.util.List;

public interface MVPInterface {
    interface TeamPresenter{
        //Get team object
        void requestTeams(int competionID);
        //GET Squad List
        void requestSquads(int teamID, TextView playesNameTv);
    }
    interface TeamInteractor{
        interface TeamListner{
            //Listeners on Request team
            void onFinishedRequestTeams(List<Team> teamList);
            void onFailedRequestTeams(Throwable t);
            //Listeners on Request TeamDetails contain squad list
            void onFinishedRequestSquads(TeamDetails teamDetails, TextView playesNameTv);
            void onFailedRequestSquads(Throwable t);
        }
        //GET teams
        void getTeams(TeamListner teamListner,int competionID);
        //GET TeamDetails contain squad list
        void getSquad(TeamListner teamListner,int teamID,TextView playesNameTv);
    }
    interface TeamView{
        //Attach team object on view
        void getTeamSuccess(List<Team> teamList);
        void failedGetTeam(Throwable e);

        //Attach Squad object on View
        void getSquadSuccessfully(TeamDetails teamDetails,TextView playesNameTv);
        void failedGetSquad(Throwable t);
    }
}
