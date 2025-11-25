import java.time.LocalDate ;

public class OrderAVLTree{
   private AVLNode root ;
   private AVLNode pivot ;

   public OrderAVLTree(){
      root = null ;
      pivot = null ; }

//find the order by its ID
   public boolean findOrder(int orderId){
      return findOrder(root, orderId) ; }

   private boolean findOrder(AVLNode node, int orderId){
      if (node == null) 
         return false ;
   
      if(orderId < node.orderId) 
         return findOrder(node.left, orderId) ;
      else if (orderId > node.orderId) 
         return findOrder(node.right, orderId) ;
      else 
         return true ; }

   public Order retrieveOrder(int orderId){
      return retrieveOrder(root, orderId) ; }

   private Order retrieveOrder(AVLNode node, int orderId){
      if(node == null) 
         return null ;
      if(orderId < node.orderId) 
         return retrieveOrder(node.left, orderId) ;
      else if (orderId > node.orderId) 
         return retrieveOrder(node.right, orderId) ;
      else 
         return node.data ; }

   public boolean insertOrder(Order order){
      pivot = null ;
      boolean[] inserted = new boolean[1] ;
      root = insertOrder(root, order.getOrderId(), order, inserted) ;
      return inserted[0] ; }

   private AVLNode insertOrder(AVLNode node, int orderId, Order order, boolean[] inserted){
      if(node == null) {
         inserted[0] = true ;
         return new AVLNode(orderId, order) ; }
   
      if(orderId < node.orderId){
         node.left = insertOrder(node.left, orderId, order, inserted) ;
         if(inserted[0]){
            switch(node.bal){
               case Negative: 
                  node.bal = Balance.Zero ;
                  inserted[0] = false ; 
                  break ; 
            
               case Zero:
                  node.bal = Balance.Positive ;
                  break ;
            
               case Positive:
                  if(pivot == null)
                     pivot = node ;
                  node = rebalanceLeft(node) ;
                  inserted[0] = false ;
                  break ; }
         }
         
         else if(orderId > node.orderId){
            node.right = insertOrder(node.right, orderId, order, inserted) ;
            if(inserted[0]){
               switch(node.bal){
                  case Positive:
                     node.bal = Balance.Zero ;
                     inserted[0] = false ;
                     break ; 
               
                  case Zero: 
                     node.bal = Balance.Negative ;
                     break ;
               
                  case Negative:
                     if(pivot == null) 
                        pivot = node ;
                     node = rebalanceRight(node) ;
                     inserted[0] = false ;
                     break ; }
            }
         }
         else
            inserted[0] = false ; }
      return node ; }

   public boolean removeOrder(int orderId){
      pivot = null ;
      boolean[] deleted = new boolean[1] ;
      root = removeOrder(root, orderId, deleted) ;
      return deleted[0] ; }

   private AVLNode removeOrder(AVLNode node, int orderId, boolean[] deleted){
      if(node == null){
         deleted[0] = false ;
         return null ; }
   
      if(orderId < node.orderId){
         node.left = removeOrder(node.left, orderId, deleted) ;
      
         if(deleted[0]){
            switch(node.bal){
               case Positive:
                  node.bal = Balance.Zero ;
                  break ;
            
               case Zero:
                  node.bal = Balance.Negative ;
                  deleted[0] = false ;
                  break ;
            
               case Negative:
                  if(pivot == null)
                     pivot = node ;
                  node = rebalanceRight(node) ;
               
                  if(node.bal == Balance.Zero)
                     deleted[0] = false ;
               
                  break ;
            }
         }
      }
      else if(orderId > node.orderId){
         node.right = removeOrder(node.right, orderId, deleted) ;
         
         if(deleted[0]){
            switch(node.bal){
               case Negative:
                  node.bal = Balance.Zero ;
                  break ;
               
               case Zero:
                  node.bal = Balance.Positive ;
                  deleted[0] = false ;
                  break ;
               
               case Positive:
                  if(pivot == null)
                     pivot = node ;
                  node = rebalanceLeft(node) ;
                  if(node.bal == Balance.Zero)
                     deleted[0] = false ;
                  
                  break ; }
         }
      }
         
      else{
         deleted[0] = true ;
         
         //Case of no children
         if(node.left == null && node.right == null)
            return null ;
            //case of only right child
         else if(node.left == null)
            return node.right ;
            //case of only left child
         else if(node.right == null)
            return node.left ;
            //case of two children
         else{
            AVLNode successor = findMinInRight(node.right) ;
            node.orderId = successor.orderId ;
            node.data = successor.data ;
            
            boolean[] sDeleted = new boolean[1] ;
            node.right = removeOrder(node.right, successor.orderId, sDeleted) ;
            
            if(sDeleted[0]){
               switch(node.bal){
                  case Negative:
                     node.bal = Balance.Zero ;
                     break ;
                  
                  case Zero:
                     node.bal = Balance.Positive ;
                     deleted[0] = false ;
                     break ;
                  
                  case Positive:
                     if(pivot == null)
                        pivot = node ;
                     node = rebalanceLeft(node) ;
                     
                     if(node.bal == Balance.Zero)
                        deleted[0] = false ;
                     
                     break ; 
               }
            }
            return node ;
         }
      }
      return node ; 
   }
   
