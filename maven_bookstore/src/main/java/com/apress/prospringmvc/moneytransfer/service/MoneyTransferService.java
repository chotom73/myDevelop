package com.apress.prospringmvc.moneytransfer.service;

import java.math.BigDecimal;

import com.apress.prospringmvc.moneytransfer.domain.Transaction;

public interface MoneyTransferService {

	Transaction transfer(String source, String target, BigDecimal amount);
}
