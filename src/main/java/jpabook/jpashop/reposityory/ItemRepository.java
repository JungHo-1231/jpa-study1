package jpabook.jpashop.reposityory;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save (Item item){
        if (item.getId() == null) {
            em.persist(item);
            // 아이디가 없다는 것은 완전히 새로 생성한 객체라는 의미
            // 애를 완전히 신규로 등록한다는 의미
        } else {
            em.merge(item);
            // update 비슷한 건데(진짜 업데이트는 아님) 뒤에서 자세한 설명
            // id 값이 널이 아니라는 의미는 이미 디비에 등록된 값을 가져왔다는 의미.
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
