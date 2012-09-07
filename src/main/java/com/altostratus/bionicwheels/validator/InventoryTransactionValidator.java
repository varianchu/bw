package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.service.InventoryTransactionService;

@Component
public class InventoryTransactionValidator implements Validator {

	@Autowired
	InventoryTransactionService its;

	@Override
	public boolean supports(Class inventoryTransaction) {
		// TODO Auto-generated method stub
		return InventoryTransaction.class.equals(inventoryTransaction);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		InventoryTransaction inventoryTransaction = (InventoryTransaction) target;

		for (InventoryTransaction it : its.getAllInventoryTransactions()) {
			if (it.getReferenceNumber().equalsIgnoreCase(
					inventoryTransaction.getReferenceNumber())) {
				errors.rejectValue(
						"referenceNumber",
						"field.required",
						"Reference Number should be unique, reference number was used by a different transaction");
				break;
			}
		}

	}
}
