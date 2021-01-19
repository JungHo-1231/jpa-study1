package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.reposityory.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
//
//    @Transactional
//    public void updateItem(Long itemId, Book param){
//        Item findItem = itemRepository.findOne(itemId);
//        findItem.setPrice(param.getPrice());
//        findItem.setName(param.getName());
//        .... 나머지도 마저 채웠다고 가정하고
        // save를 호출할래? update를 할래?
        // 그런거 할 필요가 없다.
        // 스프링의 트랜잭션에 의해서 커밋이 되고 플러시가 되면서 쿼리가 날리간다.

//    }

    @Transactional
    public void updateItem (Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }
}
