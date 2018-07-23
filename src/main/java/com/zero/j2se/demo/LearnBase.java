package com.zero.j2se.demo;

import java.util.Arrays;

import com.zero.j2se.demo.bean.Person;

/**
 * 
 * 
 * 1、JVM、JRE和JDK的区别：
 *   JVM(Java Virtual Machine):java虚拟机，用于保证java的跨平台的特性。java语言是跨平台，jvm不是跨平台的。
 *   JRE(Java Runtime Environment):java的运行环境,包括jvm+java的核心类库。	
 *   JDK(Java Development Kit):java的开发工具,包括jre+开发工具
 *   
 * 环境path是配置Windows可执行文件的搜索路径，即扩展名为.exe的程序文件所在的目录，用于指定DOS窗口命令的路径。
 * classpath是配置class文件所在的目录，用于指定类搜索路径，JVM就是通过它来寻找该类的class类文件的。
 * 
 * Throwable：可抛出的。
    |--Error：错误，一般情况下，不编写针对性的代码进行处理，通常是jvm发生的，需要对程序进行修正。
    |--Exception：异常，可以有针对性的处理方式
 *
 * 类初始化顺序：父类静态代变量 -> 父类静态代码块 -> 子类静态变量 -> 子类静态代码块 -> 父类非静态变量（父类实例成员变量） -> 父类构造函数 -> 子类非静态变量（子类实例成员变量） -> 子类构造函数
 *
 */
public class LearnBase {

	// 定义一个基本数据类型的变量，一个对象的引用，还有就是函数调用的现场保存都使用内存中的栈空间.栈空间操作起来最快但是栈很小
	private int param;
	private int[] array;
	
	public static void learn() {
		
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
		
		// 不是线程安全的操作。它涉及到多个指令，如读取变量值，增加，然后存储回内存，这个过程可能会出现多个线程交叉。
		num++;
		
		// false，因为有些浮点数不能完全精确的表示出来。
		System.out.println(3*0.1 == 0.3);
		
		try {
			
		}catch(Exception e) {
			System.exit(0); //退出jvm，只有这种情况finally不执行。
		} finally{
			
		}
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
