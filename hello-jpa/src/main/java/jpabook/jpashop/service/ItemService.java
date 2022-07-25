package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.api.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    public final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId); // 영속성 찾아온다
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
        // 영속 상태이므로 set 과정에서 이미 반영이 된다. (merge, save 필요가없음)
        // 위에서 한 코드가 em.merge에서 일어나는 일이다.
        // 단, merge는 해당 필드값이 없으면 null이 입력된다.
        // 따라서 안정성을 높이기 위해 merge는 지양하는게 좋다.
        // 또한, 코드가 복잡하다면 setter를 쓰는것보다 도메인에서 변경되가 만드는것이 추적에 용이하다.
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
