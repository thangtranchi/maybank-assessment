package com.mb.assessment.application.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.mb.assessment.application.dao.TransactionRepository;
import com.mb.assessment.application.entity.Transaction;

public class TransactionItemWriter implements ItemWriter<Transaction> {

	@Autowired
	TransactionRepository trxRepository;
	
	@Override
	public void write(Chunk<? extends Transaction> chunk) throws Exception {
		for(Transaction t: chunk) {
			trxRepository.save(t);
		}
	}
 

 
}
