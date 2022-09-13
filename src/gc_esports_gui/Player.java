/*
   Filename: Player
   Purpose: Player Class
   Author: Shatha Almaktoum
   Date: 13 Sept 22
   Version: 2.0
   License (if applicable): n/a
   Notes, Fixes, Updates:
 */


package gc_esports_gui;

// Creating class player
public class Player 
{   
    private String playerName;
    private String team;

    // Defining display players field in GUI
    public Player()
    {
    this.playerName = "Default";
    this.team = "No team";
    }


    /*
   Method name: Constructor method
   Purpose: create instance of Player class using dot notation
   Inputs: scope of instance i.e. String + playerName
   Output: instance.field i.e. this.playerName
    */

    public Player (String playerName, String team)
    {
    this.playerName = playerName;
    this.team = team;
    }


    // GET methods
    public String getPlayerName()
    {
    return playerName;
    }

   
    public String getTeamName()
    {
    return team;
    }

    //SET assigns values. void provides no return value 
    public void setPlayerName (String playerName)
    {
    this.playerName = playerName;
    }

    //SET assigns values. void provides no return value 
    public void setTeamName (String team)
    {
    this.team = team;
    }
    
}
