package com.thinking.my.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * (1) 找出2011年发生的所有交易，并按交易额排序(从低到高)。 (2) 交易员都在哪些不同的城市工作过?
 * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
 * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
 * (5) 有没有交易员是在米兰工作的?
 * (6) 打印生活在剑桥的交易员的所有交易额。 (7) 所有交易中，最高的交易额是多少?
 * (8) 找到交易额最小的交易。
 * @Author liyong
 * @Date 2020/7/14 11:16 上午
 **/
public class PuttingIntoPractice {


    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        /**
         * 找出2011年的所有交易并按交易额排序(从低到高)
         */
        List<Transaction> tr2011 =  transactions.stream().filter(e->e.getYear()==2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(tr2011);
        /**
         * 交易员都在哪些不同的城市工作过
         */
        List<String> citys = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(citys);
         citys = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(citys);
        Set<String> citySets = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(Collectors.toSet());
        System.out.println(citySets);

        /**
         * 查找所有来自于剑桥的交易员，并按姓名排序
         */
       List<Trader> traders = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(traders);

        /**
         * 返回所有交易员的姓名字符串，按字母顺序排序
         */
        String traderStr = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("",(n1,n2) -> n1+n2);
        System.out.println(traderStr);
        traderStr = transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());
        System.out.println(traderStr);

        /**
         * 所有交易中，最高的交易额是多少
         */
        Optional rr= transactions.stream().map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println(rr.get());

        /**
         * 找到交易额最小的交易
         */
        Optional<Transaction> smallestTransaction =
                transactions.stream().reduce((t1,t2)->t1.getValue() < t2.getValue()?t1:t2);
        System.out.println(smallestTransaction.get());
        smallestTransaction =
                transactions.stream().sorted(Comparator.comparingInt(Transaction::getValue)).findFirst();
        System.out.println(smallestTransaction.get());
        smallestTransaction =
                transactions.stream().max(Comparator.comparingInt(Transaction::getValue));
        System.out.println(smallestTransaction.get());



    }



}
