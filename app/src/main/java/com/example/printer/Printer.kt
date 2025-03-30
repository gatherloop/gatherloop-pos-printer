package com.example.printer

enum class Align(val command: ByteArray) {
    LEFT(byteArrayOf(0x1B, 0x61, 0x00)),
    CENTER(byteArrayOf(0x1B, 0x61, 0x01)),
    RIGHT(byteArrayOf(0x1B, 0x61, 0x02))
}

class Printer(val bluetoothManager: BluetoothManager) {
    fun printTextLine(text: String, align: Align = Align.LEFT, bold: Boolean = false) {
        val boldCommand = if (bold) byteArrayOf(0x1B, 0x45, 0x01) else byteArrayOf(0x1B, 0x45, 0x00)
        val formattedText = align.command + boldCommand + text.toByteArray() + byteArrayOf(0x0A)
        bluetoothManager.sendData(formattedText)
    }

    fun printText(text: String, align: Align = Align.LEFT, bold: Boolean = false) {
        val boldCommand = if (bold) byteArrayOf(0x1B, 0x45, 0x01) else byteArrayOf(0x1B, 0x45, 0x00)
        val formattedText = align.command + boldCommand + text.toByteArray()
        bluetoothManager.sendData(formattedText)
    }

    fun printTextTwoColumn(leftText: String, rightText: String, totalWidth: Int = 32) {
        val maxLeftWidth = totalWidth / 2 // Approximate half-width for left text
        val leftTrimmed = if (leftText.length > maxLeftWidth) leftText.substring(0, maxLeftWidth - 1) + "." else leftText
        val rightTrimmed = if (rightText.length > maxLeftWidth) rightText.substring(0, maxLeftWidth - 1) + "." else rightText

        val spaceWidth = totalWidth - (leftTrimmed.length + rightTrimmed.length)
        val spaces = " ".repeat(spaceWidth)

        val formattedText = "$leftTrimmed$spaces$rightTrimmed"

        bluetoothManager.socket.outputStream.write(formattedText.toByteArray())
        bluetoothManager.socket.outputStream.write("\n".toByteArray()) // Move to the next line
    }

    fun feed() {
        bluetoothManager.sendData(byteArrayOf(0x0A))
    }
}