package com.thinking.my.stream;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Description
 * @Author liyong
 * @Date 2020/7/13 8:34 下午
 **/
public class FlatMapTest {

    public static void main(String[] args) {

//        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
//
//        System.out.println(list22(numbers));

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        System.out.println(list2to2(numbers1,numbers2));
        System.out.println(list2to2andFilter(numbers1,numbers2));

    }

    /**
     * 给定一个数字列表，如何返回一个由每个数的平方构成的列表呢?例如，给定[1, 2, 3, 4,
     * 5]，应该返回[1, 4, 9, 16, 25]。
     * @param numbers
     * @return
     */
    public static List<Integer> list22(List<Integer> numbers){
        return numbers.stream()
                        .map(n -> n * n)
                        .collect(toList());

    }

    /**
     * 给定两个数字列表，如何返回所有的数对呢?例如，给定列表[1, 2, 3]和列表[3, 4]，应 该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。为简单起见，你可以用有两个元素的数组来代 表数对
     * @param numbers1
     * @param numbers2
     * @return
     */
    public static List<int[]> list2to2(List<Integer> numbers1,List<Integer> numbers2){
        List<int[]> pairs = numbers1.stream()
                .flatMap(i->numbers2.stream()
                        .map(
                            j->{
                                System.out.println(i+"-"+j);
                                return new int[]{i,j};
                            }
                        )
                )
                .collect(toList());
        return pairs;
    }

    /**
     * 如何扩展前一个例子，只返回总和能被3整除的数对呢?例如(2, 4)和(3, 3)是可以的
     * @param numbers1
     * @param numbers2
     * @return
     */
    public static List<int[]> list2to2andFilter(List<Integer> numbers1,List<Integer> numbers2){
        List<int[]> pairs = numbers1.stream()
                .flatMap(i->numbers2.stream()
                        .filter(j->(i+j)%3==0)
                        .map(
                            j->{
                                System.out.println(i+"-"+j);
                                return new int[]{i,j};
                            }
                        )
                )
                .collect(toList());
        return pairs;
    }



}
