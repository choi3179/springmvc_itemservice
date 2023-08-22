package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data       // @Data는 실무에서 일반적으로 사용하기 위험하다. 보통 @Getter, @Setter 처럼 필요한걸 분리해서 사용하며 테스트 프로젝트이기 때문에 사용한다.(위험성 강조)
public class Item {

    private Long id;            // 상품 ID
    private String itemName;    // 상품 이름
    private Integer price;      // 상품 가격
    private Integer quantity;   // 상품 수량

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
