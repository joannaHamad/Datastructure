import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class Demo {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		int rating = 0;
		OrderList orders = new OrderList();
		AVLProducts products = new AVLProducts();
		CustomerList customers = new CustomerList();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
      LinkedReviews  reviews = new LinkedReviews() ;

//Reading customer files

		try {
			BufferedReader br = new BufferedReader(new FileReader("customers.csv"));
			String line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] r = line.split(",");

				int id = Integer.parseInt(r[0].trim());
				String name = r[1].trim();
				String email = r[2].trim();

				customers.registerCustomer(id, name, email);
			}
			br.close();

		} catch (Exception e) {
			System.out.println("Error reading customers file.");
		} // end customer file

// reading product file

		try {

			BufferedReader br = new BufferedReader(new FileReader("prodcuts.csv"));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] r = line.split(",");
				int id = Integer.parseInt(r[0].trim());
				String name = r[1].trim();
				double price = Double.parseDouble(r[2].trim());
				int stock = Integer.parseInt(r[3].trim());

				Product p = new Product(id, name, price, stock);
				products.enqueue(p);

			}

			br.close();

		} catch (Exception e) {
			System.out.println("Error reading products file.");
		} // end reading from products file

//reading reviews
		try {

			BufferedReader br = new BufferedReader(new FileReader("reviews.csv"));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] r = line.split(",");
				int reviewID = Integer.parseInt(r[0].trim());
				int productID = Integer.parseInt(r[1].trim());
				int customerID = Integer.parseInt(r[2].trim());
				int rate = Integer.parseInt(r[3].trim());
				String comment = r[4].trim();

				Product p = products.searchByID(productID);

				if (p != null) {
					p.addReviewForProduct(rate, comment, customerID, productID);

				}
            Review re = new Review(rate, comment, reviewID, customerID, productID) ;
            reviews.addReview(re) ;

			}

			br.close();

		} catch (Exception e) {
			System.out.println("Error reading reviews file.");
		} // end reading from reviews file
      
      AVLProducts newQue = new AVLProducts() ;
      
      while( !products.isEmpty() ){
      PQElement e = products.Remove() ;
      newQue.enqueue(e.product) ;
      }
      
      products = newQue ;

//reading order files

		try {
			BufferedReader br = new BufferedReader(new FileReader("orders.csv"));
			String line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] r = line.split(",");

				int orderId = Integer.parseInt(r[0].trim());
				int custId = Integer.parseInt(r[1].trim());
				String[] productIds = r[2].trim().split(";");
				double total = Double.parseDouble(r[3].trim());
				Date orderDate = date.parse(r[4].trim());
				String status = r[5].trim();

				Customer c = customers.findCustomer(custId);
				if (c == null)
					continue;

				AVLProducts productsInOrder = new AVLProducts();
				for (int i = 0; i < productIds.length; i++) {
					String strPId = productIds[i].trim();
					strPId = strPId.replace("\"", "");
					int pId = Integer.parseInt(strPId);

					Product p = products.searchByID(pId);
					if (p != null)
						productsInOrder.enqueue(p);
				}

				Order ord = new Order(orderId, c, productsInOrder, total, orderDate, status);
				orders.createOrder(ord);
            c.placeOrder(ord) ;
			}

			br.close();
		} catch (Exception e) {
			System.out.println("Error reading orders file.");
			e.printStackTrace();
		} // end reading order files

		int mainChoice;
