package util;

import MyMath.Eval;

public class computeL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(computeLong(2,3,2));
		int a=0;
		String fx="Math.pow((1+4x+3),1/2)";
		Eval eval= new Eval();
		//eval.integral(fx, "0", "2", a);
		int oosdf=12;
	}
	public static float computeLong(float a,float b,float d)
	{
		float result=0;
		result=(float) ((Math.pow(2*a*d+b+1, 3/2)-Math.pow(b+1, 3/2))/(3*a));
		return result;
	}
}
