package bootstrap;


import domain.Board;
import domain.BoardBuilder;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.EnhancedPatternLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Driver {
    public static Logger logger = LoggerFactory.getLogger(Driver.class);

    public static void main(String args[]) {
        configureLogging("snakesAndLadders.log", "INFO");
        logger.info("Starting snakesAndLadders");

        int choice;
        String serializedFileName = "snakesAndLadders.dat";
        Scanner sc = new Scanner(System.in);
        Board board = (new BoardBuilder()).withStartFlag(0).withNoOfPlayers(0).withCurrentPlayerTurn(0).withShutDownFlag(0).build();


        while (true) {
            System.out.println("1)Play new game");
            System.out.println("2)Continue previous game");
            if (sc.nextInt() == 1) {
                board = (new BoardBuilder()).withStartFlag(0).withNoOfPlayers(0).withCurrentPlayerTurn(0).withShutDownFlag(0).build();
                logger.info("Starting new game");
                break;
            } else {
                try {
                    board = board.deSerialize(serializedFileName);
                    board.setShutDownFlag(0);
                } catch (Exception e) {
                    System.out.println("No previous game instance was found");
                    logger.warn("Exception found " + e.toString());
                    logger.info("No previous game instance was found");
                }
                if (board == null)
                    continue;
                else
                    break;
            }
        }

        while (board.getStartFlag() == 0 && board.getNoOfPlayers() < 2) {
            System.out.println("\n#####SNAKES AND LADDERS#####\n");
            System.out.println("Enter number of players(greater than 2)");
            board.setNoOfPlayers(sc.nextInt());
            board.enrollPlayers();
            board.placeSnakesAndLadders();
            board.setStartFlag(1);
            logger.info("Total number of players " + board.getNoOfPlayers());
        }

        do {
            System.out.println();
            System.out.println("#######################################");
            System.out.println("1)Add player");
            System.out.println("2)Show current positions of players");
            System.out.println("3)Show positions of snakes and ladders");
            System.out.println("4)Roll Dice");
            System.out.println("5)Show ranking");
            System.out.println("6)Exit");
            System.out.println("#######################################");
            System.out.println("Choose an option");
            System.out.println();
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    board.addPlayer();
                    logger.info("New player added \n Total number of players " + board.getNoOfPlayers());
                    break;
                case 2:
                    board.showPlayerPositions();
                    break;
                case 3:
                    board.showSnakesAndLadders();
                    break;
                case 4:
                    board.rollDice();
                    board.setCurrentPlayerTurn(board.getCurrentPlayerTurn() + 1);
                    break;
                case 5:
                    board.showRanking();
                    break;
                case 6:
                    try {
                        board.shutDown(serializedFileName);
                    } catch (Exception e) {
                        System.out.println(e);
                        logger.warn("Exception found " + e.toString());
                        logger.info("Could not save game");
                    }
                    break;

                default:
                    System.out.println("Invalid option choose again");
            }


        } while (choice != 6 && board.getShutDownFlag() == 0);

    }

    public static String configureLogging(String logFile, String logLevel) {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();

        switch (logLevel) {
            case "DEBUG": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.DEBUG_INT));
            }
            case "WARN": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.WARN_INT));
            }
            case "ERROR": {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.ERROR_INT));
            }
            default: {
                dailyRollingFileAppender.setThreshold(Level.toLevel(Priority.INFO_INT));
            }
            break;
        }

        System.out.println("Log files written out at " + logFile);
        dailyRollingFileAppender.setFile(logFile);
        dailyRollingFileAppender.setLayout(new EnhancedPatternLayout("%d [%t] %-5p %c - %m%n"));

        dailyRollingFileAppender.activateOptions();
        org.apache.log4j.Logger.getRootLogger().addAppender(dailyRollingFileAppender);
        return dailyRollingFileAppender.getFile();
    }
}




