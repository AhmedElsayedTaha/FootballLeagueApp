package com.league.foorballleagueapp.MVPImplementations;

import android.widget.TextView;

import com.league.foorballleagueapp.Interfaces.MVPInterface;
import com.league.foorballleagueapp.Model.LeagueTeams;
import com.league.foorballleagueapp.Model.TeamDetailsModel.TeamDetails;
import com.league.foorballleagueapp.Network.APIClient;
import com.league.foorballleagueapp.Network.APIService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Implementation of the requests
 */
public class TeamInteractorImp implements MVPInterface.TeamInteractor {

    private CompositeDisposable disposable = new CompositeDisposable();

    /**
     * Implement of GET Request teams
     * @param teamListner listener on request
     * @param competionID competition ID
     */
    @Override
    public void getTeams(final TeamListner teamListner, int competionID) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);

        disposable.add(apiService.getTeams(competionID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LeagueTeams>() {
                    @Override
                    public void onSuccess(LeagueTeams leagueTeams) {
                     teamListner.onFinishedRequestTeams(leagueTeams.getTeams());
                    }

                    @Override
                    public void onError(Throwable e) {
                        teamListner.onFailedRequestTeams(e);
                    }
                }));
    }

    /**
     * Implementation of Request get TeamDetails contain squad list
     * @param teamListner request Listener
     * @param teamID teamID
     *
     */
    @Override
    public void getSquad(final TeamListner teamListner, int teamID, final TextView playesNameTv) {
        APIService apiService = APIClient.getRetrofitInstance().create(APIService.class);
        disposable.add(apiService.getSquad(teamID)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableSingleObserver<TeamDetails>() {
            @Override
            public void onSuccess(TeamDetails teamDetails) {
                teamListner.onFinishedRequestSquads(teamDetails,playesNameTv);
            }

            @Override
            public void onError(Throwable e) {
                teamListner.onFailedRequestSquads(e);
            }
        }));
    }
}
