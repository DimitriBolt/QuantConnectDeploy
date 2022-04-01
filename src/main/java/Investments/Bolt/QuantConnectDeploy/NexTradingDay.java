package Investments.Bolt.QuantConnectDeploy;

import java.time.LocalDate;
import java.util.*;

class NexTradingDay {
    // static Class variables
    // private Instance variable
    private LocalDate nextTradingDay;
    private final List<LocalDate> HOLIDAYS = Arrays.asList(new LocalDate[]{
            LocalDate.of(2022, 04, 15),
            LocalDate.of(2022, 05, 30)});

    // Initializer block
    {
    }

    // Constructors
    public NexTradingDay(LocalDate now, int dateDelta) {
        this.nextTradingDay = now.plusDays(dateDelta);
        while (this.nextTradingDay.getDayOfWeek().name().equals("SATURDAY") | this.nextTradingDay.getDayOfWeek().name().equals("SUNDAY") | HOLIDAYS.contains(this.nextTradingDay)) {
            this.nextTradingDay = this.nextTradingDay.plusDays(1);
        }
        ;
    }

    // Methods
    // Mutator (= setter) methods
    // Accessor (= getter) methods
    public LocalDate getNextTradingDay() {
        return nextTradingDay;
    }
}
