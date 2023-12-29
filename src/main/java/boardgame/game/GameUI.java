package game;
import mancala.UserProfile;
import mancala.Saver;
import java.io.File;
import mancala.Player;
import mancala.KalahRules;
import java.io.IOException;
import mancala.NoSuchPlayerException;
import mancala.PitNotFoundException;
import mancala.AyoRules;
import mancala.GameNotOverException;
import mancala.GameRules;
import mancala.MancalaGame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import java.awt.Container;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;


public class GameUI extends JFrame {
   private GameRules theGame;
   private MancalaGame mancalaGame;
   private JLabel currentPlayerLabel;
   private Player player1;
   private Player player2;
   private JButton[] pitButtons;
   private JButton storeOneButton;
   private JButton storeTwoButton;
   private JLabel storeLabel1;
   private JLabel storeLabel2;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 400;
   String player1Name;
   String player2Name;

   public GameUI() {
       super("Mancala Game");
       chooseGame();
       initComponents();
       addMenuBar();
       setLayout();
       setSize(WIDTH, HEIGHT);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       pack();
       setLocationRelativeTo(null);
       setVisible(true);
   }


   private void chooseGame() {
       final String[] gameOptions = {"Kalah", "Ayo"};
       final int choice = JOptionPane.showOptionDialog(this, "Choose a game:", "Game Selection",
       JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameOptions, gameOptions[0]);


       if (choice == JOptionPane.CLOSED_OPTION) {
           return; // Exiting the application if the user canceled
       }
      
        player1Name = JOptionPane.showInputDialog(this, "Enter Player 1's name:");
        player2Name = JOptionPane.showInputDialog(this, "Enter Player 2's name:");

       final UserProfile player1Profile = new UserProfile(player1Name, 0, 0, 0, 0);
       final UserProfile player2Profile = new UserProfile(player2Name, 0, 0, 0, 0);
      
       switch (choice) {
           case 0:
               theGame = new KalahRules();
               player1Profile.addKalahGamesPlayed();
               player2Profile.addKalahGamesPlayed();
               break;
           case 1:
               theGame = new AyoRules();
               player1Profile.addAyoGamesPlayed();
               player2Profile.addAyoGamesPlayed();
               break;
           default:
            JOptionPane.showMessageDialog(this, "Invalid choice");
           return; 
       }
      
       player1 = new Player(player1Name);
       player2 = new Player(player2Name);
      
       player1.setProfile(player1Profile);
       player2.setProfile(player2Profile);
      
       mancalaGame = new MancalaGame(theGame);
       mancalaGame.setPlayers(player1, player2);
   }

   private void addMenuBar() {
       final JMenuBar menuBar = new JMenuBar();
       final JMenu fileMenu = new JMenu("Menu");

       final JMenuItem newGameItem = new JMenuItem("New Game");
       final JMenuItem quitGameItem = new JMenuItem("Quit Game");
       final JMenuItem exitItem = new JMenuItem("Exit");
       final JMenuItem saveGameItem = new JMenuItem("Save Game");
       final JMenuItem loadGameItem = new JMenuItem("Load Game");
       final JMenuItem loadPlayerItem = new JMenuItem("Load Profile");
       final JMenuItem savePlayerItem = new JMenuItem("Save Profile");       

       newGameItem.addActionListener(e -> newGame());
       quitGameItem.addActionListener(e -> quitGame());
       exitItem.addActionListener(e -> exitApplication());
       saveGameItem.addActionListener(e -> saveGame());
       loadGameItem.addActionListener(e -> loadSavedGame());
       loadPlayerItem.addActionListener(e -> loadSavedProfile());
       savePlayerItem.addActionListener(e -> saveProfile());

       fileMenu.add(newGameItem);
       fileMenu.add(quitGameItem);
       fileMenu.add(saveGameItem);
       fileMenu.add(loadGameItem);
       fileMenu.add(exitItem);
       fileMenu.add(savePlayerItem);
       fileMenu.add(loadPlayerItem);
       menuBar.add(fileMenu);
       setJMenuBar(menuBar);
   }

