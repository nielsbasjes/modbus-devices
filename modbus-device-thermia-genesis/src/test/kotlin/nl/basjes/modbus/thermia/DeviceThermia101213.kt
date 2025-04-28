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

import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster
import nl.basjes.modbus.device.api.AddressClass
import nl.basjes.modbus.device.api.ModbusDevice
import nl.basjes.modbus.device.exception.ModbusException
import nl.basjes.modbus.device.j2mod.ModbusDeviceJ2Mod
import nl.basjes.modbus.device.memory.MockedModbusDevice.Companion.builder
import nl.basjes.modbus.schema.Block
import nl.basjes.modbus.schema.Field
import nl.basjes.modbus.schema.SchemaDevice
import nl.basjes.modbus.schema.toYaml
import kotlin.test.Ignore
import kotlin.test.Test

class DeviceThermia101213 {

    @Ignore
    @Test
    fun generate() {
        val master: AbstractModbusMaster = ModbusTCPMaster("localhost", 1502    )
        try {
            val thermia = schemaDevice()
            master.connect()
            val modbusDevice: ModbusDevice = ModbusDeviceJ2Mod(master, 1)
            thermia.connectBase(modbusDevice)
            thermia.updateAll(1000000000L)    // Only fetch what is not there yet.
            thermia.createTestsUsingCurrentRealData()
            println(thermia.toYaml())

        } catch (e: Exception) {
            throw ModbusException("Unable to connect to the master", e)
        } finally {
            master.disconnect()
        }
    }

    private fun Block.newField(id: String, unit:String, expression: String, description: String ) {
        val fieldBuilder = Field.builder().block(this).id(id).expression(expression).description(description)
        if (unit.isNotBlank()) {
            fieldBuilder.unit(unit)
        }
        this.addField(fieldBuilder.build())
    }

