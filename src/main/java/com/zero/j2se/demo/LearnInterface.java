package com.zero.j2se.demo;


/**
 * 
 * 定义：
 * 接口，英文称作interface，在软件工程中，接口泛指供别人调用的方法或者函数。从这里，我们可以体会到Java语言设计者的初衷，它是对行为的抽象。 
 * 接口中可以含有 变量和方法。但是要注意，接口中的变量会被隐式地指定为public static final变量（并且只能是public static final变量，用private修饰会报编译错误），
 * 而方法会被隐式地指定为public abstract方法且只能是public abstract方法（用其他关键字，比如private、protected、static、 final等修饰会报编译错误），并且接口中所有的方法不能有具体的实现，也就是说，接口中的方法必须都是抽象方法。
 * 
 * 允许一个类遵循多个特定的接口。如果一个非抽象类遵循了某个接口，就必须实现该接口中的所有方法。
 * 对于遵循某个接口的抽象类，可以不实现该接口中的抽象方法。
 * 
 * 抽象类和接口的区别：
 * 1.语法层面上的区别
 *   1)抽象类可以提供成员方法的实现细节，而接口中只能存在public abstract 方法；
 *   2)抽象类中的成员变量可以是各种类型的，而接口中的成员变量只能是public static final类型的；
 *   3)接口中不能含有静态代码块以及静态方法，而抽象类可以有静态代码块和静态方法；
 *   4)一个类只能继承一个抽象类，而一个类却可以实现多个接口。
 *   
 * 2.设计层面上的区别
 *   1)抽象类是对一种事物的抽象，即对类抽象，而接口是对行为的抽象。抽象类是对整个类整体进行抽象，包括属性、行为，但是接口却是对类局部（行为）进行抽象。
 *     从这里可以看出，继承是一个 "是不是"的关系，而 接口 实现则是 "有没有"的关系。如果一个类继承了某个抽象类，则子类必定是抽象类的种类，而接口实现则是有没有、具备不具备的关系。
 *   2)设计层面不同，抽象类作为很多子类的父类，它是一种模板式设计。而接口是一种行为规范，它是一种辐射式设计。模板式设计如果它们的公共部分需要改动，则只需要改动模板就可以了。而对于接口则不行，如果接口进行了变更，则所有实现这个接口的类都必须进行相应的改动。
 * 
 */
public interface LearnInterface {

	public void fun();
}
