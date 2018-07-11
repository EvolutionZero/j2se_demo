package com.zero.j2se.demo.utils;

import java.util.Optional;

public class MathUtils {

    public static Optional<Double> inverse(double x){
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    public static Optional<Double> squareRoot(double x){
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
