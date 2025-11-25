
//insert - update product - search - range query by price (return all products whose price falls within a given range minPrice,maxPrice)

public class AVLProducts 
{
	private PAVLNode root;
	private PAVLNode current;
	private int size;
	private int numOutOfStockProd;
	private int numRangeOfPrice;

	
	public AVLProducts() 
	{
		root=current=null;
		size=0;
		numOutOfStockProd=0;
		numRangeOfPrice=0;
		
	}
	
	public boolean isEmpty() {
		return root ==null;
	}
	
	
	private boolean findKey(int key) 
	
	{ if(isEmpty())
		return false;
	
	PAVLNode trav = root , prev = null;
	
	while(trav!=null)
	{
		prev = trav;
		if(trav.product.productId==key)
		{
			current = trav;
			return true;
		}
		
		else
			if(trav.product.productId>key)
			trav = trav.left;
		
			else
				trav=trav.right;
	} // end while loop
	
	current = prev; // if key was not found (current points to the parent)
	
	return false;		
	}
	
	public boolean insert(Product p) 
	{
		PAVLNode preserve = current; // preserve the current if the key exists!
		if(findKey(p.productId)) // doesnt allow duplicats 
		{
			current= preserve;		
			return false;
		}
		
			root= insertRec(root,p);	//rotation may create a new root	
			return true;
	}
	
	
	private PAVLNode insertRec(PAVLNode r, Product p)
	{
		if(r==null)
		{
			size++;
			return new PAVLNode(p); //AVL is empty
		}
		
		if (p.productId < r.product.productId)
	        r.left = insertRec(r.left, p);
	    else 
	    	if (p.productId > r.product.productId)
	        r.right = insertRec(r.right, p);
		
		updateHeight(r); //updating height
		
		//apply rotation
		r= applyRotation(r);
		
		return r;
	   
	}
	
	private int height(PAVLNode node)
	{
		return node!= null ? node.height : 0; // avoiding null pointer exception
	}
	private void updateHeight(PAVLNode node) 
	{
		int maxHeight= Math.max(height(node.left), height(node.right));		
		node.height= maxHeight+1;
	}
	
	
	private int balance (PAVLNode node)
	{
		return node!= null ? height(node.left) - height(node.right) : 0; // avoiding null pointer exception
	}
	
	private PAVLNode rotateRight(PAVLNode node)
	{
		PAVLNode leftNode = node.left;
		PAVLNode centerNode = leftNode.right;
		
		//perform rotation
		leftNode.right= node;
		node.left= centerNode;
		
		updateHeight(node);
		updateHeight(leftNode);
		
		return leftNode;

	}
	private PAVLNode rotateLeft(PAVLNode node)
	{
		PAVLNode rightNode = node.right;
		PAVLNode centerNode = rightNode.left;
		
		//perform rotation
		rightNode.left= node;
		node.right= centerNode;
		
		updateHeight(node);
		updateHeight(rightNode);
		
		return rightNode;
	}
	
	private PAVLNode applyRotation(PAVLNode node) 
	{
		int balance = balance (node);
		
		if(balance>1) // longer on the right side: rotate to the left
		{
			if(balance(node.left) <0) // case where the parent and the child conflift in terms of balance
			node.left= rotateLeft(node.left);
			
			return rotateRight(node);
		}
		
		if (balance<-1) // longer on the left side: rotate to the right
		{
			
			if(balance(node.right) >0) // case where the parent and the child conflift in terms of balance
				node.right= rotateRight(node.right);
				
			return rotateLeft(node);
		}
		
		return node;
		
	} // end method
	
	
	
	// user must search by name before hand if AVL is empty , product doesn exist then update cannot be used
	//O(log n)
	public void updateProducts( Product oldProduct, String productName,double price , int stock) 
	{
		findKey(oldProduct.productId); 
		
		current.product.productName = productName;
		current.product.productPrice= price;
		current.product.stock= stock;
	}


