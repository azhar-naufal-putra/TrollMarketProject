package com.TrollMarket.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfoDTO {
    private String name;
    private String role;
    private String address;
    private String balance;
}
