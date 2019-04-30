package math;

public class Converter {
    public static double disTravel = 50;
    public static double disTime = 20;

    public static double weight(double weightFromParse, double disWeight) {
        return weightFromParse * disWeight / 1000;
    }

    public static double travel(double fromParse) {
        return fromParse * disTravel / 10000;
    }

    public static double period(double fromParse) {
        return 60000 / fromParse / disTime;
    }

    public static double mmToCm(double mm) {
        return mm / 10.0;
    }
}
