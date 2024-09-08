package com.TrollMarket.service.abstraction;

import com.TrollMarket.dto.profile.ProfileAddBalanceDTO;
import com.TrollMarket.dto.profile.ProfileHistoryGridDTO;
import com.TrollMarket.dto.profile.ProfileInfoDTO;

import java.util.List;

public interface ProfileService {
    List<ProfileHistoryGridDTO> getUserHistories(int page, String username);
    ProfileInfoDTO getProfile(String username);
    Long getTotalPages(String username);
    void updateBalance(ProfileAddBalanceDTO profileAddBalanceDTO);
}
