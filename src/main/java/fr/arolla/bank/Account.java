package fr.arolla.bank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {

    private final IBAN iban;
    private List<OperationHistory> operationHistory;

    public Account() {
        this.operationHistory = new ArrayList<>();
        this.iban = new IBAN();
    }

    public double getBalance() {
        if(operationHistory.isEmpty())
            return 0.;

        double totalDeposits = operationHistory.stream()
                .filter(o -> o.getType().equals(OperationType.DEPOSIT))
                .mapToDouble(OperationHistory::getAmount)
                .sum();

        double totalWithdrawals = operationHistory.stream()
                .filter(o -> o.getType().equals(OperationType.WITHDRAWAL))
                .mapToDouble(OperationHistory::getAmount)
                .sum();


        return totalDeposits-totalWithdrawals;
    }

    public List<OperationHistory> getOperationHistory() {
        return operationHistory;
    }

    public void deposit(Amount amount){

        saveOperation(OperationType.DEPOSIT, amount);
    }

    public void withdrawal(Amount amount){

        /**
         * TODO delete for overdraft
         */
        if(amount.getQuantity().doubleValue() > getBalance()){
            throw new IllegalArgumentException("amount > balance");
        }

        saveOperation(OperationType.WITHDRAWAL, amount);
    }

    private void saveOperation(OperationType operationType, Amount amount){
        operationHistory.add(new OperationHistory(operationType, amount.getQuantity().doubleValue(), LocalDateTime.now(), getBalance()+amount.getQuantity().doubleValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(iban, account.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }
}
