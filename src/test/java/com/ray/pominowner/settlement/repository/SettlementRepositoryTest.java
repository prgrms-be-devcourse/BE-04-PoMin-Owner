package com.ray.pominowner.settlement.repository;

import com.ray.pominowner.settlement.domain.DepositStatus;
import com.ray.pominowner.settlement.domain.PayOut;
import com.ray.pominowner.settlement.domain.Sales;
import com.ray.pominowner.settlement.domain.ServiceType;
import com.ray.pominowner.settlement.domain.Settlement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ray.pominowner.settlement.SettlementTestFixture.fee;
import static com.ray.pominowner.settlement.SettlementTestFixture.payOut;
import static com.ray.pominowner.settlement.SettlementTestFixture.sales;
import static com.ray.pominowner.settlement.SettlementTestFixture.settlement;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SettlementRepositoryTest {

    @Autowired
    SettlementRepository settlementRepository;

    Settlement deletedSettlement =
            Settlement.builder()
                    .id(1L)
                    .fee(fee())
                    .payOut(payOut())
                    .sales(sales())
                    .depositStatus(DepositStatus.SCHEDULED)
                    .serviceType(ServiceType.PACKAGING)
                    .storeId(1L)
                    .orderId(1L)
                    .paymentId(1L)
                    .deleted(true)
                    .build();

    @Test
    @DisplayName("주문 id로 삭제되지 않은 정산 정보 조회에 성공한다.")
    public void successFindSettlementByOrderIdAndDeleted() {
        // given
        settlementRepository.save(settlement());

        // when
        Optional<Settlement> settlement = settlementRepository.findSettlementByOrderIdAndDeleted(settlement().getOrderId(), false);

        // then
        assertThat(settlement).isEqualTo(Optional.of(settlement()));
    }

    @Test
    @DisplayName("주문 id가 존재하지 않는 경우 빈 값 반환에 성공한다.")
    public void successFindSettlementByNonExistentOrderId() {
        // given
        settlementRepository.save(settlement());

        // when
        Optional<Settlement> settlement = settlementRepository.findSettlementByOrderIdAndDeleted(settlement().getOrderId() + 1, false);

        // then
        assertThat(settlement).isEmpty();

    }

    @Test
    @DisplayName("삭제된 경우 빈 값 반환에 성공한다.")
    public void successFindSettlementByDeleted() {
        // given
        settlementRepository.save(deletedSettlement);

        // when
        Optional<Settlement> settlement = settlementRepository.findSettlementByOrderIdAndDeleted(deletedSettlement.getOrderId(), false);

        // then
        assertThat(settlement).isEmpty();
    }

    Sales sales = new Sales(10000, LocalDate.now().plusDays(2));

    PayOut payOut = new PayOut(10000, fee(), LocalDate.now().plusDays(2));

    Settlement outOfRangeSettlement = Settlement.builder()
            .id(2L)
            .fee(fee())
            .payOut(payOut)
            .sales(sales)
            .depositStatus(DepositStatus.SCHEDULED)
            .serviceType(ServiceType.PACKAGING)
            .storeId(1L)
            .orderId(1L)
            .paymentId(1L)
            .deleted(false)
            .build();

    LocalDate payOutDate = payOut().getPayOutDate();

    @Test
    @DisplayName("가게 id와 지정된 매출일 사이의 삭제되지 않은 정산 정보 조회에 성공한다.")
    public void successFindSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween() {
        // given
        settlementRepository.save(settlement());
        settlementRepository.save(outOfRangeSettlement);

        // when
        List<Settlement> settlements = settlementRepository.findSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween(
                settlement().getStoreId(),
                false,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        // then
        assertThat(settlements).isEqualTo(List.of(settlement()));
    }

    @Test
    @DisplayName("가게 id가 존재하지 않는 경우 매출일에 따른 정산 정보 빈 값 반환에 성공한다.")
    public void successFindSettlementsBySalesDateWithNonExistentStoreId() {
        // given
        settlementRepository.save(settlement());

        // when
        List<Settlement> settlements = settlementRepository.findSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween(
                settlement().getStoreId() + 1,
                false,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        // then
        assertThat(settlements).isEmpty();
    }

    @Test
    @DisplayName("삭제된 경우 매출일에 따른 정산 정보 빈 값 반환에 성공한다.")
    public void successFindSettlementsBySalesDateWithDeleted() {
        // given
        settlementRepository.save(deletedSettlement);

        // when
        List<Settlement> settlements = settlementRepository.findSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween(
                deletedSettlement.getStoreId(),
                false,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        // then
        assertThat(settlements).isEmpty();
    }

    @Test
    @DisplayName("가게 id와 지정된 지급일 사이의 삭제되지 않은 정산 정보 조회에 성공한다.")
    public void findSettlementsByStoreIdAndDeletedAndPayOut_PayOutDateBetween() {
        // given
        settlementRepository.save(settlement());
        settlementRepository.save(outOfRangeSettlement);

        // when
        List<Settlement> settlements = settlementRepository.findSettlementsByStoreIdAndDeletedAndPayOut_PayOutDateBetween(
                settlement().getStoreId(),
                false,
                payOutDate,
                payOutDate.plusDays(1)
        );

        // then
        assertThat(settlements).isEqualTo(List.of(settlement()));
    }

    @Test
    @DisplayName("가게 id가 존재하지 않는 경우 지급일에 따른 정산 정보 빈 값 반환에 성공한다.")
    public void successFindSettlementsByPayOutDateWithNonExistentStoreId() {
        // given
        settlementRepository.save(settlement());

        // when
        List<Settlement> settlements = settlementRepository.findSettlementsByStoreIdAndDeletedAndPayOut_PayOutDateBetween(
                settlement().getStoreId() + 1,
                false,
                payOutDate,
                payOutDate.plusDays(1)
        );

        // then
        assertThat(settlements).isEmpty();
    }

    @Test
    @DisplayName("삭제된 경우 지급일에 따른 정산 정보 빈 값 반환에 성공한다.")
    public void successFindSettlementsByPayOutDateWithDeleted() {
        // given
        settlementRepository.save(deletedSettlement);

        // when
        List<Settlement> settlements = settlementRepository.findSettlementsByStoreIdAndDeletedAndSales_SalesDateBetween(
                deletedSettlement.getStoreId(),
                false,
                payOutDate,
                payOutDate.plusDays(1)
        );

        // then
        assertThat(settlements).isEmpty();
    }

}
