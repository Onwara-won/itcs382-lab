package bikeshare;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final int STUDENT = 1;
    public static final int STAFF = 2;
    public static final List<String> STATIONS = List.of("Library", "Canteen", "Dorm");
    public static final List<Bike> BIKES = new ArrayList<>();
    private static final Scanner INPUT = new Scanner(System.in);

    static {
        int id = 1;
        for (String station : STATIONS) {
            BIKES.add(new Bike(id++, station));
            BIKES.add(new Bike(id++, station));
        }
    }

    public static void main(String[] args) {
        System.out.println("Commands: rent <memberId> <student|staff> <station>, "
                + "return <bikeId> <minutes> <station>, bikes [station], "
                + "available [station], quit");
        while (INPUT.hasNextLine()) {
            String line = INPUT.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] words = line.split("\\s+");
            switch (words[0].toLowerCase()) {
                case "rent" -> rent(words);
                case "return" -> finishRide(words);
                case "bikes" -> showBikes(words, false);
                case "available" -> showBikes(words, true);
                case "quit" -> {
                    return;
                }
                default -> System.out.println("Unknown command.");
            }
        }
    }

    private static void rent(String[] words) {
        if (words.length != 4 || words[1].isBlank()) {
            System.out.println("Usage: rent <memberId> <student|staff> <station>");
            return;
        }
        int memberType = switch (words[2].toLowerCase()) {
            case "student" -> STUDENT;
            case "staff" -> STAFF;
            default -> 0;
        };
        String station = stationName(words[3]);
        if (memberType == 0 || station == null) {
            System.out.println("Enter a valid member type and station.");
            return;
        }

        for (Bike bike : BIKES) {
            if (!bike.rented && bike.station.equals(station)) {
                bike.rented = true;
                bike.memberId = words[1];
                bike.memberType = memberType;
                System.out.println("Rented bike " + bike.id + " from " + station + ".");
                return;
            }
        }
        System.out.println("No available bike at " + station + ".");
    }

    private static void finishRide(String[] words) {
        if (words.length != 4) {
            System.out.println("Usage: return <bikeId> <minutes> <station>");
            return;
        }
        try {
            int bikeId = Integer.parseInt(words[1]);
            int minutes = Integer.parseInt(words[2]);
            String station = stationName(words[3]);
            if (minutes < 0 || station == null) {
                System.out.println("Enter non-negative minutes and a valid station.");
                return;
            }

            Bike bike = findBike(bikeId);
            if (bike == null || !bike.rented) {
                System.out.println("That bike is not currently rented.");
                return;
            }
            int fee;
            try {
                fee = Math.multiplyExact(minutes, bike.memberType == STUDENT ? 2 : 3);
            } catch (ArithmeticException e) {
                System.out.println("The fee is too large to calculate.");
                return;
            }
            if (!saveRide(bike, minutes, fee, station)) {
                return;
            }

            System.out.println("Receipt: bike " + bike.id + ", " + minutes
                    + " minutes, " + fee + " baht.");
            bike.station = station;
            bike.rented = false;
            bike.memberId = "";
            bike.memberType = 0;
        } catch (NumberFormatException e) {
            System.out.println("Bike ID and minutes must be whole numbers.");
        }
    }

    private static void showBikes(String[] words, boolean availableOnly) {
        if (words.length > 2) {
            System.out.println("Usage: " + (availableOnly ? "available" : "bikes") + " [station]");
            return;
        }
        String station = words.length == 2 ? stationName(words[1]) : null;
        if (words.length == 2 && station == null) {
            System.out.println("Enter a valid station.");
            return;
        }
        for (Bike bike : BIKES) {
            if ((station == null || bike.station.equals(station))
                    && (!availableOnly || !bike.rented)) {
                System.out.println("Bike " + bike.id + " - " + bike.station
                        + (bike.rented ? " (rented)" : " (available)"));
            }
        }
    }

    private static boolean saveRide(Bike bike, int minutes, int fee, String returnStation) {
        Path file = Path.of("rides.csv");
        try {
            boolean needsHeader = !Files.exists(file) || Files.size(file) == 0;
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                if (needsHeader) {
                    writer.write("member_id,member_type,bike_id,from_station,to_station,minutes,fee_baht");
                    writer.newLine();
                }
                writer.write(csv(bike.memberId) + ","
                        + (bike.memberType == STUDENT ? "student" : "staff") + ","
                        + bike.id + "," + csv(bike.station) + "," + csv(returnStation) + ","
                        + minutes + "," + fee);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Could not save ride to rides.csv: " + e.getMessage());
            return false;
        }
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private static String stationName(String value) {
        for (String station : STATIONS) {
            if (station.equalsIgnoreCase(value)) {
                return station;
            }
        }
        return null;
    }

    private static Bike findBike(int id) {
        for (Bike bike : BIKES) {
            if (bike.id == id) {
                return bike;
            }
        }
        return null;
    }

    public static class Bike {
        public int id;
        public String station;
        public boolean rented;
        public String memberId = "";
        public int memberType;

        public Bike(int id, String station) {
            this.id = id;
            this.station = station;
        }
    }
}
