package com.zero.j2se.demo;

/**
 * 
 * 定义：
 * 包含抽象方法的类称为抽象类，但并不意味着抽象类中只能有抽象方法，它和普通类一样，同样可以拥有成员变量和普通的成员方法。如果一个类含有抽象方法，则称这个类为抽象类，抽象类必须在类前用abstract关键字修饰。因为抽象类中含有无具体实现的方法，所以不能用抽象类创建对象。
 * 
 * 注意，抽象类和普通类的主要有三点区别：
 * 1.抽象方法必须为public或者protected（因为如果为private，则不能被子类继承，子类便无法实现该方法），缺省情况下默认为public。
 * 2.抽象类不能用来创建对象
 * 3.如果一个类继承于一个抽象类，则子类必须实现父类的抽象方法。如果子类没有实现父类的抽象方法，则必须将子类也定义为为abstract类。
 * 
 */
public abstract class LearnAbstractClass {

	public abstract void fun();
	
	public void common() {
		
	}
}
