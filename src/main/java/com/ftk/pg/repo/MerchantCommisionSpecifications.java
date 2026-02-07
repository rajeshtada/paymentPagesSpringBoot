package com.ftk.pg.repo;

import org.springframework.data.jpa.domain.Specification;

import com.ftk.pg.modal.MerchantCommision;

import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MerchantCommisionSpecifications {

    public static Specification<MerchantCommision> searchMerchantCommision(MerchantCommision commission) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add default condition
            predicates.add(builder.equal(root.get("isDefault"), commission.isDefault()));

            // Add merchantId condition
            predicates.add(builder.equal(root.get("merchantId"), commission.getMerchantId()));

            // Add productType condition (if provided)
            if (commission.getProductType() != null && !commission.getProductType().trim().isEmpty()) {
                predicates.add(builder.equal(root.get("productType"), commission.getProductType()));
            }

            // Add paymentMode condition (if provided)
            if (commission.getPaymentMode() != null && !commission.getPaymentMode().trim().isEmpty()) {
                predicates.add(builder.equal(root.get("paymentMode"), commission.getPaymentMode()));
            }

            // Add subType condition (if provided)
            if (commission.getSubType() != null && !commission.getSubType().trim().isEmpty()) {
                predicates.add(builder.equal(root.get("subType"), commission.getSubType()));
            }

            // Add commission value condition (if provided)
            if (commission.getCommisionvalue() != null) {
                Predicate fromAmountPredicate = builder.lessThanOrEqualTo(root.get("fromAmount"), commission.getCommisionvalue());
                Predicate toAmountPredicate = builder.greaterThanOrEqualTo(root.get("toAmount"), commission.getCommisionvalue());
                Predicate amountPredicate = builder.and(fromAmountPredicate, toAmountPredicate);

                // Handle null values for fromAmount and toAmount
                Predicate nullPredicate = builder.isNull(root.get("fromAmount"));
                Predicate finalAmountPredicate = builder.or(nullPredicate, amountPredicate);
                predicates.add(finalAmountPredicate);
            }

            // Combine all predicates with AND
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
