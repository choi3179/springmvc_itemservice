package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    제품 아이템 리포지토리(저장소)
 */
@Repository
public class ItemRepository {

    private static final Map<Long, Item>    store       = new HashMap<>();  //static 사용
    private static long                     sequence    = 0L;               //static 사용

    // 아이템 저장
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    // 아이템 조회
    public Item findById(Long id) {
        return store.get(id);
    }

    // 저장된 모든 아이템 조회
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    /*
        아이템 정보 수정
        - 수정할 아이템 ID와 수정할 정보를 파라미터로 받아와 수정
     */
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // 저장소에 저장된 데이터 초기화
    public void clearStore() {
        store.clear();
    }

}