   private AVLNode findMinInRight(AVLNode node){
      while(node.left != null)
         node = node.left ;
    
      return node ;
   }
   
   
   private AVLNode rebalanceLeft(AVLNode node){
      AVLNode leftChild = node.left ;
   
      if(leftChild.bal == Balance.Positive){
      //LL rotation
         node.bal = Balance.Zero ;
         return rotateRight(node) ;
      }
      else{
      //LR rotation
         AVLNode rightGrandChild = leftChild.right ;
         switch(rightGrandChild.bal){
            case Positive:
               node.bal = Balance.Negative ;
               leftChild.bal = Balance.Zero ;
               break ;
         
            case Negative:
               node.bal = Balance.Zero ;
               leftChild.bal = Balance.Positive ;
               break ;
         
            case Zero:
               node.bal = Balance.Zero ;
               leftChild.bal = Balance.Zero ;
               break ;
         }
         rightGrandChild.bal = Balance.Zero ;
         node.left = rotateLeft(leftChild) ;
         return rotateRight(node) ;
      }
   }
   
   private AVLNode rebalanceRight(AVLNode node){
      AVLNode rightChild = node.right ;
   
      if(rightChild.bal == Balance.Negative){
      //RR rotation
         node.bal = Balance.Zero ;
         rightChild.bal = Balance.Zero ;
         return rotateLeft(node) ;
      }
      else{
      //RL rotation
         AVLNode leftGrandChild = rightChild.left ;
      
         switch(leftGrandChild.bal){
            case Negative:
               node.bal = Balance.Positive ;
               rightChild.bal = Balance.Zero ;
               break ;
         
            case Positive:
               node.bal = Balance.Zero ;
               rightChild.bal = Balance.Negative ;
               break ;
         
            case Zero:
               node.bal = Balance.Zero ;
               rightChild.bal = Balance.Zero ;
               break ;
         }
         leftGrandChild.bal = Balance.Zero ;
         node.right = rotateRight(rightChild) ;
         return rotateLeft(node) ;
      }
   }
   
   private AVLNode rotateRight(AVLNode node){
      AVLNode leftChild = node.left ;
      node.left = leftChild.right ;
      return leftChild ;
   }
   
   private AVLNode rotateLeft(AVLNode node){
      AVLNode rightChild = node.right ;
      node.right = rightChild.left ;
      return rightChild ;
   }
   
   public void printOrdersBetweenTwoDates(LocalDate startDate, LocalDate endDate){
      printOrdersBetweenTwoDates(root, startDate, endDate) ;
   }
   
  private void printOrdersBetweenTwoDates(AVLNode node, LocalDate startDate, LocalDate endDate){
      if(node != null){
         printOrdersBetweenTwoDates(node.left, startDate, endDate) ;
      
         Order ord = node.data ;
         LocalDate orderDate = ord.getOrderDate() ;
      
         if(orderDate.compareTo(startDate) >= 0 && orderDate.compareTo(endDate) <= 0)
            System.out.println(ord.toString()) ;
      
         printOrdersBetweenTwoDates(node.right, startDate, endDate) ;
      }
   }
}