package com.mb.assessment.application.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.assessment.application.dao.TransactionRepository;
import com.mb.assessment.application.dto.TransactionDTO;
import com.mb.assessment.application.entity.Transaction;
import com.mb.assessment.application.rest.exception.TransactionNotFoundException;
import com.mb.assessment.application.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionRestController {

	@Autowired
	private TransactionService trxService;

	@GetMapping("/search")
	public Page<Transaction> retrieveTransactionByCriterias(@RequestParam String account, @RequestParam String desc,
			@RequestParam String custId, @RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "10") Integer size) {

		return trxService.getTransactionByAcccountNumberOrDescriptionOrCustomerID(account, desc, custId, page, size);
	}
	
	@GetMapping("/{id}")
    public EntityModel<Transaction> retrieveTransactionById(@PathVariable long id) {
        Optional<Transaction> transaction = trxService.getTransactionById(id);
        if (transaction.isEmpty())
            throw new TransactionNotFoundException("id-" + id);

        return EntityModel.of(transaction.get());
    }

	@PutMapping("/updateDesc")
    public ResponseEntity<Object> updateTransaction(@Valid @RequestBody TransactionDTO transaction) {
	    Transaction result = trxService.save(transaction);
	    if (result == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
