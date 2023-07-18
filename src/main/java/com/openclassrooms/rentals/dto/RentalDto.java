package com.openclassrooms.rentals.dto;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class RentalDto {
    @NotBlank(message = "name is mandatory")
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;

    private Date created_at;

    private Date updated_at;

    public String getName() {
        return name;
    }

    public Double getSurface() {
        return surface;
    }

    public Double getPrice() {
        return price;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
