package Calculate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCalculate {
	Calculate cal;
	String str;
	@Before
	public void setUp(){

	}
	@Test
	public void TestAdd(){
		str = "20 + 10";
		cal = new Calculate(str);			
		System.out.println(cal);
		Assert.assertEquals("加法有问题", cal, 30);
	}
	@Test
	public void TestMinus(){
		String str = "20 - 10";
		Calculate cal = new Calculate(str);			
		System.out.println(cal);
		Assert.assertEquals("减法有问题", cal, "10");
	}
	@Test
	public void TestMul(){
		str = "20 * 10";
		cal = new Calculate(str);			
		System.out.println(cal);
		Assert.assertEquals("乘法有问题", cal, "200");
	}
	@Test
	public void TestDivide(){
		str = "20 / 10";
		cal = new Calculate(str);			
		System.out.println(cal);
		Assert.assertEquals("除法有问题", cal , "2");
	}
	@Test(expected = ArithmeticException.class)
	public void TestDivideException(){
		str = "20 / 0";
		cal = new Calculate(str);			
		System.out.println(cal);
		Assert.assertEquals("除法有问题", cal, "2");
	}
		
}
