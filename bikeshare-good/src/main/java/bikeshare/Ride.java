package bikeshare;

record Ride(
        String memberId,
        MemberType memberType,
        int bikeId,
        Station fromStation,
        Station toStation,
        int minutes,
        long feeBaht) {
}
