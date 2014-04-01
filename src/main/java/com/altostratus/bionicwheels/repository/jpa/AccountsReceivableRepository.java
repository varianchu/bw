package com.altostratus.bionicwheels.repository.jpa;

import java.util.Date;	
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.AccountsReceivable;
import com.altostratus.bionicwheels.model.Customer;

public interface AccountsReceivableRepository extends JpaRepository<AccountsReceivable, Long>{
	
	public List<AccountsReceivable> findByExpectedDateReceivableLessThan(Date dateToday);
	
	public List<AccountsReceivable> findByCustomer(Customer customer);
	
	public AccountsReceivable findByReceiptNumber(String receiptNumber);
	
}
