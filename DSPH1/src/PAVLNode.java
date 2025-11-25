
public class PAVLNode 
{
	public Product product; // from here we get the priority
	public PAVLNode left; // left reference
	public PAVLNode right; // right reference
	public int height= 1; // keeps track of the AVL

	
	public PAVLNode(Product p) 
	{
		
		product= p;
		left=null;
		right=null;
	}
   
  

}
