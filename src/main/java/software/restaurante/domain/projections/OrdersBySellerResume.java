package software.restaurante.domain.projections;

import java.math.BigDecimal;

@SuppressWarnings("unused")
public interface OrdersBySellerResume {

    String getSellerId();
    String getSellerFirstName();
    String getSellerLastName();
    Long getOrdersQuantity();
    BigDecimal getTotalAmount();

}
