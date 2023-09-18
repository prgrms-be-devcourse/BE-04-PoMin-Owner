package com.ray.pominowner.settlement.service;

import com.ray.pominowner.settlement.domain.Settlement;
import com.ray.pominowner.settlement.repository.SettlementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ray.pominowner.settlement.SettlementTestFixture.settlement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SettlementServiceTest {

    @InjectMocks
    SettlementService settlementService;

    @Mock
    SettlementRepository settlementRepository;


    @Test
    @DisplayName("Settlement 저장에 성공한다.")
    public void successCreateSettlement() {
        // given
        given(settlementRepository.save(settlement())).willReturn(settlement());

        // when
        Settlement createdSettlement = settlementService.create(settlement());

        // then
        assertThat(createdSettlement).isEqualTo(createdSettlement);
    }

}
