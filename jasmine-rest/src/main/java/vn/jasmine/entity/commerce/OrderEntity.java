package vn.jasmine.entity.commerce;

import vn.jasmine.enums.commerce.OrderStatus;
import vn.jasmine.enums.commerce.PaymentMethod;
import vn.jasmine.enums.commerce.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.domain.Persistable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class OrderEntity implements Persistable<UUID> {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;

    @Column(name = "active_flg")
    private Byte activeFlg;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return getId() == null;
    }

}
