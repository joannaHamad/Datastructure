
public class CustomerList {
    
    private CustomerNode head;
    private CustomerNode current;

    
    public CustomerList(){
        head=current=null;
    }
    
    public boolean empty(){
        return head==null;
    }
    
    public boolean last(){
        return current.next==null;
    }
    
   
    
    public void findFirst(){
        current=head;
    }
    
    public void findNext(){
        current=current.next;
    }
    
    public Customer retrieve() {
    	return current.customer;
    }
    public void update(String name, String email) {
    	current.customer.name=name;
    	current.customer.email=email;
    	
    }
    
    //when customer wants to add: ID is unknown!
    public void registerCustomer(String name,String email)
    {
        if(empty()){
            head=current=new CustomerNode(new Customer(name,email));
        }
        else{
        	CustomerNode temp=current.next;
            current.next=new CustomerNode(new Customer(name,email));
            current=current.next;
            current.next=temp;
        }
    }
    
    public Customer findCustomer(int id){
    	CustomerNode temp=head;
        while(temp!=null){
            if(temp.customer.customerId==id)
                return temp.customer;
            temp=temp.next;
        }
        return null;
    }
    
    // when reading from file: ID is unknown
    public void registerCustomer(int id, String name,String email){
        if(empty()){
            head=current=new CustomerNode(new Customer(id,name,email));
        }
        else{
        	CustomerNode temp=current.next;
            current.next=new CustomerNode(new Customer(id,name,email));
            current=current.next;
            current.next=temp;
        }
    
    }
    
  
    
	
	//using list of orders
	public void viewOrderHistory(Customer cust)
	{
        
		if(!cust.orderList.empty())
		{
			cust.orderList.findFirst();
			while(!cust.orderList.last())
			{
				System.out.println(cust.orderList.retrieve().toString());
				cust.orderList.findNext();
			}
			
			System.out.println(cust.orderList.retrieve().toString());

		}
		else
			System.out.println("Customer: " + cust.customerId + " hasnt made any order yet.");
		}
      
      public AVLProducts commonProducts2(Customer c1, Customer c2, AVLProducts products){
   if (c1 == null || c2 == null){
   System.out.println("Customer doesn't exist...") ;
   return null ;
   }
   AVLProducts common = new AVLProducts() ;
   PQNode temp = products.getFrontNode() ;
   
   while(temp != null){
   Product p = temp.product ;
   
   boolean revByC1 = false ;
   boolean revByC2 = false ;
   
   if( !p.listOfReviews.isEmpty() ){
   p.listOfReviews.findFirst() ;
   
   while(true){
   Review r = p.listOfReviews.retrieve() ;
   if(r.customerID == c1.customerId){
   revByC1 = true ;
   }
   if(r.customerID == c2.customerId){
   revByC2 = true ;
   }
   if(p.listOfReviews.isLast())
   break;
   p.listOfReviews.findNext() ;
   }
   }
   if (revByC1 && revByC2){
   double sum = 0 ;
   double count = 0 ;
   
   p.listOfReviews.findFirst() ;
   while(true){
   Review r = p.listOfReviews.retrieve() ;
   sum+= r.rating ;
   count++ ;
   
   if(p.listOfReviews.isLast())
   break;
   p.listOfReviews.findNext() ;
   }
   
   double avg = sum/count ;
   
   if (avg > 4){
   common.enqueue(p) ;
   }
   }
   temp = temp.next ;
   }
return common ;
   }
	
		
	public AVLProducts commonProducts(Customer c1, Customer c2, AVLProducts products) {
    AVLProducts common = new AVLProducts();
    PQNode tmp = products.getFrontNode() ;
    
    while(tmp != null){
    Product p = tmp.product ;
    
    double c1Rating = getCRating(p, c1.customerId) ;
    double c2Rating = getCRating(p, c2.customerId) ;
    
    if ( c1Rating != -1 && c2Rating != -1){
    double average = ( c1Rating + c2Rating ) / 2 ;
    if (average > 4)
    common.enqueue(p) ;
    }
    tmp = tmp.next ;
    }
    return common ;
      
	} 	
   
   private double getCRating(Product p, int id){
   if (p.listOfReviews.isEmpty() )
   return -1 ;
   
   p.listOfReviews.findFirst() ;
   while (true){
   Review r = p.listOfReviews.retrieve() ;
   
   if(r.customerID == id)
   return r.rating ;
   
   else
   if ( p.listOfReviews.isLast())
   break ;
   
    p.listOfReviews.findNext() ;
   }
   return -1 ;
   
   }



  
} // end class
	