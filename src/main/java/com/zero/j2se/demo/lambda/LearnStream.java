package com.zero.j2se.demo.lambda;

import com.sun.xml.internal.ws.util.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearnStream {

    public static void useMap(){
        String text = "A scientific experiment satellite, PakTES-1A, developed by Pakistan, was sent into orbit via the same rocket.";
        Stream<String> words = Pattern.compile("[\\P{L}+]").splitAsStream(text);
        //  对一个流的值进行某种形式的转换，这里转换成首字母大写的形式
        words.map(word -> StringUtils.capitalize(word)).forEach(word -> System.out.print(word + " "));
        System.out.println();
    }


    public static void useFlatMap(){
        // 我们希望输出构成这一数组的所有非重复字符，那么我们可能首先会想到如下实现：
        String[] strs = {"java8", "is", "easy", "to", "use"};
        List<String[]> distinctStrs = Arrays.stream(strs)
                .map(str -> str.split(""))  // 映射成为Stream<String[]>
                .distinct()
                .collect(Collectors.toList());
        // 在执行map操作以后，我们得到是一个包含多个字符串（构成一个字符串的字符数组）的流，此时执行distinct操作是基于在这些字符串数组之间的对比，所以达不到我们希望的目的，此时的输出为：
        distinctStrs.stream().forEach(array -> System.out.println(Arrays.toString(array)));

        // distinct只有对于一个包含多个字符的流进行操作才能达到我们的目的，即对Stream<String>进行操作。此时flatMap就可以达到我们的目的：
        List<String> expectResult = Arrays.stream(strs)
                .map(str -> str.split(""))  // 映射成为Stream<String[]>
                .flatMap(Arrays::stream)  // 扁平化为Stream<String>
                .distinct()
                .collect(Collectors.toList());
        expectResult.stream().forEach(letter -> System.out.printf(letter + " "));
    }


    public static void createStream(){
        String text = "A scientific experiment satellite, PakTES-1A, developed by Pakistan, was sent into orbit via the same rocket.";
        Stream<String> words = Stream.of(text.split("[\\P{L}+]"));
        System.out.println(words.collect(Collectors.toList()));

        // 可变长度参数
        Stream<String> song = Stream.of("A", "little", "boy");
        System.out.println(song.collect(Collectors.toList()));

        Stream<String> speaks = Pattern.compile("[\\P{L}+]").splitAsStream(text);
        System.out.println(speaks.collect(Collectors.toList()));

        // 空的流
        Stream<Object> empty = Stream.empty();

        // 一个含常量的流
        Stream<String> echos = Stream.generate(() -> "Echo");

        // 一个含随机数的流
        Stream<Double> randoms = Stream.generate(Math::random);

        // 一个0,1,2,3,4....的无限序列
        Stream<BigInteger> serial = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
    }

    public static void filterLongWords(){
        try{
            String text = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/main/resources/english.txt"), "UTF-8");

            // 非字母将认为是分隔符
            List<String> words = Arrays.asList(text.split("[\\P{L}+]"));

            // 长度超过8认定为长单词,并发执行
            List<String> longWords = words.parallelStream().filter(w -> w.length() > 8).collect(Collectors.toList());

            System.out.println("长单词有：" + longWords.stream().count() + "个");
            System.out.println("分别是：" + longWords);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        filterLongWords();
        createStream();
        useMap();
        useFlatMap();
    }
}
