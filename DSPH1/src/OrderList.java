import java.util.Date;
import java.text.SimpleDateFormat;

public class OrderList{

private OrderNode head ;
private OrderNode current ;

public OrderList(){
head = current = null ; }

public boolean empty(){
return head == null ; }

public boolean last(){
return current.next == null ; }

public void findFirst(){
current = head ; }

public void findNext(){
current = current.next ; }


public Order retrieve(){
return current.order ; }

//before updating search
public void update( String s){
current.order.status=s; 
}

public void createOrder(Order o){
OrderNode tmp = new OrderNode(o) ;
if(empty()){
current = head = tmp ; }
else{
tmp.next = current.next ;
tmp.previous = current ; 

if(current.next != null)
current.next.previous = tmp ;

current.next = tmp ;
current = tmp ; }
}

public Order searchById(int id){
OrderNode tmp = head ;
while(tmp != null){
if(tmp.order.orderId == id)
return tmp.order ;
tmp = tmp.next ;
}
return null ; }



//before deleting search

public void cancelOrder()
{
if(current==head)
{
	head=head.next;
	if(head!=null)
		head.previous=null;
	}
else
{
	current.previous.next=current.next;
	if(current.next!=null)
		current.next.previous=current.previous;
	}

if(current.next==null)
	current=head;
else
	current=current.next;
}

public void PrintBetweenTwoDates(Date startDate, Date endDate){
OrderNode tmp = head ;

while(tmp != null){
Date orderDate = tmp.order.orderDate ;

if (orderDate.compareTo(startDate) >= 0 && orderDate.compareTo(endDate) <= 0)
System.out.println(tmp.order) ; 

tmp = tmp.next ;
}
}



}