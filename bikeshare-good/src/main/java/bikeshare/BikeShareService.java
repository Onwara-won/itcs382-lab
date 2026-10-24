package bikeshare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class BikeShareService {
    private final List<Bike> bikes = new ArrayList<>();
    private final RideRepository rideRepository;

    BikeShareService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
        int bikeId = 1;
        for (Station station : Station.values()) {
            bikes.add(new Bike(bikeId++, station));
            bikes.add(new Bike(bikeId++, station));
        }
    }

    Bike rent(String memberId, MemberType memberType, Station station) {
        if (memberId.isBlank()) {
            throw new IllegalArgumentException("Member ID cannot be blank.");
        }
        for (Bike bike : bikes) {
            if (bike.station() == station && bike.isAvailable()) {
                bike.rent(memberId, memberType);
                return bike;
            }
        }
        throw new IllegalStateException("No available bike at " + station.displayName() + ".");
    }

    Ride returnBike(int bikeId, int minutes, Station station) throws IOException {
        if (minutes < 0) {
            throw new IllegalArgumentException("Ride time cannot be negative.");
        }
        Bike bike = findBike(bikeId);
        Ride ride = bike.prepareRide(station, minutes);
        rideRepository.save(ride);
        bike.completeReturn(station);
        return ride;
    }

    List<Bike> bikesAt(Station station) {
        return bikes.stream().filter(bike -> bike.station() == station).toList();
    }

    List<Bike> allBikes() {
        return List.copyOf(bikes);
    }

    private Bike findBike(int bikeId) {
        return bikes.stream()
                .filter(bike -> bike.id() == bikeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown bike ID: " + bikeId));
    }
}
