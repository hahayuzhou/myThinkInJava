package com.thinking.my.xml.test.test2;

import com.thinking.my.xml.test.XMLUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author liyong
 * @Date 2020/12/4 11:46 上午
 **/
public class Test {
    public static void main(String[] args) {
        User user = new User(1, "Steven", "@sun123", new Date(), 1000.0);
        List<Computer> list = new ArrayList<Computer>();
        list.add(new Computer("xxxMMeedd", "asus", new Date(), 4455.5));
        list.add(new Computer("lenvoXx", "lenvo", new Date(), 4999));
        user.setComputers(list);
        String path = "/Users/liyong/Desktop/user.xml";
        System.out.println("---将对象转换成File类型的xml Start---");
        XMLUtil.convertToXml(user, path);
        System.out.println("---将对象转换成File类型的xml End---");
        System.out.println();
        System.out.println("---将File类型的xml转换成对象 Start---");
        User user2 = (User) XMLUtil.convertXmlFileToObject(User.class, path);
        System.out.println(user2);
        System.out.println("---将File类型的xml转换成对象 End---");
    }
}
