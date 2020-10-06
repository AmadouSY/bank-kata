package fr.arolla.bank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class AccountTest {

    Account account;

    @BeforeEach
    public void setUp(){
        account = new Account();
    }

    @Test
    void should_raise_an_exception_for_a_negative_amount(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.deposit(getDollarAmount(-100)));
    }
    @Test
    void should_not_update_the_balance_for_a_negative_amount(){

        double oldBalance = account.getBalance();
        try{
            account.deposit(getDollarAmount(-100));
        }catch(Exception ignored){}

        assertThat(account.getBalance()).isEqualTo(oldBalance);
    }

    @Test
    void should_update_the_balance_for_a_positive_deposit(){
        int oldBalance = 0;
        int amount = 100;

        account = new Account();
        account.deposit(getDollarAmount(amount));

        assertThat(account.getBalance()).isEqualTo(oldBalance + amount);
    }

    @Test
    void should_raise_an_exception_for_an_amount_greater_than_the_balance(){
        account = new Account();
        Assertions.assertThrows(IllegalArgumentException.class, () -> account.withdrawal(getDollarAmount(300)));
    }

    @Test
    void should_not_update_the_balance_for_an_amount_greater_than_the_balance(){

        account = new Account();
        double oldBalance = account.getBalance();

        try{
            account.withdrawal(getDollarAmount(300));
        }catch(Exception ignored){}

        assertThat(account.getBalance()).isEqualTo(oldBalance);
    }


    @Test
    void should_subtract_the_amount_to_the_balance_for_an_amount_smaller_than_the_balance(){

        account.deposit(getDollarAmount(1000));
        double oldBalance = account.getBalance();
        int amount = 300;

        account.withdrawal(getDollarAmount(amount));

        assertThat(account.getBalance()).isEqualTo(oldBalance - amount);

    }


    @Test
    void checkHistory(){

        account = new Account();

        account.deposit(getDollarAmount(1000));
        account.deposit(getDollarAmount(500));
        account.withdrawal(getDollarAmount(200));
        account.withdrawal(getDollarAmount(100));

        List<OperationHistory> operationHistory = new ArrayList<>();
        operationHistory.add(new OperationHistory(OperationType.DEPOSIT, 1000, LocalDateTime.now(), 21000));
        operationHistory.add(new OperationHistory(OperationType.DEPOSIT, 500, LocalDateTime.now(), 21500));
        operationHistory.add(new OperationHistory(OperationType.WITHDRAWAL, 200, LocalDateTime.now(), 21300));
        operationHistory.add(new OperationHistory(OperationType.WITHDRAWAL, 100, LocalDateTime.now(), 21200));

        assertThat(account.getOperationHistory().size()).isEqualTo(4);

        account.getOperationHistory().forEach(
                oh ->  assertThat(operationHistory.contains(oh)));

    }

    private Amount getDollarAmount(double value){
        return new Amount(new BigDecimal(value), CurrencyType.DOLLAR);
    }
}