package bikeshare;

final class Bike {
    private final int id;
    private Station station;
    private Rental rental;

    Bike(int id, Station station) {
        this.id = id;
        this.station = station;
    }

    int id() {
        return id;
    }

    Station station() {
        return station;
    }

    boolean isAvailable() {
        return rental == null;
    }

    void rent(String memberId, MemberType memberType) {
        if (!isAvailable()) {
            throw new IllegalStateException("Bike is already rented.");
        }
        rental = new Rental(memberId, memberType, station);
    }

    Ride prepareRide(Station returnStation, int minutes) {
        if (isAvailable()) {
            throw new IllegalStateException("Bike is not rented.");
        }
        long fee = rental.memberType().feeFor(minutes);
        return new Ride(rental.memberId(), rental.memberType(), id,
                rental.startStation(), returnStation, minutes, fee);
    }

    void completeReturn(Station returnStation) {
        if (isAvailable()) {
            throw new IllegalStateException("Bike is not rented.");
        }
        station = returnStation;
        rental = null;
    }

    private record Rental(String memberId, MemberType memberType, Station startStation) {
    }
}
