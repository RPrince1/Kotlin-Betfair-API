package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.enums.market.OrderStatus
import com.prince.betfair.betfair.betting.enums.order.InstructionReportErrorCode
import com.prince.betfair.betfair.betting.enums.order.InstructionReportStatus
import java.util.*

/**
 * Response to a PlaceInstruction
 *
 * @property status: (required) whether the command succeeded or failed
 * @property errorCode: cause of failure, or null if command succeeds
 * @property orderStatus: The status of the order, if the instruction succeeded. If the instruction was unsuccessful, no
 * value is provided.
 * @property instruction: (required) The instruction that was requested
 * @property betId: The bet ID of the new bet. Will be null on failure or if order was placed asynchronously.
 * @property placedDate: Will be null if order was placed asynchronously
 * @property averagePriceMatched: Will be null if order was placed asynchronously. This value is not meaningful for
 * activity on LINE markets and is not guaranteed to be returned or maintained for these markets.
 * @property sizeMatched: Will be null if order was placed asynchronously
 */
data class PlaceInstructionReport(
    val status: InstructionReportStatus,
    val errorCode: InstructionReportErrorCode?,
    val orderStatus: OrderStatus?,
    val instruction: PlaceInstruction,
    val betId: String?,
    val placedDate: Date?,
    val averagePriceMatched: Double?,
    val sizeMatched: Double?
)
