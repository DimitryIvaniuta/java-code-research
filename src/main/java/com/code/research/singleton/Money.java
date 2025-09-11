package com.code.research.singleton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class Money {
    int amount;
    String currencyCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money other)) return false;
        boolean currencyCodeEquals = (this.currencyCode == null && other.currencyCode == null)
                || (this.currencyCode != null && this.currencyCode.equals(other.currencyCode));
        return this.amount == other.amount && currencyCodeEquals;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + amount;
        if(currencyCode !=null) {
            result = 31 * result + currencyCode.hashCode();
        }
        return result;
    }
}
