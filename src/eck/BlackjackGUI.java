package eck;



import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/**
 * In this program, the user plays a game of Blackjack.  The
 * computer acts as the dealer.  The user plays by clicking
 * "Hit!" and "Stand!" buttons.
 * 
 * This program depends on the following classes:  Card, Hand,
 * BlackjackHand, Deck, CardPanel
 */

public class BlackjackGUI extends JPanel {
   
   /**
    * The main routine simply opens a window that shows a BlackjackGUI.
    */
   public static void main(String[] args) {
	   JFrame window = new JFrame("Blackjack");
	   BlackjackGUI content = new BlackjackGUI();
	   window.setContentPane(content);
	   window.pack();  // Set size of window to preferred size of its contents.
	   window.setResizable(false);  // User can't change the window's size.
	   Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	   window.setLocationRelativeTo(null);
	   window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	   window.setVisible(true);
	   System.out.println("c'est fait");
   }
      
   
   /**
    * The constructor lays out the panel.  A CardPanel occupies the CENTER 
    * position of the panel (where CardPanel is a subclass of JPanel that is 
    * defined below).  On the bottom is a panel that holds three buttons.  
    */
   public BlackjackGUI() {
      
      setBackground( new Color(130,50,40) );
      setLayout( new BorderLayout(3,3) );
      
      final CardPanel board = new CardPanel();
      add(board, BorderLayout.CENTER);
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground( new Color(220,200,180) );
      add(buttonPanel, BorderLayout.SOUTH);
      
      JButton hitButton = new JButton( "Hit!" );
      hitButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			board.doHit();
		}
      });
      buttonPanel.add(hitButton);
      
      JButton standButton = new JButton( "Stand!" );
      standButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			board.doStand();
		}
      });
      buttonPanel.add(standButton);
      
      JButton newGame = new JButton( "New Game" );
      newGame.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			board.doNewGame();
		}
      });
      buttonPanel.add(newGame);
      
//      final JCheckBox displayCalculations = new JCheckBox("calculs");
//      displayCalculations.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			if (displayCalculations.isSelected()) {
//				System.out.println("-- checked --");
//			}
//			else {
//				System.out.println("-- unchecked --");
//			}
//		}
//      });
//      buttonPanel.add(displayCalculations);

      setBorder(BorderFactory.createLineBorder( new Color(130,50,40), 3) );
      
   }  // end constructor
   
} // end class BlackjackGUI
