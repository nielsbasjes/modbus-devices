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

import com.digitalpetri.modbus.client.ModbusTcpClient
import com.digitalpetri.modbus.tcp.client.NettyClientTransportConfig
import com.digitalpetri.modbus.tcp.client.NettyTcpClientTransport
import nl.basjes.modbus.device.api.ModbusDevice
import nl.basjes.modbus.device.digitalpetri.ModbusDeviceDigitalPetri
import nl.basjes.modbus.device.exception.ModbusException

fun main() {
    val configBuilder = NettyClientTransportConfig.Builder()
    configBuilder.hostname  = "localhost"
    configBuilder.port = 1502

    val transport = NettyTcpClientTransport(configBuilder.build())
    val client = ModbusTcpClient.create(transport)

    try {
        print("Connecting...")
        client.connect()
        println(" done")

        val modbusDevice: ModbusDevice = ModbusDeviceDigitalPetri(client, 1)


        getThermiaTestCase(modbusDevice)

        getThermiaValues(modbusDevice)
    } catch (e: Exception) {
        println(" FAILED")
        throw ModbusException("Unable to connect to the master", e)
    } finally {
        println("Disconnecting...")
        client.disconnect()
    }
}
