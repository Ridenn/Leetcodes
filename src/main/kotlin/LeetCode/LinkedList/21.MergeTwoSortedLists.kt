package LeetCode.LinkedList

import leetcodes.LeetCode.ListNode

fun main() {
    val list1 = ListNode(1)
    list1.next = ListNode(2)
    list1.next?.next = ListNode(4)

    val list2 = ListNode(1)
    list1.next = ListNode(3)
    list1.next?.next = ListNode(4)

    println(mergeTwoLists(list1, list2))
}

fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
//    val result = ListNode(0)
    var head1 = list1
    var head2 = list2
    var newList = ListNode(0)

    while (head1 != null && head2 != null) {
        if (head1.`val` < head2.`val`) {
            newList.next = head1
            head1 = head1.next
        } else {
            newList.next = head2
            head2 = head2.next
        }
        newList = newList.next!!
    }

//    newList.next = head1 ?: head2

    return newList
}
