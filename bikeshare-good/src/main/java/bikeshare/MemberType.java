package bikeshare;

public enum MemberType {
    STUDENT(2),
    STAFF(3);

    private final int bahtPerMinute;

    MemberType(int bahtPerMinute) {
        this.bahtPerMinute = bahtPerMinute;
    }

    public long feeFor(int minutes) {
        return Math.multiplyExact((long) minutes, bahtPerMinute);
    }

    public static MemberType parse(String value) {
        return valueOf(value.toUpperCase());
    }
}
