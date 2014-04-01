import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.AccountsReceivable;
import com.altostratus.bionicwheels.model.Car;
import com.altostratus.bionicwheels.model.Customer;
import com.altostratus.bionicwheels.service.AccountsReceivableService;
import com.altostratus.bionicwheels.service.CustomerCarService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:META-INF/spring/*.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional()
public class CustomerCarTest {

	private final Logger logger = LoggerFactory
			.getLogger(CustomerCarTest.class);

	@Autowired
	CustomerCarService customerCarService;

	@Autowired
	AccountsReceivableService accountsReceivableService;

	@Before
	public void preMethodSetup() {
		Customer varian = new Customer();
		Customer kobe = new Customer();
		Customer lebron = new Customer();
		Customer poor = new Customer();

		Car prado = new Car();
		Car crv = new Car();
		Car veloster = new Car();
		Car enzo = new Car();
		Car veyron = new Car();
		Car cruze = new Car();
		Car mazda3 = new Car();
		Car cx7 = new Car();
		Car landcruiser = new Car();

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String date1 = "01-01-2012";
		String date2 = "01-30-2012";
		String date3 = "02-05-2012";
		String date4 = "01-15-2012";
		Date nextOil1 = null;
		Date nextOil2 = null;
		Date nextOil3 = null;
		Date nextOil4 = null;
		try {
			nextOil1 = sdf.parse(date1);
			nextOil2 = sdf.parse(date2);
			nextOil3 = sdf.parse(date3);
			nextOil4 = sdf.parse(date4);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// setup customer
		varian.setCustomerName("VARIAN CHU");
		varian.setContactNumber("911-20-63");
		varian.setCityLocation("QUEZON CITY");
		varian = (Customer) customerCarService.saveCustomer(varian);

		kobe.setCustomerName("KOBE BRYANT");
		kobe.setContactNumber("248-24-08");
		kobe.setCityLocation("QUEZON CITY");
		kobe = (Customer) customerCarService.saveCustomer(kobe);

		lebron.setCustomerName("LEBRON JAMES");
		lebron.setContactNumber("236-23-06");
		lebron.setCityLocation("MAKATI CITY");
		lebron = (Customer) customerCarService.saveCustomer(lebron);

		poor.setCustomerName("KAWAWA BOY");
		poor.setContactNumber("000-00-00");
		poor.setCityLocation("TAMBAY CITY");
		poor = (Customer) customerCarService.saveCustomer(poor);

		// setup car
		prado.setCarMake("TOYOTA");
		prado.setCarModel("PRADO");
		prado.setCustomer(lebron);
		prado.setNextChangeOil(nextOil1);
		prado = (Car) customerCarService.saveCar(prado);

		crv.setCarMake("HONDA");
		crv.setCarModel("CRV");
		crv.setCustomer(lebron);
		crv.setNextChangeOil(nextOil2);
		crv = (Car) customerCarService.saveCar(crv);

		veloster.setCarMake("HYUNDAI");
		veloster.setCarModel("VELOSTER 2012");
		veloster.setCustomer(varian);
		veloster.setNextChangeOil(nextOil3);
		veloster = (Car) customerCarService.saveCar(veloster);

		enzo.setCarMake("FERRARI");
		enzo.setCarModel("ENZO");
		enzo.setNextChangeOil(nextOil4);
		enzo.setCustomer(kobe);
		enzo = (Car) customerCarService.saveCar(enzo);

		veyron.setCarMake("BUGATTI");
		veyron.setCarModel("VEYRON");
		veyron.setPlateNumber("WAT-266");
		veyron.setCustomer(kobe);
		veyron = (Car) customerCarService.saveCar(veyron);

		cruze.setCarMake("CHEVROLET");
		cruze.setCarModel("CRUZE");
		cruze.setCustomer(poor);
		cruze = (Car) customerCarService.saveCar(cruze);

		mazda3.setCarMake("MAZDA");
		mazda3.setCarModel("MAZDA-3");
		mazda3.setCustomer(varian);
		mazda3 = (Car) customerCarService.saveCar(mazda3);

		cx7.setCarMake("MAZDA");
		cx7.setCarModel("CX-7");
		cx7.setCustomer(kobe);
		cx7 = (Car) customerCarService.saveCar(cx7);

		landcruiser.setCarMake("TOYOTA");
		landcruiser.setCarModel("LAND CRUISER");
		landcruiser.setNextChangeOil(nextOil1);
		landcruiser.setCustomer(lebron);
		landcruiser = (Car) customerCarService.saveCar(landcruiser);

		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		String date11 = "01-01-2012";
		String date21 = "01-30-2012";
		String date12 = "01-01-2013";
		String date22 = "01-30-2013";
		Date startDate = null;
		Date endDate = null;
		Date startDate1 = null;
		Date endDate1 = null;
		try {
			startDate = sdf1.parse(date11);
			endDate = sdf1.parse(date21);
			startDate1 = sdf.parse(date12);
			endDate1 = sdf.parse(date22);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(startDate);
		System.out.println(startDate1);

		AccountsReceivable account1 = new AccountsReceivable();
		AccountsReceivable account2 = new AccountsReceivable();
		AccountsReceivable account3 = new AccountsReceivable();

		account1.setAmount(5000.0);
		account1.setCustomer(kobe);
		account1.setDateCreated(startDate);
		account1.setExpectedDateReceivable(endDate);
		account1.setReceiptNumber("12345");

		account1 = (AccountsReceivable) accountsReceivableService
				.saveAccountsReceivable(account1);

		account2.setAmount(15000.0);
		account2.setCustomer(lebron);
		account2.setDateCreated(startDate1);
		account2.setExpectedDateReceivable(endDate1);
		account2.setReceiptNumber("55555");

		account2 = (AccountsReceivable) accountsReceivableService
				.saveAccountsReceivable(account2);
	}

	@Test
	public void testSizeOfAccountsReceivable() {
		logger.info("Checking the number of accounts receivable");
		Date dateToday = new Date();
		List<AccountsReceivable> accounts = accountsReceivableService
				.getDueAccountsReceivable(dateToday);

		logger.info("Accounts size: " + accounts.size());

		assertTrue(accounts.size() == 2);
	}

	@Test
	public void testSizeOfCustomers() {
		logger.debug("Checking number of customers");
		assertTrue(customerCarService.getAllCustomers().size() == 4);

	}

	@Test
	public void testSizeOfCars() {
		logger.debug("Checking number of cars");
		assertTrue(customerCarService.getAllCars().size() == 9);
		assertFalse(customerCarService.getAllCars().size() == 0);
	}

	@Test
	public void testDateOfNextChangeOil() {

		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		String date1 = "01-01-2012";
		String date2 = "01-30-2012";
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sdf.parse(date1);
			endDate = sdf.parse(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("next change oil customers size: "
				+ customerCarService.getAllCarsWithNextChangeOil(startDate,
						endDate).size());

		for (Car c : customerCarService.getAllCarsWithNextChangeOil(startDate,
				endDate)) {
			logger.info("Customer name is: "
					+ c.getCustomer().getCustomerName());
		}

		assertTrue(customerCarService.getAllCarsWithNextChangeOil(startDate,
				endDate).size() == 4);
	}

	@Test
	public void testGetCarMakeReturnsCustomers() {

		logger.info("car make - toyota's - number of cars: "
				+ customerCarService.getAllCarModelsWithCarMake("TOYOTA")
						.size());

		for (Car c : customerCarService.getAllCarModelsWithCarMake("TOYOTA")) {
			logger.info("Car Make: TOYOTA \n \t \t \t \t \t Car Model: "
					+ c.getCarModel());
			logger.info("Owner: " + c.getCustomer().getCustomerName());
		}

		assertTrue(customerCarService.getAllCarModelsWithCarMake("TOYOTA")
				.size() == 2);

		for (Customer c : customerCarService
				.getAllCustomersWithCarMake("TOYOTA")) {
			logger.info("CUSTOMER: " + c.getCustomerName() + " of "
					+ c.getCityLocation());
		}

		for (Customer c : customerCarService
				.getAllCustomersWithCarMake("MAZDA")) {
			logger.info("CUSTOMER: " + c.getCustomerName() + " of "
					+ c.getCityLocation());
		}

		assertTrue(customerCarService.getAllCustomersWithCarMake("TOYOTA")
				.size() == 1);

		assertTrue(customerCarService.getAllCustomersWithCarMake("MAZDA")
				.size() == 2);
	}

	@Test
	public void testGetCustomerByName() {
		Customer fakeKobe = new Customer();

		fakeKobe.setCustomerName("KOBE BRYANT");
		fakeKobe.setContactNumber("338-10-24");
		fakeKobe.setCityLocation("QUEZON CITY");
		fakeKobe = (Customer) customerCarService.saveCustomer(fakeKobe);

		logger.info("(TEST CUSTOMER NAME) Customer is "
				+ customerCarService.getCustomerByName("LEBRON JAMES").get(0)
						.getCustomerName()
				+ " and its ID is "
				+ customerCarService.getCustomerByName("LEBRON JAMES").get(0)
						.getId());

		assertEquals(customerCarService.getCustomerByName("LEBRON JAMES")
				.get(0).getCustomerName(), "LEBRON JAMES");

		logger.info("(TEST CUSTOMER NAME FOR FAKE KOBE) Customer is "
				+ customerCarService.getCustomerByName("KOBE BRYANT").get(1)
						.getCustomerName()
				+ " and its contact number is "
				+ customerCarService.getCustomerByName("KOBE BRYANT").get(1)
						.getContactNumber());
	}

	@Test
	public void testGetCustomersWithCityLocation() {
		String loc1 = "QUEZON CITY";
		String loc2 = "MAKATI CITY";
		String loc3 = "n/a";
		assertTrue(customerCarService.getAllCustomersByLocation(loc1).size() == 2);
		assertTrue(customerCarService.getAllCustomersByLocation(loc2).size() == 1);
		assertTrue(customerCarService.getAllCustomersByLocation(loc3).size() == 0);
	}
}
