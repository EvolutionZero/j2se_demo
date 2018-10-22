package com.zero.j2se.demo.lambda;

import com.sun.xml.internal.ws.util.StringUtils;
import com.zero.j2se.demo.bean.Person;
import com.zero.j2se.demo.utils.MathUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class LearnStream {

	public static void useOriginalStream() {
		// 直接使用原始类型，不必使用包装类
		IntStream stream = IntStream.of(1,2,3,4,5);
		IntStream stream2 = Arrays.stream(new int[] {1,2,3,4});
		
		// 不包括上限[0,100)
		IntStream zeroToNinetyNine = IntStream.range(0, 100);
		// 包括上限[0,100]
		IntStream zeroToHundred = IntStream.rangeClosed(0, 100);
		
		// 基础统计操作
		int sum = zeroToHundred.sum();
		int max = zeroToHundred.max().getAsInt();
		int min = zeroToHundred.min().getAsInt();
		double avg = zeroToHundred.average().getAsDouble();
		
		IntSummaryStatistics summaryStatistics = zeroToHundred.summaryStatistics();
		summaryStatistics.getSum();
		summaryStatistics.getAverage();
		summaryStatistics.getCount();
		summaryStatistics.getMax();
		summaryStatistics.getMin();
		
	}
	
	
    public static void useCollect(){
        Supplier<Stream<String>> streamSupplier = () -> Stream.of("中国", "美国", "法国", "日本", "欧洲");

        // 转数组
        String[] countryArray = streamSupplier.get().toArray(String[]::new);

        List<String> toList = streamSupplier.get().collect(Collectors.toList());
        Set<String> toSet = streamSupplier.get().collect(Collectors.toSet());

        // 指定集合类型
        TreeSet<String> toTreeSet = streamSupplier.get().collect(toCollection(TreeSet::new));

        System.out.println("国家:" + streamSupplier.get().collect(Collectors.joining()));
        System.out.println("国家:" + streamSupplier.get().collect(Collectors.joining(",")));


        Supplier<Stream<Person>> mapStreamSupplier = () -> Stream.of(
                new Person("鲁迅", "中国", "汉语",23),
                new Person("冰心", "中国", "汉语",12),
                new Person("莎士比亚", "英国", "英语",6),
                new Person("雨果", "法国", "法语",8)
        );

        // 名字 -> 国家
        Map<String, String> nameToCountry = mapStreamSupplier.get().collect(Collectors.toMap(Person::getName, Person::getCountry));
        System.out.println("名字 -> 国家 : " + nameToCountry);

        // 名字 -> 人
        Map<String, Person> nameToPerson = mapStreamSupplier.get().collect(Collectors.toMap(Person::getName, Function.identity()));
        System.out.println("名字 -> 人 : " + nameToPerson);

        // 国家 -> 名字, 第三个参数控制当value冲突事如何处理
        Map<String, String> countryToName = mapStreamSupplier.get().collect(Collectors.toMap(Person::getCountry, Person::getName, (existingValue, newValue) -> {
            return existingValue;
        }));
        System.out.println("国家 -> 名字 : " + countryToName);

        // 是否国内 -> 作家
        Map<Boolean, List<Person>> partitioningByCountry = mapStreamSupplier.get().collect(Collectors.partitioningBy(p -> "中国".equals(p.getCountry())));
        System.out.println("是否国内 -> 作家 : " + partitioningByCountry);

        // 国家 -> 作家
        Map<String, List<Person>> countryToPersons = mapStreamSupplier.get().collect(Collectors.groupingBy(Person::getCountry));
        System.out.println("国家 -> 作者 : " + countryToPersons);

        // 国家 -> 作家人数
        Map<String, Long> countryToAuthors = mapStreamSupplier.get().collect(Collectors.groupingBy(Person::getCountry, Collectors.counting()));
        System.out.println("国家 -> 作家人数 : " + countryToAuthors);

        // 国家 -> 作品数
        Map<String, Integer> countryToWorksNum = mapStreamSupplier.get().collect(Collectors.groupingBy(Person::getCountry, Collectors.summingInt(Person::getWorksNum)));
        System.out.println("国家 -> 作品数 : " + countryToWorksNum);

        // 国家 -> 作品数最多作家
        Map<String, Optional<Person>> countryToMaxWorksNumAuthor = mapStreamSupplier.get().collect(Collectors.groupingBy(Person::getCountry, Collectors.maxBy(comparing(Person::getWorksNum))));
        System.out.println("国家 -> 作品数最多作家 : " + countryToMaxWorksNumAuthor);

        ArrayList<Person> distinctPerson =  Stream.of(
                new Person("鲁迅", "中国", "汉语",23),
                new Person("鲁迅", "中国", "汉语",23),
                new Person("冰心", "中国", "汉语",12),
                new Person("莎士比亚", "英国", "英语",6),
                new Person("雨果", "法国", "法语",8)
        ).collect(collectingAndThen(
                toCollection(() -> new TreeSet<>(comparing(Person::getName))), ArrayList::new));
        System.out.println("根据名字去重:" + distinctPerson);
    }


    public static void useOptional(){
        String text = "A scientific experiment satellite, PakTES-1A, developed by Pakistan, was sent into orbit via the same rocket.";
        Supplier<Stream<String>> streamSupplier = () -> Pattern.compile("[\\P{L}+]").splitAsStream(text);

        // 如果找到特定字母开头的第一个单词就执行给定方法
        streamSupplier.get().filter(w -> w.startsWith("P")).findFirst().ifPresent(word -> System.out.println("have word startWith P is " + word));

        // 找出特定字母开头的第一个单词，如果没找到就返回else内容
        System.out.println("P开头的单词：" + streamSupplier.get().filter(w -> w.startsWith("P")).findFirst().orElse("haven`t word startWith P!"));
        System.out.println("Z开头的单词：" + streamSupplier.get().filter(w -> w.startsWith("Z")).findFirst().orElse("haven`t word startWith Z!"));

        // 找出特定字母开头的第一个单词，如果没找到就执行给定方法
        System.out.println("Z开头的单词：" + streamSupplier.get().filter(w -> w.startsWith("Z")).findFirst().orElseGet(()-> "function:haven`t word startWith Z!"));

        // 找出特定字母开头的第一个单词，如果没找到就抛出异常
//        System.out.println("Z开头的单词：" + streamSupplier.get().filter(w -> w.startsWith("Z")).findFirst().orElseThrow(NoSuchElementException::new);

        // 创建一个空的可选值
        Optional.empty();

        // 创建一个可选值
        Optional.of(4);

        // ofNullable被设计成为null值和可选值的一座桥梁。如果obj不为null那么会返回Optional.of(obj),否则会返回Optional.empty()
        Optional.ofNullable(4);

        // 使用flatMap来组合可选值函数，只有当其中每个部分都成功时，整个流水线才成功
        Optional<Double> result = Optional.of(-4).flatMap(MathUtils::inverse).flatMap(MathUtils::squareRoot);
        result.ifPresent(r -> System.out.println(r));
        Optional<Double> result2 = Optional.of(4).flatMap(MathUtils::inverse).flatMap(MathUtils::squareRoot);
        result2.ifPresent(r -> System.out.println(r));

        // Optional的实践用法
        String test = null;
        Optional.ofNullable(test).map(String::toUpperCase).map(s -> s.indexOf(':')).ifPresent(System.out::println);

        Person condition = new Person();
        Optional<Person> u = Optional.ofNullable(new Person());
        condition.setName(u.map(Person::getName).orElse(""));
    }

    public static void useAggregation(){
        String text = "A scientific experiment satellite, PakTES-1A, developed by Pakistan, was sent into orbit via the same rocket.";
        Supplier<Stream<String>> streamSupplier = () -> Pattern.compile("[\\P{L}+]").splitAsStream(text);

        // 找出长度最长的单词，如果存在就打印
        streamSupplier.get().max(comparing(word -> word.length())).ifPresent(word -> System.out.print("[" + word + "]"));

        System.out.println();

        // findFirst用于返回满足条件的第一个元素
        streamSupplier.get().filter(word -> word.startsWith("P")).findFirst().ifPresent(word -> System.out.print("[" + word + "]"));

        System.out.println();

        // findAny相对于findFirst的区别在于，findAny不一定返回第一个，而是返回任意一个
        streamSupplier.get().parallel().filter(word -> word.startsWith("P")).findAny().ifPresent(word -> System.out.print("[" + word + "]"));
        System.out.println();

        // 检测是否存在一个或多个满足指定的参数行为
        System.out.println("是否包含P开头的单词：" +  streamSupplier.get().parallel().anyMatch(word -> word.startsWith("P")));

        // 检测是否全部都满足指定的参数行为
        System.out.println("是否都包含P开头的单词：" +  streamSupplier.get().parallel().allMatch(word -> word.startsWith("P")));

        // 检测是否不存在满足指定行为的元素
        System.out.println("是否都不包含P开头的单词：" +  streamSupplier.get().parallel().noneMatch(word -> word.startsWith("P")));

        // 1,2,3,4...,100
        Stream<Integer> serial = Stream.iterate(1, n -> n + 1).limit(100);
        serial.reduce((x, y) -> x + y).ifPresent(sum -> System.out.println("1-100求和结果:" + sum));

        Integer wordLen = streamSupplier.get().reduce(0, (total, word) -> total + word.length(), (t1, t2) -> t1 + t2);
        System.out.println("单词总长：" + wordLen);

    }


    public static void useSorted(){
        String text = "A scientific experiment satellite, PakTES-1A, developed by Pakistan, was sent into orbit via the same rocket.";
        //  从小到大排序
        Stream<String> words = Pattern.compile("[\\P{L}+]").splitAsStream(text);
        words = words.sorted(comparing(String::length));
        words.forEach(word -> System.out.print(word + " "));
        System.out.println();

        // 从大到小排序
        words = Pattern.compile("[\\P{L}+]").splitAsStream(text);

        // Collections,sort会对原有集合排序， Stream.sorted会返回一个新的流
        words = words.sorted(comparing(String::length).reversed());
        words.forEach(word -> System.out.print(word + " "));
        System.out.println();
    }

    public static void useCombind(){
        // 返回一个长度为n的新流，如果长度小于n就返回原始流
        Stream<Double> randoms = Stream.generate(Math::random).limit(10);
        randoms.forEach(d -> System.out.print(d + " "));
        System.out.println();

        String text = "A scientific experiment satellite, PakTES-1A, developed by Pakistan, was sent into orbit via the same rocket.";
        // 丢失前n个元素
        Stream<String> words = Pattern.compile("[\\P{L}+]").splitAsStream(text).skip(2);
        words.forEach(word -> System.out.print(word + " "));
        System.out.println();

        // 将两个流连接在一起，第一个流不能是无限流
        Stream<String> sayHi = Stream.concat(Stream.of("Hello "), Stream.of("World"));
        sayHi.forEach(word -> System.out.print("[" + word + "]"));
        System.out.println();
    }

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
        System.out.println();
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
        useCombind();
        useSorted();
        useAggregation();
        useOptional();
        useCollect();
    }
}