	// must use lograthimic search
	public Product searchByID(int productID) 
	{
		PAVLNode preserve = current; // since current will change after the findkeycall
		
		if(!findKey(productID)) // changes the current position whether it finds it or not
		{ current = preserve;
			return null; // not found 
			}
		
		return current.product;
		
	}
	
public Product searchByName(String productName) 
{
	Product product = searchByNameRec( productName ,root);
	if(product==null)
		return null; // product not found
	PAVLNode preserve = current; // since current will change after the findkeycall
	
	if(!findKey(product.productId)) // changes the current position whether it finds it or not
	{ current = preserve;
		return null; // not found 
		}
	
	return current.product;
	}


private Product searchByNameRec(String productName, PAVLNode r) {
		// avoid null pointer exception
		if (r == null)
			return null;
		// base case if found
		if (r.product.productName.equalsIgnoreCase(productName))
			return r.product;

		// traverse left subtree
		Product p = searchByNameRec(productName, r.left);
		if (p == null) // element was not in left subtree
		{
			return searchByNameRec(productName, r.right); // element in right subtree
		} else // element was found in left subtree
			return p;

	}

public Product[] rangeByPrice(double min , double max) 
{
	Product[] products = new Product [size];
	
	numRangeOfPrice=0; // reset counter everytim emethod is called to prevent out of bounds error
	
	rangeByPriceRec(min , max , products,  root );
	return products;

}

public void rangeByPriceRec(double min , double max , Product[] products , PAVLNode r) 
{
	//base case: to avoid null pointer exception
	if(r==null)
		return;
	
	if((r.product.productPrice<= max  ) && (r.product.productPrice>=min))
		products[numRangeOfPrice++]=r.product;
	
	rangeByPriceRec(min, max, products,r.left);
	rangeByPriceRec(min, max, products,r.right);	
	}

// traverse the tree and store it in a sorted array
public  void top3Products() 
{
	

	if (isEmpty()) {
		System.out.println("No product has been added yet.");
		return;
	}
	
	Product [] products = selectionSort();
	System.out.print("Top 3 suggested products: \n");
	
	for(int i =0; i<3 ; i++)
		System.out.print(products[i].productName + ",");
}

private  int convertTreeToArrayRec(PAVLNode r, Product [] products, int i) 
{
	if(r==null)
		return i;
	
	i = convertTreeToArrayRec(r.left, products , i);
	products[i++]= r.product;

	return convertTreeToArrayRec(r.right, products , i);
}

private Product [] selectionSort() {
	Product[] products = new Product[size];
	convertTreeToArrayRec(root , products, 0);
	
	
	    for (int i = 0; i < products.length-1; i++) {

	        // find index of the maximum element starting from i
	        int maxIndex = i;
	        for (int j = i + 1; j < products.length; j++) {
	            if (products[j].getPriority() > products[maxIndex].getPriority()) 
	             maxIndex = j;       
	        }
	        Product temp = products[i];
	        products[i] = products[maxIndex];
	        products[maxIndex] = temp;
	    }
	return products;	
}

  	public Product[] trackOutOfStockProducts() 
	{
		Product outOfStock[] = new Product[size];
		numOutOfStockProd=0; // avoid indexout of bound error
		
		trackOutOfStockProductsRec(root ,  outOfStock);
		
		return outOfStock;
			
	}
	
	private void trackOutOfStockProductsRec(PAVLNode r ,  Product[] outOfStock) 
	{
		// base case (prevent null pointer exception)
		if(r==null)
			return;
		//track out of stock
		if(r.product.stock==0)
			outOfStock[numOutOfStockProd++] = r.product;
		
		trackOutOfStockProductsRec( r.left,  outOfStock);
		trackOutOfStockProductsRec( r.right,  outOfStock);

		
			
	}
	
	public int getNumOutofStockProducts() {
		return numOutOfStockProd;
	}
	
	public int size() {
		return size;
	}
	public void browseProducts() {
		
		browseProductsRec(root);		}
	
	private void browseProductsRec(PAVLNode r) 
	
	{
		if(r==null)
			return;
		
		System.out.println(r.product.toString());
		browseProductsRec(r.left);
		browseProductsRec(r.right);

	}
	
	
	public void printAllProducts()
	{
		printAllProductsRec(root);
	}
	
	private void printAllProductsRec(PAVLNode r) 
	{
		if(r==null)
			return;
		
		System.out.println(r.product.productName);
		printAllProductsRec(r.left);
		printAllProductsRec(r.right);
		
	}
   


} // end class




