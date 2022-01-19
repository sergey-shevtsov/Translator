package com.sshevtsov.translator.util

import io.reactivex.rxjava3.core.Scheduler

interface SchedulersProvider {

    fun io() : Scheduler

    fun main() : Scheduler

}