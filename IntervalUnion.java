package ass1;

import java.util.ArrayList;

public class IntervalUnion {
	
	private int low;
	private int high;
	private IntervalUnion low2;
	private IntervalUnion high2;
	
	public static String getAuthorName() {
		
		return "Brandon Haripersad";
	}
	
	public static String getRyersonID() {
		
		return "123456789";
	}

	public IntervalUnion(int start, int end) {
		
		this.low = start;
		this.high = end;
	}
	
	public IntervalUnion(IntervalUnion x, IntervalUnion y) {
		
		this.low2 = x;
		this.high2 = y;
	}
	
	public IntervalUnion() {
		
		this.low = 0;
		this.high = 0;
	}
	
	public static IntervalUnion create(int start, int end) {
		
		return new IntervalUnion(start, end);
	}

	@Override public String toString() {
		
		StringBuilder str = new StringBuilder("[");
		
		if ((this.low2 != null) || (this.high2 != null)) {
			
			str.append(this.low2.toString());
			str.append(',');
			str.append(this.high2.toString());
			str.append(']');
			
			return str.toString();
		
		} else {
		
			if ((this.low == 0) && (this.high == 0)) {
			
				str.append(']');
				return str.toString();
			
			}
			
			if (this.low == this.high) {
				
				str.append(this.low);
				str.append(']');
				return str.toString();
				
			}
		
			if ((this.low >= 0) && (this.high >= 0)) {
			
			str.append(this.low);
			str.append('-');
			str.append(this.high);
			str.append(']');
		}
			
		}
		
		return str.toString();
	}
	
	public int getLow() {
		
		return this.low;
	}
	
	public int getHigh() {
		
		return this.high;
	}
	
	@Override public boolean equals(Object other) {
		
		boolean flag = true;
		
		if (!(other instanceof IntervalUnion)) {
			
			flag = false;
			
		} else if (other instanceof IntervalUnion) {
			
			IntervalUnion fother = (IntervalUnion) other;
		
			if ((this.low == fother.getLow()) && (this.high == fother.getHigh())) {
				
				flag = true;
						
			} else {
				
				flag = false;
			}
		}
		
		return flag;
	}
	
	@Override public int hashCode() {
		
		int prime = 31;
		
		int result = 1;
		
		result = prime * result * this.high;
		result = prime * result * this.low;
		
		return result;
	}
	
	public boolean contains(int x) {
		
		boolean mark = true;
		
		for (int i = this.low; i <= this.high; i++) {
			
			if (i == x) {
				
				mark = true;
				break;
				
			} else {
				
				mark = false;
			}
		}
		
		return mark;
		
	}
	
	public IntervalUnion union(IntervalUnion other) {
		
		int com = 0;
		
		// checks if the intervals are already the same
		
		if (this.equals(other)) {
				
			return new IntervalUnion(this.getLow(), this.getHigh());
		}
		
		// checks if other contains more intervals
		
		if ((other.low2 != null) & (other.high2 != null)) {
			
			IntervalUnion temp = this.union(other.low2);
			temp = this.union(other.high2);
			return temp;
		}
		
		// checks if the low range of the intervals are the same
		
		if (this.getLow() == other.getLow()) {
			
			return new IntervalUnion(this.getLow(), Math.max(this.getHigh(), other.getHigh()));
	
		} else {
			
		// checks if the high range of the intervals are the same
			
			if (this.getHigh() == other.getHigh()) {
			
			return new IntervalUnion(Math.min(this.getLow(), other.low), this.high);
			
			}
		}
		
		// Checks if other interval is just a single number
		
		if (this.getLow() == this.getHigh()) {
			
			if (this.getLow() <= (other.getLow() - 1)) {
				
				return new IntervalUnion(this.getLow(), other.getHigh());
			}
			
			if (this.getHigh() >= (other.getHigh() + 1)) {
				
				return new IntervalUnion(other.getLow(), this.getHigh());
			}
			
			if ((this.getLow() >= other.getLow()) && (this.getLow() <= other.getHigh())) {
				
				return new IntervalUnion(other.getLow(), other.getHigh());
				
			} else {
				
				return new IntervalUnion(this, other);
			}
		}
		
		if (other.getLow() == other.getHigh()) {
			
			if (other.getLow() <= (this.getLow() - 1)) {
				
				return new IntervalUnion(other.getLow(), this.getHigh());
			}
			
			if (other.getHigh() >= (this.getHigh() + 1)) {
				
				return new IntervalUnion(this.getLow(), other.getHigh());
			}
			
			if ((other.getLow() >= this.getLow()) && (other.getLow() <= this.getHigh())) {
				
				return new IntervalUnion(this.getLow(), this.getHigh());
				
			} else {
				
				return new IntervalUnion(this, other);
			}
		}
		
		if (this.getHigh() > other.getHigh()) {
			
			com = other.getHigh();
			
			if (com >= this.getLow() && com <= this.getHigh()) {
				
				if (this.getLow() <= other.getLow()) {
					
					return new IntervalUnion(this.getLow(), this.getHigh());
					
				} else {
					
					return new IntervalUnion(other.getLow(), this.getHigh());
				}
			} 
		} else {
			
			if (this.getHigh() >= other.getLow() && this.getHigh() <= other.getHigh()) {
				
				if (this.getLow() <= other.getLow()) {
					
					return new IntervalUnion(this.getLow(), other.getHigh());
					
				} else {
					
					return new IntervalUnion(other.getLow(), other.getHigh());
				}
			}
		}
		
		return new IntervalUnion(this, other);
		
	}
	
	public IntervalUnion intersection(IntervalUnion other) {
		
		if ((other.low2 != null) & (other.high2 != null)) {
			
			IntervalUnion temp = this.intersection(other.high2);
			temp = this.intersection(other.low2);
			return temp;
		}
		
		if (this.equals(other)) {
			
			return new IntervalUnion(this.getLow(), this.getHigh());
			
		} else {
			
			if ((other.getLow() > this.getHigh()) || (this.getLow() > other.getHigh())) {
				
				return new IntervalUnion();
			}
		}
		
		int o1 = Math.max(this.low, other.low);
		int o2 = Math.min(this.high, other.high);
		
		return new IntervalUnion(o1, o2);
		
	}
	
	public int getPieceCount() {
		
		int count = 0;
		
		if ((this.high2 != null) && (this.low2 != null)) {
			
			count = this.high2.getPieceCount() + this.low2.getPieceCount();
			
		} else {
			
			count += 1;
		}
		
		return count;
	}
	
}
