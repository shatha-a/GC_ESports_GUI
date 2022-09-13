/*
   Filename: Team
   Purpose: Team Class
   Author: Shatha Almaktoum
   Date: 13 Sept 2022
   Version: 2.0
   License (if applicable): n/a
   Notes, Fixes, Updates:
 */
package gc_esports_gui;

import java.util.ArrayList;


public class Team 
{
    // Private data fields for each item in CSV
    private String teamName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    
    // Create an array list for the players
    private ArrayList<String> playerList;

//    public Team()
//    {
//    teamName = "";
//    contactName= "";
//    contactPhone= "";
//    contactEmail=""; 
//    }
    //constructor method to create instance of class
    public Team(String teamName, String contactName,String contactPhone, String contactEmail)
    { 
        this.teamName = teamName;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        
        // players array list
        this.playerList = new ArrayList<String>();
    }
    
    // GET method, gathering input from CSV    
    public String getTeamName()
    {
        return teamName;
    }
    public String getContactName()
    {
        return contactName;
    }
    public String getContactPhone()
    {
        return contactPhone;
    }
    public String getContactEmail()
    {
        return contactEmail;
    }
    public ArrayList<String> getPlayerList()
    {
        return playerList;
    }
    
    
    // SET assigns values. void provides no return value    
    public void setTeamName (String teamName)
    {
        this.teamName = teamName;
    }
    public void setContactName (String contactName)
    {
        this.contactName = contactName;
    }
    public void setContactPhone (String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    public void setContactEmail (String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    
    public void setPlayer (String playerName){
            this.playerList.add(playerName);
    }

    
    // removing a player method    
    public void removePlayers()
    {
        if (playerList.size() > 0)
        {           
            playerList.clear();
        }
    }
    
    public void removePlayer(String playerName)
    {
        // remove player from list based on player name
    }
    public void removePlayer(int playerID)
    {
        // remove player from list based on ID
    }
    
    
    // Override
    @Override
    public String toString()
    {
        String playersNames = "";
        
        if(playerList.size() > 0)
        {
            for (int i = 0; i < playerList.size(); i++)
                {
                    playersNames += playerList.get(i) + ", ";
                }
        }   
        return "Team: " + teamName + " Contact Person: " + contactName + " Phone: " + 
                contactPhone + " Email: " + contactEmail + " Players: " + playersNames;
    }
    
    // Gather data from CSV
    /*public String addToCSV(){
        return teamName + "," + contactName + "," + contactPhone + "," + contactEmail;
    }*/    
}
