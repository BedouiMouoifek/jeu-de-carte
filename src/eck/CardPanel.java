package eck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class CardPanel extends JPanel {
    
    Deck deck;         // A deck of cards to be used in the game.
    
    BlackjackHand dealerHand;   // Hand containing the dealer's cards.
    BlackjackHand playerHand;   // Hand containing the user's cards.
    
    String message;  // A message drawn on the canvas, which changes
                     //    to reflect the state of the game.
    
    boolean gameInProgress;  // Set to true when a game begins and to false
                             //   when the game ends.
    
    Font bigFont;      // Font that will be used to display the message.
    Font smallFont;    // Font that will be used to draw the cards.
    
    
    /**
     * The constructor creates the fonts and starts the first game.
     * It also sets a preferred size of 460-by-310 for the panel.
     * The paintComponent() method assumes that this is in fact the
     * size of the panel (although it can be a little taller with
     * no bad effect).
     */
    CardPanel() {
       setPreferredSize( new Dimension(460,310) );
       setBackground( new Color(0,120,0) );
       smallFont = new Font("SansSerif", Font.PLAIN, 12);
       bigFont = new Font("Serif", Font.BOLD, 16);
       doNewGame();
    }
    
   
    /**
     * This method is called when the user clicks the "Hit!" button.  First 
     * check that a game is actually in progress.  If not, give  an error 
     * message and exit.  Otherwise, give the user a card.  The game can end 
     * at this point if the user goes over 21 or if the user has taken 5 cards 
     * without going over 21.
     */
    void doHit() {
       if (gameInProgress == false) {
          message = "Click \"New Game\" to start a new game.";
          repaint();
          return;
       }
       playerHand.addCard( deck.dealCard() );
       if ( playerHand.getBlackjackValue() > 21 ) {
          message = "You've busted!  Sorry, you lose.";
          gameInProgress = false;
       }
       else if (playerHand.getCardCount() == 5) {
          message = "You win by taking 5 cards without going over 21.";
          gameInProgress = false;
       }
       else {
          message = "You have " + playerHand.getBlackjackValue() + ".  Hit or Stand?";
       }
       repaint();
    }
    
    
    /**
     * This method is called when the user clicks the "Stand!" button.
     * Check whether a game is actually in progress.  If it is, the game 
     * ends.  The dealer takes cards until either the dealer has 5 cards 
     * or more than 16 points.  Then the  winner of the game is determined. 
     */
    void doStand() {
       if (gameInProgress == false) {
          message = "Click \"New Game\" to start a new game.";
          repaint();
          return;
       }
       gameInProgress = false;
       while (dealerHand.getBlackjackValue() <= 16 && dealerHand.getCardCount() < 5)
          dealerHand.addCard( deck.dealCard() );
       if (dealerHand.getBlackjackValue() > 21)
          message = "You win!  Dealer has busted with " + dealerHand.getBlackjackValue() + ".";
       else if (dealerHand.getCardCount() == 5)
          message = "Sorry, you lose.  Dealer took 5 cards without going over 21.";
       else if (dealerHand.getBlackjackValue() > playerHand.getBlackjackValue())
          message = "Sorry, you lose, " + dealerHand.getBlackjackValue()
                      + " to " + playerHand.getBlackjackValue() + ".";
       else if (dealerHand.getBlackjackValue() == playerHand.getBlackjackValue())
          message = "Sorry, you lose.  Dealer wins on a tie.";
       else
          message = "You win, " + playerHand.getBlackjackValue()
                      + " to " + dealerHand.getBlackjackValue() + "!";
       repaint();
    }
    
    
    /**
     * Called by the constructor, and called by actionPerformed() if  the 
     * user clicks the "New Game" button.  Start a new game.  Deal two cards 
     * to each player.  The game might end right then  if one of the players 
     * had blackjack.  Otherwise, gameInProgress is set to true and the game 
     * begins.
     */
    void doNewGame() {
       if (gameInProgress) {
             // If the current game is not over, it is an error to try
             // to start a new game.
          message = "You still have to finish this game!";
          repaint();
          return;
       }
       deck = new Deck();   // Create the deck and hands to use for this game.
       dealerHand = new BlackjackHand();
       playerHand = new BlackjackHand();
       deck.shuffle();
       dealerHand.addCard( deck.dealCard() );  // Deal two cards to each player.
       dealerHand.addCard( deck.dealCard() );
       playerHand.addCard( deck.dealCard() );
       playerHand.addCard( deck.dealCard() );
       if (dealerHand.getBlackjackValue() == 21) {
          message = "Sorry, you lose.  Dealer has Blackjack.";
          gameInProgress = false;
       }
       else if (playerHand.getBlackjackValue() == 21) {
          message = "You win!  You have Blackjack.";
          gameInProgress = false;
       }
       else {
          message = "You have " + playerHand.getBlackjackValue() + ".  Hit or stand?";
          gameInProgress = true;
       }
       repaint();
    }  // end newGame();
    
    
    /**
     * The paint method shows the message at the bottom of the
     * canvas, and it draws all of the dealt cards spread out
     * across the canvas.
     */
    public void paintComponent(Graphics g) {
       
       super.paintComponent(g); // fill with background color.
       
       g.setFont(bigFont);
       g.setColor(Color.GREEN);
       g.drawString(message, 10, getHeight() - 10);
       
       // Draw labels for the two sets of cards.
       
       g.drawString("Dealer's Cards:", 10, 23);
       g.drawString("Your Cards:", 10, 153);
       
       // Draw dealer's cards.  Draw first card face down if
       // the game is still in progress,  It will be revealed
       // when the game ends.
       
       g.setFont(smallFont);
       if (gameInProgress)
          drawCard(g, null, 10, 30);
       else
          drawCard(g, dealerHand.getCard(0), 10, 30);
       for (int i = 1; i < dealerHand.getCardCount(); i++)
          drawCard(g, dealerHand.getCard(i), 10 + i * 90, 30);
       
       // Draw the user's cards.
       
       for (int i = 0; i < playerHand.getCardCount(); i++)
          drawCard(g, playerHand.getCard(i), 10 + i * 90, 160);
       
    }  // end paint();
    
    
    /**
     * Draws a card as a 80 by 100 rectangle with upper left corner at (x,y).
     * The card is drawn in the graphics context g.  If card is null, then
     * a face-down card is drawn.  (The cards are rather primitive!)
     */
    void drawCard(Graphics g, Card card, int x, int y) {
       if (card == null) {  
             // Draw a face-down card
          g.setColor(Color.blue);
          g.fillRect(x+10, y + 10,65,90);
          g.setColor(Color.white);
          g.drawRect(x+10, y + 10, 65,90);
       }
       else {
			// image generation
			String filename = "media/cards_gif/" + card.getCardToDisplay() + ".gif";
			ImageIcon imageIcon = new ImageIcon(filename);
			Image image = imageIcon.getImage();
			Integer height = (int) ( (double) image.getHeight(this)/2.8 );
			Integer width =  (int) ( (double) image.getWidth(this)/2.8 ) ;
			g.drawImage(image, x, y, width, height,this);
       }
    }  // end drawCard()
    
    
    
 
    
 } // end nested class CardPanel
