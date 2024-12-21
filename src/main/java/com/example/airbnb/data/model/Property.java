package com.example.airbnb.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
@Setter
@Getter
@Entity
public class Property{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


}
