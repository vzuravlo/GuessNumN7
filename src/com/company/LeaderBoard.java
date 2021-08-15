package com.company;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class LeaderBoard {

    static final ArrayList <GameResult> leaderboard = new ArrayList <>();
    static final File leaderBoardFile = new File("Leaderboard.txt");

    static void saveFile() {

        try (var out = new PrintWriter(leaderBoardFile)) {

            for (var r : leaderboard) {
                out.printf("%d %s %d %d\n", r.getStartTime(), r.getName(), r.getAttempts(), r.getDuration());
            }
        } catch (IOException e) {
            System.out.println("Sorry, cannot save leaderboard");
        }
    }


//original variant
//    static void printTable() {
//
//        leaderboard.sort(Comparator.comparingInt(GameResult::getAttempts).thenComparingLong(GameResult::getDuration));
//        System.out.println("Our leaderboard:");
//        for (var res : leaderboard) {
//            System.out.printf("%1$te %1$tB %1$tY, %1$tH:%1$tM %2$s %3$d %4$.1f\n", res.getStartTime(), res.getName(), res.getAttempts(), res.getDuration() / 1000.0);
//        }
//    }

    static void add(String userName, int attempts, long startTime, long endTime) {

        var result = new GameResult();
        result.setName(userName);
        result.setAttempts(attempts + 1);
        result.setStartTime(startTime);
        result.setDuration(endTime - startTime);
        leaderboard.add(result);
    }

    static void readFile() {

        if (! leaderBoardFile.exists()) {
            System.out.println("You are first player!");
            return;
        }

        try (var in = new Scanner(leaderBoardFile)) {

            while (in.hasNext()) {
                var result = new GameResult();
                result.setStartTime(in.nextLong());
                result.setName(in.next());
                result.setAttempts(in.nextInt());
                result.setDuration(in.nextLong());
                leaderboard.add(result);

            }

        } catch (IOException e) {
            System.out.println("Sorry, cannot read file Leaderboard.txt");
        }
    }


    //variant 1
//    static void printTable() {
//
//        leaderboard.sort(Comparator.comparingInt(GameResult::getAttempts).thenComparingLong(GameResult::getDuration));
//        System.out.println("Our leaderboard:");
//        int rows = 5;
//
//        for (var res : leaderboard) {
//            rows--;
//            if (rows < 0) {
//                return;
//            }
//            System.out.printf("%1$te %1$tB %1$tY, %1$tH:%1$tM %2$s %3$d %4$.1f\n", res.getStartTime(), res.getName(), res.getAttempts(), res.getDuration() / 1000.0);
//        }
//    }


//    variant 2
//    static void printTable() {
//
//        leaderboard.sort(Comparator.comparingInt(GameResult::getAttempts).thenComparingLong(GameResult::getDuration));
//        System.out.println("Our leaderboard:");
//
//
//        var sublist = leaderboard.subList(0,Math.min(5, leaderboard.size()));
//        for (var res : sublist) {
//            System.out.printf("%1$te %1$tB %1$tY, %1$tH:%1$tM %2$s %3$d %4$.1f\n",
//                    res.getStartTime(),
//                    res.getName(),
//                    res.getAttempts(),
//                    res.getDuration() / 1000.0);
//        }
//    }

    //    variant 3
//    static void printTable() {
//
//        leaderboard.sort(Comparator.comparingInt(GameResult::getAttempts).thenComparingLong(GameResult::getDuration));
//        System.out.println("Our leaderboard:");
//
//
//        for (var i=0; i<5 && i < leaderboard.size(); i++) {
//            var res = leaderboard.get(i);
//            System.out.printf("%1$te %1$tB %1$tY, %1$tH:%1$tM %2$s %3$d %4$.1f\n",
//                    res.getStartTime(),
//                    res.getName(),
//                    res.getAttempts(),
//                    res.getDuration() / 1000.0);
//        }
//    }

    // variant 4
    static void printTable() {

        String fCol1 = " %1$te %1$tB %1$tY, %1$tH:%1$tM ";
        String fCol2 = " %2$s ";
        String fCol3 = "%3$d ";
        String fCol4 = " %4$.1fs ";
        System.out.println("Our leaderboard:");

        String[] headers = {"When", "Who", "Attempts", "Time"};

        int width1 = headers[0].length();
        for (GameResult res : leaderboard) {
            int temp1 = String.format(fCol1, res.getStartTime()).length();
            if (width1 < temp1){
                width1 = temp1;
            }
        }

        String myDivLine = "+";
        String headerLine = "|";
        myDivLine = myDivLine + divLine(width1, "-");

        headerLine = headerLine + divLine(width1 - headers[0].length(), " ") + headers[0] + " ";


        int width2 = headers[1].length();
        for (GameResult res : leaderboard) {
            int temp2 = res.getName().length();
            if (width2 < temp2){
                width2 = temp2;
            }
        }

        myDivLine = myDivLine + "-+";
        headerLine = headerLine + "| ";
        myDivLine = myDivLine + divLine(width2, "-");
        headerLine = headerLine + divLine(width2 - headers[1].length(), " ") + headers[1] + " ";

        int width3 = headers[2].length();
        for (GameResult res : leaderboard) {
            int temp3 = String.format("%d", res.getAttempts()).length();
            if (width3 < temp3){
                width3 = temp3;
            }
        }

        myDivLine = myDivLine + "--+";
        headerLine = headerLine + "|";
        myDivLine = myDivLine + divLine(width3, "-");
        headerLine = headerLine + divLine(width3 - headers[2].length(), " ") + headers[2];


        int width4 = headers[3].length();
        for (GameResult res : leaderboard) {
            int temp4 = String.format("%.1fs", res.getDuration() / 1000.0).length();
            if (width4 < temp4){
                width4 = temp4;
            }
        }

        myDivLine = myDivLine + "+";
        headerLine = headerLine + "|";

        myDivLine = myDivLine + divLine(width4, "-");
        headerLine = headerLine + divLine(width4 - headers[3].length(), " ") + "  " + headers[3] + " ";

        myDivLine = myDivLine + "---+";
        headerLine = headerLine + "|";

        System.out.println(myDivLine);
        System.out.println(headerLine);
        System.out.println(myDivLine);


        int finalWidth = width2;
        int finalWidth1 = width1;
        int finalWidth2 = width3;
        int finalWidth3 = width4;
        leaderboard.stream().sorted(
                Comparator.comparingInt(GameResult::getAttempts)
                        .thenComparingLong(GameResult::getDuration))
                .limit(20)
                .forEach(res -> System.out.printf(
                        "|" + divLine(finalWidth1 - String.format(fCol1, res.getStartTime()).length(), " ") +" "+ fCol1 +
                        "|" + divLine(finalWidth - res.getName().length(), " ") + fCol2 +
                        "|" + divLine(finalWidth2 - String.format("%d", res.getAttempts()).length() - 1, " ") + fCol3 +
                        "|" + divLine(finalWidth3 - String.format("%.1f", res.getDuration() / 1000.0).length(), " ") + fCol4 +
                        "|" + "\n",
                        res.getStartTime(),
                        res.getName(),
                        res.getAttempts(),
                        res.getDuration() / 1000.0));

    }

    private static String divLine(int scount, String sadd) {

        String tot = "";
        for (int i = 0; i < scount; i++) {
            tot = tot + sadd;
        }
        return tot;
    }

}



