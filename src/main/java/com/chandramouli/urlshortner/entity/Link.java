package com.chandramouli.urlshortner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Link {

    @Id
    private String shortUrl;

    private String actualUrl;

}
