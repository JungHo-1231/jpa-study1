package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.thymeleaf.util.ArrayUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 상속은 받았지만 싱글 테이블 전략으로 한 테이블에 상속 받은 칼럼들이 전부다 들어감
@DiscriminatorColumn(name = "dtype") // 한 테이블에 다 들어가지만
// 그래도 데이터 베이스 입장에선 무슨 값이 들어왔는지 알아야 하기 때문에에@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==

    /*
    *   stock 증가
    */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    // 객체 지향적으로 생각해보면 데이터를 가지고 있는 쪽에서 비지니스 메서드가 있는 것이 가장 좋다.

    /*
    *  stock 감소
    * */
    public void removeStock(int quantity){
        int restStock= this.stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity= restStock;
    }

}
