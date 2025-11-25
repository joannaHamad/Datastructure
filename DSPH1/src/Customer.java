import java.util.Date;

public class Customer{
    
	public int customerId;
	public String name, email;
	public OrderList orderList;
	public static int count;
	
	
	// when customer wants to add
	public Customer(String name, String email){
	    customerId=++count;
	    this.name=name;
	    this.email=email;
	    orderList=new OrderList();
	}
	
	//for reading from file
	public Customer(int id,String name, String email)
	{
	    customerId=id;
	    this.name=name;
	    this.email=email;
	    orderList=new OrderList();
	    count=id;
	}
    
	
public void placeOrder(Order o) 
	
	{
	orderList.createOrder(o);
		
	}
public String toString() {
	
	String info="Customer ID: " + customerId + " customer name: " + name + " Email: " + email;
	
   if(orderList.empty())
   return info+" No orders have been added." ;
   
	orderList.findFirst();
	while(!orderList.last())
	{
		info+= orderList.retrieve().toString();
		orderList.findNext();
	}
   info+= orderList.retrieve().toString();
	
	return info;
	
	
}
	
}
