package org.hui.dsaa.list;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hui.Liu
 * @since 2022-04-07 16:16
 */
@Slf4j
public class ReverseList {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next;
        while (head != null) {
            next = head.next;

            head.next = pre;

            pre = head;
            head = next;
        }
        return pre;
    }

    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            this.value = data;
        }
    }

    public static DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next;
        while (head != null) {
            next = head.next;

            head.next = pre;
            head.last = next;

            pre = head;
            head = next;
        }
        return pre;
    }
}
