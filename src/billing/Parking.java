package billing;

public class Parking implements Billable {
    private final double hourlyRate;

    public Parking(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateCost(int hours) {
        if (hours <= 0) {
            return 0;
        }
        return Math.round(hourlyRate * hours * 100.0) / 100.0;
    }
}