    fun schemaDevice(): SchemaDevice {
            val device: SchemaDevice = SchemaDevice.builder()
                .description("Thermia Genesis")
                .maxRegistersPerModbusRequest(125)
                .build()

        val star1Mapping = "1->'Manual operation'; 2-> 'Defrost'; 3-> 'Hot water'; 4-> 'Heat'; 5-> 'Active Cooling'; 6-> 'Pool'; 7-> 'Anti legionella'; 8-> 'Passive Cooling'; 98-> 'Standby' ;99-> 'No demand' ;100-> 'OFF'"
        val star2Mapping = "1->'OFF'; 2-> 'Standby'; 3->'ON/Auto'"
        val star4Mapping = "1-> 'EVU'; 4-> 'Normal'; 5-> 'Comfort'; 6-> 'Boost'"
        val star6Mapping = "0-> 'Manual operation'; 1-> 'Defrost'; 2-> 'Hot water'; 3-> 'Heat'; 4-> 'Active Cooling'; 5-> 'Pool'; 6-> 'Anti legionella'; 7-> 'Passive Cooling'; 8-> 'Reserved'; 9-> 'Standby'; 10-> 'No demand'; 11-> 'OFF'"
        val star7Mapping = "0-> 'Disabled'; 2-> 'Enabled'"

        val mixingValueMapping = "0 ->'Heat'; 1 ->'Cool'; 2 ->'Auto'"

        val legacyBitSet =
            "0 -> 'Heat pump 0';" +
                "1 -> 'Heat pump 1';" +
                "2 -> 'Heat pump 2';" +
                "3 -> 'Heat pump 3';" +
                "4 -> 'Heat pump 4';" +
                "5 -> 'Heat pump 5';" +
                "6 -> 'Heat pump 6';" +
                "7 -> 'Heat pump 7';" +
                "8 -> 'Heat pump 8';" +
                "9 -> 'Heat pump 9';" +
                "10 -> 'Heat pump 10';" +
                "11 -> 'Heat pump 11';" +
                "12 -> 'Heat pump 12';" +
                "13 -> 'Heat pump 13';" +
                "14 -> 'Heat pump 14';" +
                "15 -> 'Heat pump 15'"

        /**
        COILS - Function codes: 1=read coils, 5=write single coil, 15=write multiple coils

        3       |  Reset all alarms
        4       |  Enable internal additional heater
        5       |  Enable external additional heater
        6       |  Enable HGW
        7       |  Enable flow switch/pressure switch
        8       |  Enable tap water
        9       |  Enable heat
        10      |  Enable active cooling
        11      |  Enable mix valve 1
        12      |  Enable TWC
        13      |  Enable WCS
        14      |  Enable hot gas pump
        16      |  Enable mix valve 2 (EM)
        17      |  Enable mix valve 3 (EM)
        18      |  Enable mix valve 4 (EM)
        19      |  Enable mix valve 5 (EM)
        20      |  Enable brine out monitoring
        21      |  Enable brine pump continuous operation
        22      |  Enable system circulation pump
        23      |  Enable dew point calculation
        24      |  Enable anti legionella
        25      |  Enable additional heater only (No compressor). Requires Operation mode: Standby
        26      |  Enable current limitation
        28      |  Enable pool (EM)
        29      |  Enable surplus heat, chiller (no borehole)
        30      |  Enable surplus heat, borehole (no chiller)
        31      |  Enable external additional heater for pool (EM)
        32      |  Enable internal additional heater for pool (EM)
        33      |  Enable passive cooling (EM)
        34      |  Enable variable speed mode for condenser pump
        35      |  Enable variable speed mode for brine pump
        36      |  Enable cooling mode for mixing valve 1
        37      |  Enable outdoor temp dependent for cooling with mixing valve 1
        38      |  Enable internal brine pump to start when cooling is active for mixing valve 1
        39      |  Enable outdoor temp dependent for external heater
        40      |  Enable brine in monitoring
        41      |  Enable fixed system supply set point, allows defacto address 40117
        42      |  Enable evaporator freeze protection
        43      |  Enable outdoor temp dependent for cooling with mixing valve 2 (EM3 only)*5
        44      |  Enable dew point calculation on mixing valve 2, requires room sensor for mixing valve 2 (EM3 only)
        45      |  Enable outdoor temp dependent for heating with mixing valve 2 (EM3 only)*5
        46      |  Enable outdoor temp dependent for cooling with mixing valve 3 (EM3 only)*5
        47      |  Enable dew point calculation on mixing valve 3, requires room sensor for mixing valve 3 (EM3 only)
        48      |  Enable outdoor temp dependent for heating with mixing valve 3 (EM3 only)*5
        49      |  Enable outdoor temp dependent for cooling with mixing valve 4 (EM3 only)*5
        50      |  Enable dew point calculation on mixing valve 4, requires room sensor for mixing valve 4 (EM3 only)
        51      |  Enable outdoor temp dependent for heating with mixing valve 4 (EM3 only)*5
        52      |  Enable outdoor temp dependent for cooling with mixing valve 5 (EM3 only)*5
        53      |  Enable dew point calculation on mixing valve 5, requires room sensor for mixing valve 5 (EM3 only)
        54      |  Enable outdoor temp dependent for heating with mixing valve 5 (EM3 only)*5
        55      |  Enable internal brine pump to start when cooling is active for mixing valve 2 (EM3 only)
        56      |  Enable internal brine pump to start when cooling is active for mixing valve 3 (EM3 only)
        57      |  Enable internal brine pump to start when cooling is active for mixing valve 4 (EM3 only)
        58      |  Enable internal brine pump to start when cooling is active for mixing valve 5 (EM3 only)

        59      |  Enable continuous operation mode for condenser pump.
        62      |  Allow current limiter to restrict external additional heater
        63      |  Allow current limiter to restrict secondary heat pump units
*/
        /**
        DISCRETE INPUTS - Function codes: 2=read discrete inputs

        0      |  Alarm active, Class: A
        1      |  Alarm active, Class: B
        2      |  Alarm active, Class: C
        3      |  Alarm active, Class: D - Genesis secondary
        4      |  Alarm active, Class: E - Legacy secondary

        9      |  High pressure switch alarm
        10     |  Low pressure level alarm
        11     |  High discharge pipe temperature alarm
        12     |  Operating pressure limit indication
        13     |  Discharge pipe sensor alarm
        14     |  Liquid line sensor alarm
        15     |  Suction gas sensor alarm
        16     |  Flow/pressure switch alarm
        22     |  Power input phase detection alarm
        23     |  Inverter unit alarm
        24     |  System supply low temperature alarm
        25     |  Compressor low speed alarm
        26     |  Low super heat alarm
        27     |  Pressure ratio out of range alarm
        28     |  Compressor pressure outside envelope alarm
        29     |  Brine temperature out of range alarm
        30     |  Brine in sensor alarm
        31     |  Brine out sensor alarm
        32     |  Condenser in sensor alarm
        33     |  Condenser out sensor alarm
        34     |  Outdoor sensor alarm
        35     |  System supply line sensor alarm
        36     |  Mix valve 1 supply line sensor alarm
        37     |  Mix valve 2 supply line sensor alarm (EM)
        38     |  Mix valve 3 supply line sensor alarm (EM)
        39     |  Mix valve 4 supply line sensor alarm (EM)
        40     |  Mix valve 5 supply line sensor alarm (EM)
        44     |  WCS return line sensor alarm (EM)
        45     |  TWC supply line sensor alarm (EM)
        46     |  Cooling tank sensor alarm (EM)
        47     |  Cooling supply line sensor alarm (EM)
        48     |  Cooling circuit return line sensor alarm (EM)
        49     |  Brine delta out of range alarm
        50     |  Tap water mid sensor alarm
        51     |  TWC circulation return sensor alarm (EM)
        52     |  HGW sensor alarm
        53     |  Internal additional heater alarm
        55     |  Brine in high temperature alarm
        56     |  Brine in low temperature alarm
        57     |  Brine out low temperature alarm
        58     |  TWC circulation return low temperature alarm (EM)
        59     |  TWC supply low temperature alarm (EM)
        60     |  Mix valve 1 supply temperature deviation alarm
        61     |  Mix valve 2 supply temperature deviation alarm (EM)
        62     |  Mix valve 3 supply temperature deviation alarm (EM)
        63     |  Mix valve 4 supply temperature deviation alarm (EM)
        64     |  Mix valve 5 supply temperature deviation alarm (EM)
        65     |  WCS return line temperature deviation alarm (EM)
        66     |  Sum alarm
        67     |  Cooling circuit supply line temperature deviation alarm (EM)
        68     |  Cooling tank temperature deviation alarm (EM)
        69     |  Surplus heat temperature deviation alarm (EM)
        70     |  Humidity room sensor alarm
        71     |  Surplus heat supply line sensor alarm (EM)
        72     |  Surplus heat return line sensor alarm (EM)
        73     |  Cooling tank return line sensor alarm (EM)
        74     |  Temperature room sensor alarm
        75     |  Inverter unit communication alarm
        76     |  Pool return line sensor alarm
        77     |  External stop for pool, read only
        78     |  External start brine pump, read only
        79     |  External relay for brine/ground water pump.
        81     |  Tap water end tank sensor alarm
        82     |  Maximum time for anti-legionella exceeded alarm
        83      | Genesis secondary unit alarm - this specific secondary unit can't communicate with its primary unit
        84      | Primary unit alarm - the primary has detected other primary units on the same network with a network mask that is allowing conflict. Change network settings in order to avoid problem. For instance change port number on the primary and its secondary unit.
        85     | Primary unit alarm - the primary has not detected all secondary units. Make sure that the primary/secondary settings are correct and the network mask and port and number of Genesis secondaries settings are correct.
        86     | Oil boost in progress
        87     | Tap water top sensor alarm.

        199     |   Compressor control signal
        201     |   Smart Grid 1, EVU input
        202     |   External alarm input

        204   |   Smart Grid 2
        206   |   External additional heater control signal
        209   |   Mix valve 1 circulation pump control signal
        210   |   Condenser pump On/off control
        211   |   System circulation pump control signal
        213    |  Hot gas circulation pump control signal
        218   |   Brine pump On/off control
        219   |   External heater circulation pump control signal
        220   |   Heating season (winter) active
        221   |   External additional heater active
        222    |  Internal additional heater active
        223    |  HGW regulation control signal
        224   |   Heat pump stopping
        225   |   Heat pump OK to start
        230    |  TWC supply line circulation pump control signal (EM)
        232    |  WCS regulation control signal (EM)
        233    |  WCS circulation pump control signal (EM)
        234    |  TWC end tank heater control signal (EM)
        235   |  Pool directional valve position (EM)
        236   |  Cooling circuit circulation pump control signal (EM)
        237    |  Pool circulation pump control signal (EM)
        238   |  Surplus heat directional valve position (EM)
        239    |  Surplus heat circulation pump control signal (EM)
        240   |  Cooling circuit regulation control signal (EM)
        241    |  Surplus heat regulation control signal (EM)
        242   |  Active cooling directional valve position (Borehole disconnected) (EM)
        243    |  Passive/Active cooling directional valve position (Cooling tank connected) (EM)
        244    |  Pool regulation control signal (EM)
        245   |  Indication when mixing valve 1 is producing passive cooling
        246   |  Compressor is unable to speed up
*/
//        INPUT REGISTERS - Function codes: 4=read input registers


        val irBlock: Block = Block.builder()
            .schemaDevice(device)
            .id("inputRegisters")
            .description("IR block")
            .build()
        device.addBlock(irBlock)

        irBlock.newField("CurrentlyRunningFirstDemand",                         "",          "enum   (ir:1 ; $star1Mapping)",                            "Currently running: First prioritised demand")
        irBlock.newField("CurrentlyRunningDemands",                             "",          "bitset (ir:2 ; $star6Mapping)",                            "Currently running: Bit registers that shows the all the current running demands")
        irBlock.newField("CompressorAvailableGears",                            "",          "int16  (ir:4   ) / 100",                                   "Compressor available gears")
        irBlock.newField("CompressorSpeed",                                     "rpm",       "int16  (ir:5   )",                                         "Compressor speed")
        irBlock.newField("ExternalHeaterDemand",                                "%",         "int16  (ir:6   ) / 100",                                   "External additional heater: Current demand (%)")
        irBlock.newField("DischargePipeTemperature",                            "°C",        "int16  (ir:7   ; 0x4E20 ) / 100",                          "Discharge pipe temperature")
        irBlock.newField("CondenserInTemperature",                              "°C",        "int16  (ir:8   ; 0x4E20 ) / 100",                          "Condenser in temperature")
        irBlock.newField("CondenserOutTemperature",                             "°C",        "int16  (ir:9   ; 0x4E20 ) / 100",                          "Condenser out temperature")
        irBlock.newField("BrineInTemperature",                                  "°C",        "int16  (ir:10  ; 0x4E20 ) / 100",                          "Brine in temperature")
        irBlock.newField("BrineOutTemperature",                                 "°C",        "int16  (ir:11  ; 0x4E20 ) / 100",                          "Brine out temperature")
        irBlock.newField("SystemSupplyLineTemperature",                         "°C",        "int16  (ir:12  ; 0x4E20 ) / 100",                          "System supply line temperature")
        irBlock.newField("OutdoorTemperature",                                  "°C",        "int16  (ir:13  ; 0x4E20 ) / 100",                          "Outdoor temperature")
        irBlock.newField("TapWaterTopTemperature",                              "°C",        "int16  (ir:15  ; 0x4E20 ) / 100",                          "Tap water top temperature")
        irBlock.newField("TapWaterLowerTemperature",                            "°C",        "int16  (ir:16  ; 0x4E20 ) / 100",                          "Tap water lower temperature")
        irBlock.newField("TapWaterWeightedTemperature",                         "°C",        "int16  (ir:17  ; 0x4E20 ) / 100",                          "Tap water weighted temperature")
        irBlock.newField("SystemSupplyLineCalculatedSetPoint",                  "°C",        "int16  (ir:18  ; 0x4E20 ) / 100",                          "System supply line calculated set point")
        irBlock.newField("SystemSupplyLineSelectedHeatCurve",                   "°C",        "int16  (ir:19  ; 0x4E20 ) / 100",                          "Selected heat curve, (system) supply line")
        irBlock.newField("HeatCurveX1",                                         "°C",        "int16  (ir:20  ; 0x4E20 ) / 100",                          "Heat curve, X-coordinate 1 (highest outdoor temperature)")
        irBlock.newField("HeatCurveX2",                                         "°C",        "int16  (ir:21  ; 0x4E20 ) / 100",                          "Heat curve, X-coordinate 2")
        irBlock.newField("HeatCurveX3",                                         "°C",        "int16  (ir:22  ; 0x4E20 ) / 100",                          "Heat curve, X-coordinate 3")
        irBlock.newField("HeatCurveX4",                                         "°C",        "int16  (ir:23  ; 0x4E20 ) / 100",                          "Heat curve, X-coordinate 4")
        irBlock.newField("HeatCurveX5",                                         "°C",        "int16  (ir:24  ; 0x4E20 ) / 100",                          "Heat curve, X-coordinate 5")
        irBlock.newField("HeatCurveX6",                                         "°C",        "int16  (ir:25  ; 0x4E20 ) / 100",                          "Heat curve, X-coordinate 6")
        irBlock.newField("HeatCurveX7",                                         "°C",        "int16  (ir:26  ; 0x4E20 ) / 100",                          "Heat curve, X-coordinate 7 (lowest outdoor temperature)")
        irBlock.newField("SystemReturnLineTemperature.",                        "°C",        "int16  (ir:27  ; 0x4E20 ) / 100",                          "System return line temperature.")
        irBlock.newField("CalculatedDemand",                                    "",          "int16  (ir:30  ) / 100",                                   "Calculated demand (heat)")
        irBlock.newField("CoolingSeasonIntegralValue",                          "",          "int16  (ir:36  )",                                         "Cooling season integral value")
        irBlock.newField("CondenserCirculationPumpSpeed",                       "%",         "int16  (ir:39  ) / 100",                                   "Condenser circulation pump speed (%)")
        irBlock.newField("MixValve1SupplyLineTemperature",                      "°C",        "int16  (ir:40  ; 0x4E20 ) / 100",                          "Mix valve 1 supply line temperature")
        irBlock.newField("BufferTankTemperature",                               "°C",        "int16  (ir:41  ; 0x4E20 ) / 100",                          "Buffer tank temperature")
        irBlock.newField("MixValve1Position",                                   "%",         "int16  (ir:43  ) / 100",                                   "Mix valve 1 position")
        irBlock.newField("BrineCirculationPumpSpeed",                           "%",         "int16  (ir:44  ) / 100",                                   "Brine circulation pump speed (%)")
        irBlock.newField("HGWSupplyLineTemperature",                            "°C",        "int16  (ir:45  ; 0x4E20 ) / 100",                          "HGW supply line temperature")
        irBlock.newField("TapWaterValvePosition",                               "%",         "int16  (ir:47  )",                                         "Tap water valve position (%)")
        irBlock.newField("CompressorOperatingHours",                            "h",         "uint32 (ir:48, ir:49)",                                    "Compressor operating hours")
        irBlock.newField("TapWaterOperatingHours",                              "h",         "uint32 (ir:50, ir:51)",                                    "Tap water operating hours")
        irBlock.newField("ExternalAdditionalHeaterOperatingHours",              "h",         "uint32 (ir:52, ir:53)",                                    "External additional heater operating hours")
        irBlock.newField("CompressorSpeedPercent",                              "%",         "int16  (ir:54  ) / 100",                                   "Compressor speed percent")
        irBlock.newField("CurrentlyRunning2ndDemand",                           "",          "enum   (ir:55 ;$star1Mapping)",                            "Currently running: Second prioritised demand")
        irBlock.newField("CurrentlyRunning3rdDemand",                           "",          "enum   (ir:56 ;$star1Mapping)",                            "Currently running: Third prioritised demand")
        irBlock.newField("SoftwareVersionMajor",                                "",          "int16  (ir:57  )",                                         "Software version: Major")
        irBlock.newField("SoftwareVersionMinor",                                "",          "int16  (ir:58  )",                                         "Software version: Minor")
        irBlock.newField("SoftwareVersionMicro",                                "",          "int16  (ir:59  )",                                         "Software version: Micro")
        irBlock.newField("SoftwareVersion",                                     "",          "concat  (SoftwareVersionMajor , '.' ,SoftwareVersionMinor , '.' ,SoftwareVersionMicro )",         "Software version: Major")
        irBlock.newField("CompressorTemporarilyBlockedStartRestrictionTimer",   "",          "int16  (ir:60  )",                                         "Compressor temporarily blocked, (start restriction timer)")
        irBlock.newField("CompressorCurrentGear",                               "",          "int16  (ir:61  ) / 100",                                   "Compressor current gear")
        irBlock.newField("QueuedDemand1st",                                     "",          "enum   (ir:62 ;$star1Mapping)",                            "Queued demand, first priority")
        irBlock.newField("QueuedDemand2nd",                                     "",          "enum   (ir:63 ;$star1Mapping)",                            "Queued demand, second priority")
        irBlock.newField("QueuedDemand3rd",                                     "",          "enum   (ir:64 ;$star1Mapping)",                            "Queued demand, third priority")
        irBlock.newField("QueuedDemand4th",                                     "",          "enum   (ir:65 ;$star1Mapping)",                            "Queued demand, fourth priority")
        irBlock.newField("QueuedDemand5th",                                     "",          "enum   (ir:66 ;$star1Mapping)",                            "Queued demand, fifth priority")
        irBlock.newField("ActiveStepInternalImmersionHeater",                   "step",      "int16  (ir:67  )",                                         "Active step internal immersion heater, Mega S-E only")
        irBlock.newField("BufferTankChargeSetPoint",                            "°C",        "int16  (ir:68 ; 0x4E20 ) / 100",                                   "Buffer tank charge set point")
        irBlock.newField("ElectricMeterL1Current",                              "A",         "int16  (ir:69  ) / 100",                                   "Electric meter L1 current (A)")
        irBlock.newField("ElectricMeterL2Current",                              "A",         "int16  (ir:70  ) / 100",                                   "Electric meter L2 current (A)")
        irBlock.newField("ElectricMeterL3Current",                              "A",         "int16  (ir:71  ) / 100",                                   "Electric meter L3 current (A)")
        irBlock.newField("ElectricMeterL1_0Voltage",                            "V",         "int16  (ir:72  ) / 100",                                   "Electric meter L1-0 voltage (V)")
        irBlock.newField("ElectricMeterL2_0Voltage",                            "V",         "int16  (ir:73  ) / 100",                                   "Electric meter L2-0 voltage (V)")
        irBlock.newField("ElectricMeterL3_0Voltage",                            "V",         "int16  (ir:74  ) / 100",                                   "Electric meter L3-0 voltage (V)")
        irBlock.newField("ElectricMeterL1_L2Voltage",                           "V",         "int16  (ir:75  ) / 10",                                    "Electric meter L1-L2 voltage (V)")
        irBlock.newField("ElectricMeterL2_L3Voltage",                           "V",         "int16  (ir:76  ) / 10",                                    "Electric meter L2-L3 voltage (V)")
        irBlock.newField("ElectricMeterL3_L1Voltage",                           "V",         "int16  (ir:77  ) / 10",                                    "Electric meter L3-L1 voltage (V)")
        irBlock.newField("ElectricMeterL1Power",                                "W",         "int16  (ir:78  )",                                         "Electric meter L1 power (W)")
        irBlock.newField("ElectricMeterL2Power",                                "W",         "int16  (ir:79  )",                                         "Electric meter L2 power (W)")
        irBlock.newField("ElectricMeterL3Power",                                "W",         "int16  (ir:80  )",                                         "Electric meter L3 power (W)")
        irBlock.newField("ElectricMeterMeterValue",                             "kWh",       "int16  (ir:81  )",                                         "Electric meter - meter value (kWh)")
        irBlock.newField("CurrentSmartGridMode",                                "",          "enum   (ir:82 ; $star4Mapping)",                           "Current Smart Grid mode")
        irBlock.newField("ElectricMeterKWhTotal",                               "h",         "uint32 (ir:83, ir:84)",                                    "Electric meter kWh total")
        irBlock.newField("WCSValvePosition",                                    "%",         "int16  (ir:85  ) / 100",                                   "WCS valve position (EM)")
        irBlock.newField("TWCValvePosition",                                    "%",         "int16  (ir:86  ) / 100",                                   "TWC valve position (EM)")
        irBlock.newField("MixValve2Position",                                   "%",         "int16  (ir:87  ) / 100",                                   "Mix valve 2 position (EM)")
        irBlock.newField("MixValve3Position",                                   "%",         "int16  (ir:88  ) / 100",                                   "Mix valve 3 position (EM)")
        irBlock.newField("MixValve4Position",                                   "%",         "int16  (ir:89  ) / 100",                                   "Mix valve 4 position (EM)")
        irBlock.newField("MixValve5Position",                                   "%",         "int16  (ir:90  ) / 100",                                   "Mix valve 5 position (EM)")
        irBlock.newField("DewPointRoom",                                        "",          "int16  (ir:91  ) / 100",                                   "Dew point room (EM)")
        irBlock.newField("CoolingSupplyLineMixValvePosition",                   "%",         "int16  (ir:92  ) / 100",                                   "Cooling supply line mix valve position (EM)")
        irBlock.newField("SurplusHeatFanSpeed",                                 "%",         "int16  (ir:93  ) / 100",                                   "Surplus heat fan speed (EM)")
        irBlock.newField("PoolSupplyLineMixValvePosition",                      "%",         "int16  (ir:94  ) / 100",                                   "Pool supply line mix valve position (EM)")
        irBlock.newField("TWCSupplyLineTemperature",                            "°C",        "int16  (ir:95  ; 0x4E20 ) / 100",                          "TWC supply line temperature (EM)")
        irBlock.newField("TWCReturnTemperature",                                "°C",        "int16  (ir:96  ; 0x4E20 ) / 100",                          "TWC return temperature (EM)")
        irBlock.newField("WCSReturnLineTemperature",                            "°C",        "int16  (ir:97  ; 0x4E20 ) / 100",                          "WCS return line temperature (EM)")
        irBlock.newField("TWCEndTankTemperature",                               "°C",        "int16  (ir:98  ; 0x4E20 ) / 100",                          "TWC end tank temperature (EM)")
        irBlock.newField("MixValve2SupplyLineTemperature",                      "°C",        "int16  (ir:99  ; 0x4E20 ) / 100",                          "Mix valve 2 supply line temperature (EM)")
        irBlock.newField("MixValve3SupplyLineTemperature",                      "°C",        "int16  (ir:100 ; 0x4E20 ) / 100",                          "Mix valve 3 supply line temperature (EM)")
        irBlock.newField("MixValve4SupplyLineTemperature",                      "°C",        "int16  (ir:101 ; 0x4E20 ) / 100",                          "Mix valve 4 supply line temperature (EM)")
        irBlock.newField("CoolingCircuitReturnLineTemperature",                 "°C",        "int16  (ir:103 ; 0x4E20 ) / 100",                          "Cooling circuit return line temperature (EM)")
        irBlock.newField("CoolingTankTemperature",                              "°C",        "int16  (ir:104 ; 0x4E20 ) / 100",                          "Cooling tank temperature (EM)")
        irBlock.newField("CoolingTankReturnLineTemperature",                    "°C",        "int16  (ir:105 ; 0x4E20 ) / 100",                          "Cooling tank return line temperature (EM)")
        irBlock.newField("CoolingCircuitSupplyLineTemperature",                 "°C",        "int16  (ir:106 ; 0x4E20 ) / 100",                          "Cooling circuit supply line temperature (EM)")
        irBlock.newField("MixValve5SupplyLineTemperature",                      "°C",        "int16  (ir:107 ; 0x4E20 ) / 100",                          "Mix valve 5 supply line temperature (EM)")
        irBlock.newField("MixValve2ReturnLineTemperature",                      "°C",        "int16  (ir:109 ; 0x4E20 ) / 100",                          "Mix valve 2 return line temperature (EM)")
        irBlock.newField("MixValve3ReturnLineTemperature",                      "°C",        "int16  (ir:111 ; 0x4E20 ) / 100",                          "Mix valve 3 return line temperature (EM)")
        irBlock.newField("MixValve4ReturnLineTemperature",                      "°C",        "int16  (ir:113 ; 0x4E20 ) / 100",                          "Mix valve 4 return line temperature (EM)")
        irBlock.newField("MixValve5ReturnLineTemperature",                      "°C",        "int16  (ir:115 ; 0x4E20 ) / 100",                          "Mix valve 5 return line temperature (EM)")
        irBlock.newField("SurplusHeatReturnLineTemperature",                    "°C",        "int16  (ir:117 ; 0x4E20 ) / 100",                          "Surplus heat return line temperature (EM)")
        irBlock.newField("SurplusHeatSupplyLineTemperature",                    "°C",        "int16  (ir:118 ; 0x4E20 ) / 100",                          "Surplus heat supply line temperature (EM)")
        irBlock.newField("PoolSupplyLineTemperature",                           "°C",        "int16  (ir:119 ; 0x4E20 ) / 100",                          "Pool supply line temperature (EM)")
        irBlock.newField("PoolReturnLineTemperature",                           "°C",        "int16  (ir:120 ; 0x4E20 ) / 100",                          "Pool return line temperature (EM)")
        irBlock.newField("RoomTemperatureSensor",                               "°C",        "int16  (ir:121 ; 0x4E20 ) / 10",                           "Room temperature sensor")
        irBlock.newField("BubblePointHighPressureTemperature",                  "°C",        "int16  (ir:122 ; 0x4E20 ) / 100",                          "Bubble point, high pressure temperature")
        irBlock.newField("DewPointHighPressureTemperature",                     "°C",        "int16  (ir:123 ; 0x4E20 ) / 100",                          "Dew point, high pressure temperature")
        irBlock.newField("DewPointLowPressureTemperature",                      "°C",        "int16  (ir:124 ; 0x4E20 ) / 100",                          "Dew point, low pressure temperature")
        irBlock.newField("SuperheatTemperature",                                "K",         "int16  (ir:125 ; 0x4E20 ) / 100",                          "Superheat temperature")
        irBlock.newField("SubCoolingTemperature",                               "K",         "int16  (ir:126 ; 0x4E20 ) / 100",                          "Sub cooling temperature")
        irBlock.newField("LowPressureSidePressure",                             "bar(g)",    "int16  (ir:127 ) / 100",                                   "Low pressure side, pressure (bar(g))")
        irBlock.newField("HighPressureSidePressure",                            "bar(g)",    "int16  (ir:128 ) / 100",                                   "High pressure side, pressure (bar(g))")
        irBlock.newField("LiquidLineTemperature",                               "°C",        "int16  (ir:129 ; 0x4E20) / 100",                           "Liquid line temperature")
        irBlock.newField("SuctionGasTemperature",                               "°C",        "int16  (ir:130 ; 0x4E20) / 100",                           "Suction gas temperature")
        irBlock.newField("HeatingSeasonIntegralValue",                          "",          "int16  (ir:131 )",                                         "Heating season integral value")
        irBlock.newField("PValueForGearShiftingAndDemandCalculation",           "",          "int16  (ir:132 ) / 100",                                   "P - value for gear shifting and demand calculation")
        irBlock.newField("IValueForGearShiftingAndDemandCalculation",           "",          "int16  (ir:133 ) / 100",                                   "I - value for gear shifting and demand calculation")
        irBlock.newField("DValueForGearShiftingAndDemandCalculation",           "",          "int16  (ir:134 ) / 100",                                   "D - value for gear shifting and demand calculation")
        irBlock.newField("IValueForCompressorBufferTank",                       "",          "int16  (ir:135 ) / 100",                                   "I - value for compressor ON/OFF (Buffer tank)")
        irBlock.newField("PValueForCompressorBufferTank",                       "",          "int16  (ir:136 ) / 100",                                   "P - value for compressor ON/OFF (Buffer tank)")
        irBlock.newField("MixValveCoolingOpeningDegree ",                       "%",         "int16  (ir:137 )",                                         "Mix valve cooling opening degree (EM2/3)")
        irBlock.newField("DesiredGearForTapWater",                              "",          "int16  (ir:139 )",                                         "Desired gear for tap water")
        irBlock.newField("DesiredGearForHeating",                               "",          "int16  (ir:140 )",                                         "Desired gear for heating")
        irBlock.newField("DesiredGearForCooling",                               "",          "int16  (ir:141 )",                                         "Desired gear for cooling")
        irBlock.newField("DesiredGearForPool",                                  "",          "int16  (ir:142 )",                                         "Desired gear for pool")
        irBlock.newField("NumberOfAvailableSecondariesGenesis",                 "",          "int16  (ir:143 )",                                         "Number of available secondaries Genesis")
        irBlock.newField("NumberOfAvailableSecondariesLegacy",                  "",          "int16  (ir:144 )",                                         "Number of available secondaries Legacy")
        irBlock.newField("TotalDistributedGearsToAllUnits",                     "",          "int16  (ir:145 )",                                         "Total distributed gears to all units")
        irBlock.newField("MaximumGearOutOfAllTheCurrentlyRequestedGears",       "",          "int16  (ir:146 )",                                         "Maximum gear out of all the currently requested gears")
        irBlock.newField("MixValve1_DesiredTemperatureDistributionCircuit",     "°C",        "int16  (ir:147 ; 0x4E20 ) / 100",                          "Desired temperature distribution circuit Mix valve 1")
        irBlock.newField("MixValve2_DesiredTemperatureDistributionCircuit",     "°C",        "int16  (ir:148 ; 0x4E20 ) / 100",                          "Desired temperature distribution circuit Mix valve 2")
        irBlock.newField("MixValve3_DesiredTemperatureDistributionCircuit",     "°C",        "int16  (ir:149 ; 0x4E20 ) / 100",                          "Desired temperature distribution circuit Mix valve 3")
        irBlock.newField("MixValve4_DesiredTemperatureDistributionCircuit",     "°C",        "int16  (ir:150 ; 0x4E20 ) / 100",                          "Desired temperature distribution circuit Mix valve 4")
        irBlock.newField("MixValve5_DesiredTemperatureDistributionCircuit",     "°C",        "int16  (ir:151 ; 0x4E20 ) / 100",                          "Desired temperature distribution circuit Mix valve 5")
        irBlock.newField("DisconnectHotGasEndTank",                             "",          "enum  (ir:152 ; 0 -> 'Connected'; 1 -> 'Disconnected')",   "Disconnect hot gas end tank, 0 = connected, 1 = disconnected")
        irBlock.newField("LegacyHeatPumpCompressorRunning",                     "",          "bitset (ir:153; $legacyBitSet)",                           "Legacy heat pump compressor running (bit field)")
        irBlock.newField("LegacyHeatPumpReportingAlarm",                        "",          "bitset (ir:154; $legacyBitSet)",                           "Legacy heat pump reporting alarm (bit field)")
        irBlock.newField("LegacyHeatPumpStartSignal",                           "",          "bitset (ir:155; $legacyBitSet)",                           "Legacy heat pump start signal (bit field)")
        irBlock.newField("LegacyHeatPumpTapWaterSignal",                        "",          "bitset (ir:156; $legacyBitSet)",                           "Legacy heat pump tap water signal (bit field)")
        irBlock.newField("PrimaryUnitAlarmClassD",                              "",          "bitset (ir:160; $legacyBitSet)",                           "Primary unit alarm - the combined output of all Class D alarms. This signal is a bit field, one bit for each secondary heat pump unit.")
        irBlock.newField("PrimaryUnitAlarmLostCommunication",                   "",          "bitset (ir:161; $legacyBitSet)",                           "Primary unit alarm - the primary unit has lost communication with one or more Genesis secondaries. This signal is a bit field, one bit for each heat pump.")
        irBlock.newField("PrimaryUnitAlarmClassA",                              "",          "bitset (ir:162; $legacyBitSet)",                           "Primary unit alarm - Class A alarm detected on the Genesis secondary heat pump unit. This signal is a bit field, one bit for each secondary heat pump unit.")
        irBlock.newField("PrimaryUnitAlarmClassB",                              "",          "bitset (ir:163; $legacyBitSet)",                           "Primary unit alarm - Class B alarm detected on the Genesis secondary heat pump unit. This signal is a bit field, one bit for each secondary heat pump unit.")
        irBlock.newField("PrimaryUnitAlarmClassE",                              "",          "bitset (ir:170; $legacyBitSet)",                           "Primary unit alarm - the combined output of all Class E alarms. This signal is a bit field, one bit for each legacy secondary heat pump unit.")
        irBlock.newField("PrimaryUnitAlarmGeneral",                             "",          "bitset (ir:171; $legacyBitSet)",                           "Primary unit alarm - general legacy heat pump alarm. This signal is a bit field, one bit for each legacy secondary heat pump unit. Detects if the sum alarm of the secondary unit is active.")
        irBlock.newField("PrimaryUnitAlarmLostCommunicationLegacyHeatPump",     "",          "bitset (ir:173; $legacyBitSet)",                           "Primary unit alarm - the primary unit can not communicate with the corresponding expansion card for the legacy heat pump. This signal is a bit field, one bit for each legacy secondary heat pump unit.")
        irBlock.newField("ControlSoftwareVersionMajor",                         "",          "int16  (ir:311 )",                                         "Control software version: Major")
        irBlock.newField("ControlSoftwareVersionMinor",                         "",          "int16  (ir:312 )",                                         "Control software version: Minor")
        irBlock.newField("ControlSoftwareVersionMicro",                         "",          "int16  (ir:313 )",                                         "Control software version: Micro")
        irBlock.newField("ControlSoftwareVersion",                              "",          "concat (ControlSoftwareVersionMajor,'.',ControlSoftwareVersionMinor ,'.',ControlSoftwareVersionMicro)",                     "Control software version: Major")
        irBlock.newField("ExpansionValveOpeningDegree",                         "%",         "int16  (ir:315 ) / 100",                                   "Expansion valve opening degree")
        irBlock.newField("InverterTemperature",                                 "°C",        "int16  (ir:319 ; 0x4E20 )",                                "Inverter temperature")

        val hrBlock: Block = Block.builder()
            .schemaDevice(device)
            .id("holdingRegisters")
            .description("HR block")
            .build()
        device.addBlock(irBlock)

//        HOLDING REGISTERS - Function codes: 3=read holding registers, 6=write single register, 16=write multiple registers

        hrBlock.newField("OperationalMode",                                        "",      "enum   (hr:0 ; $star2Mapping)", "Operational mode *2")
        hrBlock.newField("MaxLimitation, setPointCurveRadiator",                   "°C",       "int16(hr:3 ; 0x4E20 ) / 100 ", "Max limitation, set point curve radiator")
        hrBlock.newField("MinLimitation, setPointCurveRadiator",                   "°C",       "int16(hr:4 ; 0x4E20 ) / 100 ", "Min limitation, set point curve radiator")
        hrBlock.newField("ComfortWheelSetting",                                    "",         "int16(hr:5  ) / 100 ", "Comfort wheel setting")
        hrBlock.newField("SetPointHeatCurveY1",                                    "°C",       "int16(hr:6  ; 0x4E20 ) / 100 ", "Set point heat curve, Y-coordinate 1 (highest outdoor temperature)")
        hrBlock.newField("SetPointHeatCurveY2",                                    "°C",       "int16(hr:7  ; 0x4E20 ) / 100 ", "Set point heat curve, Y-coordinate 2")
        hrBlock.newField("SetPointHeatCurveY3",                                    "°C",       "int16(hr:8  ; 0x4E20 ) / 100 ", "Set point heat curve, Y-coordinate 3")
        hrBlock.newField("SetPointHeatCurveY4",                                    "°C",       "int16(hr:9  ; 0x4E20 ) / 100 ", "Set point heat curve, Y-coordinate 4")
        hrBlock.newField("SetPointHeatCurveY5",                                    "°C",       "int16(hr:10 ; 0x4E20 ) / 100 ", "Set point heat curve, Y-coordinate 5")
        hrBlock.newField("SetPointHeatCurveY6",                                    "°C",       "int16(hr:11 ; 0x4E20 ) / 100 ", "Set point heat curve, Y-coordinate 6")
        hrBlock.newField("SetPointHeatCurveY7",                                    "°C",       "int16(hr:12 ; 0x4E20 ) / 100 ", "Set point heat curve, Y-coordinate 7 (lowest outdoor temperature)")
        hrBlock.newField("HeatingSeasonStopTemperature",                           "°C",       "int16(hr:16 ; 0x4E20 ) / 100 ", "Heating season stop temperature")
        hrBlock.newField("StartTemperatureTapWater",                               "°C",       "int16(hr:22 ; 0x4E20 ) / 100 ", "Start temperature tap water (only valid when tap water mode is Normal)")
        hrBlock.newField("StopTemperatureTapWater",                                "°C",       "int16(hr:23 ; 0x4E20 ) / 100 ", "Stop temperature tap water (only valid when tap water mode is Normal)")
        hrBlock.newField("MinimumAllowedGearInHeating",                            "",         "int16(hr:26 )       ", "Minimum allowed gear in heating")
        hrBlock.newField("MaximumAllowedGearInHeating",                            "",         "int16(hr:27 )       ", "Maximum allowed gear in heating")
        hrBlock.newField("MaximumAllowedGearInTapWater",                           "",         "int16(hr:28 )       ", "Maximum allowed gear in tap water (only valid when tap water mode is Normal)")
        hrBlock.newField("MinimumAllowedGearInTapWater",                           "",         "int16(hr:29 )       ", "Minimum allowed gear in tap water (only valid when tap water mode is Normal)")
        hrBlock.newField("CoolingMixValveSetPoint",                                "%",        "int16(hr:30 ) / 100 ", "Cooling mix valve set point (EM)")
        hrBlock.newField("TWCMixValveSetPoint",                                    "%",        "int16(hr:31 ) / 100 ", "TWC mix valve set point (EM)")
        hrBlock.newField("WCSReturnLineSetPoint",                                  "%",        "int16(hr:32 ) / 100 ", "WCS return line set point (EM)")
        hrBlock.newField("TWCMixValveLowestAllowedOpeningDegree",                  "%",        "int16(hr:33 ) / 100 ", "TWC mix valve lowest allowed opening degree (EM)")
        hrBlock.newField("TWCMixValveHighestAllowedOpeningDegree",                 "%",        "int16(hr:34 ) / 100 ", "TWC mix valve highest allowed opening degree (EM)")
        hrBlock.newField("TWCStartTemperatureImmersionHeater",                     "°C",       "int16(hr:35; 0x4E20 ) / 100 ", "TWC start temperature immersion heater (EM)")
        hrBlock.newField("TWCStartDelayImmersionHeater, seconds",                  "seconds",  "int16(hr:36 ) / 100 ", "TWC start delay immersion heater, seconds (EM)")
        hrBlock.newField("TWCStopTemperatureImmersionHeater",                      "°C",       "int16(hr:37; 0x4E20 ) / 100 ", "TWC stop temperature immersion heater (EM)")
        hrBlock.newField("WCSMixValveLowestAllowedOpeningDegree",                  "%",        "int16(hr:38 ) / 100 ", "WCS mix valve lowest allowed opening degree (EM)")
        hrBlock.newField("WCSMixValveHighestAllowedOpeningDegree",                 "%",        "int16(hr:39 ) / 100 ", "WCS mix valve highest allowed opening degree (EM)")
        hrBlock.newField("MixValve2LowestAllowedOpeningDegree",                    "%",        "int16(hr:40 ) / 100 ", "Mix valve 2 lowest allowed opening degree (EM)")
        hrBlock.newField("MixValve2HighestAllowedOpeningDegree",                   "%",        "int16(hr:41 ) / 100 ", "Mix valve 2 highest allowed opening degree (EM)")
        hrBlock.newField("MixValve3LowestAllowedOpeningDegree",                    "%",        "int16(hr:42 ) / 100 ", "Mix valve 3 lowest allowed opening degree (EM)")
        hrBlock.newField("MixValve3HighestAllowedOpeningDegree",                   "%",        "int16(hr:43 ) / 100 ", "Mix valve 3 highest allowed opening degree (EM)")
        hrBlock.newField("MixValve4LowestAllowedOpeningDegree",                    "%",        "int16(hr:44 ) / 100 ", "Mix valve 4 lowest allowed opening degree (EM)")
        hrBlock.newField("MixValve4HighestAllowedOpeningDegree",                   "%",        "int16(hr:45 ) / 100 ", "Mix valve 4 highest allowed opening degree (EM)")
        hrBlock.newField("MixValve5LowestAllowedOpeningDegree",                    "%",        "int16(hr:46 ) / 100 ", "Mix valve 5 lowest allowed opening degree (EM)")
        hrBlock.newField("MixValve5HighestAllowedOpeningDegree",                   "%",        "int16(hr:47 ) / 100 ", "Mix valve 5 highest allowed opening degree (EM)")
        hrBlock.newField("SurplusHeatChillerSetPoint",                             "%",        "int16(hr:48 ) / 100 ", "Surplus heat chiller set point (EM)")
        hrBlock.newField("CoolingSupplyLineMixValve: LowestAllowedOpeningDegree",  "%",        "int16(hr:49 ) / 100 ", "Cooling supply line mix valve: Lowest allowed opening degree (EM)")
        hrBlock.newField("CoolingSupplyLineMixValve: HighestAllowedOpeningDegree", "%",        "int16(hr:50 ) / 100 ", "Cooling supply line mix valve: Highest allowed opening degree (EM)")
        hrBlock.newField("SurplusHeatOpeningDegreeForStartingFan1",                "%",        "int16(hr:51 ) / 100 ", "Surplus heat opening degree for starting fan 1 (EM)")
        hrBlock.newField("SurplusHeatOpeningDegreeForStartingFan2",                "%",        "int16(hr:52 ) / 100 ", "Surplus heat opening degree for starting fan 2 (EM)")
        hrBlock.newField("SurplusHeatOpeningDegreeForStoppingFan1",                "%",        "int16(hr:53 ) / 100 ", "Surplus heat opening degree for stopping fan 1 (EM)")
        hrBlock.newField("SurplusHeatOpeningDegreeForStoppingFan2",                "%",        "int16(hr:54 ) / 100 ", "Surplus heat opening degree for stopping fan 2 (EM)")
        hrBlock.newField("SurplusHeatLowestAllowedOpeningDegree",                  "%",        "int16(hr:55 ) / 100 ", "Surplus heat lowest allowed opening degree (EM)")
        hrBlock.newField("SurplusHeatHighestAllowedOpeningDegree",                 "%",        "int16(hr:56 ) / 100 ", "Surplus heat highest allowed opening degree (EM)")
        hrBlock.newField("PoolChargeSetPoint",                                     "%",        "int16(hr:58 ) / 100 ", "Pool charge set point (EM)")
        hrBlock.newField("PoolMixValveLowestAllowedOpeningDegree",                 "%",        "int16(hr:59 ) / 100 ", "Pool mix valve lowest allowed opening degree (EM)")
        hrBlock.newField("PoolMixValveHighestAllowedOpeningDegree",                "%",        "int16(hr:60 ) / 100 ", "Pool mix valve highest allowed opening degree (EM)")
        hrBlock.newField("GearShiftDelayHeating",                                  "min",      "int16(hr:61 )       ", "Gear shift delay heating")
        hrBlock.newField("GearShiftDelayPool",                                     "min",      "int16(hr:62 )       ", "Gear shift delay pool")
        hrBlock.newField("GearShiftDelayCooling",                                  "min",      "int16(hr:63 )       ", "Gear shift delay cooling")
        hrBlock.newField("BrineInHighAlarmLimit",                                  "°C",       "int16(hr:67 ; 0x4E20) / 100 ", "Brine in high alarm limit")
        hrBlock.newField("BrineInLowAlarmLimit",                                   "°C",       "int16(hr:68 ; 0x4E20) / 100 ", "Brine in low alarm limit")
        hrBlock.newField("BrineOutLowAlarmLimit",                                  "°C",       "int16(hr:69 ; 0x4E20) / 100 ", "Brine out low alarm limit")
        hrBlock.newField("BrineMaxDeltaLimit",                                     "K",        "int16(hr:70 ) / 100 ", "Brine max delta limit")
        hrBlock.newField("HotGasPumpStartTemperatureDischargePipe",                "°C",       "int16(hr:71 ; 0x4E20) / 100 ", "Hot gas pump start temperature discharge pipe")
        hrBlock.newField("HotGasPumpLowerStopLimitTemperatureDischargePipe",       "°C",       "int16(hr:72 ; 0x4E20) / 100 ", "Hot gas pump lower stop limit temperature discharge pipe")
        hrBlock.newField("HotGasPumpUpperStopLimitTemperatureDischargePipe",       "°C",       "int16(hr:73 ; 0x4E20) / 100 ", "Hot gas pump upper stop limit temperature discharge pipe")
        hrBlock.newField("ExternalAdditionalHeaterStart",                          "",         "int16(hr:75 )       ", "External additional heater start (PID sum)")
        hrBlock.newField("CondenserPumpLowestAllowedSpeed",                        "%",        "int16(hr:76 ) / 100 ", "Condenser pump lowest allowed speed (%)")
        hrBlock.newField("BrinePumpLowestAllowedSpeed",                            "%",        "int16(hr:77 ) / 100 ", "Brine pump lowest allowed speed (%)")
        hrBlock.newField("ExternalAdditionalHeaterStop",                           "",         "int16(hr:78 ) / 100 ", "External additional heater stop (PID sum)")
        hrBlock.newField("CondenserPumpHighestAllowedSpeed",                       "%",        "int16(hr:79 ) / 100 ", "Condenser pump highest allowed speed (%)")
        hrBlock.newField("BrinePumpHighestAllowedSpeed",                           "%",        "int16(hr:80 ) / 100 ", "Brine pump highest allowed speed (%)")
        hrBlock.newField("CondenserPumpStandbySpeed",                              "%",        "int16(hr:81 ) / 100 ", "Condenser pump standby speed (%)")
        hrBlock.newField("BrinePumpStandbySpeed",                                  "%",        "int16(hr:82 ) / 100 ", "Brine pump standby speed (%)")
        hrBlock.newField("MinimumAllowedGearInPool",                               "",         "int16(hr:85 )       ", "Minimum allowed gear in pool")
        hrBlock.newField("MaximumAllowedGearInPool",                               "",         "int16(hr:86 )       ", "Maximum allowed gear in pool")
        hrBlock.newField("MinimumAllowedGearInCooling",                            "",         "int16(hr:87 )       ", "Minimum allowed gear in cooling")
        hrBlock.newField("MaximumAllowedGearInCooling",                            "",         "int16(hr:88 )       ", "Maximum allowed gear in cooling")
        hrBlock.newField("StartTempForCooling",                                    "°C",       "int16(hr:105 ; 0x4E20 ) / 100 ", "Start temp for cooling (EM)")
        hrBlock.newField("StopTempForCooling",                                     "°C",       "int16(hr:106 ; 0x4E20 ) / 100 ", "Stop temp for cooling (EM)")
        hrBlock.newField("MixValve1_MinLimitationSetPointCurveRadiator",           "°C",       "int16(hr:107 ; 0x4E20 ) / 100 ", "Min limitation Set point curve radiator Mix valve 1")
        hrBlock.newField("MixValve1_MaxLimitationSetPointCurveRadiator",           "°C",       "int16(hr:108 ; 0x4E20 ) / 100 ", "Max limitation Set point curve radiator Mix valve 1")
        hrBlock.newField("MixValve1_SetPointCurveY1",                              "°C",       "int16(hr:109 ; 0x4E20 ) / 100 ", "Set point curve, Y-coordinate 1 Mix valve 1 (highest outdoor temperature)")
        hrBlock.newField("MixValve1_SetPointCurveY2",                              "°C",       "int16(hr:110 ; 0x4E20 ) / 100 ", "Set point curve, Y-coordinate 2 Mix valve 1")
        hrBlock.newField("MixValve1_SetPointCurveY3",                              "°C",       "int16(hr:111 ; 0x4E20 ) / 100 ", "Set point curve, Y-coordinate 3 Mix valve 1")
        hrBlock.newField("MixValve1_SetPointCurveY4",                              "°C",       "int16(hr:112 ; 0x4E20 ) / 100 ", "Set point curve, Y-coordinate 4 Mix valve 1")
        hrBlock.newField("MixValve1_SetPointCurveY5",                              "°C",       "int16(hr:113 ; 0x4E20 ) / 100 ", "Set point curve, Y-coordinate 5 Mix valve 1")
        hrBlock.newField("MixValve1_SetPointCurveY6",                              "°C",       "int16(hr:114 ; 0x4E20 ) / 100 ", "Set point curve, Y-coordinate 6 Mix valve 1")
        hrBlock.newField("MixValve1_SetPointCurveY7",                              "°C",       "int16(hr:115 ; 0x4E20 ) / 100 ", "Set point curve, Y-coordinate 7 Mix valve 1 (lowest outdoor temperature)")
        hrBlock.newField("FixedSystemSupplySetPoint",                              "°C",       "int16(hr:116 ; 0x4E20 ) / 100 ", "Fixed system supply set point, requires defacto address 42 to be enabled")
        hrBlock.newField("OutdoorTemperatureSource",                               "",         "enum(hr:117; 0->'PT1000'; 1->'OutdoorTemperatureSensor')",     "Outdoor temperature source, is an enumeration where 0 = designated PT1000 sensor located on BM-card. 1 = BMS register hr:118 (De Facto). When the source is BMS the outdoor temperature alarm is automatically removed when the sensor data is valid. If no valid sensor data is present the heat pump will use its designated PT1000 sensor and if that sensor is missing the heat pump will use 0 degrees C as a fallback value.")
        hrBlock.newField("OutdoorTemperatureSensor",                               "°C",       "int16(hr:118; 0x4E20) / 100 ",                                         "Outdoor temperature sensor, this register will be the source of the outdoor temperature given that BMS-address hr:117 is set to 1. The valid range of the temperature is between -50 to 200 degrees C. If this register is not updated with a new temperature within 12 hours or the value is outside the valid range, the fallback logic will be triggered stated in description of BMS register hr:117. This signal is automatically filtered in the heat pump.")
//        hrBlock.newField("MaximumPhaseCurrent",                                    "A",        "int16(hr:119)       ", "Maximum phase current")
//        hrBlock.newField("CompressorCurrentHysteresis",                            "A",        "int16(hr:120)       ", "Compressor current hysteresis")
        hrBlock.newField("MixValve2_MinLimitationSetPointCurveRadiator",           "°C",       "int16(hr:199; 0x4E20) / 100 ", "Min limitation Set point curve radiator Mix valve 2")
        hrBlock.newField("MixValve2_MaxLimitationSetPointCurveRadiator",           "°C",       "int16(hr:200; 0x4E20) / 100 ", "Max limitation Set point curve radiator Mix valve 2")
        hrBlock.newField("MixValve2_SetPointCurveY1",                              "°C",       "int16(hr:201; 0x4E20) / 100 ", "Set point curve, Y-coordinate 1 Mix valve 2 (highest outdoor temperature)")
        hrBlock.newField("MixValve2_SetPointCurveY2",                              "°C",       "int16(hr:202; 0x4E20) / 100 ", "Set point curve, Y-coordinate 2 Mix valve 2")
        hrBlock.newField("MixValve2_SetPointCurveY3",                              "°C",       "int16(hr:203; 0x4E20) / 100 ", "Set point curve, Y-coordinate 3 Mix valve 2")
        hrBlock.newField("MixValve2_SetPointCurveY4",                              "°C",       "int16(hr:204; 0x4E20) / 100 ", "Set point curve, Y-coordinate 4 Mix valve 2")
        hrBlock.newField("MixValve2_SetPointCurveY5",                              "°C",       "int16(hr:205; 0x4E20) / 100 ", "Set point curve, Y-coordinate 5 Mix valve 2")
        hrBlock.newField("MixValve2_SetPointCurveY6",                              "°C",       "int16(hr:206; 0x4E20) / 100 ", "Set point curve, Y-coordinate 6 Mix valve 2")
        hrBlock.newField("MixValve2_SetPointCurveY7",                              "°C",       "int16(hr:207; 0x4E20) / 100 ", "Set point curve, Y-coordinate 7 Mix valve 2 (lowest outdoor temperature)")
        hrBlock.newField("MixValve3_MinLimitationSetPointCurveRadiator",           "°C",       "int16(hr:208; 0x4E20) / 100 ", "Min limitation Set point curve radiator Mix valve 3")
        hrBlock.newField("MixValve3_MaxLimitationSetPointCurveRadiator",           "°C",       "int16(hr:209; 0x4E20) / 100 ", "Max limitation Set point curve radiator Mix valve 3")
        hrBlock.newField("MixValve3_SetPointCurveY1",                              "°C",       "int16(hr:210; 0x4E20) / 100 ", "Set point curve, Y-coordinate 1 Mix valve 3 (highest outdoor temperature)")
        hrBlock.newField("MixValve3_SetPointCurveY2",                              "°C",       "int16(hr:211; 0x4E20) / 100 ", "Set point curve, Y-coordinate 2 Mix valve 3")
        hrBlock.newField("MixValve3_SetPointCurveY3",                              "°C",       "int16(hr:212; 0x4E20) / 100 ", "Set point curve, Y-coordinate 3 Mix valve 3")
        hrBlock.newField("MixValve3_SetPointCurveY4",                              "°C",       "int16(hr:213; 0x4E20) / 100 ", "Set point curve, Y-coordinate 4 Mix valve 3")
        hrBlock.newField("MixValve3_SetPointCurveY5",                              "°C",       "int16(hr:214; 0x4E20) / 100 ", "Set point curve, Y-coordinate 5 Mix valve 3")
        hrBlock.newField("MixValve3_SetPointCurveY6",                              "°C",       "int16(hr:215; 0x4E20) / 100 ", "Set point curve, Y-coordinate 6 Mix valve 3")
        hrBlock.newField("MixValve3_SetPointCurveY7",                              "°C",       "int16(hr:216; 0x4E20) / 100 ", "Set point curve, Y-coordinate 7 Mix valve 3 (lowest outdoor temperature)")
        hrBlock.newField("MixValve4_MinLimitationSetPointCurveRadiator",           "°C",       "int16(hr:239; 0x4E20) / 100 ", "Min limitation Set point curve radiator Mix valve 4")
        hrBlock.newField("MixValve4_MaxLimitationSetPointCurveRadiator",           "°C",       "int16(hr:240; 0x4E20) / 100 ", "Max limitation Set point curve radiator Mix valve 4")
        hrBlock.newField("MixValve4_SetPointCurveY1",                              "°C",       "int16(hr:241; 0x4E20) / 100 ", "Set point curve, Y-coordinate 1 Mix valve 4 (highest outdoor temperature)")
        hrBlock.newField("MixValve4_SetPointCurveY2",                              "°C",       "int16(hr:242; 0x4E20) / 100 ", "Set point curve, Y-coordinate 2 Mix valve 4")
        hrBlock.newField("MixValve4_SetPointCurveY3",                              "°C",       "int16(hr:243; 0x4E20) / 100 ", "Set point curve, Y-coordinate 3 Mix valve 4")
        hrBlock.newField("MixValve4_SetPointCurveY4",                              "°C",       "int16(hr:244; 0x4E20) / 100 ", "Set point curve, Y-coordinate 4 Mix valve 4")
        hrBlock.newField("MixValve4_SetPointCurveY5",                              "°C",       "int16(hr:245; 0x4E20) / 100 ", "Set point curve, Y-coordinate 5 Mix valve 4")
        hrBlock.newField("MixValve4_SetPointCurveY6",                              "°C",       "int16(hr:246; 0x4E20) / 100 ", "Set point curve, Y-coordinate 6 Mix valve 4")
        hrBlock.newField("MixValve4_SetPointCurveY7",                              "°C",       "int16(hr:247; 0x4E20) / 100 ", "Set point curve, Y-coordinate 7 Mix valve 4 (lowest outdoor temperature)")
        hrBlock.newField("MixValve5_MinLimitationSetPointCurveRadiator",           "°C",       "int16(hr:248; 0x4E20) / 100 ", "Min limitation Set point curve radiator Mix valve 5")
        hrBlock.newField("MixValve5_MaxLimitationSetPointCurveRadiator",           "°C",       "int16(hr:249; 0x4E20) / 100 ", "Max limitation Set point curve radiator Mix valve 5")
        hrBlock.newField("MixValve5_SetPointCurveY1",                              "°C",       "int16(hr:250; 0x4E20) / 100 ", "Set point curve, Y-coordinate 1 Mix valve 5 (highest outdoor temperature)")
        hrBlock.newField("MixValve5_SetPointCurveY2",                              "°C",       "int16(hr:251; 0x4E20) / 100 ", "Set point curve, Y-coordinate 2 Mix valve 5")
        hrBlock.newField("MixValve5_SetPointCurveY3",                              "°C",       "int16(hr:252; 0x4E20) / 100 ", "Set point curve, Y-coordinate 3 Mix valve 5")
        hrBlock.newField("MixValve5_SetPointCurveY4",                              "°C",       "int16(hr:253; 0x4E20) / 100 ", "Set point curve, Y-coordinate 4 Mix valve 5")
        hrBlock.newField("MixValve5_SetPointCurveY5",                              "°C",       "int16(hr:254; 0x4E20) / 100 ", "Set point curve, Y-coordinate 5 Mix valve 5")
        hrBlock.newField("MixValve5_SetPointCurveY6",                              "°C",       "int16(hr:255; 0x4E20) / 100 ", "Set point curve, Y-coordinate 6 Mix valve 5")
        hrBlock.newField("MixValve5_SetPointCurveY7",                              "°C",       "int16(hr:256; 0x4E20) / 100 ", "Set point curve, Y-coordinate 7 Mix valve 5 (lowest outdoor temperature)")
        hrBlock.newField("MixValve1_SelectedMode",                                 "",         "enum(hr:298; $mixingValueMapping)",   "Selected mode for mixing valve 1, 0:Heat, 1:Cool, 2:Auto")
        hrBlock.newField("SetPointReturnTempFromPoolToHeatExchanger",              "°C",       "int16(hr:299; 0x4E20) / 10  ",                "Set point return temp from pool to heat exchanger (EM)")
        hrBlock.newField("SetPointPoolHysteresis",                                 "K",        "int16(hr:300) / 10  ",                "Set point pool hysteresis (EM)")
        hrBlock.newField("MixValve1SetPointForSupplyLineTempPassiveCooling",       "°C",       "int16(hr:302; 0x4E20) / 100 ",                "Set point for supply line temp passive cooling with mixing valve 1")
        hrBlock.newField("SetPointMinimumOutdoorTempWhenCoolingIsPermitted",       "°C",       "int16(hr:303; 0x4E20) / 100 ",                "Set point minimum outdoor temp when cooling is permitted")
        hrBlock.newField("ExternalHeaterOutdoorTempLimit",                         "°C",       "int16(hr:304; 0x4E20) / 100 ",                "External heater outdoor temp limit")
        hrBlock.newField("MixValve2_SelectedMode",                                 "",         "enum(hr:305; $mixingValueMapping)",   "Selected mode for mixing valve 2, 0:Heat, 1:Cool, 2:Auto (EM3 only)")
        hrBlock.newField("MixValve2_DesiredCoolingTemperatureSetpoint",            "°C",       "int16(hr:306; 0x4E20) / 100 ",                "Desired cooling temperature setpoint mixing valve 2 (EM3 only)")
        hrBlock.newField("MixValve2_SeasonalCoolingOutdoorTemperature",            "°C",       "int16(hr:307; 0x4E20) / 100 ",                "Seasonal cooling temperature (outdoor temp.), mixing valve 2 (EM3 only)")
        hrBlock.newField("MixValve2_SeasonalHeatingOutdoorTemperature",            "°C",       "int16(hr:308; 0x4E20) / 100 ",                "Seasonal heating temperature (outdoor temp.), mixing valve 2 (EM3 only)")
        hrBlock.newField("MixValve3_SelectedMode",                                 "",         "enum(hr:309; $mixingValueMapping)",   "Selected mode for mixing valve 3, 0:Heat, 1:Cool, 2:Auto (EM3 only)")
        hrBlock.newField("MixValve3_DesiredCoolingTemperatureSetpoint",            "°C",       "int16(hr:310; 0x4E20) / 100 ",                "Desired cooling temperature setpoint mixing valve 3 (EM3 only)")
        hrBlock.newField("MixValve3_SeasonalCoolingOutdoorTemperature",            "°C",       "int16(hr:311; 0x4E20) / 100 ",                "Seasonal cooling temperature (outdoor temp.), mixing valve 3 (EM3 only)")
        hrBlock.newField("MixValve3_SeasonalHeatingOutdoorTemperature",            "°C",       "int16(hr:312; 0x4E20) / 100 ",                "Seasonal heating temperature (outdoor temp.), mixing valve 3 (EM3 only)")
        hrBlock.newField("MixValve4_SelectedMode",                                 "",         "enum(hr:313; $mixingValueMapping)",   "Selected mode for mixing valve 4, 0:Heat, 1:Cool, 2:Auto (EM3 only)")
        hrBlock.newField("MixValve4_DesiredCoolingTemperatureSetpoint",            "°C",       "int16(hr:314; 0x4E20) / 100 ",                "Desired cooling temperature setpoint mixing valve 4 (EM3 only)")
        hrBlock.newField("MixValve4_SeasonalCoolingOutdoorTemperature",            "°C",       "int16(hr:315; 0x4E20) / 100 ",                "Seasonal cooling temperature (outdoor temp.), mixing valve 4 (EM3 only)")
        hrBlock.newField("MixValve4_SeasonalHeatingOutdoorTemperature",            "°C",       "int16(hr:316; 0x4E20) / 100 ",                "Seasonal heating temperature (outdoor temp.), mixing valve 4 (EM3 only)")
        hrBlock.newField("MixValve5_SelectedMode",                                 "",         "enum(hr:317; $mixingValueMapping)",   "Selected mode for mixing valve 5, 0:Heat, 1:Cool, 2:Auto (EM3 only)")
        hrBlock.newField("MixValve5_DesiredCoolingTemperatureSetpoint",            "°C",       "int16(hr:318; 0x4E20) / 100 ",                "Desired cooling temperature setpoint mixing valve 5 (EM3 only)")
        hrBlock.newField("MixValve5_SeasonalCoolingOutdoorTemperature",            "°C",       "int16(hr:319; 0x4E20) / 100 ",                "Seasonal cooling temperature (outdoor temp.), mixing valve 5 (EM3 only)")
        hrBlock.newField("MixValve5_SeasonalHeatingOutdoorTemperature",            "°C",       "int16(hr:320; 0x4E20) / 100 ",                "Seasonal heating temperature (outdoor temp.), mixing valve 5 (EM3 only)")
//        hrBlock.newField("ImmersionHeaterStatus",                                  "",         "enum(hr:321; $star7Mapping)",         "Enabled immersion heater, Mega S-E only *7")

            return device
        }

