package com.study.dsa.twopointers;

public class DifferentSpeedPointers {

    // Simple ListNode definition
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Problem: Detect cycle in linked list
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using slow/fast pointers
     */
    public static boolean hasCycle(ListNode head) {
        // TODO: Move pointers at different speeds
        // What happens when they meet?

        return false; // Replace with implementation
    }

    /**
     * Problem: Find middle of linked list
     * If even length, return second middle node
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using slow/fast pointers
     */
    public static ListNode findMiddle(ListNode head) {
        // TODO: Use different pointer speeds
        // Where is the slow pointer when fast reaches the end?

        return null; // Replace with implementation
    }

    /**
     * Problem: Find kth node from end
     * Time: O(n), Space: O(1)
     *
     * TODO: Implement using two pointers with gap
     */
    public static ListNode findKthFromEnd(ListNode head, int k) {
        // TODO: Create a fixed gap between pointers
        // When one reaches the end, where is the other?

        return null; // Replace with implementation
    }

    // Helper: Create linked list from array
    static ListNode createList(int[] values) {
        if (values.length == 0) return null;

        ListNode head = new ListNode(values[0]);
        ListNode current = head;

        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }

        return head;
    }

    // Helper: Print linked list
    static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) System.out.print(" -> ");
            current = current.next;
        }
        System.out.println();
    }


    // --- demo (moved from DifferentSpeedClient) ---

    public static void main(String[] args) {
        System.out.println("=== Different Speed Two Pointers ===\n");

        // Test 1: Cycle detection
        System.out.println("--- Test 1: Cycle Detection ---");

        // List without cycle: 1 -> 2 -> 3 -> 4 -> 5
        ListNode list1 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("List: ");
        printList(list1);
        System.out.println("Has cycle: " + hasCycle(list1));

        // List with cycle: 1 -> 2 -> 3 -> 4 -> 5 -> (back to 3)
        ListNode list2 = createList(new int[]{1, 2, 3, 4, 5});
        ListNode node3 = list2.next.next; // Node with value 3
        ListNode tail = list2.next.next.next.next; // Node with value 5
        tail.next = node3; // Create cycle

        System.out.println("\nList with cycle (5 -> 3):");
        System.out.println("Has cycle: " + hasCycle(list2));

        // Test 2: Find middle
        System.out.println("\n--- Test 2: Find Middle ---");
        ListNode list3 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("List (odd length): ");
        printList(list3);

        ListNode middle = findMiddle(list3);
        System.out.println("Middle value: " + middle.val);

        ListNode list4 = createList(new int[]{1, 2, 3, 4, 5, 6});
        System.out.print("List (even length): ");
        printList(list4);

        middle = findMiddle(list4);
        System.out.println("Middle value: " + middle.val);

        // Test 3: Kth from end
        System.out.println("\n--- Test 3: Kth From End ---");
        ListNode list5 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("List: ");
        printList(list5);

        for (int k = 1; k <= 3; k++) {
            ListNode kthNode = findKthFromEnd(list5, k);
            System.out.printf("%dth from end: %d%n", k, kthNode.val);
        }
    }
}
