package com.example.orderservice.jpa;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(force = true)
@Entity
@Table(name = "product_order") // order는 H2 DBMS에서 예약어이므로 테이블 이름을 product_order로 정함
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    public final String orderId;

    @Column(nullable = false, length = 120)
    public final String productId;

    @Column(nullable = false)
    public final int quantity;

    @Column(nullable = false)
    public final int unitPrice;

    @Column(nullable = false)
    public final int totalPrice;

    @Column(nullable = false)
    public final String userId;

    @Column(nullable = false, updatable = false) // cf. insertable = false element 추가할 경우 INSERT 문에서 해당 컬럼을 제외한다고 함
    @CreationTimestamp // cf. @ColumnDefault(value = "CURRENT_TIMESTAMP")를 사용할 경우, 저장된 entity의 createdAt의 DB 값을 반영하기 위해선 entity manager를 이용한 refresh 필요
    LocalDateTime createdAt;

    public OrderEntity(String productId, int quantity, int unitPrice, String userId) {
        this.orderId = UUID.randomUUID().toString();
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
