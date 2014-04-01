package com.altostratus.bionicwheels.service;

import java.util.Date;
import java.util.List;

import com.altostratus.bionicwheels.model.AccountsReceivable;
import com.altostratus.bionicwheels.model.Customer;

public interface AccountsReceivableService {

	public AccountsReceivable saveAccountsReceivable(AccountsReceivable accountsReceivable);
	
	public AccountsReceivable getAccountsReceivable(Long id);
	
	public List<AccountsReceivable> getAllAccountsReceivable();
	
	public void removeAccountsReceivable(Long id);
	
	public List<AccountsReceivable> getDueAccountsReceivable(Date dateToday);
	
	public List<AccountsReceivable> getAccountsReceivableByCustomer(Customer customer);
	
	public AccountsReceivable getAccountsReceivableByReceiptNumber(String receiptNumber);
	
}

