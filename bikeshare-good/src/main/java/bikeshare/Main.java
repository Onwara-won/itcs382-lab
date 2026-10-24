package bikeshare;

import java.nio.file.Path;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        RideRepository repository = new CsvRideRepository(Path.of("rides.csv"));
        BikeShareService service = new BikeShareService(repository);
        new ConsoleMenu(service).run();
    }
}
