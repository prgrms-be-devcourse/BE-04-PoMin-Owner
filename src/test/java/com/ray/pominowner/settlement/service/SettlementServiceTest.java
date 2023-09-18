package com.ray.pominowner.settlement.service;

import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.settlement.domain.DepositStatus;
import com.ray.pominowner.settlement.domain.Fee;
import com.ray.pominowner.settlement.domain.PayOut;
import com.ray.pominowner.settlement.domain.Sales;
import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @InjectMocks
    SettlementService settlementService;

    @Mock
    SettlementRepository settlementRepository;

    private final Fee fee = new Fee(PGType.TOSS, 10000);

    private final PayOut payOut = new PayOut(10000, fee, LocalDate.now().plusDays(1));

    private final Sales sales = new Sales(10000, LocalDate.now());

    private final Settlement settlement = Settlement.builder()
            .id(1L)
            .fee(fee)
            .payOut(payOut)
            .sales(sales)
            .depositStatus(DepositStatus.SCHEDULED)
            .storeId(1L)
            .orderId(1L)
            .paymentId(1L)
            .deleted(false)
            .build();


    @Test
    @DisplayName("Settlement 저장에 성공한다.")
    public void successCreateSettlement() {
        // given
        given(settlementRepository.save(settlement)).willReturn(settlement);

        // when
        Settlement createdSettlement = settlementService.create(settlement);

        // then
        assertThat(createdSettlement).isEqualTo(createdSettlement);
    }

}
