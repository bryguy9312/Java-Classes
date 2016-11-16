package util;
/*
 * Bryan Wicker
 * Class that handles fractions, fraction arithmetic, and fraction comparison
 */


public class Fraction implements Comparable<Fraction>{
	//denominator
    private long denom;
    //numerator
    private long numer;
    
    
    //main constructor
    public Fraction(long numer, long denom){
    	if(denom == 0){
    		throw new IllegalArgumentException("Denominator cannot be zero");		//cannot divide by zero
    	}
        this.denom = denom;
        this.numer = numer;
        simplify();
    }
    //usability constructors
    public Fraction(int numer, int denom){
        this((long) numer, (long) denom);
    }
    public Fraction(int numer, long denom){
        this((long) numer, denom);
    }
    public Fraction(long numer, int denom){
        this(numer, (long) denom);
    }
    
    //constructs fraction representation of whole number
    public Fraction(int numer){
    	this((long) numer, 1);
    }
    
    //Returns the fraction result of adding the passed fraction y to this fraction
    public Fraction add(Fraction y){
        long den = lcm(this.denom, y.denom());
        long num = this.numer * (den / this.denom) + y.numer() * (den / y.denom());
        Fraction temp = new Fraction(num, den);
        return temp;
    }
    
    //Returns the fraction result of subtracting this fraction by the passed fraction y
    public Fraction subtract(Fraction y){
    	//we perform the equivalent operation of adding negative y to this fraction
    	Fraction z = new Fraction(- y.numer(), y.denom());
    	return this.add(z);
    }
    
    //Returns the fraction result of multiplying this fraction by the passed fraction y
    public Fraction multiply(Fraction y){
        long num1 = this.numer;
        long num2 = y.numer();
        long den1 = this.denom;
        long den2 = y.denom();
        //cross cancel
        long temp = gcd(num1, den2);
        if(temp > 1){
            num1 /= temp;
            den2 /= temp;
        }
        temp = gcd(num2, den1);
        if(temp > 1){
            num2 /= temp;
            den1 /= temp;
        }
        return new Fraction(num1*num2, den1*den2);
    }
    
    //returns the fraction result of dividing this fraction by the passed fraction y
    public Fraction divide(Fraction y){
    	if(y.equals(new Fraction(0))){
    		throw new ArithmeticException("Division by zero");
    	}
    	return this.multiply(new Fraction(y.denom(), y.numer()));
    }
    
    //internal method to keep fractions as simple as possible
    private void simplify(){
        long gcd = gcd(numer, denom);
        numer = numer / gcd;
        denom = denom / gcd;
        //if denominator and numerator are both negative, simplify to positive fraction
        //if denominator is negative and numerator is non-negative, simplify to negative numerator
        if((numer < 0 && denom < 0) || (numer >= 0 && denom < 0)){
        	numer = -numer;
        	denom = -denom;
        }
    }
    
    //return denominator of this fraction
    public long denom(){
        return denom;
    }
    //return numerator of this fraction
    public long numer(){
        return numer;
    }
    
    //Returns greatest common integer (long) divisor
    //Euclid's Algorithm
    private long gcd(long a, long b){
    	//need positive numbers for Euclid's Algorithm to work
    	a = Math.abs(a);
    	b = Math.abs(b);
        while (b > 0){
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    //Returns the least common multiple of the passed longs a,b
    private long lcm(long a, long b){
        return a * (b / gcd(a,b));
    }
    
    //returns true if this fraction is greater than the passed fraction y, false otherwise
	public boolean greaterThan(Fraction y){
		return (this.compareTo(y) > 0);
	}
	
	//returns true if this fraction is equal to the passed fraction y, false otherwise
    public boolean equals(Fraction y){
    	return (this.compareTo(y) == 0);
    }
    
    //returns true if this fraction is less than the passed fraction y, false otherwise
    public boolean lessThan(Fraction y){
    	return(this.compareTo(y) < 0);
    }
    
    //returns String representation of our fraction, shows whole numbers instead of fractions where possible
    public String toString(){
    	if(this.denom() == 1){
    		return "" + this.numer();
    	}
    	return "" + this.numer + "/" + this.denom;
    }
    
	@Override
	public int compareTo(Fraction y) {
		return (int) this.subtract(y).numer();
	}
}
