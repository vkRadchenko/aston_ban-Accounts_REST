package ru.radchenko.BankRestService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    private String name;

    @NotBlank(message = "ПИН-код обязателен для заполнения")
    @Pattern(regexp = "\\d{4}", message = "ПИН-код должен состоять из четырех цифр")
    @Schema(description = "Четырехзначный ПИН-код", example = "1111")
    private String password;

    public UserDTO() {
    }
    public UserDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
