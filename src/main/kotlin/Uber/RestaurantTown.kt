package Uber

class RestaurantTown(
    private val rows: Int,
    private val cols: Int
) {
    private val grid = Array(rows) { IntArray(cols) { 0 } }

    fun openRestaurant(r: Int, c: Int) {
        val restaurantCount = grid[r][c]
        grid[r][c] = restaurantCount + 1
    }

    fun countRestaurantsInPosition(r: Int, c: Int): Int {
        return grid[r][c]
    }

    fun openedRestaurants(r: Int, c: Int): Boolean {
        return grid[r][c] > 0
    }

    fun countRestaurantDistricts(): Int {
        var numOfRestaurantDistricts = 0
        val visited = mutableSetOf<Pair<Int, Int>>()

        fun dfs(row: Int, col: Int, visited: MutableSet<Pair<Int, Int>>) {
            if (
                row !in 0 until rows ||
                col !in 0 until cols ||
                grid[row][col] == 0 ||
                (row to col) in visited
            )
            {
                return
            }

            visited.add(row to col)

            dfs(row - 1, col, visited)
            dfs(row + 1, col, visited)
            dfs(row, col - 1, visited)
            dfs(row, col + 1, visited)
        }

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (grid[row][col] > 0 && (row to col) !in visited) {
                    numOfRestaurantDistricts++
                    dfs(row, col, visited)
                }
            }
        }

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if ((row to col) in visited) {
                    visited.remove(row to col)
                }
            }
        }

        return numOfRestaurantDistricts
    }
}

fun main() {

    /*
         0 1 2 3
      0  1 0 0 1
      1  0 0 0 1
      2  0 1 0 0
      3  0 1 2 0
    */

    val town = RestaurantTown(4, 4)
    with(town) {
        openRestaurant(0,0)
        println(countRestaurantDistricts()) // 1
        openRestaurant(2,1)
        openRestaurant(3,1)
        println(countRestaurantDistricts()) // 2
        openRestaurant(3,2)
        openRestaurant(3,2)
        openRestaurant(0,3)
        openRestaurant(1,3)
        println(openedRestaurants(0,1)) // false
        println(countRestaurantsInPosition(3,2)) // 2
        println(countRestaurantDistricts()) // 3
        openRestaurant(0,1)
        openRestaurant(0,2)
        println(openedRestaurants(3,2)) // true
        println(countRestaurantDistricts()) // 2
        openRestaurant(1,1)
        println(countRestaurantDistricts()) // 1
    }
}