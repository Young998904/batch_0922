package com.ll.exam.app_2022_09_22.app.product.entity;

import static javax.persistence.CascadeType.ALL;

import com.ll.exam.app_2022_09_22.app.base.entity.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Product extends BaseEntity {
    private int salePrice; // 판매가
    private int price; // 소비자가
    private int wholesalePrice; // 도매가
    private String name;
    private String makerShopName;

    private boolean isSoldOut; // 관련 옵션들이 전부 판매불능 상태일 때

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = ALL, orphanRemoval = true)
    private List<ProductOption> productOptions = new ArrayList<>();

    public void addOption(ProductOption option) {
        option.setProduct(this);
        option.setPrice(getPrice());
        option.setSalePrice(getSalePrice());
        option.setWholesalePrice(getWholesalePrice());

        productOptions.add(option);
    }
}