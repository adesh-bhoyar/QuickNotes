package com.example.quicknotesmini_app.dsa

data class Interval(val start: Int, val end: Int)

fun parseLineToInterval(line: String): Interval? {
    val nums = Regex("(-?\\d+)").findAll(line).map { it.value.toInt() }.toList()
    if (nums.size < 2) return null
    var a = nums[0]
    var b = nums[1]
    if (a > b) { val t = a; a = b; b = t }
    return Interval(a, b)
}


fun mergeIntervals(intervals: List<Interval>, mergeTouching: Boolean = true): List<Interval> {
    if (intervals.isEmpty()) return emptyList()
    val sorted = intervals.sortedWith(compareBy({ it.start }, { it.end }))
    val out = mutableListOf<Interval>()
    var cur = sorted[0]
    for (i in 1 until sorted.size) {
        val next = sorted[i]
        val overlap = if (mergeTouching) next.start <= cur.end + 1 else next.start <= cur.end
        if (overlap) {
            cur = Interval(cur.start, maxOf(cur.end, next.end))
        } else {
            out.add(cur)
            cur = next
        }
    }
    out.add(cur)
    return out
}

fun main() {
    println("Enter intervals (one per line). Examples: '1 3', '2,6', '4-5'. Type 'stop' to finish.")
    val intervals = mutableListOf<Interval>()
    while (true) {
        print("> ")
        val line = readLine() ?: break
        if (line.trim().equals("stop", ignoreCase = true)) break
        val parsed = parseLineToInterval(line)
        if (parsed == null) {
            println("  -> couldn't parse two integers; try again (e.g. `1 3`).")
            continue
        }
        intervals.add(parsed)
    }

    if (intervals.isEmpty()) {
        println("No intervals provided.")
        return
    }

    val merged = mergeIntervals(intervals, mergeTouching = true)

    println("Merged intervals:")
    merged.forEach { println("${it.start} ${it.end}") }
}
