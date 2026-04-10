package LeetCode.Graph.AdjacencyList

import java.util.*

fun main() {
    println(findOrder(2, arrayOf(intArrayOf(1,0)))) // [0,1]
    println(findOrder(4, arrayOf(intArrayOf(1,0), intArrayOf(2,0), intArrayOf(3,1), intArrayOf(3,2)))) // [0,2,1,3]
}

fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
    /*
    * Time complexity: O(V + E) -> traverses vertices and edges
    * Space complexity: O(V + E) -> adjacency list with vertices + edges
    * Approach: First we build an adjacency list of prerequisite courses to courses
        that need that prerequisite. Then we also need an indegree array of how many
        prerequisites each course need before being taken.
        Then we need to enqueue only the courses that doesn’t need prerequisites to run
        the BFS. For each course, we add to the result list and check it's neighbours
        (courses that depend on the current course), taking 1 from the indegree (because
        we just took the prerequisite).
        If the dependant course already has 0 indegrees (without any other prerequisite to
        take), we can enqueue it.
        At the end, we return the coursed list as an integer array.
    * Intuition:
        - Graph
        - Build adjacency list of (prereq -> {course1, course2...})
        - Indegree IntArray of courses that need prerequisites
        - BFS
    */

    // prereq -> {course1, course2...}
    val graph = Array(numCourses) { mutableListOf<Int>() }
    val indegree = IntArray(numCourses) { 0 }

    // Create graph and indegree of courses that need prerequisites
    for ((prereq, course) in prerequisites) {
        graph[prereq].add(course)
        indegree[course]++
    }

    // Add every course without prerequisites to queue
    val q = ArrayDeque<Int>()
    for (course in 0 until numCourses) {
        if (indegree[course] == 0) q.addLast(course)
    }

    val order = mutableListOf<Int>()

    while (q.isNotEmpty()) {
        val currCourse = q.removeFirst()
        order.add(currCourse)

        // Get neighbors (courses that depend on currCourse) and take 1 from their indegree
        // If their indegree is now 0, they don't have any other prerequisite and can be queued
        for (course in graph[currCourse]) {
            indegree[course]--
            if (indegree[course] == 0) q.addLast(course)
        }
    }

    return if (order.size == numCourses) order.toIntArray() else intArrayOf()
}
