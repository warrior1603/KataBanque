package com.example.katabanque.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Money {
    private double amount;
    public boolean isPositive(){
        return amount > 0;
    }
}
