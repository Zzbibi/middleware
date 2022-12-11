package com.zz.lambda;

import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author zhangzhen
 * @create 2022/12/9 16:42
 */
public class Demo {


    private static ThreadLocal<SimpleDateFormat> formatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    public static void main(String[] args) {

        Predicate<Integer> atLeast5 = x -> x > 5;

        BinaryOperator<Long> addLongs = (x, y) -> x + y;

        Function<Integer, Integer> addOne = x -> x + 1;

        Consumer<String> consumer = x -> System.out.println(x);

        Optional<String> optional = Optional.of("a");

        Integer sum = Stream.of(1, 2).reduce(0, (acc, element) -> acc + element);

    }

}
