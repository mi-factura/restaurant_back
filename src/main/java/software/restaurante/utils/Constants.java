package software.restaurante.utils;

import java.math.BigDecimal;

public interface Constants {

    interface Tax {
        BigDecimal IVA = BigDecimal.valueOf(0.19);

        BigDecimal INC = BigDecimal.valueOf(0.08);
    }
}
