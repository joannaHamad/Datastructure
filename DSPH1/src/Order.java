import java.util.Date;
import java.text.SimpleDateFormat;

public class Order {
	public int orderId;
	public Customer customer;
	private AVLProducts products;
	public double totalPrice;
	public Date orderDate;
	public String status;
	public static int count;
	
	// for creating a new order each time for the customer, customer doesnt know the order id!
		public Order( Customer c) {
			orderId = ++count; // incrementing each time
			customer = c;	
         products = new AVLProducts() ;	
         totalPrice = 0 ;
		}
		
		// for reading from file: Order id is known
		public Order( int id,Customer c, AVLProducts p, double t, Date d, String s) {
			orderId = id;
			customer = c;
			products = p;
			totalPrice = t;
			orderDate = d;
			status = s;
			count=orderId;
			
		}
		
		
// for placing orders
	public Order(  AVLProducts p, double t, Date d, String s) {
		products = p;
		totalPrice = t;
		orderDate = d;
		status = s;		
	}
	
	
	

	//UPDATED
	public void productsOrdered(Product p) 
	{
		
		products.enqueue(p);
		totalPrice+=p.productPrice;
		p.stock--;
	}
	
	public AVLProducts getPQProducts() {
		return products;
	}

	public String toString() {
   SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd") ;
		return "Order ID:" + orderId +  " Total price: " + totalPrice
				+ " Order date: " + d.format(orderDate) + " Order status: " + status;
	}
	
	


}