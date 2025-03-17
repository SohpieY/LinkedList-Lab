package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the iterative implementation of the IListManipulator interface.
 *
 */
public class ListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        // TODO Auto-generated method stub
        // Initialize a counter to help with later counting algorithm.
        int count = 0;
        // If the current node is not null, then move to the next.
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    @Override
    public boolean isEmpty(ListNode head) {
        // TODO Auto-generated method stub
        return head == null;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        // TODO Auto-generated method stub
        while (head != null) {
            if (head.equals(element)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    @Override
    public int count(ListNode head, Object element) {
        // TODO Auto-generated method stub
        int count = 0;
        while (head != null) {
            if (head.equals(element)) {
                count++;
            }
            head = head.next;
        }
        return count;
    }

    @Override
    public String convertToString(ListNode head) {
        // TODO Auto-generated method stub
        StringBuilder result = new StringBuilder();
        while (head != null) {
            result.append(head.element);
            if (head.next != null) {
                result.append(", ");
            }
            head = head.next;
        }
        return result.toString();
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if (head == null) {
            throw new InvalidIndexException(); // Out of bounds
        }
        if (n == 0) {
            throw new InvalidIndexException();
        }
        else {
            int count = 1;
            ListNode currentNode = head;
            while (count != n) {
                currentNode = currentNode.next;
                count += 1;
            }
            return currentNode.element;
        }
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        ListNode currentNode = head.previous;
        int count = 1;
        while (count != n) {
            currentNode = currentNode.previous;
            count += 1;
        }
        return currentNode.element;
    }

    @Override
    public boolean equals(ListNode head1, ListNode head2) {
        // TODO Auto-generated method stub
        if (head1 == null && head2 == null) {
            return true; // Both lists are empty
        }
        if (head1 == null || head2 == null) {
            return false; // One list is empty
        }

        ListNode current1 = head1;
        ListNode current2 = head2;

        do {
            if (!current1.element.equals(current2.element)) {
                return false; // Mismatch found
            }
            current1 = current1.next;
            current2 = current2.next;


            if (current1 == head1 && current2 == head2) {
                return true; // Both lists are equal and we've returned to the start
            }
        } while (current1 != head1 && current2 != head2);

        // If we exit the loop and they are not both at the start, they are not equal
        return false;
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        // TODO Auto-generated method stub
        if (head == null) {
            return false;
        }
        if (contains(head.next, head)) {
            return true;
        }
        return containsDuplicates(head.next); // Base case
    }

    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        // TODO Auto-generated method stub
        node.next = head; // New node points to head
        node.previous = head.previous;
        head.previous = node;
        return node; // Node becomes new head
    }

    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        // TODO Auto-generated method stub
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }

        ListNode current = head1;

        while (current.next != null && current.next != head1) {
            current = current.next;
        }

        current.next = head2;

        return head1;
    }

    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if (n == 0) {
            System.out.println("Error");
            throw new InvalidIndexException();
        }
        if (n == 1) {
            return addHead(head, node);
        }
        if (head == null) {
            throw new InvalidIndexException();
        }
        int count = 1;
        ListNode currentNode = head;
        while (count != n) {
            currentNode = currentNode.next;
            count += 1;
        }
        return addHead(currentNode, node);
    }

    public ListNode delete(ListNode head, Object elem) {
        // TODO Auto-generated method stub
        if (head == null) {
            return null; // If the list is empty, return null
        }

        ListNode currentNode = head;
        do {
            if (currentNode.element.equals(elem)) {
                // Case 1: Deleting the head node
                if (currentNode == head) {
                    // If there's only one node
                    if (currentNode.next == head) {
                        return null; // List will be empty after deletion
                    }
                    // Find the last node to update its next reference
                    ListNode lastNode = head;
                    while (lastNode.next != head) {
                        lastNode = lastNode.next;
                    }
                    // Update head
                    head = head.next;
                    lastNode.next = head; // Connect last node to new head
                    head.previous = lastNode; // Update new head's previous reference
                } else {
                    // Case 2: Deleting a middle or tail node
                    ListNode prev = currentNode.previous;
                    ListNode next = currentNode.next;
                    prev.next = next;
                    if (next != null) {
                        next.previous = prev;
                    }
                }
                return head;
            }
            currentNode = currentNode.next;
        } while (currentNode != head);

        return head;
    }

    @Override
    public ListNode reverse(ListNode head) {
        // TODO Auto-generated method stub
        ListNode temp = null;
        ListNode current = head;

        while (current != null) {
            temp = current.previous;
            current.previous = current.next;
            current.next = temp;
            current = current.previous;
        }

        if (temp != null) {
            return temp.previous; // Return the new head of the reversed list
        } else {
            return head; // Return the original head if the list is empty or has one node
        }
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        // TODO Auto-generated method stub
        if (head == null || node == null) {
            throw new InvalidListException();
        }

        ListNode secondHead = node.next; // Store the head of the second part
        node.next = head; // Link the node to the head

        return secondHead; // Return the second part
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        // TODO Auto-generated method stub
        if (head == null) {
            return null;
        }

        ListNode newHead = null;
        ListNode lastNode = null;

        for (ListNode current = head; current != null; current = current.next) {
            ListNode newNode = new ListNode((Integer) transformation.transform(current.element)); // Create a new node
            if (newHead == null) {
                newHead = newNode;
            } else {
                lastNode.next = newNode;
            }
            lastNode = newNode;
        }

        return newHead;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        // TODO Auto-generated method stub
        Object result = initial;

        for (ListNode current = head; current != null; current = current.next) {
            result = operator.operate(result, current.element);
        }

        return result;
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        // TODO Auto-generated method stub
        if (head == null) {
            return null; // Return null for an empty list
        }

        ListNode newHead = null;
        ListNode lastNode = null;

        for (ListNode current = head; current != null; current = current.next) {
            if (condition.isSatisfied(current.element)) {
                ListNode newNode = new ListNode(current.element);
                if (newHead == null) {
                    newHead = newNode;
                } else {
                    lastNode.next = newNode;
                }
                lastNode = newNode;
            }
        }

        return newHead;
    }


}
