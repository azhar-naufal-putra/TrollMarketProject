package com.TrollMarket.dto.merchandise;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MerchandiseInsertUpdateDTO {
    private Integer merchandiseId;
    @NotBlank(message = "Merchandise name cannot be blank!!")
    @Length(max = 100, message = "Merchandise name cannot be more than 100 characters")
    private String merchandiseName;
    @NotNull(message = "Category must be specified")
    private Integer categoryId;
    @Length(max = 255, message = "Description cannot be more than 255 characters")
    private String description;
    @NotNull(message = "Price must be specified")
    @Min(value = 0, message = "Price must be equals or greater than 0")
    private BigDecimal price;
    private Boolean discontinue;
    private String sellerUsername;
}
