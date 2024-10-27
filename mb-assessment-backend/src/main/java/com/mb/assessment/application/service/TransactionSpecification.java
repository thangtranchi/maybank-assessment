package com.mb.assessment.application.service;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import com.mb.assessment.application.entity.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TransactionSpecification {

    // #### EQUALITY
    public static Specification<Transaction> customerIdEqual(String customerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("customerId"), customerId);
    }

    public static Specification<Transaction> descriptionLike(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("description"), description);
    }
    
	public static Specification<Transaction> accountNumberIn(List<String> listToSearch) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder .in(root.get("accountNumber"))
                .value(listToSearch);
    }

 
}
