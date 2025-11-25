
public class LinkedReviews
{
	// single linked list
	private ReviewNode head;
	private ReviewNode current;
	private int size;
	
	
	public LinkedReviews() 
	{
		
		head=current=null;
		size=0;
	}
	
	
	public void addReview(Review review) 
	{
		// simplest case (empty)
		if(head==null)
		{
			head= current = new ReviewNode(review);
		}
		// has elements
		else
		{
			ReviewNode newNode = new ReviewNode(review);
			current.next= newNode;
			current= newNode;
		}
		
		size++;
		
		
	}
	
	public boolean editReview(int revID ,int newRating , String newComment) 
	{
		ReviewNode foundNode= searchOldReview(revID);
		if(foundNode == null)
		{
			return false;
		}
		
		
		foundNode.review.rating=newRating;
		foundNode.review.comment = newComment;
		return true;
	}
	
	
	private ReviewNode searchOldReview(int revID) 
	{
		// traverse to look for old review 
		ReviewNode trav = head;
		for(int i=0 ;i<size ;i++)
		{
			if(trav.review.revID==revID)
			{
				return trav; // old review exists
			}
			trav=trav.next;
			
		}
		
		return null;
		
	
		 
	}
	// for a product
	// must search for product before hand 
	public double getAverageRatings() 
	{
		double sumRating=0;
		int count=0;
		if(isEmpty())
			return 0;
		
		findFirst();
		while(!isLast())
		{
			sumRating += retrieve().rating;
			count++;
			findNext();
		}
		sumRating += retrieve().rating;
		count++;
		return sumRating/count;
	}
	
	public void findFirst() 
	{
		
		current=head;
	}

	public void findNext() 
	{
		current=current.next;
	}

	public boolean isLast() {
		
		return current.next==null;
	}
	
	public boolean isEmpty() 
	{
		return head==null;
	}
	
	public Review retrieve  () 
	{
		return current.review;
	}
	

	
	public int Size(){ return size ; }
	

}
