package com.zero.j2se.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 
 *  
 * happens-before原则主要包括：
 * 1.程序次序规则(Program Order Rule)：在同一个线程中，按照程序代码顺序，书写在前面的操作先行发生于书写在后面的操纵。准确的说是程序的控制流顺序，考虑分支和循环等。
 * 2.管理锁定规则(Monitor Lock Rule)：一个unlock操作先行发生于后面（时间上的顺序）对同一个锁的lock操作。
 * 3.volatile变量规则(Volatile Variable Rule)：对一个volatile变量的写操作先行发生于后面（时间上的顺序）对该变量的读操作。
 * 4.线程启动规则(Thread Start Rule)：Thread对象的start()方法先行发生于此线程的每一个动作。
 * 5.线程终止规则(Thread Termination Rule)：线程的所有操作都先行发生于对此线程的终止检测，可以通过Thread.join()方法结束、Thread.isAlive()的返回值等手段检测到线程已经终止执行。
 * 6.线程中断规则(Thread Interruption Rule)：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断时事件的发生。Thread.interrupted()可以检测是否有中断发生。
 * 7.对象终结规则(Finilizer Rule)：一个对象的初始化完成（构造函数执行结束）先行发生于它的finalize()的开始。
 * 8.传递性(Transitivity)：如果操作A 先行发生于操作B，操作B 先行发生于操作C，那么可以得出A 先行发生于操作C 
 * 
 * 原子性:
 *   JMM保证的原子性变量操作包括read、load、assign、use、store、write，而long、double非原子协定导致的非原子性操作基本可以忽略。如果需要对更大范围的代码实行原子性操作，则需要JMM提供的lock、unlock、synchronized等来保证。
 *   
 * 可见性：
 *   可见性是指当一个线程修改了共享变量的值，其他线程能够立即得知这个修改。JMM在变量修改后将新值同步回主内存，依赖主内存作为媒介，在变量被线程读取前从内存刷新变量新值，保证变量的可见性。普通变量和volatile变量都是如此，只不过volatile的特殊规则保证了这种可见性是立即得知的，
 * 而普通变量并不具备这种严格的可见性。除了volatile外，synchronized和final也能保证可见性。
 * 
 * 有序性：
 *   如果在本线程内观察，所有的操作都是有序的；如果在一个线程中观察另一个线程，所有的操作都是无序的。前半句指“线程内表现为串行的语义”（as-if-serial），后半句值“指令重排序”和普通变量的”工作内存与主内存同步延迟“的现象。
 *   
 * CAS（Compare And Swap） 无锁算法是乐观锁技术，当多个线程尝试使用CAS同时更新同一个变量时，只有其中一个线程能更新变量的值，而其它线程都失败，失败的线程并不会被挂起，而是被告知这次竞争中失败，并可以再次尝试。
 * CAS有3个操作数，内存值V，旧的预期值A，要修改的新值B。当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。   
 * 
 * 多线程环境下的伪共享（false sharing）:伪共享是多线程系统（每个处理器有自己的局部缓存）中一个众所周知的性能问题。伪共享发生在不同处理器的上的线程对变量的修改依赖于相同的缓存行.
 */
public class LearnThread extends Thread implements Runnable{
	
	/**
	 * 原理:
	 * 每个Thread 维护一个 ThreadLocalMap 映射表，这个映射表的 key 是 ThreadLocal实例本身，value 是真正需要存储的 Object。也就是说 ThreadLocal 本身并不存储值，它只是作为一个 key 来让线程从 ThreadLocalMap 获取 value。
	 * 值得注意的是 ThreadLocalMap 是使用 ThreadLocal 的弱引用作为 Key 的，弱引用的对象在 GC 时会被回收。
	 * 
	 * 总结：
	 * 1.通过访问副本来运行业务，这样的结果是耗费了内存，但大大减少了线程同步所带来性能消耗，也减少了线程并发控制的复杂度。
	 * 2.ThreadLocal不能使用原子类型，只能使用Object类型。
	 * 
	 * 内存泄漏点:
	 * ThreadLocalMap使用ThreadLocal的弱引用作为key，如果一个ThreadLocal没有外部强引用来引用它，那么系统 GC 的时候，这个ThreadLocal势必会被回收，这样一来，ThreadLocalMap中就会出现key为null的Entry，就没有办法访问这些key为null的Entry的value，
	 * 如果当前线程再迟迟不结束的话，这些key为null的Entry的value就会一直存在一条强引用链：Thread Ref -> Thread -> ThreaLocalMap -> Entry -> value永远无法回收，造成内存泄漏。
	 * ThreadLocal内存泄漏的根源是由于ThreadLocalMap的生命周期跟Thread一样长，如果没有手动删除对应key就会导致内存泄漏，而不是因为弱引用。
	 * 
	 * ThreadLocal 最佳实践:
	 * 每次使用完ThreadLocal，都调用它的remove()方法，清除数据。
	 * 
	 */
	private ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	/**
	 * JMM要求lock、unlock、read、load、assign、use、store、write这8个操作都必须具有原子性，但对于64为的数据类型(long和double)，具有非原子协定：允许虚拟机将没有被volatile修饰的64位数据的读写操作划分为2次32位操作进行。
	 * 如果多个线程共享一个没有声明为volatile的long或double变量，并且同时读取和修改，某些线程可能会读取到一个既非原值，只能看到该值的一半(前 32 位)
	 */
	private long count;
	
