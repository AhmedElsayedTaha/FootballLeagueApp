package com.league.foorballleagueapp.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.league.foorballleagueapp.Interfaces.MVPInterface;
import com.league.foorballleagueapp.MVPImplementations.TeamInteractorImp;
import com.league.foorballleagueapp.MVPImplementations.TeamPresenterImp;
import com.league.foorballleagueapp.Model.Team;
import com.league.foorballleagueapp.Model.TeamDetailsModel.Squad;
import com.league.foorballleagueapp.Model.TeamDetailsModel.TeamDetails;
import com.league.foorballleagueapp.R;
import com.league.foorballleagueapp.Utiles.Utiles;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * My Team Adapter . .
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.MyViewHolder> implements
        MVPInterface.TeamView {
    //App Context
    private Context context;
    //Team List
    private List<Team> teamsList;
    //Presenter interface
    private MVPInterface.TeamPresenter presenter;
    private OnClicksListner onClicksListner;
    private ProgressDialog progressDialog ;
    //Team details contain squad list
    private List<TeamDetails> teamDetailList = new ArrayList<>();
    private List<Team> favouritList = new ArrayList<>();
    private Boolean flag=false,nameFlag=false;
    int number=0;
   private List<Integer> teamsListIDs = new ArrayList<>();
   private Boolean isFav ;


    /**
     * The interface On clicks listner.
     */
    public interface OnClicksListner{
        /**
         * Show details of the team
         *
         * @param position the position
         */
        void showDetails(int position);

        /**
         * Show website.
         *
         * @param position the position
         */
        void showWebsite(int position);
    }

    /**
     * Instantiates a new Team adapter.
     *
     * @param context         the App context
     * @param teamsList       the teams list
     * @param onClicksListner the on clicks listner
     * @param isFav boolean if favourite
     */
    public TeamAdapter(Context context, List<Team> teamsList,Boolean isFav,OnClicksListner onClicksListner) {
        this.context = context;
        this.teamsList = teamsList;
        this.onClicksListner = onClicksListner;
        this.isFav=isFav;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.team_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {


        presenter = new TeamPresenterImp(this, new TeamInteractorImp());
        final Team team = teamsList.get(i);
        viewHolder.teamNameTv.setText(team.getName());
        viewHolder.teamWebsiteTv.setText(team.getWebsite());
        viewHolder.clubColorTv.setText(team.getClubColors());
        viewHolder.teamVenueTv.setText(team.getVenue());

        /*
         * Show website
         *  calling interface function to implement it in the activity
         */
        viewHolder.teamWebsiteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicksListner.showWebsite(i);
            }
        });

        if(isFav){
            viewHolder.favImg.setVisibility(View.GONE);
        }
        else {
            viewHolder.favImg.setVisibility(View.VISIBLE);

            viewHolder.favImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favouritList.clear();
                    teamsListIDs.clear();
                    if(Utiles.getFavouritesTeam(context)!=null){
                        favouritList.addAll(Utiles.getFavouritesTeam(context));
                        teamsListIDs.addAll(Utiles.getTeamIDs(context));
                    }
                    if (String.valueOf(viewHolder.favImg.getTag()).equals("empty")) {
                        viewHolder.favImg.setImageResource(R.drawable.fill_fav);
                        viewHolder.favImg.setTag("fill");
                        favouritList.add(teamsList.get(i));
                        teamsListIDs.add(teamsList.get(viewHolder.getAdapterPosition()).getId());
                        Utiles.saveTeamIDs(teamsListIDs, context);
                        Utiles.saveFavouritesTeam(favouritList, context);

                    } else {
                        viewHolder.favImg.setImageResource(R.drawable.blank_fav);
                        viewHolder.favImg.setTag("empty");
                        Iterator<Team> iter = favouritList.iterator();
                        while (iter.hasNext()) {
                            Team p = iter.next();
                            if (p.getName().equals(team.getName())) iter.remove();
                        }
                        teamsListIDs.remove(teamsList.get(viewHolder.getAdapterPosition()).getId());
                        Utiles.saveTeamIDs(teamsListIDs, context);
                        Utiles.saveFavouritesTeam(favouritList, context);

                    }
                }

            });

            if (Utiles.getTeamIDs(context) != null) {
                for (int x = 0; x < Utiles.getTeamIDs(context).size(); x++) {
                    if (teamsList.get(i).getId().equals(Utiles.getTeamIDs(context).get(x))) {
                        viewHolder.favImg.setImageResource(R.drawable.fill_fav);
                        viewHolder.favImg.setTag("fill");
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    @Override
    public void getTeamSuccess(List<Team> teamList) {

    }

    @Override
    public void failedGetTeam(Throwable e) {

    }

    /**
     * GET Team details of the selected team by its ID
     * and we target the squad list of this team
     * @param teamDetails teamDetails Object
     * @param NameTv carry team players names
     */
    @Override
    public void getSquadSuccessfully(TeamDetails teamDetails, TextView NameTv) {

        /*
         * Get the squad of the selected team and save it to view it if user is offline
         * if team details already saved . then don't save
         * if the saved list team details is empty , then save the first team details
         *
         */
       if(Utiles.getDetailsTeam(context)!=null&&Utiles.getDetailsTeam(context).size()>0){
           for(int i=0;i<Utiles.getDetailsTeam(context).size();i++){
               if(teamDetails.getId().equals(Utiles.getDetailsTeam(context).get(i).getId())){
                   flag=true;
                   break;
               }
           }
           if(!flag){
               teamDetailList.add(teamDetails);
               Utiles.saveDetailsTeam(teamDetailList,context);

           }
       }
       else {
           teamDetailList.add(teamDetails);
           Utiles.saveDetailsTeam(teamDetailList,context);
       }
        /*
         * Convert the list of players name to a single string
         */
        int i=0;
        if (teamDetails != null ) {
            StringBuilder listString= new StringBuilder();
            for (Squad s : teamDetails.getSquad())
            {       i++;
                if(teamDetails.getSquad().size()==i)
                listString.append(s.getName());
                else
                    listString.append(s.getName()).append(" - ");
            }
            NameTv.setText(listString.toString());
            progressDialog.dismiss();
        }
    }

    /**
     * If request to get Team details failed
     * @param t carry error message
     */
    @Override
    public void failedGetSquad(Throwable t) {
        Toast.makeText(context, context.getString(R.string.reached_request_limit),Toast.LENGTH_LONG).show();
        Log.e("error "," "+t.getMessage());
        progressDialog.dismiss();
    }

    /**
     * Implemented function of the recyclerview Adapter
     * @param position team position
     * @return id of the team
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Implemented function of the recyclerview Adapter
     * @param position team position
     * @return position
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }


    /**
     * The type My view holder.
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Team name tv.
         */
        TextView teamNameTv, /**
         * The Team website tv.
         */
        teamWebsiteTv, /**
         * The Club color tv.
         */
        clubColorTv, /**
         * The Team venue tv.
         */
        teamVenueTv, /**
         * The Player name tv.
         */
        playerNameTv, /**
         * The Names tv.
         */
        namesTv;
        /**
         * The Team card.
         */
        CardView teamCard;
        /**
         * The Fav img.
         */
        ImageView favImg;

        /**
         * Instantiates a new My view holder.
         *
         * @param itemView the item view
         */
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            teamNameTv = itemView.findViewById(R.id.teamNameTv);
            teamWebsiteTv = itemView.findViewById(R.id.teamWebsiteTv);
            clubColorTv = itemView.findViewById(R.id.clubColorTv);
            teamVenueTv = itemView.findViewById(R.id.teamVenueTv);
            teamCard = itemView.findViewById(R.id.teamCard);
            playerNameTv = itemView.findViewById(R.id.playerNameTv);
            namesTv = itemView.findViewById(R.id.namesTv);
            favImg = itemView.findViewById(R.id.favImg);

            /*
             * Show team details
             */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClicksListner.showDetails(getAdapterPosition());
                }
            });

            /* Here is the listener on the player name textview to show PlayersName
             *
             */
            playerNameTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    /* if text = show more , then show players name
                     *  Firest check on Internet and if there is internet , then make the request
                     *  if not , then get the saved item details and show players Name
                     */
                    if( playerNameTv.getText().equals(context.getString(R.string.show_more))) {
                        if(Utiles.isOnline(context)) {
                            showProgress();
                            presenter.requestSquads(teamsList.get(getAdapterPosition()).getId(), namesTv);
                            playerNameTv.setText(context.getString(R.string.show_less));
                        }
                        else {
                            int i = 0;
                            TeamDetails teamDetails = new TeamDetails();
                            if (Utiles.getDetailsTeam(context) != null) {
                                for (int x = 0; x < Utiles.getDetailsTeam(context).size(); x++) {
                                    if (teamsList.get(getAdapterPosition()).getName().
                                            equals(Utiles.getDetailsTeam(context).get(x).getName())) {
                                        teamDetails = Utiles.getDetailsTeam(context).get(x);
                                        nameFlag = true;
                                    }
                                }

                                if (nameFlag) {
                                    nameFlag = false;
                                    if (teamDetails != null) {
                                        StringBuilder listString = new StringBuilder();
                                        for (Squad s : teamDetails.getSquad()) {
                                            i++;
                                            if (teamDetails.getSquad().size() == i)
                                                listString.append(s.getName());
                                            else
                                                listString.append(s.getName()).append(" - ");
                                        }
                                        namesTv.setText(listString.toString());
                                        playerNameTv.setText(context.getString(R.string.show_less));
                                    }

                                } else
                                    Toast.makeText(context, context.getString(R.string.data_didnt_saved), Toast.LENGTH_LONG).show();
                            }else Toast.makeText(context,context.getString(R.string.check_intenet),Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        Log.e("size"," "+Utiles.getDetailsTeam(context).size());
                        namesTv.setText("");
                        playerNameTv.setText(context.getString(R.string.show_more));

                    }

                }
            });
        }
    }

    private void showProgress(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}


