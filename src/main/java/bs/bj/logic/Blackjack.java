package bs.bj.logic;

import java.util.Scanner;

/**
 * Created by boubdyk on 21.10.2015.
 */
public class Blackjack {
    public static void main(String[] args) {
        System.out.println("Welcome!");

        Deck playingDeck = new Deck();
        playingDeck.createDeck();
        playingDeck.shuffle();

        //create a deck for the player
        Deck playerDeck = new Deck();

        //create a deck for the player
        Deck dealerDeck = new Deck();

        double playerMoney = 100.00;

        Scanner userInput = new Scanner(System.in);

        while(playerMoney > 0) {
            System.out.println("You have $" + playerMoney + ", how much would you like to bet?");
            double playerBet = userInput.nextDouble();
            if (playerBet > playerMoney) {
                System.out.println("You cannot bet more than you have.");
                break;
            }

            boolean endRound = false;

            //Start dealing
            //Player gets two cards
            playerDeck.draw(playingDeck);
            playerDeck.draw(playingDeck);

            //Dealer gets two cards
            dealerDeck.draw(playingDeck);
            dealerDeck.draw(playingDeck);

            while(true) {
                System.out.println("Your hand:\n" + playerDeck.toString());
                System.out.println("Your hand is valued at: " + playerDeck.cardsValue());

                //Display dealer hand
                System.out.println("Dealer hand: " + dealerDeck.getCard(0).toString() + " and [Hidden]");

                //See if player has Blackjack
                if (playerDeck.cardsValue() == 21) {
                    if (playerDeck.cardsValue() == dealerDeck.cardsValue()) {
                        System.out.println("Push");
                    } else {
                        System.out.println("You have Blackjack! You win.");
                        playerMoney += playerBet * 3 / 2;
                    }
                    endRound = true;
                    break;
                }

                //What does the player want to do?
                System.out.println("Would you like to (1)Hit or (2)Stand?");
                int response = userInput.nextInt();

                //They hit
                if (response == 1) {
                    playerDeck.draw(playingDeck);
                    System.out.println("You draw a: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());

                    //Bust if > 21
                    if (playerDeck.cardsValue() > 21) {
                        System.out.println("Bust. Currently valued at: " + playerDeck.cardsValue());
                        playerMoney -= playerBet;
                        endRound = true;
                        break;
                    }
                }

                if (response == 2) {
                    break;
                }
            }

            //Reveal dealer cards
            System.out.println("Dealer cards: " + dealerDeck.toString());

            //See if dealer has more points than player
            if ((dealerDeck.cardsValue() > playerDeck.cardsValue()) && !endRound) {
                System.out.println("Dealer beats you!");
                playerMoney -= playerBet;
                endRound = true;
            }

            //Dealer draws at 16, stand at 17
            while ((dealerDeck.cardsValue() < 17) && !endRound) {
                dealerDeck.draw(playingDeck);
                System.out.println("Dealer draws: " + dealerDeck.getCard(dealerDeck.deckSize() - 1).toString());
            }

            //Display total value for dealer
            System.out.println("Dealer's hand is valued at: " + dealerDeck.cardsValue());

            //Determine if dealer busted
            if ((dealerDeck.cardsValue() > 21) && !endRound) {
                System.out.println("Dealer busts! You win.");
                playerMoney += playerBet;
                endRound = true;
            }

            //Determine if push
            if ((playerDeck.cardsValue() == dealerDeck.cardsValue()) && !endRound) {
                System.out.println("Push.");
                endRound = true;
            }

            if ((playerDeck.cardsValue() > dealerDeck.cardsValue()) && !endRound) {
                System.out.println("You win the hand!");
                playerMoney += playerBet;
                endRound = true;
            } else if (!endRound){
                System.out.println("You lose the hand.");
                playerMoney -= playerBet;
                endRound = true;
            }

            playerDeck.moveAllToDeck(playingDeck);
            dealerDeck.moveAllToDeck(playingDeck);
            System.out.println("End of hand.");
        }

        System.out.println("Game over! You are out of momey. :(");

        //System.out.println(playingDeck.toString());
    }
}
