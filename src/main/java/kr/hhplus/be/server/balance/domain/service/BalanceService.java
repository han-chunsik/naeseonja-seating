package kr.hhplus.be.server.balance.domain.service;

import kr.hhplus.be.server.balance.domain.vo.BalanceChargeVO;
import kr.hhplus.be.server.balance.domain.vo.BalanceVO;

public interface BalanceService {
    BalanceChargeVO chargeBalance(Long userId, Long amount);
    BalanceVO getBalance(Long userId);
}
