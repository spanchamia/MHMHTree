package mhmht;

// TODO: Current implementation only works with integers since we depend
// on median(), but we can do away with that restriction.
public class MHMHT 
{
    private static class MHMHTN {
    	// user data
    	int int_;
    	
    	// does this node have a valid user data
    	boolean hasint_;
    	
    	// number of ints in my tree
    	int nints_;
    	
    	MHMHTN parent_;
    	MHMHTN left_;
    	MHMHTN right_;
    	
		public int compareTo(int i) {
			return int_ - i;
		}
		
		MHMHTN() {
			int_ = 0;
			hasint_ = false;
			nints_ = 0;
			parent_ = null;
			left_ = null;
			right_ = null;
		}
		
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("{hasData: " + hasint_ + ", ");
			builder.append(hasint_ ? Integer.toString(int_) : "XXX");
			builder.append(", nInts: " + Integer.toString(nints_));
			builder.append(", parent: " + ((parent_ == null) ? "nil" : "not nil"));
			builder.append(", left: " + ((left_ == null) ? "nil" : "not nil"));
			builder.append(", right: " + ((right_ == null) ? "nil" : "not nil" + "}"));
			return builder.toString();
		}
		
		// return smallest node in the tree
		// TODO: Make this O(1) by saving references to leftSmallest during
		// Insert()
		public MHMHTN smallest() {
			if (left_ != null) {
				MHMHTN leftSmallest = left_.smallest();
				if (leftSmallest != null) { return leftSmallest; }
			}
			
			return hasint() ? this : null;
		}

		// return largest node in the tree
		// TODO: Make this O(1) by saving references to rightLargest during
		// Insert()
		public MHMHTN largest() {
			if (right_ != null) {
				MHMHTN rightLargest = right_.largest();
				if (rightLargest != null) { return rightLargest; }
			}
			
			return hasint() ? this : null;
		}
		
		// return the median of the tree rooted at current node
		// nInts_ cannot be 0 here.
		public double median() {
			if (hasint_) { return int_; }
			int leftLargest = left_.largest().int_;
			int rightSmallest = right_.smallest().int_;
			return leftLargest + (rightSmallest - leftLargest)/2;
		}
		
		public void setint(int i) {
			int_ = i;
			hasint_ = true;
			nints_ ++;
		}
		
		public int removeint() {
			int i = int_;
			int_ = 0;
			hasint_ = false;
			nints_ --;
			return i;
		}
		
		public int numints() {
			return nints_;
		}
		
		public void incrints() {
			nints_ ++;
		}
		
		public void decrints() {
			nints_ --;
		}
		
		public void setParent(MHMHTN p) { parent_ = p; } 
		public MHMHTN getParent() { return parent_; }

		public void setRight(MHMHTN r) { right_ = r; } 
		public MHMHTN getRight() { return right_; }

		public void setLeft(MHMHTN l) { left_ = l; } 
		public MHMHTN getLeft() { return left_; }
		
		public boolean hasint() { return hasint_; }

		public MHMHTN Lookup(int i) {
			if (numints() == 0) { return null; }
			if (hasint_ && int_ == i) { return this; }
			if ((median() >= i) && getLeft() != null) { return getLeft().Lookup(i); }
			else if (getRight() != null)              { return getRight().Lookup(i); }
			return null;
		}
    }
    
    MHMHTN root_;
    int numNodes_;
    
    MHMHT() { root_ = null; numNodes_ = 0; }

    // returns new root
    private MHMHTN Insert(MHMHTN root, int i) {
    	if (root == null) {
    		root = new MHMHTN();
    		++ numNodes_;
    	}
    	
    	// pre-existing leaf node
    	if (root.numints() == 0) {
    		root.setint(i);
    		return root;
    	}
    	
    	// odd
    	if (root.numints() % 2 != 0) {
    		int rootint = root.removeint();
    		if (root.compareTo(i) < 0) {
    			MHMHTN left = Insert(root.getLeft(), rootint);
    			left.setParent(root);
    			root.setLeft(left);
    			MHMHTN right = Insert(root.getRight(), i);
    			right.setParent(root);
    			root.setRight(right);
    		} else {
    			MHMHTN left = Insert(root.getLeft(), i);
    			left.setParent(root);
    			root.setLeft(left);
    			MHMHTN right = Insert(root.getRight(), rootint);
    			right.setParent(root);
    			root.setRight(right);
    		}
    		root.incrints(); // for rebalance
    		root.incrints(); // for new int
    	} else {
        	// even
    		int val;
    		if (root.median() < i) {
    			val = Remove(root.getRight(), root.getRight().smallest());
    			Insert(root.getRight(), i);
    		} else {
    			val = Remove(root.getLeft(), root.getLeft().largest());
    			Insert(root.getLeft(), i);
    		}
    		
    		root.setint(val);
    	}
    	
    	return root;
    }
    
    private int Remove(MHMHTN root, MHMHTN leaf) {
    	MHMHTN node = leaf;
    	int ret = node.removeint();
    	
    	while (node != root) {
    		MHMHTN parent = node.getParent();
    		if (parent.hasint()) {
    			if (parent.getLeft() == node) {
    				Insert(parent.getLeft(), parent.removeint());
    			} else {
    				Insert(parent.getRight(), parent.removeint());
    			}
    			parent.incrints(); // moved int down
    		} else {
    			int v;
    			if (parent.getLeft() == node) {
    				v = Remove(parent.getRight(), parent.getRight().smallest());
    			} else {
    				v = Remove(parent.getLeft(), parent.getLeft().largest());
    			}
    			parent.setint(v);
				parent.decrints(); // moved int up
    		}
    		parent.decrints(); // for the removed node
    		node = parent;
    	}
    	
    	return ret;
    }

	// Only discreet ints
    public void Insert(int i) {
    	root_ = Insert(root_, i);
    }
    
    public boolean Lookup(int i) {
    	if (root_ == null) {
    		return false;
    	}
    	
    	return root_.Lookup(i) != null;
    }
    
    public boolean Remove(int i) {
    	if (root_ != null) {
    		MHMHTN node = root_.Lookup(i);
    		if (node != null) {
    			Remove(root_, node);
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public int NumItems() {
    	return root_.numints();
    }
    
    public int NumNodes() {
    	return numNodes_;
    }
}