	/**
	 * volatile具备两种特性： 
	 *  1. 提供 happens-before的保证变量对所有线程的可见性，指一条线程修改了这个变量的值，新值对于其他线程来说是可见的，但并不是多线程安全的。 
	 *	2. 禁止指令重排序优化提供顺序保证。 
	 *
	 *  volatile如何保证内存可见性: 
	 *	1.当写一个volatile变量时,Java内存模型会插入一个写屏障（write barrier），JMM会把该线程对应的本地内存中的共享变量刷新到主内存。 
	 *	2.当读一个volatile变量时,Java内存模型会插入一个读屏障（read barrier），JMM会把该线程对应的本地内存置为无效。线程接下来将从主内存中读取共享变量。
	 *	
	 *  由于volatile只能保证变量的可见性和屏蔽指令重排序，只有满足下面2条规则时，才能使用volatile来保证并发安全，否则就需要加锁（使用synchronized、lock或者java.util.concurrent中的Atomic原子类）来保证并发中的原子性。
	 *  1.运算结果不存在数据依赖（重排序的数据依赖性），或者只有单一的线程修改变量的值（重排序的as-if-serial语义）
	 *  2.变量不需要与其他的状态变量共同参与不变约束
	 */
	private volatile int param;
	
	public static void main(String[] args) {
		/**
		 * 用start方法来启动线程，真正实现了多线程运行，这时无需等待run方法体代码执行完毕而直接继续执行下面的代码。通过调用Thread类的start()方法来启动一个线程，这时此线程处于就绪（可运行）状态，并没有运行，
		 * 一旦得到cpu时间片，就开始执行run()方法，这里方法run()称为线程体，它包含了要执行的这个线程的内容，Run方法运行结束，此线程随即终止。
		 */
		new Thread(new LearnThread()).start();
	}
	
	
	/**
	 * 线程5状态：
	 * 1.新建状态:当用new操作符创建一个线程时。此时程序还没有开始运行线程中的代码。
	 * 2.就绪状态:处于就绪状态的线程并不一定立即运行run()方法，线程还必须同其他线程竞争CPU时间片，只有获得CPU时间片才可以运行线程。因为在单CPU的计算机系统中，不可能同时运行多个线程，一个时刻仅有一个线程处于运行状态。因此此时可能有多个线程处于就绪状态。
	 *         对多个处于就绪状态的线程是由Java运行时系统的线程调度程序来调度的。
	 * 3.运行状态:当线程获得CPU时间片后，它才进入运行状态，真正开始执行run()方法。
	 * 4.阻塞状态:线程运行过程中，可能由于各种原因进入阻塞状态:
	 *         - 线程通过调用sleep方法进入睡眠状态；
     *         - 线程调用一个在I/O上被阻塞的操作，即该操作在输入输出操作完成之前不会返回到它的调用者；
     *         - 线程试图得到一个锁，而该锁正被其他线程持有；
     *         - 线程在等待某个触发条件；
     *         所谓阻塞状态是正在运行的线程没有运行结束，暂时让出CPU，这时其他处于就绪状态的线程就可以获得CPU时间，进入运行状态。
     * 5.死亡状态:有两个原因会导致线程死亡：
     *         - run方法正常退出而自然死亡
     *         - 一个未捕获的异常终止了run方法而使线程猝死
     *         
     * 线程安全性的五种类别：
     * 1.不可变的对象一定是线程安全的，并且永远也不需要额外的同步。        
     * 2.线程安全 的对象，由类的规格说明所规定的约束在对象被多个线程访问时仍然有效，不管运行时环境如何排列，线程都不需要任何额外的同步。
     * 3.有条件的线程安全类对于单独的操作可以是线程安全的，但是某些操作序列可能需要外部同步。
     * 4.线程兼容类不是线程安全的，但是可以通过正确使用同步而在并发环境中安全地使用。这可能意味着用一个 synchronized 块包围每一个方法调用，或者创建一个包装器对象，其中每一个方法都是同步的。
     * 5.线程对立类是那些不管是否调用了外部同步都不能在并发使用时安全地呈现的类。线程对立很少见，当类修改静态数据，而静态数据会影响在其他线程中执行的其他类的行为，这时通常会出现线程对立。
     * 
	 */
	@Override
	public void run() {
		
	}
	
	/**
	 * 
	 * 多线程技术主要解决处理器单元内多个线程执行的问题，它可以显著减少处理器单元的闲置时间，增加处理器单元的吞吐能力。
	 * 假设一个服务器完成一项任务所需时间为：T1 创建线程时间，T2 在线程中执行任务的时间，T3 销毁线程时间。 如果：T1 + T3 远大于 T2，则可以采用线程池，以提高服务器性能。
	 * 
	 * 一个线程池包括以下四个基本组成部分：
	 * 1.线程池管理器（ThreadPool）：用于创建并管理线程池，包括 创建线程池，销毁线程池，添加新任务；
	 * 2.工作线程（PoolWorker）：线程池中线程，在没有任务时处于等待状态，可以循环的执行任务；
	 * 3.任务接口（Task）：每个任务必须实现的接口，以供工作线程调度任务的执行，它主要规定了任务的入口，任务执行完后的收尾工作，任务的执行状态等；
	 * 4.任务队列（taskQueue）：用于存放没有处理的任务。提供一种缓冲机制。 
	 */
	public void useThreadPool() {
		// 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		// 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		// 创建一个定长线程池，支持定时及周期性任务执行。
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
		// 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	}
	
	// 线程阻塞
	// 保证三大特性
	// 编译器优化
	public synchronized void syncMethod() {
		
	}
	
	public void syncBlockCode() {
		int a = 0;
		synchronized (this) {

			for (int i = 0; i < 5; i++) {

				System.out.println(Thread.currentThread().getName() + "synchronized loop " + i);

			}
		}
		int b = 1;
	}
	
	/**
	 * 
	 */
	public void useAQS() {
		
	}
}
