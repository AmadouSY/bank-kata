package fr.arolla.bank;

import java.time.LocalDateTime;
import java.util.Objects;

public class OperationHistory {

    private final OperationType type;
    private final double amount;
    private final LocalDateTime date;
    private final double balance;

    public OperationHistory(OperationType type, double amount, LocalDateTime date, double balance) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.balance = balance;
    }

    public OperationType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationHistory that = (OperationHistory) o;
        return Double.compare(that.amount, amount) == 0 &&
                Double.compare(that.balance, balance) == 0 &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount, balance);
    }
}
