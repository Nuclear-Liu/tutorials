package org.hui.dsaa;

import lombok.extern.slf4j.Slf4j;
import org.hui.dsaa.list.ReverseList;
import org.junit.jupiter.api.Test;

import static org.hui.dsaa.list.ReverseList.reverseDoubleList;
import static org.hui.dsaa.list.ReverseList.reverseLinkedList;

/**
 * @author Hui.Liu
 * @since 2022-04-07 16:37
 */
@Slf4j
public class ReverseListTests {

    private int len = 50;
    private int value = 100;
    private int testTime = 100000;
    @Test
    public void testReversLinkedList() {

        ReverseList.Node n1 = new ReverseList.Node(1);
        n1.next = new ReverseList.Node(2);
        n1.next.next = new ReverseList.Node(3);

        n1 = reverseLinkedList(n1);

        while (n1 != null) {
            log.info("node: {}", n1.value);
            n1 = n1.next;
        }
    }
    @Test public void testReversDoubleList() {
        ReverseList.DoubleNode n1 = new ReverseList.DoubleNode(1);
        n1.last = null;
        ReverseList.DoubleNode n2 = new ReverseList.DoubleNode(2);
        n2.last = n1;
        n1.next = n2;
        ReverseList.DoubleNode n3 = new ReverseList.DoubleNode(3);
        n3.last = n2;
        n2.next = n3;
        n3.next = null;

        n1 = reverseDoubleList(n1);
        while (n1 != null) {
            log.info("DoubleNode: {}", n1.value);
            n1 = n1.next;
        }
    }
}