// start main menu
		do {
			System.out.println("==== MAIN MENU ===");
			System.out.println("1. Manager view.");
			System.out.println("2. Customer view.");
			System.out.println("3. Exit.");
			mainChoice = input.nextInt();

			switch (mainChoice) {
			case 1:
				// Manger menu
				int managerChoice;
            do {  //was after printing made it before
				System.out.println("==== MANAGER VIEW ===");
				System.out.println("1. Add product.");
				System.out.println("2. Delete a product.");
				System.out.println("3. Show All orders between two dates.");
				System.out.println("4. For 2 customers Show list of common products with rating > 4/5.");
				System.out.println("5. Update order status.");
				System.out.println("6. Browse products.");
				System.out.println("7. Browse customers.");
				System.out.println("8. Print out of stock products.");
				System.out.println("9. Exit.");

				managerChoice = input.nextInt();

				
					switch (managerChoice) {
					case 1: // adding a product CHECKED
						System.out.println("For adding a product enter the following..");
						System.out.println("ID: ");

						int id = input.nextInt();
						input.nextLine();
                  
                  while(products.searchByID(id) != null)
                  {
						System.out.println("Product: " + products.searchByID(id).productName+ " exists, ID must be unique!\nTry again. ");
               	System.out.println("ID: ");
                  id = input.nextInt();
						input.nextLine();
                  }

						System.out.println("Name: ");
						String name = input.nextLine();
						System.out.println("Price: ");
						double price = input.nextDouble();
						System.out.println("Stock: ");
						int stock = input.nextInt();
						Product p1 = new Product(id, name, price, stock);
						products.enqueue(p1);
						System.out.println("Product: " + p1.productName + " with ID: "
								+ p1.productId
								+ " has been added successfully.");
						
						break;

					case 2: // deleting a product CHECKED
						System.out.println("For deleting a product enter the following..");
						System.out.println("ID: ");
						int productId = input.nextInt();
						if (!products.isEmpty()) {

							Product p3 = products.searchByID(productId);

							while (p3 == null) {
								System.out.println("Product: " + productId + " does not exist.\nEnter again.");
								System.out.println("ID: ");
								productId = input.nextInt();
								p3 = products.searchByID(productId);
							}

							PQElement deleted = products.dequeue(p3);
							System.out.println("Product: " + deleted.product.productName + " with ID: "
									+ deleted.product.productId + " with average ratings: " + deleted.priority
									+ " has been deleted successfully.");
						} else
							System.out.println("You have not added any products yet.");

						break;

					case 3: // DIDNT CHECK
						// Show All orders between two dates
						
						if(orders.empty())
							System.out.println("No orders have been made yet.");
						
						else {
						Date startDate = null;
						Date endDate = null;
						System.out.println("Enter the start date of the search: in the format yyyy-mm-dd");
						try {
							startDate = date.parse(input.next());
						} catch (Exception e) {
							System.out.println("An error has occured.");
						}

						System.out.println("Enter the end date of the search: in the format yyyy-mm-dd");
						try {
							endDate = date.parse(input.next());
						} catch (Exception e) {
							System.out.println("An error has occured.");
						}
								
						orders.PrintBetweenTwoDates(startDate, endDate); 
						}
						break;

					case 4: // For 2 customers Show list of common products with rating > 4/5.


						System.out.println("Enter the first customer's ID: ");
						int custID1 = input.nextInt();
						System.out.println("Enter the second customer's ID: ");
						int custID2 = input.nextInt();
                 
						
                  if (!customers.empty()) {
							Customer cust1 = customers.findCustomer(custID1);
							Customer cust2 = customers.findCustomer(custID2);
							while (cust1 == null || cust2 == null) {
								System.out.println(
										"One of the customers that you have entered does not exist.\nTry again.");
								System.out.print("Enter the first customer's ID: ");
								custID1 = input.nextInt();

								System.out.print("Enter the second customer's ID: ");
								custID2 = input.nextInt();

								cust1 = customers.findCustomer(custID1);
								cust2 = customers.findCustomer(custID2);

							}
                     
                     AVLProducts common2 = customers.commonProducts(cust1, cust2,products) ;
                   if (common2== null||common2.isEmpty())
								System.out.println(
										"customer one with ID: " + cust1.customerId + " and customer two with ID: "
												+ cust2.customerId + " dont have any products in common.");
                   else //PRINTPRODUCTS
							{ System.out.println("List of common Products where the overall average rating is > 4: ");
								common2.printAllProducts(common2);
							}
                     

							AVLProducts common = customers.commonProducts(cust1, cust2, products);
							
							if (common== null||common.isEmpty())
								System.out.println(
										"customer one with ID: " + cust1.customerId + " and customer two with ID: "
												+ cust2.customerId + " dont have any products in common.");
							else //PRINTPRODUCTS
							{ System.out.println("List of common Products where the average rating for only thoese two customers is > 4: ");
								common.printAllProducts(common);
							}

						} else
							System.out.println("No customer has made an order yet.");
                     
                 

						break;

					case 5: // Update order status CHECKED

						
                  if (!orders.empty()) {
							System.out.println("For updating the order status enter the following..");
							System.out.println("ID: ");
							int id2 = input.nextInt();

							Order o = orders.searchById(id2);

							while (o == null) {
								System.out.println("Order: " + id2 + " does not exist.\nEnter again.");
								System.out.println("ID: ");
								id2 = input.nextInt();
								o = orders.searchById(id2);
							}

							input.nextLine();

							System.out.println("Enter the new order status: ");
							String st = input.next();
							orders.findFirst();
							while (!orders.last()) {
								if (orders.retrieve().equals(o))
									break;
								orders.findNext();
							}

							orders.update(st);
						} else
							System.out.println("Orders have not been made yet.");

						break;
					case 6:
						// browse all products with their reviews
						if (products.isEmpty())
							System.out.println("No products have been added yet.");
						else {
							products.browseProducts();
						}
						break;
					case 7: // browse customers and their orders
						if (customers.empty())
							System.out.println("No Customer has placed an order yet.");
						else {
							customers.findFirst();
							while (!customers.last()) {
								System.out.println(customers.retrieve().toString());
								customers.findNext();
							}
							System.out.println(customers.retrieve().toString());

						}
						break;
						
					case 8: // print out of stock products
						Product outOfStock[] = products.trackOutOfStockProducts();
						if(products.getNumOutofStockProducts()==0)
							System.out.println("All products are fully stocked.");
						else {
							
						
						for(int i =0 ;i < products.getNumOutofStockProducts();i++)
						{
							System.out.println(outOfStock[i].toString());
						}
						}
					
						
						break;

					case 9:
						System.out.println("Exiting...");
						break;

					default:
						System.out.println("Invalid entry.\nTry again..");

					}
				} while (managerChoice != 9);// end manger menu

				break;

			case 2:
				int cChoice = 0;
				// customer menu
				do {
					System.out.println("==== CUSTOMER VIEW ===");
					System.out.println("1. Browse Products");
					System.out.println("2. Add customer.");
					System.out.println("3. place an order.");
					System.out.println("4. Add a review to a product.");
					System.out.println("5. Extract all reviews for all products.");
					System.out.println("6. View order history for a customer");
					System.out.println("7. Suggest top 3 products.");
					System.out.println("8. Exit.");
					cChoice = input.nextInt();
					switch (cChoice) {
					case 1: // browse products
						if (!products.isEmpty()) 
						{
							products.printAllProducts(products);
						} else
							System.out.println("No products have been added yet.");
						break;

					case 2: // adding a customer CHECKED
						System.out.println("For adding a customer enter the following..");
						input.nextLine();
						System.out.println("Name: ");
						String name1 = input.nextLine();
						System.out.println("Email: ");
						String email = input.nextLine();
						customers.registerCustomer(name1, email);

						System.out.println("Welcome: " + name1 + " your ID is: " + customers.retrieve().customerId);
						break;

					case 3: // placing an order CHECKED
						int stockChoice=0;
						String pName2;
						boolean isCancelled=false;
						Product p2 = null;
						System.out.println("For placing an order enter the following..");
						System.out.println("Customer ID: ");
						int cId3 = input.nextInt();
						Customer c3 = customers.findCustomer(cId3);

						while (c3 == null) {
							System.out.println("Customer: " + cId3 + " does not exist.\nEnter again.");
							System.out.println("Customer ID: ");
							cId3 = input.nextInt();
							c3 = customers.findCustomer(cId3);
						}

						orders.createOrder(new Order(c3)); // creating an order for the customer, current is
															// pointing to that node

						System.out.println("Enter the number of products you wish to order: ");
						int n = input.nextInt();
                  input.nextLine();
                  
						for (int i = 0; i < n; i++) {
							

							System.out.println("Enter product name: ");
							pName2 = input.nextLine();
							if (!products.isEmpty()) {
								p2 = products.searchByName(pName2);
								// searching for the product
								if (p2 == null) {
									System.out.println("Product: " + pName2 + " does not exist.");
									i--;
									continue;
								}
								else // creating the PQproduct for customer
								{
									
									if(p2.stock!=0)
									{
									orders.retrieve().productsOrdered(p2);
									System.out.println("Product: " + pName2 + " added to order successfully.");
									
									}
									else
									{
										System.out.println(p2.productName + " is out of stock.");
										do {
										System.out.println("Enter 1 to add another product, Enter 0 to cancel order.");
										stockChoice= input.nextInt();
										if(stockChoice==0)
										{
											System.out.println("Order has been cancelled.");
											orders.cancelOrder(); // cancelling the order that has just been created
											isCancelled=true;
											break;
											
										}
										
										if(stockChoice !=1 && stockChoice!=0)
											System.out.println("Invalid entry.\nTry again..");
										
										} while(stockChoice!=1  && stockChoice!=0);
										
										
										if (isCancelled)
								            break;

								        if (stockChoice == 1)
								            i--; 

									}
									
								}
								
								
								
								
							} // end if: empty
							else
							{
								System.out.println("No products have been added yet.");
								orders.cancelOrder(); // cancelling the order that has just been created
								break;
							}
						} // end for loop
						
						
						if(!products.isEmpty() && !isCancelled) // because order was cancelled when the product list was empty
						{

						Date d = null;
						System.out.println("Date: in the format yyyy-mm-dd");
						try {
							d = date.parse(input.next());
							
							// searching for the customer
							if (!customers.empty()) {
								customers.findFirst();
								while (!customers.last()) {
									if (customers.retrieve().equals(c3))
										break;
									customers.findNext();
								}
								//adding the order to the customers order list
								Order newOrd = orders.retrieve() ;
	                     newOrd.orderDate = d ;
	                     newOrd.status = "Pending" ;
	                     c3.placeOrder(newOrd) ;
								
								
								
								System.out.println("Order: " + orders.retrieve().orderId + " has been added to customer:  "
										+ c3.customerId + " list.");
								
								
							}
						} catch (Exception e) {
							System.out.println("An error has occured.");
						}

						
						
						}

						break;

					case 4: // adding a review CHECKED
						System.out.println("To add a review enter the following");
						System.out.println("Customer ID: ");
						int cId = input.nextInt();
						Customer c1 = customers.findCustomer(cId);

						while (c1 == null) {
							System.out.println("Customer: " + cId + " does not exist.\nEnter again.");
							System.out.println("Customer ID: ");
							cId = input.nextInt();
							c1 = customers.findCustomer(cId);
						}

						input.nextLine();
						System.out.println("Product Name: ");
						String pName = input.nextLine();
						Product p1 = products.searchByName(pName);
						if (!products.isEmpty()) {
							while (p1 == null) {
								System.out.println("Product: " + pName + " does not exist.\nEnter again");
								System.out.println("Product Name: ");
								pName = input.nextLine();
							}
						}
						do {
							System.out.println("Rating out of 5");
							rating = input.nextInt();
							if (rating > 5 || rating < 0)
								System.out.println("Invalid rating..\nTry again.");

						} while (rating > 5 || rating < 0);
						input.nextLine();

						System.out.println("Comment: ");
						String comment = input.nextLine();

						p1.addReviewForProduct(rating, comment, cId, p1.productId);
                  Review re = new Review(rating, comment, reviews.Size()+1, cId, p1.productId) ;
                 reviews.addReview(re) ;
                  
                  products.dequeue(p1) ;
                  products.enqueue(p1) ;
                 

						break;

					case 5: // extracting all reviews: NEED TO BE DONE
						int cId2 = 0;
						System.out.println("Customer ID: ");
						cId2 = input.nextInt();
						if (!customers.empty()) {
							Customer c2 = customers.findCustomer(cId2);
							while (c2 == null) {
								System.out.println("Customer: " + cId2 + " does not exist.\nEnter again.");
								System.out.println("Customer Id: ");
								cId2 = input.nextInt();
								c2 = customers.findCustomer(cId2);
							}

							System.out.println("Extracting all reviews for customer: " + cId2);
							printAllreviewForC(reviews, cId2) ;
							
							} else
								System.out.println("No customers have registered yet.");

				

						break;

					case 6: // view order history
						System.out.println("Enter Customer ID:");
						int custID = input.nextInt();
						if (!customers.empty()) {
							Customer cust = customers.findCustomer(custID);
							while (cust == null) {
								System.out.println("Customer: " + custID + " does not exist.Enter again.");
								System.out.println("Customer Id: ");
								custID = input.nextInt();
								cust = customers.findCustomer(custID);
							}
							customers.viewOrderHistory(cust);

						}

						else
							System.out.println("No customer has been added yet.");

						break;

					case 7:
						products.top3Products(products);
						break;

					case 8:
						System.out.println("Exiting...");
						break;

					}
				} while (cChoice != 8); // end customer menu
				break;

			case 3:
				System.out.println("Exiting...");
				break;
			}

		} while (mainChoice != 3); // end main menu

	}

public static void printAllreviewForC(LinkedReviews revs, int id){
if (revs.isEmpty()){
System.out.println("No reviews for any customers.") ;
return ;
}

boolean found = false ;

revs.findFirst() ;
while(!revs.isLast()){
Review r = revs.retrieve() ;

if (r.customerID == id){
System.out.println(r) ;
found = true ;
}

revs.findNext() ;
}

Review r = revs.retrieve() ;

if (r.customerID == id){
System.out.println(r) ;
found = true ;
}

if(!found){
System.out.println("This customer did not leave any reviews...") ;
}

}

}
