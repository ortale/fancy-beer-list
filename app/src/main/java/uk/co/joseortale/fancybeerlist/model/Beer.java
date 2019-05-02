package uk.co.joseortale.fancybeerlist.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Beer Entity
 *
 */
public class Beer implements Serializable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("tagline")
    private String tagline;
    @SerializedName("description")
    private String description;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("abv")
    private Double abv;
    @SerializedName("ibu")
    private Double ibu;
    @SerializedName("food_pairing")
    private List<String> foodPairing;

    public Beer() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getAbv() {
        return abv;
    }

    public Double getIbu() {
        return ibu;
    }

    public List<String> getFoodPairing() {
        return foodPairing;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public void setFoodPairing(List<String> foodPairing) {
        this.foodPairing = foodPairing;
    }
}