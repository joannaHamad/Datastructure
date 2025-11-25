import java.time.LocalDate ;
import java.time.format.DateTimeFormatter ;

public class Order{
private int orderId ;
private int customerId ;
private LocalDate orderDate ;
private AVLProducts productsTree ;
private double price ;
private String status ;
private int generatedId = 1 ;

public Order(int cId, String d, double p, String s){
orderId = generatedId++ ; 
customerId = cId ;
orderDate = LocalDate.parse(d, DateTimeFormatter.ofPattern("dd-MM-yyyy")) ; ;
price = p ;
status = s ;
productsTree = new AVLProducts() ; }

public void printAllProductsInOrder(){
System.out.println("Products in this order\n================================") ;
productsTree.printAllProducts() ;
System.out.println("================================") ;
}

public String toString(){
return orderId+"     "+customerId+"     "+orderDate+"     "+price+"     "+status+".\n" ;
}

public int getOrderId(){ return orderId ; }
public void setOrderId(int n){ orderId = n ; }
public LocalDate getOrderDate(){ return orderDate ; }

}