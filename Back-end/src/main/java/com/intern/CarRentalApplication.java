package com.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Main application class for the Car Rental System.
 * Configured with reactive support, AOP capabilities and JPA repositories.
 */
@SpringBootApplication
@EnableWebFlux
@EnableAspectJAutoProxy
@EnableJpaRepositories
public class CarRentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }
}

@Autowired
private CarRentalSystemRepository carRentalSystemRepo;

@Autowired
private ParkingStallRepository parkingStallRepo;

@Autowired
private VehicleRepository vehicleRepo;

@Autowired
private BarcodeRepository barcodeRepo;

@Autowired
private BarcodeReaderRepository barcodeReaderRepo;

@Autowired
private MemberRepository memberRepo;

@Autowired
private AdditionalDriverRepository additionalDriverRepo;

@Autowired
private VehicleReservationRepository vehicleReservationRepo;

@Autowired
private BillRepository billRepo;

@Autowired
private BillItemRepository billItemRepo;

@Autowired
private NotificationRepository notificationRepo;

@Autowired
private PaymentRepository paymentRepo;

@Override
public void run(String... args) throws Exception {


	// CarRentalSystem created
	CarRentalSystem carrentalsystem1 = new CarRentalSystem();
	carrentalsystem1.setCarRentalLocation(null);
	carrentalsystem1.setName("mohit");
	carRentalSystemRepo.save(carrentalsystem1);

	// CarRentalLocation created
	CarRentalLocation carRentallocation1 = new CarRentalLocation();
	Location address1 = new Location();
	address1.setCity("Chandigarh");
	address1.setCountry("India");
	address1.setState("Chandigarh UT");
	address1.setStreetAddress("#164 vahan vihar, Sector 8");
	address1.setZipcode("4165");
	carRentallocation1.setAddress(address1);
	carRentallocation1.setName("Random123");
	carRentallocation1.setVehicle(new ArrayList<>());
	carRentallocation1.setCarRentalSystem(carrentalsystem1);
	carRentalRepo.save(carRentallocation1);

	//ParkingStall created
	ParkingStall parkingstall1 = new ParkingStall();
	parkingstall1.setLocationIdentifier("A17");
	parkingstall1.setStallNumber("42");
	parkingStallRepo.save(parkingstall1);

	//Car type vehicle created
	Car vehicle1 = new Car();
	vehicle1.setLicenseNumber("rf120");
	vehicle1.setStockNumber("an11");
	vehicle1.setPassengerCapacity(4);
	vehicle1.setHasSunroof(true);
	vehicle1.setModel("Car truckk");
	vehicle1.setMake("2001");
	vehicle1.setManufacturingYear(2021);
	vehicle1.setMileage(10);
	vehicle1.setBarcode("77r77");
	vehicle1.setStatus(VehicleStatus.Lost);
	vehicle1.setType(CarType.Economy);
	vehicle1.setCarRentalLocation(carRentallocation1);
	vehicle1.setParkingstall(parkingstall1);
	vehicleRepo.save(vehicle1);


	//two dates for differentiation
	String sDate1 = "31/12/1998";
	Date date1 = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);

	String sDate2 = "19/01/1999";
	Date date2 = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);

	//barcode created
	Barcode barcode1 = new Barcode();
	barcode1.setBarcode("1a1b1");
	barcode1.setIssuedAt(date1);
	barcode1.setActive(true);
	barcode1.setVehicle(vehicle1);
	barcodeRepo.save(barcode1);

	//barcodereader created
	BarcodeReader barcodereader1 = new BarcodeReader();
	barcodereader1.setBarcode(barcode1);
	barcodereader1.setRegisteredAt(date2);
	barcodereader1.setActive(true);
	barcodeReaderRepo.save(barcodereader1);


	//Address Creation
	Location address2 = new Location();
	address2.setCity("faridabad");
	address2.setCountry("India");
	address2.setState("Haryana");
	address2.setStreetAddress("#198 bharat vihar, Sector 45, Rohini");
	address2.setZipcode("160032");

	//person Creation
	Person person1=new Person();
	person1.setName("Thor Odinson");
	person1.setPhone("9845621781");
	person1.setEmail("thor123@kries.com");
	person1.setAddress(address2);


	/*
	 * for Vehicle Reservation Creation a object of account is needed
	 */
	/*
	//Member creation
	Member member1=new Member();
	member1.setPerson(person1);
	member1.setASstatus(AccountStatus.Active);
	member1.setDriverLicenseNumber("485Ad51c");

	String sDate3 = "12/01/2029";
	Date date3 = (Date) new SimpleDateFormat("dd/MM/yyyy").parse(sDate3);


	member1.setDriverLicenseExpiry(date3);

	member1.setPassword("Password@123");
	member1.setVehicle(vehicle1);
	member1.setVehiclereservation(null);
	memberRepo.save(member1);


	//vehicle reservation is created
	VehicleReservation vehiclereservation1 = new VehicleReservation();

	vehiclereservation1.setAccount(member1);

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	String sDate4 = "11/04/2022 12:00";
	Date date4 =  dateFormat.parse(sDate4);

	String sDate5 = "15/05/2022 12:00";
	Date date5 =  dateFormat.parse(sDate5);

	String sDate6 = "17/05/2022 14:00";
	Date date6 =  dateFormat.parse(sDate6);


	//dates to check inventory of vehicles
	vehiclereservation1.setCreationDate(date4);
	vehiclereservation1.setDueDate(date5);
	vehiclereservation1.setReturnDate(date6);

	//vehicle set
	vehiclereservation1.setVehicle(vehicle1);

	//location data+status
	vehiclereservation1.setPickupLocationName("Panchkula");
	vehiclereservation1.setReturnLocationName("Patiala");


	//additionaldriver created
	AdditionalDriver additionaldriver1 = new AdditionalDriver();
	additionaldriver1.setDriverID("A2B5C6");
	additionaldriver1.setPerson(person1);
	additionaldriver1.setVehicleReservation(vehiclereservation1);


	vehiclereservation1.setAdditionaldriver(new ArrayList<>());
	vehiclereservation1.getAdditionaldriver().add(additionaldriver1);


	//extra services //null for now
	vehiclereservation1.setService(null);
	vehiclereservation1.setRentalinsurance(null);
	vehiclereservation1.setEquipment(null);


	//for future create a map such that no two reservation numbers are same [use the code from shopping cart]
	vehiclereservation1.setReservationNumber("grlv98");


	//Creating Bill
		//Bill
		Bill bill1 = new Bill();

			BillItem billItem1=new BillItem();
			billItem1.setService("wipers");
			billItem1.setAmount(420);

		billItem1.setBill(bill1);

			BillItem billItem2=new BillItem();
			billItem2.setService("Tote bag");
			billItem2.setAmount(69);

		billItem2.setBill(bill1);
		bill1.setBillitem(new ArrayList<>());
		bill1.getBillitem().add(billItem1);
		bill1.getBillitem().add(billItem2);
		bill1.setTotalAmount(billItem1.getAmount()+billItem2.getAmount());

	vehiclereservation1.setBill(bill1);


	String sDate7 = "11/04/2022 13:00";
	Date date7 =  dateFormat.parse(sDate7);

	//payment after creation of bill
	CheckTransaction payment1 = new CheckTransaction();
		payment1.setBankName("State Bank of India");
		payment1.setCheckNumber("AZ42F12S854GSD2");
		payment1.setBill(bill1);
		payment1.setAmount(bill1.getTotalAmount());
		payment1.setCreationDate(date7);
		payment1.setStatus(PaymentStatus.Completed);

	if(payment1.getStatus().equals(PaymentStatus.Completed)){
		vehiclereservation1.setRSstatus(ReservationStatus.Confirmed);
	}

	String sDate8 = "11/04/2022 15:00";
	Date date8 =  dateFormat.parse(sDate8);

	//Creating Notification
		SMSNotification mobilenotification=new SMSNotification();
		    mobilenotification.setAddress(address2);
		    mobilenotification.setPhoneNumber(person1.getPhone());

			mobilenotification.setVehiclereservation(vehiclereservation1);
			mobilenotification.setBill(bill1);
			mobilenotification.setContent("Your rental has been Confirmed.");
			mobilenotification.setCreatedOn(date8);

		EmailNotification webnotification=new EmailNotification();
			webnotification.setEmail(person1.getEmail());
			webnotification.setVehiclereservation(vehiclereservation1);
			webnotification.setBill(bill1);
			webnotification.setContent("Your rental has been Confirmed.");
			webnotification.setCreatedOn(date8);
			webnotification.setPhoneNumber(person1.getPhone());

			//sending notification
	vehiclereservation1.setNotification(new ArrayList<>());
	vehiclereservation1.getNotification().add(webnotification);
	vehiclereservation1.getNotification().add(mobilenotification);

	//vehicleReservationRepo.save(vehiclereservation1);

	billRepo.save(bill1);

	billItemRepo.save(billItem1);
	billItemRepo.save(billItem2);

	paymentRepo.save(payment1);

	vehicleReservationRepo.save(vehiclereservation1);
	billRepo.save(bill1);
	additionalDriverRepo.save(additionaldriver1);


	notificationRepo.save(mobilenotification);
	notificationRepo.save(webnotification);

}
*/