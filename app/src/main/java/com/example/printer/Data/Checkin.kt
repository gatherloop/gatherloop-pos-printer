package com.example.printer.Data

data class Ticket (
   val name: String,
   val variant: String,
) {
}

data class Checkin (
    val createdAt: String,
    val name: String,
    val tickets: Array<Ticket>
    ) {
}