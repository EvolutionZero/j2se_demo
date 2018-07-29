package com.zero.j2se.demo;

/**
 * 
 * 在Java1.4之前，Java的IO模型为blocking IO(BIO)，即阻塞IO，在连接建立后，如果没有数据传输就会一直处于阻塞状态，这时候从线程来看就处于休眠状态，从CPU来看就是处于停滞状态，极大的浪费CPU的计算能力，
 * 如果以这个模型开发应用服务器，则因为大量线程阻塞，占用内存资源大，导致有效连接数很低。BIO通常以字节流或者字符流进行数据传输。
 * 
 * NIO使用bufffer缓冲，channel管道，selector选择器进行核心实现。
 * 
 *
 */
public class LearnNIO {

}
