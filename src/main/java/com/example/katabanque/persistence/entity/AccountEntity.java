package com.example.katabanque.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "balance")
    private double balance;

    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL)
    private List<OperationEntity> operations = new java.util.ArrayList<>();


}