    val modbusDevice: ModbusDevice
        get() = builder()
            .withLogging()
            .withRegisters(
                AddressClass.HOLDING_REGISTER, 0,  // 1
                "0003 0000 0000 1194 07D0 07D0 07D0 0960 0A8C 0BB8" +
                    "0C1C 0DAC 0F3C 0000 0000 0000 06A4 0000 0000 0000" +
                    "0000 0000 1130 1770 0000 0000 0001 0009 0007 0005" +
                    "0708 1770 1194 0000 2710 1770 0000 1964 05DC 2710" +
                    "0000 2710 0000 2710 0000 2710 0000 2710 1194 0000" +
                    "2710 1388 1F40 07D0 0FA0 0000 2710 0002 0A28 0000" +
                    "2710 012C 012C 003C 00B4 000A 001E 07D0 FE0C 00C8" +
                    "04B0 1964 1770 30D4 0001 FFFA 0FA0 07D0 0190 2710" +
                    "2710 1388 1388 0001 0001 0001 0009 0001 0009 0BB8" +
                    "2710 0096 FF6A FF9C 0064 7530 8AD0 0000 FF9C 0000" +  // 101
                    "00C8 F6A0 0960 0BB8 F448 0708 0514 03E8 1770 0834" +
                    "0B54 0D48 0FA0 10CC 13EC 170C 09C4 0000 4E20"
            )
            .withRegisters(
                AddressClass.INPUT_REGISTER, 0,  // 0
                "0000 0008 0080 0000 0384 0000 0000 0827 077B 0716" +
                    "06AD 0763 4E20 0A3D 0A3D 134C 1316 1329 0000 0BB8" +
                    "06A4 03E8 01F4 0000 FE0C FA24 F63C 4E20 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 03E8 0000 0000 1D4C" +
                    "4E20 4E20 09C4 0000 044C 4E20 044C 0000 0000 0B22" +
                    "0000 01CE 0000 0000 0000 0063 0063 000B 0000 00C5" +
                    "0000 0000 0008 0063 0063 0063 0063 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0004 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 C3DC 0000 0000 0000 4E20 4E20 4E20 4E20 4E20" +  // 100
                    "4E20 4E20 0064 4E20 4E20 4E20 4E20 4E20 0708 4E20" +
                    "4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20" +
                    "4E20 00E9 0800 080C 0657 01A9 FFFE 04AE 0556 07FD" +
                    "07FE 0096 0000 0000 0000 0000 0000 000B 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +  // 200
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +  // 300
                    "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
                    "0000 000B 0000 000C 0001 0000 0000 0000 0000 0014"
            )
            .build() //    20:01:12,478 [INFO ] TestSchemaDeviceThermia                :   89: Input Register starting at ir:00001:
    //    0004 0008 0000 0384 04AE 0000 11CD 094E 0B19 0405 039A 4E20 02EA 02EA 1379 1326 1343 0A5A 0BB8 06A4 03E8 01F4 0000 FE0C FA24 F63C 4E20 0000 0000 0106 0000 0000 0000 0000 0000 FF6A 0000 0000 0FA0 4E20 4E20 1770 0000 17CC 4E20 17CC 0000 0000 0FC0 0000 0280 0000 0000 0AE2 0063 0063 000B 0000 00C5 0000 0064 0004 0063 0063 0063 0063 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0004 0000 0000 0000 0000 0000 0000 0000 0000 C3DC 0000 0000 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 0064 4E20 4E20 4E20 4E20 4E20 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 00CD 0B13 0B1E 0352 00F8 01A7 03A8 06AD 096C 044A FF6A 007C 0083 0007 0000 0000 0000 0000 0000 0001 0000 0000 0000 0000 0001 0001 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0107 001E 0000 0000 0000 0000 0000 null null null null null null 0000 0000 0000 0000 null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null 000B 0000 000C 0001 08A0 0000 0000 0000 001B
    //20:17:46,467 [INFO ] TestSchemaDeviceThermia                :   89: Input Register starting at ir:00001:
    //        0004 0008 0000 0384 04AE 0000 129C 0968 0B28 0408 0341 4E20 02EB 02EB 136A 131D 1335 0A58 0BB8 06A4 03E8 01F4 0000 FE0C FA24 F63C 4E20 0000 0000 014B 0000 0000 0000 0000 0000 FF6A 0000 0000 0FA0 4E20 4E20 1770 0000 0BAF 4E20 0BAF 0000 0000 0FC1 0000 0280 0000 0000 0AE2 0063 0063 000B 0000 00C5 0000 0064 0004 0063 0063 0063 0063 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0004 0000 0000 0000 0000 0000 0000 0000 0000 C3DC 0000 0000 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 0064 4E20 4E20 4E20 4E20 4E20 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 00CD 0B23 0B2F 02AF 01A4 01B2 0377 06B5 096E 0453 FF6A 00A6 00A2 0003 0000 0000 0000 0000 0000 0001 0000 0000 0000 0000 0001 0001 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 00FC 001F 0000 0000 0000 0000 0000 null null null null null null 0000 0000 0000 0000 null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null 000B 0000 000C 0001 0793 0000 0000 0000 001B
}
