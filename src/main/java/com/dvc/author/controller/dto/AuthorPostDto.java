package com.dvc.author.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthorPostDto {
    @NotEmpty(message = "Full name cannot be empty or null")
    @Size(min=6, max=128, message = "Full name must be between 6 and 128 symbols")
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String deathday;

    private String bio;

    @Size(min=3, max=128, message = "Pseudoname must be between 3 and 128 symbols")
    private String pseudoname;

    @Size(max=2048, message = "portraitPic must not be over 2048 symbols")
    private String portraitPic;

    public AuthorPostDto() {
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getBio() {
        return bio;
    }

    public String getPseudoname() {
        return pseudoname;
    }

    public String getPortraitPic() {
        return portraitPic;
    }
}
