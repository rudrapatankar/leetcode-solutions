/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode partition(ListNode head, int x) {
        ListNode dummyless = new ListNode(0);
        ListNode dummymore = new ListNode(0);
        ListNode lessTail = dummyless;
        ListNode moreTail = dummymore;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val < x) {
                lessTail.next = new ListNode(curr.val);
                lessTail = lessTail.next;
            }
            if (curr.val >= x) {
                moreTail.next = new ListNode(curr.val);
                moreTail = moreTail.next;
            }
            curr = curr.next;
        }
        lessTail.next = dummymore.next;
        return dummyless.next;
    }
}