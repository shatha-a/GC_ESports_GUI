
package gc_esports_gui;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JOptionPane;

/*
   Filename: GC_ESports_GUI
   Purpose: GUI for Esport app
   Author: Shatha Almaktoum
   Date: 13 Sept 2022
   Version: 2.0 (rework)
   License (if applicable): n/a
   Notes, Fixes, Updates:
 
*/


public class GC_ESports_GUI extends javax.swing.JFrame {

    // private data
    // for storing comp results
    private ArrayList<Competition> competitions;
    // store our team instances
    private ArrayList<Team> Teams;
    // boolean to track the item selection changes to the JCombo Boxes
    boolean comboBoxStatus;
    // store our Player instances
    //private ArrayList<Player> Players;
    
    // for customising the JTable (which display comp results
    private DefaultTableModel compResultsTableModel;

    //*********************** CONSTRUCTOR METHOD*******************************
    public GC_ESports_GUI()
    {
     //************** 1. INITIALISE PRIVATE DATA FIELDS ***********************
        competitions = new ArrayList<Competition>();
        Teams = new ArrayList<Team>();
        //Players = new ArrayList<Player>();
        comboBoxStatus = false;
        compResultsTableModel = new DefaultTableModel();    
        
    //****************** 2. CUSTOMISE TABLE MODEL******************************
    // Customised column names for movie JTable
        String [] columnNames_Results = new String[]
                    {"Date", "Location", "Game", "Team", "Points"};
    // set up customisation
        compResultsTableModel.setColumnIdentifiers(columnNames_Results);
    
    //*******************INITIALISE ALL SWING CONTROLS*************************
        initComponents();
        
    //*******************CUSTOMISE TABLE COLUMNS FOR JTABLE********************
        resizeTableColumns();
        
    //*******************READ IN EXTERNAL CSV FILES ***************************
        readCompetitionData();   
        readTeamData();        
        
    //****************DISPLAY COMPETITION DATE IN JTABLE************************  
        displayJTable();
    //*******************DISPLAY TEAM NAMES IN COMBO BOXES**********************
        displayTeams();
     //**************DISPLAY TEAM DETAILS IN UPDATE TEAM PANEL ******************
        displayTeamDetails();
    //************************* CHANGE BOOLEAN *********************************
       comboBoxStatus = true;
        
    }
    

    //Team Competition Results
    //display ArrayList Competitions in JTable
    private void displayJTable(){
        
    //populate competition data into JTable
        if (competitions.size() > 0)
        {
        //create Object[] 2D array for JTable
            Object[][] competitions2DArray = new Object[competitions.size()][];
            // populate 2D array from competitions ArrayList
            for (int i = 0; i < competitions2DArray.length; i++){
                
            // create Object[] for single row of data containing 6 compnents
                Object[] competition = new Object[5];
                // date
                competition[0] = competitions.get(i).getCompetitionDate();
                // location
                competition[1] = competitions.get(i).getLocation();
                // game
                competition[2] = competitions.get(i).getGame();
                // team
                competition[3] = competitions.get(i).getTeam();
                // points
                competition[4] = competitions.get(i).getPoints();
                // append to 2D array
                competitions2DArray[i] = competition;
            }
            
             // first, remove all existing rows if there are any
            if (compResultsTableModel.getRowCount() > 0){
                
                for (int i = compResultsTableModel.getRowCount() - 1; i > -1; i--){
                    
                    compResultsTableModel.removeRow(i);
                }              
            }
            
            // add data to tableModel
                for (int row = 0; row < competitions2DArray.length; row++){
                    
                    compResultsTableModel.addRow(competitions2DArray[row]);
                }
                // update
                compResultsTableModel.fireTableDataChanged();    
        }
    }
    
