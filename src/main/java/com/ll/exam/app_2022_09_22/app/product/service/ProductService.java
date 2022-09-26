package com.ll.exam.app_2022_09_22.app.product.service;

import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import com.ll.exam.app_2022_09_22.app.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(String name, int price, String makerShopName, List<ProductOption> options) {
        Product product = Product.builder()
            .name(name)
            .price(price)
            .makerShopName(makerShopName)
            .build();

        for ( ProductOption option : options ) {
            product.addOption(option);
        }

        productRepository.save(product);
        return product;
    }
}
// 강사님! isOrderable 함수에서 isSoldOut 이 true 이면 return false; 가 되어야 주문 불능 상태일 때가 반영되는게 아닌가요?
// 아까 Order Entity 에서 Member 에 ManyToOne 을 써주지 않으면 빨간줄 나왔는데 그 이유