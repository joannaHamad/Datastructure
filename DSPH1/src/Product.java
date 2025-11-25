
public class Product {
	public int productId;
	public String productName;
	public double productPrice;
	public int stock;
	public LinkedReviews listOfReviews; // each product has a list of reviews
	int count; // for =review ID
	
	public Product (int productId, String productName , double productPrice , int stock )

	{
		this.productId=productId;
		this.productName=productName;
		this.productPrice=productPrice;
		this.stock= stock;
		count=0;
		listOfReviews= new LinkedReviews(); // Composition >> when product is deleted the review must be deleted as well
		
	}
	
	public void addReviewForProduct(int rating , String comment , int cusID , int prodID) 
	{
		listOfReviews.addReview(new Review (rating,comment, count+1, cusID, prodID)); // everytime it adds 1 to the ID 
		count++; // increment after adding

	}
	
	
	
	//user enters their old review along side the updated values
	public void editReviewOfProduct(int revID, int newRating , String newComment)
	{	
		if(listOfReviews.editReview(revID,newRating,newComment))
				System.out.println("Sucessfully edited the review of product:" + productName);
		else
			System.out.println("You have not added a review.");
		
	}
	
	public double getPriority() // based on avg ratings
	{
		return listOfReviews.getAverageRatings(); 
	}
	
	

	
	public String toString()
	{
		
		String reviews="";
		if(listOfReviews.isEmpty())
		{
			reviews = "No reviews have been made yet.";
		}
		else
		{
			listOfReviews.findFirst();
			while(!listOfReviews.isLast())
			{
				reviews += listOfReviews.retrieve().toString();
				listOfReviews.findNext();
			}

		}
		
		
		return "Product ID: " + productId + "\nProduct Name: " + productName + "\nProduct Price: " + productPrice +"\nStock: " +stock +"\nReviews: " +reviews ; 
	}
	
	
	
}
