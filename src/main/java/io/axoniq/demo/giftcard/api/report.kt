package io.axoniq.demo.giftcard.api

import java.util.*

data class ReportedEvent(val id: UUID, val action: String) {
    constructor(action: String) : this (UUID.randomUUID(), action)
}
