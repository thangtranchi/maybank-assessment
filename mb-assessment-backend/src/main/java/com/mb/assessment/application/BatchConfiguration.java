package com.mb.assessment.application;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import com.mb.assessment.application.entity.Transaction;
import com.mb.assessment.application.writer.TransactionItemWriter;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration extends DefaultBatchConfiguration {

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public Job importFileJob1(
			// EmpJobExecutionListener listener,
			Step importTransctionStep) throws Exception {
		return new JobBuilder("importTransctionStep", jobRepository()).start(importTransactionFileStep()).build();
	}

	@Bean
	public Step importTransactionFileStep() {
		return new StepBuilder("importTransactionFileStep", jobRepository())
				.<Transaction, Transaction>chunk(100, transactionManager).reader(transactionItemReader())
				.writer(writer()).build();
	}

	@Bean
	@StepScope
	public TransactionItemWriter writer() {
		return new TransactionItemWriter();
	}

	@Bean
	public FlatFileItemReader<Transaction> transactionItemReader() {
		return new FlatFileItemReaderBuilder<Transaction>().name("transactionItemReader")
				.resource(new ClassPathResource("dataSource.txt")).delimited().delimiter("|")
				.names(new String[] { "accountNumber", "trxAmount", "description", "trxDate", "trxTime", "customerId" })
				.linesToSkip(1).fieldSetMapper(new BeanWrapperFieldSetMapper<Transaction>() {
					{
						setTargetType(Transaction.class);
					}
				}).build();
	}

}