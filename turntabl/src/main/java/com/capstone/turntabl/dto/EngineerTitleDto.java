package com.capstone.turntabl.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EngineerTitleDto {
    private Long id;

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "titleCode must not be blank")
    private String titleCode;

    private String description;
    private Boolean isActive;
}
