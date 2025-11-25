
public class CustomerNode {
	
	Customer customer;
	CustomerNode next;
	
	public CustomerNode() {
		customer=null;
		next=null;
	}
	
	public CustomerNode(Customer c) {
		customer=c;
		next=null;
	}
}
