package bikeshare;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

final class ConsoleMenu {
    private final BikeShareService service;

    ConsoleMenu(BikeShareService service) {
        this.service = service;
    }

    void run() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <student|staff> <station>, "
                    + "return <bikeId> <minutes> <station>, bikes [station], "
                    + "available [station], quit");
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.equalsIgnoreCase("quit")) {
                    return;
                }
                if (!line.isEmpty()) {
                    execute(line);
                }
            }
        }
    }

    private void execute(String line) {
        String[] words = line.split("\\s+");
        try {
            switch (words[0].toLowerCase()) {
                case "rent" -> rent(words);
                case "return" -> returnBike(words);
                case "bikes" -> showBikes(words, false);
                case "available" -> showBikes(words, true);
                default -> System.out.println("Unknown command.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Could not save ride to rides.csv: " + e.getMessage());
        }
    }

    private void rent(String[] words) {
        requireLength(words, 4, "rent <memberId> <student|staff> <station>");
        Bike bike = service.rent(words[1], MemberType.parse(words[2]), Station.parse(words[3]));
        System.out.println("Rented bike " + bike.id() + " from "
                + bike.station().displayName() + ".");
    }

    private void returnBike(String[] words) throws IOException {
        requireLength(words, 4, "return <bikeId> <minutes> <station>");
        Ride ride = service.returnBike(
                Integer.parseInt(words[1]), Integer.parseInt(words[2]), Station.parse(words[3]));
        System.out.println("Receipt: bike " + ride.bikeId() + ", " + ride.minutes()
                + " minutes, " + ride.feeBaht() + " baht.");
    }

    private void showBikes(String[] words, boolean availableOnly) {
        if (words.length > 2) {
            throw new IllegalArgumentException("Usage: "
                    + (availableOnly ? "available" : "bikes") + " [station]");
        }
        List<Bike> bikes = words.length == 2
                ? service.bikesAt(Station.parse(words[1]))
                : service.allBikes();
        bikes.stream()
                .filter(bike -> !availableOnly || bike.isAvailable())
                .forEach(bike -> System.out.println("Bike " + bike.id() + " - "
                        + bike.station().displayName()
                        + (bike.isAvailable() ? " (available)" : " (rented)")));
    }

    private void requireLength(String[] words, int expected, String usage) {
        if (words.length != expected) {
            throw new IllegalArgumentException("Usage: " + usage);
        }
    }
}
