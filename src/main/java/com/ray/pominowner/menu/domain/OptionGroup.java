package com.ray.pominowner.menu.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import com.ray.pominowner.menu.service.vo.OptionGroupUpdateInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = PROTECTED)
public class OptionGroup extends BaseTimeEntity {

    @Id
    @Column(name = "OPTION_GROUP_ID")
    @GeneratedValue
    private Long id;

    private String name;

    private int maxOptionCount;

    private boolean required;

    private Long storeId;

    @OneToMany(mappedBy = "optionGroup", fetch = EAGER, orphanRemoval = true, cascade = {PERSIST, REMOVE})
    private final List<Option> options = new ArrayList<>();

    @Builder
    private OptionGroup(Long id, String name, int maxOptionCount, boolean required, Long storeId) {
        validateConstructor(name, maxOptionCount);

        this.id = id;
        this.name = name;
        this.maxOptionCount = maxOptionCount;
        this.required = required;
        this.storeId = storeId;
    }

    private void validateConstructor(String name, int maxOptionCount) {
        Assert.hasText(name, "옵션 그룹 이름은 필수입니다.");
        Assert.isTrue(maxOptionCount >= 0, "최대 옵션 개수는 0 이상이어야 합니다.");
    }

    public Option addOption(Option option) {
        Option optionGroupChangedOption = Option.builder()
                .id(option.getId())
                .name(option.getName())
                .price(option.getPrice())
                .selected(option.isSelected())
                .optionGroup(this)
                .build();

        this.options.add(optionGroupChangedOption);
        checkMaxOptionCount();
        checkSelectedOptionCount();

        return optionGroupChangedOption;
    }

    private void checkMaxOptionCount() {
        final int maxOptionCount = 10;

        if (options.size() > maxOptionCount) {
            throw new IllegalArgumentException("옵션 개수는 " + maxOptionCount + "개를 초과할 수 없습니다.");
        }
    }

    private void checkSelectedOptionCount() {
        long totalCheckedOptionCount = this.options.stream().filter(Option::isSelected).count();
        if (this.maxOptionCount < totalCheckedOptionCount) {
            throw new IllegalArgumentException("최대 옵션 개수를 초과하였습니다.");
        }
    }

    public int getTotalPrice() {
        return this.options.stream()
                .filter(Option::isSelected)
                .mapToInt(Option::getPrice)
                .sum();
    }

    public void checkRequiredOption() {
        if (this.required && this.options.stream().noneMatch(Option::isSelected)) {
            throw new IllegalArgumentException("필수 옵션을 선택해주세요.");
        }
    }

    public OptionGroup update(OptionGroupUpdateInfo optionGroupUpdateInfo) {
        return OptionGroup.builder()
                .id(id)
                .name(optionGroupUpdateInfo.name())
                .maxOptionCount(optionGroupUpdateInfo.maxOptionCount())
                .required(optionGroupUpdateInfo.required())
                .storeId(storeId)
                .build();
    }

    public List<Option> getOptions() {
        return options;
    }

    public Long getId() {
        return id;
    }

}