    //read in external CSV file data for competitions
    public void readCompetitionData()
    {
        try 
        {
            // 1. Create reader and designate external file to read from
            FileReader reader = new FileReader("competitions.csv");
            // 2. Create bufferedReader wehich uses the reader object
            BufferedReader bufferedReader = new BufferedReader(reader);
            // 3. Declare line string (used to read in and store each line read from the external file)
            String line; 
            // 4. Loop through each line in the external file until the EOF (end of life)
            while ((line = bufferedReader.readLine()) != null)
            {
                // Check if line is not empty and contains something
                if (line.length() > 0)
                {
                    
                    // split the line by its delimiter comma and set up each value in the lines array
                    // League of Legends,14-Jan-2022,TAFE Coomera,BioHazards,5
                    String [] lineArray = line.split(",");
                    // set up individual variables for each split line components
                    String game = lineArray[0];
                    String compDate = lineArray[1];
                    String location = lineArray[2];
                    String team = lineArray[3];
                    int points = Integer.parseInt(lineArray[4]);
                    // create Competition instance
                    Competition comp =  new Competition(game, compDate, location, team, points);
                    // add instance to the ArrayList
                    competitions.add(comp);
                }
            }
            
            // 5. Close reader object
            reader.close();   
        }
        
        // Catch methods, using message Dialog box
        catch (FileNotFoundException fnfe)
        {
            //catch any file not found exception
          System.out.println("ERROR: competitions.csv file was not found");
          javax.swing.JOptionPane.showMessageDialog(null,fnfe.getMessage(), "ERROR" , javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        
        catch (IOException ioe)
        {
          // catch any File IO-related exception
            System.out.println("ERROR: competitions.csv file was found, but there is a read problem");
            javax.swing.JOptionPane.showMessageDialog(null,ioe.getMessage(), "ERROR" , javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        
        catch (NumberFormatException nfe)
        {
            System.out.println("ERROR: number format exception- trying to convert a string into an integer");
            javax.swing.JOptionPane.showMessageDialog(null,nfe.getMessage(), "ERROR" , javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    // TO BE UPDATED FOR TEAM FILE
    public void readTeamData()
    {
        try 
        {
            
            // 1. Create reader and designate external file to read from
            FileReader reader = new FileReader("teams.csv");
            
            // 2. Create bufferedReader wehich uses the reader object
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            // 3. Declare line string (used to read in and store each line read from the external file)
            String line; 
            
            // 4. Loop through each line in the external file until the EOF (end of life)
            while ((line = bufferedReader.readLine()) != null)
            {
                
                //System.out.println(line);
                // Check if line is not empty and contains something
                if (line.length() > 0)
                {
                    // split the line by its delimiter comma and set up each value in the lines array
                    // League of Legends,14-Jan-2022,TAFE Coomera,BioHazards,5
                    String [] lineArray = line.split(",");
                    // set up individual variables for each split line components
                    String teamName = lineArray[0];
                    String contactName = lineArray[1];
                    String contactPhone = lineArray[2];
                    String contactEmail = lineArray[3];
                    // add team instance to the array list
                    Teams.add(new Team(teamName, contactName, contactPhone, contactEmail));   
                }
            }
            
            // 5. Close reader object
            reader.close();   
        }
        
        catch (FileNotFoundException fnfe)
        {
            //catch any file not found exception
          System.out.println("ERROR: team.csv file not found");
        }
        
        catch (IOException ioe)
        {
          // catch any File IO-related exception
            System.out.println("ERROR: team.csv file found, but there is a read problem");
        }
        
     
        
        ArrayList<Player> teamPlayerList = new ArrayList<Player>();
        
        
        //**************read in data for players.csv***************************
        try 
        {
            // 1. Create reader and designate external file to read from
            FileReader reader = new FileReader("players.csv");
            // 2. Create bufferedReader wehich uses the reader object
            BufferedReader bufferedReader = new BufferedReader(reader);
            // 3. Declare line string (used to read in and store each line read from the external file)
            String line; 
            // 4. Loop through each line in the external file until the EOF (end of life)
            while ((line = bufferedReader.readLine()) != null)
            {
                //System.out.println(line);
                // Check if line is not empty and contains something
                if (line.length() > 0)
                {
                    // split the line by its delimiter comma and set up each value in the lines array
                    // James Taylor,Coomera Bombers
                    String [] lineArray = line.split(",");
                    // set up individual variables for each split line components
                    String playerName = lineArray[0];
                    String team = lineArray[1];
                    //Add player name to the existing teams (previously read in) 
                    
                    // add team instance to the Teams array list                 
//                    for (Team teamName : Teams)
//                    {			
//                        if (teamName.getTeamName().equals(team))
//                        {
//                            teamName.setPlayer(playerName);
//                        }	
//                    }
                    //and match the teamName with the Teams team name
                    for (int i =0; i< Teams.size(); i++)
                    {
                        if (Teams.get(i).getTeamName().equals(team))
                        {
                        Teams.get(i).setPlayer(playerName);
                        }
                    }
                }
            }
            
            // 5. Close reader object
            reader.close();   
        }
        
        catch (FileNotFoundException fnfe)
        {
            //catch any file not found exception
          System.out.println("ERROR: player.csv file not found");
        }
        
        catch (IOException ioe)
        {
          // catch any File IO-related exception
            System.out.println("ERROR: player.csv file found, but there is a read problem");
        }
        
      
       
        for (int i = 0; i <teamPlayerList.size(); i++)
        {
            String playerName = teamPlayerList.get(i).getPlayerName();
            String teamName = teamPlayerList.get(i).getTeamName();

            for (int j = 0; j <Teams.size(); j++)
            {
                if (teamName.equals(Teams.get(j).getTeamName()))
                {}
                Teams.get(j).setPlayer(playerName);
            }
        }
    }        
private void saveTeamData()
 {
    //write to external file [teams.csv- overwrite mode]
        try
        {
            //1. create outputStream and designate the external file
            //to append to
            //true means it is in append mode
            //false means it is in overwrite mode
            FileOutputStream outputStream = new FileOutputStream("teams.csv", false);
            //2. Create outputStreamWriter and designate the character
            //set used by the outputStream object
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter (outputStream, "UTF-8");
            //3. Create bufferedWriter which uses the outputStreamWriter to write to the external file
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            //4. write to the external file (overwrite existing contents
            ///Coomera Bombers,James Taylor,0433948765,jamestaylor123@coolmail.com
            //format: teamName, contactName, contactPhone,contactEmail
          //loop through team array list....Team(String teamName, String contactName,String contactPhone, String contactEmail)
           for (int i = 0; i< Teams.size(); i++)
            {
                String teamName = Teams.get(i).getTeamName();
                String contactName = Teams.get(i).getContactName();
                String contactPhone = Teams.get(i).getContactPhone();
                String contactEmail = Teams.get(i).getContactEmail();
//      PLAYER LIST SHOULD SAVE TO PLAYERS.CSV        public ArrayList<String> getPlayerList()
                
                bufferedWriter.write(teamName + "," + contactName + "," + contactPhone + "," + contactEmail);
                bufferedWriter.newLine();
            }
            //5. Close the bufferedWriter object
           bufferedWriter.close();
        }    
        catch (IOException e)
        {
            //catch any File IO- related exception and display
            //exception message using printStackTrace()
            //e.g printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "ERROR: Problem in writing to competitions.csv", "FILE WRITE (FOR NEW COMPETITION RESULT) ERROR DETECTED!", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    private void saveCompetitionData()
    {
    //write to external file [competitions.csv- overwrite mode]
        try
        {
            //1. create outputStream and designate the external file
            //to append to
            //true means it is in append mode
            //false means it is in overwrite mode
            FileOutputStream outputStream = new FileOutputStream("competitions.csv", false);
            //2. Create outputStreamWriter and designate the character
            //set used by the outputStream object
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter (outputStream, "UTF-8");
            //3. Create bufferedWriter which uses the outputStreamWriter to write to the external file
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            //4. write to the external file (overwrite existing contents
            //format: game, date, location, teams,points
            //loop through competitions array list
            for (int i=0; i< competitions.size(); i++)
            {
                String game = competitions.get(i).getGame();
                String date = competitions.get(i).getCompetitionDate();
                String location = competitions.get(i).getLocation();
                String team = competitions.get(i).getTeam();
                int points = competitions.get(i).getPoints();
                
                bufferedWriter.write(game + "," + date + "," + location + "," + team + "," + points);
                bufferedWriter.newLine();
            }
             //5. Close the bufferedWriter object
            bufferedWriter.close();
        }    
        catch (IOException e)
        {
        //catch any File IO- related exception and display
        //exception message using printStackTrace()
        //e.g printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(null, "ERROR: Problem in writing to competitions.csv", "FILE WRITE (FOR NEW COMPETITION RESULT) ERROR DETECTED!", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void resizeTableColumns(){
        
    //Columns: date, location, competition, Platform, Team, Points
    // (total numeric vcalues must = 1)
        double[] columnWidthPercentage = {0.2f, 0.2f, 0.3f, 0.2f, 0.1f};
        int tableWidth = compResults_jTable.getWidth();
        TableColumn column;
        TableColumnModel tableColumnModel = compResults_jTable.getColumnModel();
        int cantCols = tableColumnModel.getColumnCount();
        for (int i = 0; i < cantCols; i++){
            
        column = tableColumnModel.getColumn(i);
        float pWidth = Math.round(columnWidthPercentage[i] * tableWidth);
        column.setPreferredWidth((int)pWidth);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        banner = new javax.swing.JLabel();
        allTabs = new javax.swing.JTabbedPane();
        teamCompetitionResults = new javax.swing.JPanel();
        Tab1_Header = new javax.swing.JLabel();
        team_comp_results_table = new javax.swing.JScrollPane();
        compResults_jTable = new javax.swing.JTable();
        Button_Display_Top_team = new javax.swing.JButton();
        addNewTeam = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Text_team_name_t2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextArea_player_names_t2 = new javax.swing.JTextArea();
        Text_contact_person_t2 = new javax.swing.JTextField();
        Text_contact_phone_t2 = new javax.swing.JTextField();
        Text_contact_email_t2 = new javax.swing.JTextField();
        Button_save_new_team = new javax.swing.JButton();
        Tab2_Header = new javax.swing.JLabel();
        addNewCompetitionResult = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Text_date_t3 = new javax.swing.JTextField();
        Text_location_t3 = new javax.swing.JTextField();
        Text_game_t3 = new javax.swing.JTextField();
        Text_points_t3 = new javax.swing.JTextField();
        Combo_box_team_t3 = new javax.swing.JComboBox<>();
        Button_save_new_competition_result = new javax.swing.JButton();
        Tab3_Header = new javax.swing.JLabel();
        updateAnExistingTeam = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        Combo_box_team_t4 = new javax.swing.JComboBox<>();
        Text_contact_person_t4 = new javax.swing.JTextField();
        Text_contact_phone_t4 = new javax.swing.JTextField();
        Text_contact_email_t4 = new javax.swing.JTextField();
        Tab4_Header = new javax.swing.JLabel();
        Button_Update_existing_Team = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextArea_Player_Names_t4 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        banner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image_package/GoldCoastESports_Header.jpg"))); // NOI18N

        Tab1_Header.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Tab1_Header.setText("Team Competition Results");

        compResults_jTable.setModel(compResultsTableModel);
        team_comp_results_table.setViewportView(compResults_jTable);

        Button_Display_Top_team.setText("Display Top Teams");
        Button_Display_Top_team.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_Display_Top_teamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout teamCompetitionResultsLayout = new javax.swing.GroupLayout(teamCompetitionResults);
        teamCompetitionResults.setLayout(teamCompetitionResultsLayout);
        teamCompetitionResultsLayout.setHorizontalGroup(
            teamCompetitionResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teamCompetitionResultsLayout.createSequentialGroup()
                .addGroup(teamCompetitionResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(teamCompetitionResultsLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(team_comp_results_table, javax.swing.GroupLayout.PREFERRED_SIZE, 729, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(teamCompetitionResultsLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(Tab1_Header))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, teamCompetitionResultsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Button_Display_Top_team)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        teamCompetitionResultsLayout.setVerticalGroup(
            teamCompetitionResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(teamCompetitionResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tab1_Header)
                .addGap(12, 12, 12)
                .addComponent(team_comp_results_table, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Button_Display_Top_team)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        allTabs.addTab("Team competition results", teamCompetitionResults);

        jLabel2.setText("Team Name :");

        jLabel3.setText("Contact Person :");

        jLabel4.setText("Contact Phone :");

        jLabel5.setText("Contact Email :");

        jLabel6.setText("Player Names :");

        TextArea_player_names_t2.setColumns(20);
        TextArea_player_names_t2.setRows(5);
        jScrollPane2.setViewportView(TextArea_player_names_t2);

        Button_save_new_team.setText("Add New Team");
        Button_save_new_team.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_save_new_teamActionPerformed(evt);
            }
        });

        Tab2_Header.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Tab2_Header.setText("Add new Team");

        javax.swing.GroupLayout addNewTeamLayout = new javax.swing.GroupLayout(addNewTeam);
        addNewTeam.setLayout(addNewTeamLayout);
        addNewTeamLayout.setHorizontalGroup(
            addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeamLayout.createSequentialGroup()
                .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Button_save_new_team)
                    .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(addNewTeamLayout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(Tab2_Header))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewTeamLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6))
                            .addGap(18, 18, 18)
                            .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Text_contact_person_t2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Text_team_name_t2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Text_contact_email_t2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                                        .addComponent(Text_contact_phone_t2)))))))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        addNewTeamLayout.setVerticalGroup(
            addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewTeamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tab2_Header)
                .addGap(50, 50, 50)
                .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Text_team_name_t2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Text_contact_person_t2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Text_contact_phone_t2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Text_contact_email_t2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Button_save_new_team)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        allTabs.addTab("Add new Team", addNewTeam);

        jLabel7.setText("Date :");

        jLabel8.setText("Location :");

        jLabel9.setText("Game :");

        jLabel10.setText("Team :");

        jLabel11.setText("Points :");

        Combo_box_team_t3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Button_save_new_competition_result.setText("Add New Competition Results");
        Button_save_new_competition_result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_save_new_competition_resultActionPerformed(evt);
            }
        });

        Tab3_Header.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Tab3_Header.setText("Add new competition result");

        javax.swing.GroupLayout addNewCompetitionResultLayout = new javax.swing.GroupLayout(addNewCompetitionResult);
        addNewCompetitionResult.setLayout(addNewCompetitionResultLayout);
        addNewCompetitionResultLayout.setHorizontalGroup(
            addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewCompetitionResultLayout.createSequentialGroup()
                .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(addNewCompetitionResultLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Button_save_new_competition_result))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addNewCompetitionResultLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Combo_box_team_t3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Text_points_t3)
                            .addComponent(Text_date_t3, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Text_location_t3)
                            .addComponent(Text_game_t3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addNewCompetitionResultLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(Tab3_Header)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(147, 147, 147))
        );
        addNewCompetitionResultLayout.setVerticalGroup(
            addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addNewCompetitionResultLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tab3_Header)
                .addGap(75, 75, 75)
                .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Text_date_t3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Text_location_t3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(Text_game_t3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(Combo_box_team_t3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addNewCompetitionResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(Text_points_t3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Button_save_new_competition_result)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        allTabs.addTab("Add new competition result", addNewCompetitionResult);

        jLabel12.setText("Team :");

        jLabel13.setText("Contact Person :");

        jLabel14.setText("Contact Phone :");

        jLabel15.setText("Contact Email :");

        jLabel16.setText("Player names :");

        Combo_box_team_t4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Combo_box_team_t4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Combo_box_team_t4ItemStateChanged(evt);
            }
        });

        Tab4_Header.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Tab4_Header.setText("Update an existing team");

        Button_Update_existing_Team.setText("Update existing team");
        Button_Update_existing_Team.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_Update_existing_TeamActionPerformed(evt);
            }
        });

        TextArea_Player_Names_t4.setColumns(20);
        TextArea_Player_Names_t4.setRows(5);
        jScrollPane1.setViewportView(TextArea_Player_Names_t4);

        javax.swing.GroupLayout updateAnExistingTeamLayout = new javax.swing.GroupLayout(updateAnExistingTeam);
        updateAnExistingTeam.setLayout(updateAnExistingTeamLayout);
        updateAnExistingTeamLayout.setHorizontalGroup(
            updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateAnExistingTeamLayout.createSequentialGroup()
                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(updateAnExistingTeamLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(Tab4_Header))
                    .addGroup(updateAnExistingTeamLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Button_Update_existing_Team)
                            .addGroup(updateAnExistingTeamLayout.createSequentialGroup()
                                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(Text_contact_email_t4, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                                    .addComponent(Text_contact_person_t4)
                                    .addComponent(Text_contact_phone_t4)
                                    .addComponent(Combo_box_team_t4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1))))))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        updateAnExistingTeamLayout.setVerticalGroup(
            updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateAnExistingTeamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tab4_Header)
                .addGap(50, 50, 50)
                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Combo_box_team_t4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(Text_contact_person_t4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(Text_contact_phone_t4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(Text_contact_email_t4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(updateAnExistingTeamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(Button_Update_existing_Team)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        allTabs.addTab("Update an existing team", updateAnExistingTeam);

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(banner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(allTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addComponent(banner, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(allTabs))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
// Display Team details 
    
    private void displayTeams()
    {
        //Populate the team names from teams ArrayList<Team>
        //into the 2 x JComboBoxes
        //Combo_box_team_t3
        //Combo_box_team_t4
        //checks if there are items in the combo boxes
        //if any, remove them
        if (Combo_box_team_t3.getItemCount()> 0)
        {
            Combo_box_team_t3.removeAllItems();
        }
        if (Combo_box_team_t4.getItemCount()> 0)
        {
            Combo_box_team_t4.removeAllItems();
        }
        if (Teams.size() > 0)
        {
            for (int i = 0; i< Teams.size(); i++)
            {
                Combo_box_team_t3.addItem(Teams.get(i).getTeamName());
                Combo_box_team_t4.addItem(Teams.get(i).getTeamName()); 
            }
        }
    }
            
    private void displayTeamDetails()        
    {
    int selectedIndex = 0;
        
        if (comboBoxStatus == true)
        { 
            Text_contact_person_t4.setText(Teams.get(selectedIndex).getContactName());
            Text_contact_phone_t4.setText(Teams.get(selectedIndex).getContactPhone());
            Text_contact_email_t4.setText(Teams.get(selectedIndex).getContactEmail());
            // String for all player name to be displayed
            String playerNames = "";
            // Remove the Text area info
            TextArea_Player_Names_t4.removeAll();
                        
            
            for (int i = 0; i < Teams.get(selectedIndex).getPlayerList().size(); i++)
            {
                playerNames += Teams.get(selectedIndex).getPlayerList().get(i) + "\n";                
            }
            
            TextArea_Player_Names_t4.setText(playerNames);
        }
    }
    private void Button_Display_Top_teamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_Display_Top_teamActionPerformed
     
        String leaderBoardResults = "Teams Leader Board\n\nPoints\t\tTeam\n";
        
        for (int i = 0; i < Teams.size(); i++)
        {
            String teamNameStr = Teams.get(i).getTeamName();
            int totalPoints = 0;
            
            for (int j = 0; j < competitions.size(); j++)
            {
                if (teamNameStr.equals(competitions.get(j).getTeam()))
                {
                   totalPoints += competitions.get(j).getPoints();
                }    
            }
            
            leaderBoardResults += totalPoints + " " + "\t\t" + teamNameStr + "\n";          
        }        
        JOptionPane.showMessageDialog(null, leaderBoardResults, "Leader Board", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_Button_Display_Top_teamActionPerformed

    private void Button_save_new_teamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_save_new_teamActionPerformed
        
        comboBoxStatus = false;
        
        // Introduce the text field
        String newTeamName = Text_team_name_t2.getText();
        String newContactPerson = Text_contact_person_t2.getText();
        String newContactPhone = Text_contact_phone_t2.getText();
        String newContactEmail = Text_contact_email_t2.getText();
        
        String [] newPlayerNames = null;
        
        if (! TextArea_player_names_t2.getText().isEmpty())
        {
                newPlayerNames = TextArea_player_names_t2.getText().split("\\n");
        }
        
        // Define error message depending on error cases
        String errorMessage = "ERROR(S) DETECTED \n\n";        
        boolean errorStatus = false;
        
            
            if (newTeamName.isEmpty() == true)
            {                
                errorStatus = true;
                errorMessage += " Team name is Required\n";
            }
            
            
            if (newContactPerson.isEmpty() == true)
            {                
                errorStatus = true;
                errorMessage += " Contact person is Required\n";
            }            
            
            
            if (newContactPhone.isEmpty() == true)
            {                
                errorStatus = true;
                errorMessage += " Contact phone Number is Required\n";
            }
            

            if (newContactEmail.isEmpty() == true)
            {                
                errorStatus = true;
                errorMessage += " Contact email is Required\n";
            }
            
           
            if (newPlayerNames == null)
            {                
                    errorStatus = true;
                    errorMessage += " Player names are required\n";
            }

            // ERROR Display
            if (errorStatus == true)
            {            
                javax.swing.JOptionPane.showMessageDialog(null, errorMessage, "ERROR ", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            
            else
            {
                
                // Validation pop up message
                int yesOrNo = javax.swing.JOptionPane.showConfirmDialog(
                    null, "Confirm ADD NEW TEAM : " + 
                    newTeamName + "\n to the list of availlable teams ?", "ADD NEW TEAM", 
                    javax.swing.JOptionPane.YES_NO_OPTION
                );
                
                // Console check for confirmation status
                if (yesOrNo == javax.swing.JOptionPane.NO_OPTION){                            
                    System.out.println("ADD NEW TEAM: " + newTeamName + " was Cancelled");
                }   
                
                else
                {
                    System.out.println("ADD NEW TEAM: " + newTeamName + "was added");
                                                
                            boolean teamsWriteStatus = false;
                            boolean playersWriteStatus = false;
                            
                            try
                            {
                                FileOutputStream outputStream = new FileOutputStream("teams.csv", true);
                                
                                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                                
                                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                                
                                bufferedWriter.write(newTeamName + "," + newContactPerson + "," + newContactPhone + "," + newContactEmail );
                                bufferedWriter.newLine();
                                
                                bufferedWriter.close();
                                
                                teamsWriteStatus = true;
                            }
                            
                            catch (Exception e){
                               e.printStackTrace();
                            }
                            
                            
                            try
                                {
                            FileOutputStream outputStream = new FileOutputStream("players.csv", true);
                                
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                                
                            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                                
                                for (String newPlayerName : newPlayerNames) 
                                    {
                                    bufferedWriter.write(newPlayerName + "," + newTeamName);
                                    bufferedWriter.newLine();
                                    }
                                
                            bufferedWriter.close();
                                
                            playersWriteStatus = true;
                                    }
                            catch (IOException e)
                                {                          
                                e.printStackTrace();
                                }
                            
                            
                            // Saving data from new field input
                            
                            if (teamsWriteStatus == true && playersWriteStatus == true){
                                
                                Text_team_name_t2.setText("");
                                Text_contact_person_t2.setText("");
                                Text_contact_phone_t2.setText("");
                                Text_contact_email_t2.setText("");
                                TextArea_player_names_t2.setText("");
                                
                                Teams.add(new Team(newTeamName, newContactPerson, newContactPhone, newContactEmail));
                                
                                int indexNewTeam = Teams.size() - 1;
                                
                                for (String newPlayerName : newPlayerNames) {
                                    Teams.get(indexNewTeam).setPlayer(newPlayerName);
                                }
                                              
                                displayTeamDetails();
                            }
                            
                }
            }

        comboBoxStatus = true;
    }//GEN-LAST:event_Button_save_new_teamActionPerformed

    private void Button_save_new_competition_resultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_save_new_competition_resultActionPerformed
//League of Legends,14-Jan-2022,TAFE Coomera,BioHazards,5
    //Text_game_t3 (game)
    //Text_date_t3
    //Text_location_t3
    //Combo_box_team_t3 (team combo box)
    //Text_points_t3
    
    if (validateNewCompResult() == true)
    {
        String teamName= Combo_box_team_t3.getSelectedItem().toString();
        int acceptNewCompResult = JOptionPane.showConfirmDialog(null, "You are about to save a new competition result for the team:" + teamName + "\n Do you wish to continue?", "ADD NEW COMPETITION RESULT", JOptionPane.YES_NO_OPTION);
        if (acceptNewCompResult == JOptionPane.YES_OPTION)
        {
            // add new competition result to competition ArrayList
            String game = Text_game_t3.getText();
            String competitionDate = Text_date_t3.getText();
            String location = Text_location_t3.getText();
            String team = Combo_box_team_t3.getSelectedItem().toString();
            int points = Integer.parseInt(Text_points_t3.getText());

            Competition newComp = new Competition(game, competitionDate, location, team, points);
            competitions.add(newComp);
            //Add new competition result to J Table
            displayJTable();
    }
    else
    {
    //nothing
    }

}

    }//GEN-LAST:event_Button_save_new_competition_resultActionPerformed
private boolean validateNewCompResult()
{
    boolean validation = true;
    String errorMsg = "ERROR(S) DETECTED\n";
    if (Text_date_t3.getText().length() == 0)
    {
        validation = false;
        errorMsg += "Competition date is required\n";
    }
    
    if (Text_location_t3.getText().length() == 0)
    {
        validation = false;
        errorMsg += "Location is required\n";
    }
    if (Text_game_t3.getText().length() == 0)
    {
        validation = false;
        errorMsg += "Game is required\n";
    }
//    Text_points_t3
    if (Text_points_t3.getText().length() == 0)
    {
        validation = false;
        errorMsg += "Points are required\n";
    }
    else
    {
        try
        {
            int points = Integer.parseInt(Text_points_t3.getText());
            if (points <= 0)
            {
                validation = false;
                errorMsg += "Points must greater than 0\n";
            }
        }
        catch (Exception e)
        {
            validation = false;
            errorMsg += "Points must be numeric\n";
        }
    }

    
    if (validation == false)
    {
    JOptionPane.showMessageDialog(null, errorMsg, "ERRORS DETECTED", JOptionPane.ERROR_MESSAGE);
    }
    return validation;
}
    
    
    
    private void Button_Update_existing_TeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_Update_existing_TeamActionPerformed
        
    if (validateNewCompResult() == true)
    {
        // add new competition result to competitions ArrayList
        
        //add new competition result to J Table 
    }   
    

    }//GEN-LAST:event_Button_Update_existing_TeamActionPerformed

    
    private void Combo_box_team_t4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Combo_box_team_t4ItemStateChanged
        if (comboBoxStatus == true)
            {
                displayTeamDetails();
            }
    }//GEN-LAST:event_Combo_box_team_t4ItemStateChanged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
//yesOrNo variable for user's selection
    int yesOrNo = JOptionPane.showConfirmDialog(null,"Do you wish to save changes before closing?", "SAVE CHANGES?", JOptionPane.YES_NO_OPTION);
    if (yesOrNo == JOptionPane.YES_OPTION)   
    {
        //exit and save changes
        saveCompetitionData();
        //save team data
        saveTeamData();
    }
    else
    {
       System.exit(0);
      //exit and don't save changes  
    }
        
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(GC_ESports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GC_ESports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GC_ESports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GC_ESports_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GC_ESports_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_Display_Top_team;
    private javax.swing.JButton Button_Update_existing_Team;
    private javax.swing.JButton Button_save_new_competition_result;
    private javax.swing.JButton Button_save_new_team;
    private javax.swing.JComboBox<String> Combo_box_team_t3;
    private javax.swing.JComboBox<String> Combo_box_team_t4;
    private javax.swing.JLabel Tab1_Header;
    private javax.swing.JLabel Tab2_Header;
    private javax.swing.JLabel Tab3_Header;
    private javax.swing.JLabel Tab4_Header;
    private javax.swing.JTextArea TextArea_Player_Names_t4;
    private javax.swing.JTextArea TextArea_player_names_t2;
    private javax.swing.JTextField Text_contact_email_t2;
    private javax.swing.JTextField Text_contact_email_t4;
    private javax.swing.JTextField Text_contact_person_t2;
    private javax.swing.JTextField Text_contact_person_t4;
    private javax.swing.JTextField Text_contact_phone_t2;
    private javax.swing.JTextField Text_contact_phone_t4;
    private javax.swing.JTextField Text_date_t3;
    private javax.swing.JTextField Text_game_t3;
    private javax.swing.JTextField Text_location_t3;
    private javax.swing.JTextField Text_points_t3;
    private javax.swing.JTextField Text_team_name_t2;
    private javax.swing.JPanel addNewCompetitionResult;
    private javax.swing.JPanel addNewTeam;
    private javax.swing.JTabbedPane allTabs;
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JLabel banner;
    private javax.swing.JTable compResults_jTable;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel teamCompetitionResults;
    private javax.swing.JScrollPane team_comp_results_table;
    private javax.swing.JPanel updateAnExistingTeam;
    // End of variables declaration//GEN-END:variables
}
