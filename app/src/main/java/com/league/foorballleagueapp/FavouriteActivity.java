package com.league.foorballleagueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.league.foorballleagueapp.Adapters.TeamAdapter;
import com.league.foorballleagueapp.Utiles.Utiles;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * The type Favourite activity.
 */
public class FavouriteActivity extends AppCompatActivity {

    /**
     * The Unbinder.
     */
    Unbinder unbinder;

    /**
     * The Fav rec.
     */
    @BindView(R.id.favRec)
    RecyclerView favRec;
    /**
     * The Adapter.
     */
    TeamAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourit);
        unbinder = ButterKnife.bind(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        favRec.setLayoutManager(layoutManager);
        favRec.setItemAnimator(new DefaultItemAnimator());
        /*
         * Check if favourite list is not null then send list to adapter
         */
        if(Utiles.getFavouritesTeam(this)!=null){
            adapter = new TeamAdapter(this, Utiles.getFavouritesTeam(this),true, new TeamAdapter.OnClicksListner() {
                @Override
                public void showDetails(int position) {
                    Intent intent = new Intent(FavouriteActivity.this,TeamDetaisActivity.class);
                    intent.putExtra(getString(R.string.team_list),Utiles.getFavouritesTeam(FavouriteActivity.this).get(position));
                    startActivity(intent);
                }

                @Override
                public void showWebsite(int position) {
                    Intent intent = new Intent(FavouriteActivity.this,WebViewActivity.class);
                    intent.putExtra("url",Utiles.getFavouritesTeam(FavouriteActivity.this).get(position).getWebsite());
                    startActivity(intent);
                }
            });
            favRec.setAdapter(adapter);
        }
        else Toast.makeText(this,"Empty list",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
