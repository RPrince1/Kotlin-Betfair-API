package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.enums.order.InstructionReportErrorCode
import com.prince.betfair.betfair.betting.enums.order.InstructionReportStatus

/**
 * status: (required) whether the command succeeded or failed
 * errorCode: cause of failure, or null if command succeeds
 * instruction: (required) The instruction that was requested
 */
data class UpdateInstructionReport(
    val status: InstructionReportStatus,
    val errorCode: InstructionReportErrorCode?,
    val instruction: UpdateInstruction
)
