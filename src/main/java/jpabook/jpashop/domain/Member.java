package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded // jpa의 내장타입
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member" )
    private List<Order> orders = new ArrayList<>();


}
