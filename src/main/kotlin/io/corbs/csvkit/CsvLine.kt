package io.corbs.csvkit


open class CsvLine(val tag: String, val line: String) {

    private val values = this.line.split(",").toList()

    override fun toString(): String {
        return this.line
    }
}