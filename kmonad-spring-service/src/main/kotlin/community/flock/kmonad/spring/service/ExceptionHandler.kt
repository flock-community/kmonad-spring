package community.flock.kmonad.spring.service

import arrow.core.continuations.EffectScope
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.spring.service.common.LiveLogger
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(e: AppException) = when (e) {
        is NotFound -> NOT_FOUND
        is BadRequest -> BAD_REQUEST
        is Conflict -> CONFLICT
        is InternalServerError -> INTERNAL_SERVER_ERROR
    }.let { ResponseEntity.status(it).body(e.message) }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception) = ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .also { LiveLogger.error(e.stackTraceToString()) }

}

typealias HasAppException = EffectScope<AppException>
