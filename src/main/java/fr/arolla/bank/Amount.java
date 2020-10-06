package fr.arolla.bank;

import java.math.BigDecimal;

public class Amount {

    private final BigDecimal quantity;
    private final CurrencyType currency;

    public Amount(BigDecimal quantity, CurrencyType currency) {
        if(quantity.doubleValue() <= 0){
            throw new IllegalArgumentException("An amount cant be null or negative");
        }
        this.quantity = quantity;
        this.currency = currency;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public CurrencyType getCurrency() {
        return currency;
    }
}
