package com.mb.assessment.application.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mb.assessment.application.dao.TransactionRepository;
import com.mb.assessment.application.dto.TransactionDTO;
import com.mb.assessment.application.entity.Transaction;
import com.mb.assessment.application.rest.exception.ConcurrentUpdateException;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

	@Autowired
	TransactionRepository trxRepository;

	public Optional<Transaction> getTransactionById(Long id) {
		return trxRepository.findById(id);
	}

	@Transactional
	public Transaction save(TransactionDTO trxDTO) {
		Optional<Transaction> trxEntity = getTransactionById(trxDTO.getId());

		if (trxEntity.isEmpty())
			return null;
		if(!trxEntity.get().getDescription().equals(trxDTO.getOldDescription()))
			throw new ConcurrentUpdateException("Someone else has already modified the record you were working on");

		trxEntity.get().setDescription(trxDTO.getDescription());
		return trxRepository.save(trxEntity.get());
	}

	public Page<Transaction> getTransactionByAcccountNumberOrDescriptionOrCustomerID(String accountNumber,
			String description, String customerId, Integer page, Integer size) {
		return trxRepository.findAll(specBuilder(accountNumber, description, customerId), PageRequest.of(page, size));
	}

	private Specification<Transaction> specBuilder(String accountNumber, String description, String customerId) {
		Specification<Transaction> specification = Specification.where(null);

		if (StringUtils.hasText(description)) {
			description = "%" +description+"%";
			specification = specification.or(TransactionSpecification.descriptionLike(description));
		}

		if (StringUtils.hasText(customerId)) {
			specification = specification.or(TransactionSpecification.customerIdEqual(customerId));
		}

		if (StringUtils.hasText(accountNumber)) {
			List<String> list = Arrays.asList(accountNumber.split(","));
			specification = specification.or(TransactionSpecification.accountNumberIn(list));
		}

		return specification;
	}

}
