package com.league.foorballleagueapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.league.foorballleagueapp.Adapters.TeamAdapter;
import com.league.foorballleagueapp.Interfaces.MVPInterface;
import com.league.foorballleagueapp.MVPImplementations.TeamInteractorImp;
import com.league.foorballleagueapp.MVPImplementations.TeamPresenterImp;
import com.league.foorballleagueapp.Model.Team;
import com.league.foorballleagueapp.Model.TeamDetailsModel.TeamDetails;
import com.league.foorballleagueapp.RoomDatabase.TeamDatabase;
import com.league.foorballleagueapp.Utiles.Utiles;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements MVPInterface.TeamView {

    /**
     * The Team rec.
     */
    @BindView(R.id.teamRec)
    RecyclerView teamRec;
    /**
     * The Swipe refresh layout.
     */
    @BindView(R.id.myswip)
    SwipeRefreshLayout swipeRefreshLayout;
    /**
     * The No internet img.
     */
    @BindView(R.id.noInternetImg)
    ImageView noInternetImg;
    /**
     * The Team presenter.
     */
    MVPInterface.TeamPresenter teamPresenter;
    /**
     * The Unbinder.
     */
    Unbinder unbinder;
    /**
     * The Adapter.
     */
    TeamAdapter adapter ;
    /**
     * The Progress dialog.
     */
    ProgressDialog progressDialog;
    /**
     * The Teams.
     */
    List<Team> teams =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);


        teamPresenter = new TeamPresenterImp(this,new TeamInteractorImp());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
      //  LinearLayoutManager gridLayoutManager = new GridLayoutManager(this,6,LinearLayoutManager.HORIZONTAL,false);
        teamRec.setLayoutManager(layoutManager);
        teamRec.setItemAnimator(new DefaultItemAnimator());

        if(!Utiles.isOnline(this)){
            getSavedTeam();
            if(!Utiles.getBoolFlag(this)){
                noInternetImg.setVisibility(View.VISIBLE);
            }
            else
                noInternetImg.setVisibility(View.GONE);

        }
        else {
            progressDialog.show();
            teamPresenter.requestTeams(2021);
            noInternetImg.setVisibility(View.GONE);
        }

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               if(Utiles.isOnline(MainActivity.this)){
                   noInternetImg.setVisibility(View.GONE);
                   teamPresenter.requestTeams(2021);
               }
               else {
                   Toast.makeText(MainActivity.this,"Check your internet",Toast.LENGTH_LONG).show();
                   swipeRefreshLayout.setRefreshing(false);


               }

           }
       });




    }

    /**
     * Get League teams data
     * @param teamList retrieved team list
     */
    @Override
    public void getTeamSuccess(final List<Team> teamList) {
        if(teamList!=null){

            if(!Utiles.getBoolFlag(MainActivity.this))
                new insertDataAsyncTask().execute(teamList);

            adapter = new TeamAdapter(this, teamList,false, new TeamAdapter.OnClicksListner() {
                @Override
                public void showDetails(int position) {
                    Intent intent = new Intent(MainActivity.this,TeamDetaisActivity.class);
                    intent.putExtra(getString(R.string.team_list),teamList.get(position));
                    startActivity(intent);
                }

                @Override
                public void showWebsite(int position) {
                        Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                        intent.putExtra("url",teamList.get(position).getWebsite());
                        startActivity(intent);
                }
            });
            teamRec.setAdapter(adapter);
        }
        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failedGetTeam(Throwable t) {
        Toast.makeText(MainActivity.this,getString(R.string.reached_request_limit),Toast.LENGTH_LONG).show();
        Log.e("error","error is "+t.getMessage());
        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getSquadSuccessfully(TeamDetails teamDetails, TextView textView) {
    }

    @Override
    public void failedGetSquad(Throwable t) {

    }


    /**
     * Get saved team from Room Database using Single Observable
     */
    @SuppressLint("CheckResult")
    public void getSavedTeam(){
          TeamDatabase.getTeamDatabaseInstance(this)
                .teamDAO()
                .getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Team>>() {
                     @Override
                     public void onSuccess(final List<Team> teamList) {
                         teams.addAll(teamList);
                         adapter = new TeamAdapter(MainActivity.this, teamList,false, new TeamAdapter.OnClicksListner() {
                             @Override
                             public void showDetails( int position) {
                                 Intent intent = new Intent(MainActivity.this, TeamDetaisActivity.class);
                                 intent.putExtra(getString(R.string.team_list), teamList.get(position));
                                 startActivity(intent);
                         }

                             @Override
                             public void showWebsite(int position) {
                                 Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                                 intent.putExtra("url",teamList.get(position).getWebsite());
                                 startActivity(intent);
                             }
                         });
                         teamRec.setAdapter(adapter);

                     }

                     @Override
                     public void onError(Throwable e) {
                         Log.e("error"," "+e.getMessage());
                     }
                 });

    }


    /**
     * The Insert data using async task in Room database
     */
    @SuppressLint("StaticFieldLeak")
    public class insertDataAsyncTask extends AsyncTask<List<Team>,Void,Void>{


        @SafeVarargs
        @Override
        protected final Void doInBackground(List<Team>... lists) {
            TeamDatabase.getTeamDatabaseInstance(MainActivity.this)
                    .teamDAO().insertTeamData(lists[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           Utiles.saveBoolFlag(true,MainActivity.this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menue,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.fav){
            Intent intent = new Intent(this, FavouriteActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
