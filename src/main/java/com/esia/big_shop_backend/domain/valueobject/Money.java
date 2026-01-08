package com.esia.big_shop_backend.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Money {
    public static final Money ZERO = new Money(0, "XAF");

    private final double amount;
    private final String currency;


    public Money( double amount, String currency){
        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(currency == null || currency.isBlank()){
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount + other.amount, this.currency);
    }

    public Money subtract(Money other) {
        validateSameCurrency(other);
        return new Money(this.amount - other.amount, this.currency);
    }

    public boolean isGreaterThan(Money other) {
        validateSameCurrency(other);
        return this.amount > other.amount;
    }

    public boolean isLessThan(Money other) {
        validateSameCurrency(other);
        return this.amount < other.amount;
    }

    public Money multiply(int multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("Multiplier cannot be negative");
        }
        return new Money(this.amount * multiplier, this.currency);
    }

    private void validateSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot compare money with different currencies");
        }
    }
}
