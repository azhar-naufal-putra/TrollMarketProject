package com.TrollMarket.dto.merchandise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiseGridDTO {
    private Integer merchandiseId;
    private String merchandiseName;
    private String categoryName;
    private String discontinue;
    private Boolean editable;
}
