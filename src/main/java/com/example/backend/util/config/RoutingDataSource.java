package com.example.backend.util.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {
	@Nullable
	@Override
	protected Object determineCurrentLookupKey() {
		//비용 문제로 읽기 전용 복제본 비활성화.
		String lookupKey = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "master" : "master";
		log.info("Current DataSource is {}", lookupKey);
		return lookupKey;
	}
}
