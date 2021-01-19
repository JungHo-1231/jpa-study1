package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.reposityory.MemberRepository;
import jpabook.jpashop.reposityory.ItemRepository;
import jpabook.jpashop.reposityory.OrderRepository;
import jpabook.jpashop.reposityory.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        //엔티디 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.creteOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        // Delivery, OrderItem은 order.save할 때 자동으로 persist 된다.
        // 왜냐하면 castcade가 걸려 있기 때문에
        // 그럼 언제 castcade를 써야하냐?
        // order가 관리할 때만, orderItem은 다른 것을 참조할 수는 있지만
        // 다른 객체가 orderItem을 참조하는 경우는 없다. 이럴때는 사용이 가능하다.
        // 라이프 라이클에 대해서 동일하게 관리할 때는 가능하다.
        // 다른 것이 참조할 수 없는 프라이빗인 경우는 가능.
        // 예를 들어서 delivery가 중요하다. 그래서 다른 곳에서 딜리버리를 참조해서 갔다가 쓰는 경우에는
        // castcade를 사용할 수 없다.
        // 처음에 이해가 안된다면 사용하지 말고, 나중에 사용하면서 감을 잡으면 그 때 사용한다.
        return order.getId();
    }

    /*
    *  주문 최소
    * */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        // jpa를 활용하면 데이터만 바꾸면 jpa가 알아서 변경 감지를 통해서 알아서 업데이트 쿼리를 날려준다.
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }
}
