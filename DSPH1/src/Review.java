
public class Review 
{
	public int rating; 
	public String comment;
	public  int revID; 
	public int customerID;
	public int productID;

	// constructer for add rev
	public Review (int rating, String comment, int revID, int cusID , int prodID)

	{
		this.rating=rating;
		this.comment=comment;
		this.revID= revID;
		customerID= cusID;
		productID= prodID;
		
	}
	// constrcuter for edit rev
	public Review (int rating, String comment , int revID)

	{
		this.rating=rating;
		this.comment=comment;
		this.revID= revID;

	}
	
	public String toString()
	{
		return "Rating : " + rating + "\nComment: " + comment ; 
	}

}
