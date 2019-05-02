package uk.co.joseortale.fancybeerlist.model;

/**
 *
 * Food Pairing Entity
 *
 */
public class FoodPairing {
    private Integer id;
    private String name;
    private Integer beerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBeerId() {
        return beerId;
    }

    public void setBeerId(Integer beerId) {
        this.beerId = beerId;
    }
}
