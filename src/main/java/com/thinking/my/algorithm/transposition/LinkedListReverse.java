package com.thinking.my.algorithm.transposition;

/**
 *
 * 单链表反转
 * Created by liyong on 2019/7/2.
 */
public class LinkedListReverse {

    public static void main(String[] args) {

        Node head = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        head.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);

        // 打印反转前的链表
        Node h = head;
        while (null != h) {
            System.out.print(h.getData() + " ");
            h = h.getNext();
        }

//        Node r = reverse1(head);
//
//        while (null != r) {
//            System.out.print(r.getData() + " ");
//            r = r.getNext();
//        }
        Node r2 = reverse2(head);

        while (null != r2) {
            System.out.print(r2.getData() + " ");
            r2 = r2.getNext();
        }




    }

    /**
     * 递归反转
     * @return
     */
    public static Node reverse1(Node head){

        if(head==null||head.getNext()==null){
            return head;
        }
        Node reHead = reverse1(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);


        return reHead;
    }

    /**
     * 循环返回
     * @param head
     * @return
     */
    public static Node reverse2(Node head){
        Node reHead = head;
        Node cur = head.getNext();
        Node tmp;
        while (cur!=null){
            tmp = cur.getNext();
            cur.setNext(reHead);
            reHead = cur;
            cur = tmp;
        }
        head.setNext(null);

        return reHead;
    }


}


/**
 * node 实体
 */
class Node{
    private int data;

    private Node next;

    public Node(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
