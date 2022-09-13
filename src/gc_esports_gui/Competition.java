
package gc_esports_gui;

/*
   Filename: Competition
   Purpose: Competition Class in one place
   Author: Shatha
   Date: 13 Sep 2022
   Version: 2.0
   License (if applicable): n/a
   Notes, Fixes, Updates:
*/

public class Competition 
{
    //private data
    //League of Legends,14-Jan-22,TAFE Coomera,BioHazards,5
    private String game; 
    private String competitionDate;
    private String location;
    private String team;
    private int points;
    
    //constructor method to create instance of class
    public Competition(String game, String competitionDate, String location, String team, int points){
        
        this.game = game;
        this.competitionDate = competitionDate;
        this.location = location;
        this.team = team;
        this.points = points;
        }
    
        // get methods for each private data field
        public String getGame(){
        return game;
        }

        public String getCompetitionDate(){
        return competitionDate;
        }

        public String getLocation(){
        return location;
        }

        public String getTeam(){
        return team;
        }

        public int getPoints(){
        return points;
        }


        //// SET assigns values. void provides no return value 
         public void setGame(String game){
        this.game = game;
        }

        public void setCompetitionDate(String competitionDate){
        this.competitionDate = competitionDate;
        }

        public void setLocation(String location){
        this.location = location;
        }

        public void setTeam(String team){
        this.team = team;
        }

        public void setPoints(){
        this.points = points;
        }

        public String addToCSV(){
            return game + "," + competitionDate + "," + location + "," + team + "," + points; 
        }
    
}
