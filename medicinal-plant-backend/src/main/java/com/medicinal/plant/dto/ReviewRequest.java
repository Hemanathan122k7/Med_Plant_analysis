package com.medicinal.plant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private String userName;
    private String userEmail;
    private Integer rating;
    private String experience;
}
