package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.enums.order.InstructionReportErrorCode
import com.prince.betfair.betfair.betting.enums.order.InstructionReportStatus
import java.util.*

/**
 * @property status: (required) whether the command succeeded or failed
 * @property errorCode: cause of failure, or null if command succeeds
 * @property instruction: The instruction that was requested
 * @property sizeCancelled: (required)
 * @property cancelledDate
 */
data class CancelInstructionReport(
    val status: InstructionReportStatus,
    val errorCode: InstructionReportErrorCode?,
    val instruction: CancelInstruction?,
    val sizeCancelled: Double,
    val cancelledDate: Date?
)
