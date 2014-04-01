package com.altostratus.bionicwheels.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.AccountsReceivable;
import com.altostratus.bionicwheels.model.Customer;
import com.altostratus.bionicwheels.repository.jpa.AccountsReceivableRepository;

@Service("accountsReceivableService")
@Transactional
public class AccountsReceivableServiceImpl implements AccountsReceivableService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	AccountsReceivableRepository accountsReceivableRepository;

	@Override
	public AccountsReceivable getAccountsReceivable(Long id) {
		// TODO Auto-generated method stub
		return accountsReceivableRepository.findOne(id);
	}

	@Override
	public List<AccountsReceivable> getAccountsReceivableByCustomer(
			Customer customer) {
		// TODO Auto-generated method stub
		return accountsReceivableRepository.findByCustomer(customer);
	}

	@Override
	public List<AccountsReceivable> getAllAccountsReceivable() {
		// TODO Auto-generated method stub
		return accountsReceivableRepository.findAll();
	}

	@Override
	public List<AccountsReceivable> getDueAccountsReceivable(Date dateToday) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("select a from AccountsReceivable a where a.expectedDateReceivable < :dateToday");
		query.setParameter("dateToday", dateToday);
		List<AccountsReceivable> accounts = (List<AccountsReceivable>) query.getResultList();
		
		return accounts;
	}

	@Override
	public void removeAccountsReceivable(Long id) {
		// TODO Auto-generated method stub
		accountsReceivableRepository.delete(id);
	}

	@Override
	public AccountsReceivable saveAccountsReceivable(
			AccountsReceivable accountsReceivable) {
		// TODO Auto-generated method stub
		return accountsReceivableRepository.save(accountsReceivable);
	}

	@Override
	public AccountsReceivable getAccountsReceivableByReceiptNumber(
			String receiptNumber) {
		// TODO Auto-generated method stub
		return accountsReceivableRepository.findByReceiptNumber(receiptNumber);
	}
}
