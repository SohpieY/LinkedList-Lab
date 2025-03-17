package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 *
 */
public class RecursiveListManipulator implements IListManipulator {

    @Override
    public int size(ListNode head) {
        // TODO Auto-generated method stub
        if(head == null){
            return 0; //base case return the count if the current is empty
        }
        return 1 + size(head.next);

    }

    @Override
    public boolean isEmpty(ListNode head) {
        // TODO Auto-generated method stub
        return head == null;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        // TODO Auto-generated method stub
        if (head == null) {
            return false; // Base case: reached the end of the list without finding the element
        }
        if (head.element.equals(element)) {
            return true; // Found the element
        }
        return contains(head.next, element); // Recur for the next node

    }

    @Override
    public int count(ListNode head, Object element) {
        // TODO Auto-generated method stub
        if (head == null) {
            return 0;
        }
        int count = (head.element.equals(element) ? 1 : 0);
        return count + count(head.next, element);
    }

    @Override
    public String convertToString(ListNode head) {
        // TODO Auto-generated method stub
        if (head == null) {
            return ""; // Base case: empty list
        }
        StringBuilder result = new StringBuilder(head.element.toString());
        if (head.next != null) {
            result.append(", ").append(convertToString(head.next));
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
            return head.element; // Return the element if n is 0
        }
        return getFromFront(head.next, n - 1);
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if (head == null) {
            throw new InvalidIndexException();
        }
        int size = size(head);
        if (n >= size) {
            throw new InvalidIndexException();
        }
        return getFromFront(head, size - n - 1);
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
        if (!head1.element.equals(head2.element)) {
            return false; // Mismatch in elements
        }
        return equals(head1.next, head2.next);
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        return containsDuplicates(head, head);
    }

    private boolean containsDuplicates(ListNode current, ListNode head) {
        if (current == null) {
            return false;
        }

        ListNode temp = head; // Start from the head for comparison
        while (temp != current) {
            if (temp.element.equals(current.element)) {
                return true;
            }
            temp = temp.next;
        }

        return containsDuplicates(current.next, head);
    }
    
    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        // TODO Auto-generated method stub
        if (head == null) {
            return node;
        }
        node.next = head;
        node.previous = head.previous;
        head.previous = node;
        return node;
    }

    @Override
    public ListNode append(ListNode head1, ListNode head2) {
        // TODO Auto-generated method stub
        if (head1 == null) {
            return head2; // Base case
        }
        head1.next = append(head1.next, head2); // Recurse until end
        return head1;
    }

    @Override
    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        // TODO Auto-generated method stub
        if (n < 0) {
            throw new InvalidIndexException();
        }
        if (n == 0) {
            node.next = head;
            return node;
        }
        if (head == null) {
            throw new InvalidIndexException();
        }
        head.next = insert(head.next, node, n - 1);
        return head;
    }

    @Override
    public ListNode delete(ListNode head, Object elem) {
        // TODO Auto-generated method stub
        if (head == null) {
            return null;
        }
        if (head.element.equals(elem)) {
            return head.next;
        }
        head.next = delete(head.next, elem);
        return head;
    }


    @Override
    public ListNode reverse(ListNode head) {
        // TODO Auto-generated method stub
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        // TODO Auto-generated method stub
        if (head == null || node == null) {
            throw new InvalidListException();
        }
        if (node.next == null) {
            return null;
        }

        ListNode secondHead = node.next;
        node.next = head;
        return secondHead;
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        // TODO Auto-generated method stub
        if (head == null) {
            return null;
        }
        ListNode newNode = new ListNode(transformation.transform(head.element));
        newNode.next = map(head.next, transformation);
        return newNode;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        // TODO Auto-generated method stub
        if (head == null) {
            return initial;
        }
        Object newResult = operator.operate(initial, head.element);
        return reduce(head.next, operator, newResult);
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        // TODO Auto-generated method stub
        if (head == null) {
            return null;
        }

        ListNode newHead = filter(head.next, condition);
        if (condition.isSatisfied(head.element)) {
            ListNode newNode = new ListNode(head.element);
            newNode.next = newHead;
            return newNode;
        }
        return newHead;
    }

}
