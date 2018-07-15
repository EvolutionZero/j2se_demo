package com.zero.j2se.demo;

import java.util.Arrays;

import com.zero.j2se.demo.bean.Person;

public class LearnBase {

	// 定义一个基本数据类型的变量，一个对象的引用，还有就是函数调用的现场保存都使用内存中的栈空间.栈空间操作起来最快但是栈很小
	private int param;
	private int[] array;
	
	public static void useBoxed() {
		
		// 通过new关键字和构造器创建的对象放在堆空间.通常大量的对象都是放在堆空间
		Person person = new Person("鲁迅", "中国", "汉语",23);
		
		// 程序中的字面量（literal）如直接书写的100、”hello”和常量都是放在静态区中
		int num = 100;
		
		// String 类是final类，不可以被继承。
		// String是只读字符串，也就意味着String引用的字符串内容是不能被改变的
		String hello = "hello";
		
		// StringBuffer/StringBuilder类表示的字符串对象可以直接进行修改
		// 它的所有方面都没有被synchronized修饰，因此效率比较高
		StringBuilder stringBuilder = new StringBuilder();
		// 线程安全
		StringBuffer stringBuffer = new StringBuffer();
		
		// 语句中变量str放在栈上，用new创建出来的字符串对象放在堆上，而”hello”这个字面量放在静态区。
		String str = new String("hello");
		
		// 如果字面量是[-128,127],那么不会new新的Integer对象，而是直接引用常量池的Integer对象
		Integer int1 = new Integer(120);
		Integer int2 = new Integer(1024);
		
		
		
	}

	/**
	 * Java对于eqauls方法和hashCode方法是这样规定的：
	 * (1)如果两个对象相同（equals方法返回true），那么它们的hashCode值一定要相同；
	 * (2)如果两个对象的hashCode相同，它们并不一定相同。
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(array);
		result = prime * result + param;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LearnBase other = (LearnBase) obj;
		if (!Arrays.equals(array, other.array))
			return false;
		if (param != other.param)
			return false;
		return true;
	}
}
