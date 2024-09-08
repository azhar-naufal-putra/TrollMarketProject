package com.TrollMarket.service.abstraction;

import com.TrollMarket.dto.account.AccountLoginDTO;
import com.TrollMarket.dto.account.AccountRegisterDTO;
import com.TrollMarket.dto.account.AccountTokenDTO;

public interface AccountService {
    void register(AccountRegisterDTO accountRegisterDTO);
    AccountTokenDTO createToken(AccountLoginDTO accountLoginDTO);
    Boolean checkExistingUsername(String username);
}
