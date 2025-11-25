public class AVLNode<T>{
public int orderId ; //key
public Order data ; //data
public Balance bal ;
public AVLNode<T> left, right ;

public AVLNode(int id, Order o){
orderId = id ;
data = o ;
bal = Balance.Zero ;
left = right = null ; }
}