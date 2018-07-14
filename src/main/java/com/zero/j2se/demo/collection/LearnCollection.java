package com.zero.j2se.demo.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LearnCollection {

	private int param;
	
	/**
	 * 最佳实践
	 */
	public static void bestPractise() {
		// 如果指定了大小，会选用Array而非ArrayList
		String[] array = new String[1024];
		ArrayList<String> arrayList = new ArrayList<>();
		
		// 想根据插入顺序遍历一个Map，使用TreeMap
		// 使用JDK提供的不可变类作为Map的key，可以避免自己实现hashCode()和equals()
		TreeMap<String, String> treeMap = new TreeMap<>();
	}
	
	/**
	 * 1.Collection为集合层级的根接口。一个集合代表一组对象，这些对象即为它的元素。Java平台不提供这个接口任何直接的实现。
	 * 2.Collection不从Cloneable和Serializable接口继承：
	 *   Collection接口指定一组对象，对象即为它的元素。很多Collection实现有一个公有的clone方法。然而，把它放到集合的所有实现中也是没有意义的。这是因为Collection是一个抽象表现。重要的是实现。
	 *   当与具体实现打交道的时候，克隆或序列化的语义和含义才发挥作用。所以，具体实现应该决定如何对它进行克隆或序列化，或它是否可以被克隆或序列化。
                           在所有的实现中授权克隆和序列化，最终导致更少的灵活性和更多的限制。特定的实现应该决定它是否可以被克隆和序列化。
	 * 3.Java.util包中的所有集合类都被设计为fail-fast的， 而java.util.concurrent中的集合类都为fail-safe的。
	     Fail-fast迭代器抛出ConcurrentModificationException，而fail-safe迭代器从不抛出ConcurrentModificationException。
	 */
	public static void useCollection() {
		LinkedList<String> linkedList = new LinkedList<>();
		
		linkedList.add("A");
		linkedList.add("B");
		linkedList.add("C");
		linkedList.add("D");
		
		// Collections是一个工具类仅包含静态方法，它们操作或返回集合。它包含操作集合的多态算法，返回一个由指定集合支持的新集合和其它一些内容。这个类包含集合框架算法的方法，比如折半搜索、排序、混编和逆序等。
		Collections.binarySearch(linkedList, "B");
		
		// 创建一个只读集合，这将确保改变集合的任何操作都会抛出UnsupportedOperationException
		Collection<String> unmodifiableCollection = Collections.unmodifiableCollection(linkedList);
		// 根据指定集合来获取一个synchronized（线程安全的）集合
		Collection<String> synchronizedCollection = Collections.synchronizedCollection(linkedList);
		
	}
	
	/**
	 * 1.Set是一个不能包含重复元素的集合
	 * 
	 * 
	 */
	public static void useSet() {
		CopyOnWriteArrayList<Object> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
	}
	
	/**
	 * 
	 * 1.List是一个有序集合，可以包含重复元素
	 * 
	 */
	public static void useList() {
		
		// ArrayList是由Array所支持的基于一个索引的数据结构，所以它提供对元素的随机访问，复杂度为O(1)
		// 在LinkedList中插入、添加和删除一个元素会移动list内元素，因此会必LinkedList慢
		ArrayList<String> arrayList = new ArrayList<>();
		
		// LinkedList存储一系列的节点数据，每个节点都与前一个和下一个节点相连接。所以，尽管有使用索引获取元素的方法，内部实现是从起始点开始遍历，遍历到索引的节点然后返回元素，时间复杂度为O(n)，比ArrayList要慢
		// 在LinkedList中插入、添加和删除一个元素会更快，因为在一个元素被插入到中间的时候，不会涉及改变数组的大小，或更新索引。
		LinkedList<String> linkedList = new LinkedList<>();
		
		linkedList.add("A");
		linkedList.add("B");
		linkedList.add("C");
		linkedList.add("D");
		
		Iterator<String> it = linkedList.iterator();
		// Iterator遍历更加安全，因为当一个集合正在被遍历的时候，它会阻止其它线程去修改集合
		// 在当前遍历的集合元素被更改的时候，它会抛出ConcurrentModificationException
		while (it.hasNext()) {
			String obj = (String) it.next();
			if("A".equals(obj)) {
				// 迭代器允许调用者从集合中移除元素
				it.remove();
			}
			System.out.print(obj + " ");
		}
		System.out.println();
		
		// 我们可以使用Iterator来遍历Set和List集合，而ListIterator只能遍历List。
		// Iterator只可以向前遍历，而LIstIterator可以双向遍历。
		// ListIterator从Iterator接口继承，然后添加了一些额外的功能，比如添加一个元素、替换一个元素、获取前面或后面元素的索引位置。
		ListIterator<String> listIterator = linkedList.listIterator();
		
	}
	
	/**
	 * 
	 * 原理：
	 * HashMap在Map.Entry静态内部类实现中存储key-value对。HashMap使用哈希算法，在put和get方法中，它使用hashCode()和equals()方法。
	 * 当我们通过传递key-value对调用put方法的时候，HashMap使用Key hashCode()和哈希算法来找出存储key-value对的索引。
	 * Entry存储在LinkedList中，所以如果存在entry，它使用equals()方法来检查传递的key是否已经存在，如果存在，它会覆盖value，如果不存在，它会创建一个新的entry然后保存。
	 * 当我们通过传递key调用get方法时，它再次使用hashCode()来找到数组中的索引，然后使用equals()方法找出正确的Entry，然后返回它的值。
	 * 
	 * 关于HashMap比较重要的问题是容量、负荷系数和阀值调整。HashMap默认的初始容量是32，负荷系数是0.75。阀值是为负荷系数乘以容量，无论何时我们尝试添加一个entry，如果map的大小比阀值大的时候，HashMap会对map的内容进行重新哈希，且使用更大的容量。
	 * 
	 * 1.Map是一个将key映射到value的对象
	 * 2.Map接口不继承Collection接口
	 */
	public static void useMap() {
		// 容量总是2的幂，所以如果知道的容量大小和负荷系数，在初始化阶段去指定它是可以防止多次扩容导致的性能损失。
		HashMap<String, String> map = new HashMap<String, String>(1024, 0.8f);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		LearnCollection other = (LearnCollection) obj;
		if (param != other.param)
			return false;
		return true;
	}
}
