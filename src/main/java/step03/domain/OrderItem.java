package step03.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // @Column(name = "order_id")
    // private Long orderId;
    // @Column(name = "item_id")
    // private Long itemId;
    // 연관관계 매핑(참조를 사용하도록) 변경
    // N:1 양방향 연관관계 매핑
    // 객체의 두 관계중 하나를 연관관계의 주인으로 지정한다. => 외래 키가 있는 곳을 주인으로 한다.
    // 연관관계의 주인만이 외래 키를 관리(등록, 수정)
    // 주인이 아닌쪽은 읽기만 가능
    // 주인은 mappedBy 속성 사용하지 않는다.
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
