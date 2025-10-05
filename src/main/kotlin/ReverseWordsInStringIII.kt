package leetcodes

/*
* 557. Reverse Words in a String III
* https://leetcode.com/problems/reverse-words-in-a-string-iii/
* Given a string s, reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

* Example 1:

Input: s = "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"

* Example 2:

Input: s = "Mr Ding"
Output: "rM gniD"

Constraints:

    1 <= s.length <= 5 * 104
    s contains printable ASCII characters.
    s does not contain any leading or trailing spaces.
    There is at least one word in s.
    All the words in s are separated by a single space.
*/

fun main() {
    println(reverseWords("Let's take LeetCode contest"))
    //println(reverseWords("Mr Ding"))

    //println(reverseWordsPerfect("Let's take LeetCode contest"))
    //println(reverseWordsPerfect("Mr Ding"))
}

fun reverseWords(s: String): String {
    val wordToRevert = "$s "
    var reversedString = ""

    var left = 0
    var right = 0

    for(i in wordToRevert.indices) {
        if(wordToRevert[i] == ' ') {
            right = i
            reversedString += wordToRevert.substring(left, right).reversed() + " "
            left = right + 1
        }
    }

    return reversedString.trim()
}

fun reverseWords2(s: String): String {
    var reversedString = ""

    var left = 0
    var right = 0

    while(right < s.length) {
        if (s[right] != ' ') {
            right++
        } else {
            //println("left in character: ${s[left]} and right in character: ${s[right]}")
            reversedString += s.substring(left, right).reversed() + " " // '+ " "' or 'right + 1' to include the space
            right++ // go to the next word or end
            left = right // update left to start of next word
            //println("and now left in character: ${s[left]} and right in character: ${s[right]}")
        }
    }

    // right is out of bounds right now
    // but substring gets the string until the last index - 1
    reversedString += s.substring(left, right).reversed()

    return reversedString.trim()
}

// step 1: split by space
// s.split(" ") = [Let's, take, LeetCode, contest]
// step 2: reverse each element
// s.split(" ").map { it.reversed() } = [s'teL, ekat, edoCteeL, tsetnoc]
// step 3: join them back
// s.split(" ").map { it.reversed() }.joinToString(" ") = "s'teL ekat edoCteeL tsetnoc"
fun reverseWordsPerfect(s: String): String {
    return s.split(" ").joinToString(" ") { it.reversed() }
}
