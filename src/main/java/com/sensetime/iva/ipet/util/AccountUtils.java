package com.sensetime.iva.ipet.util;

import com.sensetime.iva.ipet.entity.AccountEntity;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author: ChaiLongLong
 * @date : 2018/7/31 15:51
 */
public class AccountUtils {
    public static AccountEntity getCurrentHr() {
        return (AccountEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