   private void exitApplication() {
    final int exitChoice = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit mancala?",
               "Exit", JOptionPane.YES_NO_OPTION);
       if (exitChoice == JOptionPane.YES_OPTION) {
           System.exit(0);
       }
   }

   private void quitGame() {
    final int quitChoice = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?", "Quit Game",
               JOptionPane.YES_NO_OPTION);
       if (quitChoice == JOptionPane.YES_OPTION) {
           // Reset or exit the game as needed
           chooseGame();
           currentPlayerLabel.setText("Current Player: " + mancalaGame.getCurrentPlayer());
           updateLabels();
       }
   }

    private void newGame() {
        final  int newChoice = JOptionPane.showConfirmDialog(this, "Are you sure you want to start a new game?", "New Game",
            JOptionPane.YES_NO_OPTION);
        if (newChoice == JOptionPane.YES_OPTION) {
        // Reset or exit the game as needed
        chooseGame();
        currentPlayerLabel.setText("Current Player: " + mancalaGame.getCurrentPlayer());
        updateLabels();
        }
    }

    private void initComponents() {
        currentPlayerLabel = new JLabel("Current Player: " + mancalaGame.getCurrentPlayer());
        pitButtons = new JButton[12];
        for (int i = 0; i<6; i++) {
            final int thePit=6-i;
            pitButtons[i] = new JButton("Pit " + thePit + "\nStones: 4");
            pitButtons[i].addActionListener(e -> dealWithPitButton(thePit));
       }
       for(int i=6;i<12;i++){
            final int aNum=i+1;
            pitButtons[i] = new JButton("Pit " + aNum + "\nStones: 4");
            pitButtons[i].addActionListener(e -> dealWithPitButton(aNum));
       }
        
       storeOneButton=new JButton("View Userprofile");
       storeOneButton.addActionListener(e -> viewUserProfile(player1));

       storeTwoButton=new JButton("View Userprofile");
       storeTwoButton.addActionListener(e -> viewUserProfile(player2));

       try{
           storeLabel1 = new JLabel("Stones: " + mancalaGame.getStoreCount(player1));
           storeLabel2 = new JLabel("Stones: " + mancalaGame.getStoreCount(player2));
      
       }catch (NoSuchPlayerException e){
        JOptionPane.showMessageDialog(this, "No Such Player Exception. Try again.");
       }
   }

   private void viewUserProfile(Player player) {
        JOptionPane.showMessageDialog(this, player.getProfile().toString(), "User Profile", JOptionPane.INFORMATION_MESSAGE);
        //JOptionPane.showMessageDialog(this, player.);
    }



   private void updateLabels() {
       int stonesInStore1=0;
       int stonesInStore2=0;

       currentPlayerLabel.setText("Current Player: " + mancalaGame.getCurrentPlayer());
       
       try{
           stonesInStore1=mancalaGame.getStoreCount(player1);
           storeLabel1.setText("Stones: "+ stonesInStore1);
           stonesInStore2=mancalaGame.getStoreCount(player2);
           storeLabel2.setText("Stones: "+ stonesInStore2);
       }catch (NoSuchPlayerException e){
            JOptionPane.showMessageDialog(this, "No Such Player Exception. Try again.");
       }

       for (int i = 0; i<6; i++) {
            final int thePit=6-i;
            try{
                pitButtons[i].setText("Pit " + thePit + "\nStones: "+ mancalaGame.getNumStones(thePit));
            }catch(PitNotFoundException e){
                JOptionPane.showMessageDialog(this, "Pit Not Found Exception. Try again.");
            }
        }
        for(int i=6;i<12;i++){
            final int aNum=i+1;
            try{
                pitButtons[i].setText("Pit " + aNum + "\nStones: " + mancalaGame.getNumStones(aNum));
            }catch(PitNotFoundException e){
                JOptionPane.showMessageDialog(this, "Pit Not Found Exception. Try again.");
            }
        }
   }

   private void setLayout() {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        final JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(2, 6));
      
        for (int i = 0; i < 12; i++) {
            boardPanel.add(pitButtons[i]);
        }

       contentPane.add(currentPlayerLabel, BorderLayout.NORTH);
       contentPane.add(boardPanel, BorderLayout.CENTER);

       final JPanel leftStorePanel = new JPanel();
       leftStorePanel.setLayout(new BoxLayout(leftStorePanel, BoxLayout.Y_AXIS));
       leftStorePanel.add(new JLabel("Store 1"));
       leftStorePanel.add(storeLabel1);
       leftStorePanel.add(storeOneButton);

       final JPanel rightStorePanel = new JPanel();
       rightStorePanel.setLayout(new BoxLayout(rightStorePanel, BoxLayout.Y_AXIS));
       rightStorePanel.add(new JLabel("Store 2"));
       rightStorePanel.add(storeLabel2);
       rightStorePanel.add(storeTwoButton);

       contentPane.add(leftStorePanel, BorderLayout.WEST);
       contentPane.add(rightStorePanel, BorderLayout.EAST);

       boardPanel.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e) {
               updateLabels();
           }
       });
   }

   private void dealWithPitButton(final int pitIndex) {
        int playerNum=0;
        Player player= mancalaGame.getCurrentPlayer();
        if(player==player1){
           playerNum=1;
        }
        else if(player==player2){
           playerNum=2;
        }
        try{
            mancalaGame.move(pitIndex);
        } catch(PitNotFoundException e){
            JOptionPane.showMessageDialog(this, "Pit Not found. Try again.");
        }
        updateLabels();

       if (mancalaGame.isGameOver()) {
        try{
            if (mancalaGame.getWinner()!=null) {
                Player holdCurrent=mancalaGame.getWinner();
                // Increment the win counter for the current player's game type
                if (theGame instanceof KalahRules) {
                    holdCurrent.getProfile().addKalahGamesWon();
                } else if (theGame instanceof AyoRules) {
                    holdCurrent.getProfile().addAyoGamesWon();       
                }
                JOptionPane.showMessageDialog(this, holdCurrent + " has won!!");
            }
            else{
                JOptionPane.showMessageDialog(this, "Game ended in a tie");
            }
        }catch (GameNotOverException e){
            JOptionPane.showMessageDialog(this, "Game has not ended");
        }
        
           int playAgainChoice = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again",
                   JOptionPane.YES_NO_OPTION);
           if (playAgainChoice == JOptionPane.YES_OPTION) {
            chooseGame();
            currentPlayerLabel.setText("Current Player: " + mancalaGame.getCurrentPlayer());
            updateLabels();
           } else {
               System.exit(0);
           }
       }
   }
   private void saveGame() {
       try{
        final String fileName = JOptionPane.showInputDialog(this, "Enter the file name to save the game:");
        Saver.saveObject(mancalaGame, fileName);
       } catch (IOException e) {
           System.out.println("Exception Occured");
       }
   }

   private void loadSavedGame() {

        try {
            JFileChooser fileChooser = new JFileChooser("assets/");
            int result = fileChooser.showOpenDialog(this);
    
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getName();
    
                final MancalaGame loadedGame = (MancalaGame) Saver.loadObject(fileName);
                mancalaGame = loadedGame;
                player1 = mancalaGame.getPlayers().get(0);
                player2 = mancalaGame.getPlayers().get(1);
                updateLabels();
            } else {
                JOptionPane.showMessageDialog(this, "Game loading canceled.");
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading game.");
        }
        
    }

    private void saveProfile() {
        try {
            final String fileName = JOptionPane.showInputDialog(this, "Enter the file name to save the profile:");
            final Player currentPlayer = mancalaGame.getCurrentPlayer();
            Saver.saveObject(currentPlayer.getProfile(), fileName);
            JOptionPane.showMessageDialog(this, "User profile saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user profile.");
        }
    }

   
    private void loadSavedProfile() {

        final Player currentPlayer = mancalaGame.getCurrentPlayer();
        try {
            JFileChooser fileChooser = new JFileChooser("assets/");
            int result = fileChooser.showOpenDialog(this);
    
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String fileName = selectedFile.getName();

                UserProfile loadedProfile = (UserProfile) Saver.loadObject(fileName);
                currentPlayer.setProfile(loadedProfile);

                if (currentPlayer == player1) {
                    player1Name = currentPlayer.getProfile().getName();
                } else if (currentPlayer == player2) {
                    player2Name = currentPlayer.getProfile().getName();
                }
    
                JOptionPane.showMessageDialog(this, "User profile loaded successfully.");
                updateLabels();
            } else {
                JOptionPane.showMessageDialog(this, "Profile loading canceled.");
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading user profile.");
        }
        
    }

   public static void main(String[] args) {
       SwingUtilities.invokeLater(() -> {
           new GameUI();
       });
   }

}
