package com.league.foorballleagueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.league.foorballleagueapp.Model.Team;
import com.league.foorballleagueapp.Utiles.Utiles;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * The type Team details activity.
 */
public class TeamDetaisActivity extends AppCompatActivity {

    /**
     * The Unbinder.
     */
    Unbinder unbinder;
    /**
     * The Team name tv.
     */
    @BindView(R.id.teamNameTv)
    TextView teamNameTv;
    /**
     * The Short name tv.
     */
    @BindView(R.id.shortNameTv)
    TextView shortNameTv;
    /**
     * The Tla tv.
     */
    @BindView(R.id.tlaTv)
    TextView tlaTv;

    /**
     * The Address tv.
     */
    @BindView(R.id.addressTv)
    TextView addressTv;
    /**
     * The Phone tv.
     */
    @BindView(R.id.phoneTv)
    TextView phoneTv;
    /**
     * The Team website tv.
     */
    @BindView(R.id.teamWebsiteTv)
    TextView teamWebsiteTv;
    /**
     * The Email tv.
     */
    @BindView(R.id.emailTv)
    TextView emailTv;
    /**
     * The Founded tv.
     */
    @BindView(R.id.foundedTv)
    TextView foundedTv;
    /**
     * The Club color tv.
     */
    @BindView(R.id.clubColorTv)
    TextView clubColorTv;
    /**
     * The Team venue tv.
     */
    @BindView(R.id.teamVenueTv)
    TextView teamVenueTv;
    /**
     * The Club image.
     */
    @BindView(R.id.clubImage)
    ImageView clubImage;

    /**
     * The Team.
     */
    Team team = new Team();
    /**
     * The Url.
     */
    String url;

    List<String> imagesString = new ArrayList<>();
    List<Integer> imagedIDS = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_detais);
        unbinder = ButterKnife.bind(this);


        team = (Team) getIntent().getSerializableExtra(getString(R.string.team_list));
        if (team != null) {

            if(Utiles.isOnline(this)) {

                if (team.getCrestUrl().contains(".svg")) {
                    if (team.getCrestUrl().contains("https"))
                        url = team.getCrestUrl().replace("https", "http");
                    else
                        url = team.getCrestUrl();
                    Utiles.fetchSvg(this, url, clubImage);
                } else {
                    Glide.with(this)
                            .load(team.getCrestUrl())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder))
                            .into(clubImage);
                }


            }


            teamNameTv.setText(team.getName());
            shortNameTv.setText(team.getShortName());
            tlaTv.setText(team.getTla());
            addressTv.setText(team.getAddress());
            phoneTv.setText(team.getPhone());
            teamWebsiteTv.setText(team.getWebsite());
            emailTv.setText(team.getEmail());
            if(team.getFounded()==null)
            foundedTv.setText(getString(R.string.no_result));
            else
                foundedTv.setText(String.valueOf(team.getFounded()));
            clubColorTv.setText(team.getClubColors());
            teamVenueTv.setText(team.getVenue());


            teamWebsiteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TeamDetaisActivity.this,WebViewActivity.class);
                    intent.putExtra("url",team.getWebsite());
                    startActivity(intent);
                }
            });


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
