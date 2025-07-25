/*
 * Modbus Schema Toolkit
 * Copyright (C) 2019-2025 Niels Basjes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.basjes.modbus.thermia

import nl.basjes.modbus.device.api.ModbusDevice
import nl.basjes.modbus.schema.toYaml
import nl.basjes.modbus.schema.utils.toTable
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.time.Clock.System.now
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun getThermiaTestCase(modbusDevice: ModbusDevice) {
    val thermia = ThermiaGenesis()
    thermia.connect(modbusDevice)
    val modbusQueries = thermia.updateAll()
    println(modbusQueries.toTable())

    thermia.schemaDevice.createTestsUsingCurrentRealData()
    println(thermia.schemaDevice.toYaml())
}

@OptIn(ExperimentalTime::class)
fun getThermiaValues(modbusDevice: ModbusDevice) {
    val thermia = ThermiaGenesis()
    thermia.connect(modbusDevice)

    val fields =
        listOf(
            thermia.sensors.currentlyRunning1stDemand,
            thermia.sensors.currentlyRunning2ndDemand,
            thermia.sensors.currentlyRunning3rdDemand,
            thermia.sensors.queuedDemand1st,
            thermia.sensors.queuedDemand2nd,
            thermia.sensors.queuedDemand3rd,
            thermia.sensors.queuedDemand4th,
            thermia.sensors.outdoorTemperature,
            thermia.sensors.roomTemperature,
            thermia.sensors.tapWaterWeightedTemperature,
            thermia.settings.comfortWheelSetting,
        )

    fields.forEach { it.need() }

    var count = 0

    var previousRun = now()

    val timer = Timer("Fetcher")
    timer.scheduleAtFixedRate(
        timerTask {
            val thisRun = now()
            println("Fetching thermia input registers $thisRun (is ${thisRun - previousRun} after previous)    ${count++}")
            previousRun = thisRun
            val start = now()
            thermia.update()
            val stop = now()
            println("Fetching took ${stop - start}")
            fields.forEach { printField(it) }
            println("---------")
        },
        0,
        5000,
    )

    Thread.sleep(20000) // Run for at most 20 seconds
    timer.cancel()
}

@OptIn(ExperimentalTime::class)
fun printField(field: ThermiaGenesis.DeviceField) {
    val fieldValueTime = field.field.valueEpochMs ?.let { Instant.fromEpochMilliseconds(it).toString() } ?: "<Immutable>"
    println(String.format("%-40s = %-15s %-5s  @  %s", field.field.id, field.value, field.field.unit, fieldValueTime))
}
