package com.zero.j2se.demo;

/**
 * 
 * IO流：也称流、数据流，数据流(Stream)是一组有顺序的、有起点和终点的字节集合，是对输入和输出(数据传输)的总称和抽象。即数据在两设备间的传输称为流。流的本质是数据传输，根据数据传输特性将流抽象为各种类，方便更直观的进行数据操作。
 * IO流根据处理数据类型不同分为字符流和字节流
 * IO流根据数据流向不同分为输入流和输出流
 * IO流根据功能不同分为节点流(Node)和过滤流(Filter)
 * 
 * 字符流和字节流区别：
 * 字符流的由来：因为数据编码的不同，而有了对字符进行高效操作的流对象。本质其实就是基于字节流读取时，去查了指定的码表。
 * 1.读写单位不同：字节流以字节(8bit)为基本处理单位，字符流以字符为基本处理单位，根据码表映射字符，一次可能读多个字节。Java中1个字符占2个字节。
 * 2.处理对象不同：字节流能处理所有类型的数据(如图片、AVI等)，而字符流只能处理字符类型的数据。只要是处理纯文本数据，就优先考虑使用字符流。除此之外都使用字节流。
 * 
 * 
 * InputStream(输入字节流)：
 * 1.InputStream是所有的输入字节流的父类，是抽象类。
 * 2.ByteArrayInputStream、StringBufferInputStream、FileInputStream是三种基本的介质流(基础流)，它们分别从Byte数组、StringBuffer、和本地文件中读取数据。PipedInputstream是从与其它线程共用的管道中读取数据。
 * 3.ObjectInputStream和所有FilterInputStream的子类都是装饰流(装饰器模式的主角)。
 * 
 * OutputStream(输出字节流)：
 * 1.OutputStream是所有的输出字节流的父类，是抽象类。
 * 2.ByteArrayOutputStream、FileOutputStream是两种基本的介质流(基础流)，它们分别向Byte数组、和本地文件中写入数据。PipedOutputStream是向与其它线程共用的管道中写入数据。
 * 3.ObjectOutputStream和所有FilterOutputStream的子类都是装饰流。
 * 
 * Reader(字符输入流)：
 * 1.Reader是所有的字符输入流的父类，是抽象类。
 * 2.CharArrayReader、StringReader是两种基本的介质流(基础流)，它们分别将Char数组、String中读取数据。PipedReader是从与其它线程共用的管道中读取数据。
 * 3.BufferedReader很明显就是一个装饰器，它和其子类负责装饰其它Reader类对象。
 * 4.FilterReader是所有自定义具体装饰流的父类，其子类PushbackReader对Reader类对象进行装饰，会增加一个行号。
 * 5.InputStreamReader是一个连接字节流和字符流的桥梁，它将字节流转变为字符流。FileReader可以说是一个达到此功能常用的工具类。
 * 
 * Writer(字符输出流)：
 * 1.Writer是所有的字符输出流的父类，是抽象类。
 * 2.CharArrayWriter、StringWriter是两种基本的介质流(基础流)，它们分别向Char数组、String中写入数据。PipedWriter是向与其它线程共用的管道中写入数据。
 * 3.BufferedWriter是一个装饰器，为Writer提供缓冲功能。
 * 4.PrintWriter和PrintStream极其类似，功能和使用也非常相似。
 * 5.OutputStreamWriter是OutputStream到Writer转换的桥梁，它的子类FileWriter其实就是一个实现此功能的具体类
 * 
 * 字符流与字节流转换：转换流(中介流)的特点：
 * 1.其是字符流和字节流之间的桥梁。
 * 2.可对读取到的字节数据经过指定编码转换成字符。
 * 3.可对读取到的字符数据经过指定编码转换成字节。当字节和字符之间有转换动作时、流操作的数据需要编码或解码时使用转换流。
 * 
 * InputStreamReader：字节到字符的桥梁
 * OutputStreamWriter：字符到字节的桥梁。
 * InputStreamReader、OutputStreamWriter这两个流对象是字符体系中的成员，它们有转换作用，本身又是字符流，所以在构造的时候需要传入字节流对象进来。
 * 
 * File类：File类是对文件系统中文件以及文件夹进行封装的对象，可以通过对象的思想来操作文件和文件夹。File类保存文件或目录的各种元数据信息，包括文件名、文件长度、最后修改时间、是否可读、获取当前文件的路径名、判断指定文件是否存在、获得当前目录中的文件列表、创建、删除文件和目录等方法。
 * RandomAccessFile类：
 *   该对象并不是流体系中的一员，其封装了字节流，同时还封装了一个缓冲区(字符数组)，通过内部的指针来操作字符数组中的数据。该对象特点：
               该对象只能操作文件，所以构造函数接收两种类型的参数：字符串文件路径，File类对象。
               该对象既可以对文件进行读操作，也能进行写操作，在进行对象实例化时可指定操作模式(r,rw)。
 *   注意：该对象在实例化时，如果要操作的文件不存在，会自动创建；如果文件存在，写数据未指定位置，会从头开始写，即覆盖原有的内容。可以用于多线程下载或多个线程同时写数据到文件。
 */
public class LearnIO {

}
