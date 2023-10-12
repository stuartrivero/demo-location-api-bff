package com.example.demolocationapibff.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "PostcodeSearch")
public class PostcodeSearch {

    private long id;
    private String postcode1;
    private String postcode2;
    private BigDecimal distance;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "postcode1", nullable = false)
    public String getPostcode1() {
        return postcode1;
    }

    public void setPostcode1(String postcode) {
        this.postcode1 = postcode;
    }

    @Column(name = "postcode2", nullable = false)
    public String getPostcode2() {
        return postcode2;
    }

    public void setPostcode2(String postcode) {
        this.postcode2 = postcode;
    }

    @Column(name = "distance", nullable = false)
    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "PostcodeSearch [id=" + id + ", postcode1="+ postcode1 + ", postcode2= " + postcode2 + ", distance=" + distance + "]";
    }

    public static PostcodeSearch from(Postcode postcode1, Postcode postcode2, BigDecimal distance){
        PostcodeSearch postcodeSearch = new PostcodeSearch();
        postcodeSearch.setPostcode1(postcode1.value());
        postcodeSearch.setPostcode2(postcode2.value());
        postcodeSearch.setDistance(distance);
        return postcodeSearch;
    }
}