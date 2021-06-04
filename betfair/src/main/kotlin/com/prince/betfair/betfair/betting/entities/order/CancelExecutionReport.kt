package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.enums.order.ExecutionReportErrorCode
import com.prince.betfair.betfair.betting.enums.order.ExecutionReportStatus

/**
 * @property customerRef: Echo of the customerRef if passed.
 * @property status: (required)
 * @property errorCode
 * @property marketId: Echo of marketId passed
 * @property instructionReports
 */
data class CancelExecutionReport(
    val customerRef: String?,
    val status: ExecutionReportStatus,
    val errorCode: ExecutionReportErrorCode?,
    val marketId: String?,
    val instructionReports: List<CancelInstructionReport>?
)

