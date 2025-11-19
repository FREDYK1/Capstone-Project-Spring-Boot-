package com.capstone.turntabl.dto;


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
    private String title;
    private String titleCode;
    private String description;
    private Boolean isActive;
}
