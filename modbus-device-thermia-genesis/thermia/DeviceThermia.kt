//package nl.basjes.modbus.schema.thermia
//
//import nl.basjes.modbus.device.api.AddressClass
//import nl.basjes.modbus.device.api.ModbusDevice
//import nl.basjes.modbus.device.memory.MockedModbusDevice.Companion.builder
//import nl.basjes.modbus.device.memory.MockedModbusDevice.MockedModbusDeviceBuilder.build
//import nl.basjes.modbus.schema.Block
//import nl.basjes.modbus.schema.Field
//import nl.basjes.modbus.schema.SchemaDevice
//import nl.basjes.modbus.schema.ReturnType
//
//internal object DeviceThermia {
//    val schemaDevice: SchemaDevice
//        get() {
//            val device: SchemaDevice = SchemaDevice.builder()
//                .name("ThermiaGenesis")
//                .maxRegistersPerModbusRequest(120)
//                .build()
//
//            val block: Block = Block.builder()
//                .id("0")
//                .description("Only block")
//                .build()
//            device.addBlock(block)
//
//            val Star1Mapping =
//                "1->'Manual operation'; 2-> 'Defrost'; 3-> 'Hot water'; 4-> 'Heat'; 5-> 'Cool'; 6-> 'Pool'; 7-> 'Anti legionella'; 8-> 'Passive Cooling'; 98-> 'Standby' ;99-> 'No demand' ;100-> 'OFF'"
//            val Star4Mapping = "1-> 'EVU'; 4-> 'Normal'; 5-> 'Comfort'; 6-> 'Boost'"
//            val Star6Mapping =
//                "0-> 'Manual operation'; 1-> 'Defrost'; 2-> 'Hot water'; 3-> 'Heat'; 4-> 'Active Cooling'; 5-> 'Pool'; 6-> 'Anti legionella'; 7-> 'Passive Cooling'; 8-> 'Reserved'; 9-> 'Standby'; 10-> 'No demand'; 11-> 'OFF'"
//
//            block.addField(
//                Field.builder().id("Currently running: First prioritised demand").type(ReturnType.STRING).source(
//                    "enum ( ir:1;$Star1Mapping )"
//                ).description("Currently running: First prioritised demand").build()
//            )
//            block.addField(
//                Field.builder().id("Currently running demands").type(ReturnType.STRINGLIST).source(
//                    "bitset( ir:2 ; $Star6Mapping )"
//                ).description("Currently running: Bit registers that shows the all the current running demands").build()
//            )
//            block.addField(
//                Field.builder().id("Compressor available gears").type(ReturnType.DOUBLE)
//                    .source("int16( ir:4 )/100").description("Compressor available gears").build()
//            )
//            block.addField(
//                Field.builder().id("Compressor speed").unit("rpm").type(ReturnType.LONG).source("int16( ir:5 )")
//                    .description("Compressor speed").build()
//            )
//            block.addField(
//                Field.builder().id("External additional heater: Current demand (%)").unit("%")
//                    .type(ReturnType.DOUBLE).source("int16( ir:6 )/100")
//                    .description("External additional heater: Current demand (%)").build()
//            )
//            block.addField(
//                Field.builder().id("Discharge pipe temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:7   ; 0x4E20 )/100").description("Discharge pipe temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Condenser in temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:8   ; 0x4E20 )/100").description("Condenser in temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Condenser out temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:9   ; 0x4E20 )/100").description("Condenser out temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Brine in temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:10  ; 0x4E20  )/100").description("Brine in temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Brine out temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:11  ; 0x4E20  )/100").description("Brine out temperature").build()
//            )
//            block.addField(
//                Field.builder().id("System supply line temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:12  ; 0x4E20  )/100").description("System supply line temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Outdoor temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:13  ; 0x4E20  )/100").description("Outdoor temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Tap water top temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:15  ; 0x4E20  )/100").description("Tap water top temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Tap water lower temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:16  ; 0x4E20  )/100").description("Tap water lower temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Tap water weighted temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:17  ; 0x4E20  )/100").description("Tap water weighted temperature").build()
//            )
//            block.addField(
//                Field.builder().id("System supply line calculated set point").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:18  ; 0x4E20)/100").description("System supply line calculated set point")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Selected heat curve, (system) supply line").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:19  ; 0x4E20)/100").description("Selected heat curve, (system) supply line")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Heat curve, X-coordinate 1 (highest outdoor temperature)").unit("°C").type(
//                    ReturnType.DOUBLE
//                ).source("int16( ir:20  ; 0x4E20)/100")
//                    .description("Heat curve, X-coordinate 1 (highest outdoor temperature)").build()
//            )
//            block.addField(
//                Field.builder().id("Heat curve, X-coordinate 2").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:21  ; 0x4E20 )/100").description("Heat curve, X-coordinate 2").build()
//            )
//            block.addField(
//                Field.builder().id("Heat curve, X-coordinate 3").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:22  ; 0x4E20 )/100").description("Heat curve, X-coordinate 3").build()
//            )
//            block.addField(
//                Field.builder().id("Heat curve, X-coordinate 4").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:23  ; 0x4E20 )/100").description("Heat curve, X-coordinate 4").build()
//            )
//            block.addField(
//                Field.builder().id("Heat curve, X-coordinate 5").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:24  ; 0x4E20 )/100").description("Heat curve, X-coordinate 5").build()
//            )
//            block.addField(
//                Field.builder().id("Heat curve, X-coordinate 6").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:25  ; 0x4E20 )/100").description("Heat curve, X-coordinate 6").build()
//            )
//            block.addField(
//                Field.builder().id("Heat curve, X-coordinate 7 (lowest outdoor temperature)").unit("°C").type(
//                    ReturnType.DOUBLE
//                ).source("int16( ir:26  ; 0x4E20)/100")
//                    .description("Heat curve, X-coordinate 7 (lowest outdoor temperature)").build()
//            )
//            block.addField(
//                Field.builder().id("System return line temperature.").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:27  ; 0x4E20)/100").description("System return line temperature.").build()
//            )
//            block.addField(
//                Field.builder().id("Calculated demand (heat)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:30 )/100").description("Calculated demand (heat)").build()
//            )
//            block.addField(
//                Field.builder().id("Cooling season integral value").type(ReturnType.LONG).source("int16( ir:36 )")
//                    .description("Cooling season integral value").build()
//            )
//            block.addField(
//                Field.builder().id("Condenser circulation pump speed (%)").unit("%").type(ReturnType.DOUBLE)
//                    .source("int16( ir:39 )/100").description("Condenser circulation pump speed (%)").build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 1 supply line temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:40  ; 0x4E20)/100").description("Mix valve 1 supply line temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Buffer tank temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:41  ; 0x4E20)/100").description("Buffer tank temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 1 position").unit("%").type(ReturnType.DOUBLE)
//                    .source("int16( ir:43 )/100").description("Mix valve 1 position").build()
//            )
//            block.addField(
//                Field.builder().id("Brine circulation pump speed (%)").unit("%").type(ReturnType.DOUBLE)
//                    .source("int16( ir:44 )/100").description("Brine circulation pump speed (%)").build()
//            )
//            block.addField(
//                Field.builder().id("Tap water valve position (%)").unit("%").type(ReturnType.LONG)
//                    .source("int16( ir:47 )").description("Tap water valve position (%)").build()
//            )
//
//            block.addField(
//                Field.builder().id("Compressor operating hours").unit("h").type(ReturnType.LONG)
//                    .source("uint32( ir:48 ir:49 )").description("Compressor operating hours").build()
//            )
//            block.addField(
//                Field.builder().id("Tap water operating hours").unit("h").type(ReturnType.LONG)
//                    .source("uint32( ir:50 ir:51)").description("Tap water operating hours").build()
//            )
//            block.addField(
//                Field.builder().id("External additional heater operating hours").unit("h").type(ReturnType.LONG)
//                    .source("uint32( ir:52 ir:53)").description("External additional heater operating hours").build()
//            )
//
//            block.addField(
//                Field.builder().id("Compressor speed percent").unit("%").type(ReturnType.DOUBLE)
//                    .source("int16( ir:54 )/100").description("Compressor speed percent").build()
//            )
//            block.addField(
//                Field.builder().id("Currently running: Second prioritised demand").type(ReturnType.STRING).source(
//                    "enum( ir:55 ;$Star1Mapping)"
//                ).description("Currently running: Second prioritised demand").build()
//            )
//            block.addField(
//                Field.builder().id("Currently running: Third prioritised demand").type(ReturnType.STRING).source(
//                    "enum( ir:56 ;$Star1Mapping)"
//                ).description("Currently running: Third prioritised demand").build()
//            )
//
//            block.addField(
//                Field.builder().id("Software version: Major").type(ReturnType.LONG).source("int16( 3x00058 )")
//                    .description("Software version: Major").build()
//            )
//            block.addField(
//                Field.builder().id("Software version: Minor").type(ReturnType.LONG).source("int16( 3x00059 )")
//                    .description("Software version: Minor").build()
//            )
//            block.addField(
//                Field.builder().id("Software version: Micro").type(ReturnType.LONG).source("int16( 3x00060 )")
//                    .description("Software version: Micro").build()
//            )
//
//            block.addField(
//                Field.builder().id("Compressor temporarily blocked, (start restriction timer)")
//                    .type(ReturnType.LONG).source("int16( ir:60 )")
//                    .description("Compressor temporarily blocked, (start restriction timer)").build()
//            )
//            block.addField(
//                Field.builder().id("Compressor current gear").type(ReturnType.DOUBLE).source("int16( ir:61 )/100")
//                    .description("Compressor current gear").build()
//            )
//            block.addField(
//                Field.builder().id("Queued demand, first priority").type(ReturnType.STRING).source(
//                    "enum( ir:62 ;$Star1Mapping )"
//                ).description("Queued demand, first priority").build()
//            )
//            block.addField(
//                Field.builder().id("Queued demand, second priority").type(ReturnType.STRING).source(
//                    "enum( ir:63 ;$Star1Mapping )"
//                ).description("Queued demand, second priority").build()
//            )
//            block.addField(
//                Field.builder().id("Queued demand, third priority").type(ReturnType.STRING).source(
//                    "enum( ir:64 ;$Star1Mapping )"
//                ).description("Queued demand, third priority").build()
//            )
//            block.addField(
//                Field.builder().id("Queued demand, fourth priority").type(ReturnType.STRING).source(
//                    "enum( ir:65 ;$Star1Mapping )"
//                ).description("Queued demand, fourth priority").build()
//            )
//            block.addField(
//                Field.builder().id("Queued demand, fifth priority").type(ReturnType.STRING).source(
//                    "enum( ir:66 ;$Star1Mapping )"
//                ).description("Queued demand, fifth priority").build()
//            )
//            block.addField(
//                Field.builder().id("Active step internal immersion heater").unit("step").type(ReturnType.LONG)
//                    .source("int16( ir:67  )").description("Active step internal immersion heater").build()
//            )
//            block.addField(
//                Field.builder().id("Buffer tank charge set point").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:68 )/100").description("Buffer tank charge set point").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L1 current (A)").unit("A").type(ReturnType.DOUBLE)
//                    .source("int16( ir:69 )/100").description("Electric meter L1 current (A)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L2 current (A)").unit("A").type(ReturnType.DOUBLE)
//                    .source("int16( ir:70 )/100").description("Electric meter L2 current (A)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L3 current (A)").unit("A").type(ReturnType.DOUBLE)
//                    .source("int16( ir:71 )/100").description("Electric meter L3 current (A)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L1-0 voltage (V)").unit("V").type(ReturnType.DOUBLE)
//                    .source("int16( ir:72 )/100").description("Electric meter L1-0 voltage (V)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L2-0 voltage (V)").unit("V").type(ReturnType.DOUBLE)
//                    .source("int16( ir:73 )/100").description("Electric meter L2-0 voltage (V)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L3-0 voltage (V)").unit("V").type(ReturnType.DOUBLE)
//                    .source("int16( ir:74 )/100").description("Electric meter L3-0 voltage (V)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L1-L2 voltage (V)").unit("V").type(ReturnType.DOUBLE)
//                    .source("int16( ir:75 )/10").description("Electric meter L1-L2 voltage (V)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L2-L3 voltage (V)").unit("V").type(ReturnType.DOUBLE)
//                    .source("int16( ir:76 )/10").description("Electric meter L2-L3 voltage (V)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L3-L1 voltage (V)").unit("V").type(ReturnType.DOUBLE)
//                    .source("int16( ir:77 )/10").description("Electric meter L3-L1 voltage (V)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L1 power (W)").unit("W").type(ReturnType.LONG)
//                    .source("int16( ir:78 )").description("Electric meter L1 power (W)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L2 power (W)").unit("W").type(ReturnType.LONG)
//                    .source("int16( ir:79 )").description("Electric meter L2 power (W)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter L3 power (W)").unit("W").type(ReturnType.LONG)
//                    .source("int16( ir:80 )").description("Electric meter L3 power (W)").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter - meter value (kWh)").unit("kWh").type(ReturnType.LONG)
//                    .source("int16( ir:81 )").description("Electric meter - meter value (kWh)").build()
//            )
//            block.addField(
//                Field.builder().id("Current Smart Grid mode").type(ReturnType.STRING).source(
//                    "enum( ir:82;$Star4Mapping )"
//                ).description("Current Smart Grid mode").build()
//            )
//            block.addField(
//                Field.builder().id("Electric meter kWh total").unit("kWh").type(ReturnType.DOUBLE)
//                    .source("uint32( ir:84 ir:83)/10").description("Electric meter kWh total").build()
//            )
//
//            block.addField(
//                Field.builder().id("WCS valve position (EM)").type(ReturnType.DOUBLE).source("int16( ir:85 )/100")
//                    .description("WCS valve position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("TWC valve position (EM)").type(ReturnType.DOUBLE).source("int16( ir:86 )/100")
//                    .description("TWC valve position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 2 position (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:87 )/100").description("Mix valve 2 position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 3 position (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:88 )/100").description("Mix valve 3 position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 4 position (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:89 )/100").description("Mix valve 4 position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 5 position (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:90 )/100").description("Mix valve 5 position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Dew point room (EM)").type(ReturnType.DOUBLE).source("int16( ir:91 )/100")
//                    .description("Dew point room (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Cooling supply line mix valve position (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:92 )/100").description("Cooling supply line mix valve position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Surplus heat fan speed (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:93 )/100").description("Surplus heat fan speed (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Pool supply line mix valve position (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:94 )/100").description("Pool supply line mix valve position (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("TWC supply line temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:95 ; 0x4E20 )/100").description("TWC supply line temperature (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("TWC return temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:96 ; 0x4E20 )/100").description("TWC return temperature (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("WCS return line temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:97 ; 0x4E20 )/100").description("WCS return line temperature (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("TWC end tank temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:98 ; 0x4E20 )/100").description("TWC end tank temperature (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 2 supply line temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:99 ; 0x4E20 )/100").description("Mix valve 2 supply line temperature (EM)")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 3 supply line temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:100 ; 0x4E20 )/100").description("Mix valve 3 supply line temperature (EM)")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Mix valve 4 supply line temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:101 ; 0x4E20 )/100").description("Mix valve 4 supply line temperature (EM)")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Cooling circuit return line temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:102 ; 0x4E20 )/100").description("Cooling circuit return line temperature (EM)")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Cooling tank temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:104 ; 0x4E20 )/100").description("Cooling tank temperature (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Cooling tank return line temperature (EM)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:105 ; 0x4E20 )/100").description("Cooling tank return line temperature (EM)")
//                    .build()
//            )
//
//
//            block.addField(
//                Field.builder().id("Cooling circuit supply line temperature (EM)").unit("°C")
//                    .type(ReturnType.DOUBLE).source("int16( ir:106 ; 0x4E20)/100")
//                    .description("Cooling circuit supply line temperature (EM)").build()
//            )
//            block.addField(
//                Field.builder().id("Room temperature sensor").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:121; 0x4E20 )/10").description("Room temperature sensor").build()
//            )
//            block.addField(
//                Field.builder().id("Bubble point, high pressure temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:122; 0x4E20 )/100").description("Bubble point, high pressure temperature")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Dew point, high pressure temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:123; 0x4E20 )/100").description("Dew point, high pressure temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Dew point, low pressure temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:124; 0x4E20 )/100").description("Dew point, low pressure temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Superheat temperature").unit("K").type(ReturnType.DOUBLE)
//                    .source("int16( ir:125; 0x4E20 )/100").description("Superheat temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Sub cooling temperature").unit("K").type(ReturnType.DOUBLE)
//                    .source("int16( ir:126 ; 0x4E20)/100").description("Sub cooling temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Low pressure side, pressure (bar(g))").unit("bar(g)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:127 )/100").description("Low pressure side, pressure (bar(g))").build()
//            )
//            block.addField(
//                Field.builder().id("High pressure side, pressure (bar(g))").unit("bar(g)").type(ReturnType.DOUBLE)
//                    .source("int16( ir:128)/100").description("High pressure side, pressure (bar(g))").build()
//            )
//            block.addField(
//                Field.builder().id("Liquid line temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:129; 0x4E20 )/100").description("Liquid line temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Suction gas temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:130; 0x4E20 )/100").description("Suction gas temperature").build()
//            )
//            block.addField(
//                Field.builder().id("Heating season integral value").type(ReturnType.LONG)
//                    .source("int16( ir:131 )").description("Heating season integral value").build()
//            )
//
//            block.addField(
//                Field.builder().id("Field 132").type(ReturnType.DOUBLE).source("int16( 3x00133 )/100")
//                    .description("P - value for gear shifting and demand calculation").build()
//            )
//            block.addField(
//                Field.builder().id("Field 133").type(ReturnType.DOUBLE).source("int16( 3x00134 )/100")
//                    .description("I - value for gear shifting and demand calculation").build()
//            )
//            block.addField(
//                Field.builder().id("Field 134").type(ReturnType.DOUBLE).source("int16( 3x00135 )/100")
//                    .description("D - value for gear shifting and demand calculation").build()
//            )
//            block.addField(
//                Field.builder().id("Field 135").type(ReturnType.DOUBLE).source("int16( 3x00136 )/100")
//                    .description("I - value for compressor ON/OFF (Buffer tank)").build()
//            )
//            block.addField(
//                Field.builder().id("Field 136").type(ReturnType.DOUBLE).source("int16( 3x00137 )/100")
//                    .description("P - value for compressor ON/OFF (Buffer tank)").build()
//            )
//
//            block.addField(
//                Field.builder().id("Mix valve cooling opening degree (EM2/3)").unit("%").type(ReturnType.LONG)
//                    .source("int16( ir:137 )").description("Mix valve cooling opening degree (EM2/3)").build()
//            )
//            block.addField(
//                Field.builder().id("Desired gear for tap water").type(ReturnType.LONG).source("int16( ir:139 )")
//                    .description("Desired gear for tap water").build()
//            )
//            block.addField(
//                Field.builder().id("Desired gear for heating").type(ReturnType.LONG).source("int16( ir:140 )")
//                    .description("Desired gear for heating").build()
//            )
//            block.addField(
//                Field.builder().id("Desired gear for cooling").type(ReturnType.LONG).source("int16( ir:141 )")
//                    .description("Desired gear for cooling").build()
//            )
//            block.addField(
//                Field.builder().id("Desired gear for pool").type(ReturnType.LONG).source("int16( ir:142 )")
//                    .description("Desired gear for pool").build()
//            )
//            block.addField(
//                Field.builder().id("Number of available secondaries Genesis").type(ReturnType.LONG)
//                    .source("int16( ir:143 )").description("Number of available secondaries Genesis").build()
//            )
//            block.addField(
//                Field.builder().id("Total distributed gears to all units").type(ReturnType.LONG)
//                    .source("int16( ir:145 )").description("Total distributed gears to all units").build()
//            )
//            block.addField(
//                Field.builder().id("Maximum gear out of all the currently requested gears").type(ReturnType.LONG)
//                    .source("int16( ir:146 )").description("Maximum gear out of all the currently requested gears")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Desired temperature distribution circuit Mix valve 1").unit("°C")
//                    .type(ReturnType.DOUBLE).source("int16( ir:147 )/100")
//                    .description("Desired temperature distribution circuit Mix valve 1").build()
//            )
//
//            block.addField(
//                Field.builder().id("Desired temperature distribution circuit Mix valve 2").type(ReturnType.DOUBLE)
//                    .source("int16( 3x00149 )/100").description("Desired temperature distribution circuit Mix valve 2")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Desired temperature distribution circuit Mix valve 3").type(ReturnType.DOUBLE)
//                    .source("int16( 3x00150 )/100").description("Desired temperature distribution circuit Mix valve 3")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Desired temperature distribution circuit Mix valve 4").type(ReturnType.DOUBLE)
//                    .source("int16( 3x00151 )/100").description("Desired temperature distribution circuit Mix valve 4")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Desired temperature distribution circuit Mix valve 5").type(ReturnType.DOUBLE)
//                    .source("int16( 3x00152 )/100").description("Desired temperature distribution circuit Mix valve 5")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Disconnect hot gas end tank").type(ReturnType.STRINGLIST)
//                    .source("bitset( 3x00153; 0 -> 'connected'; 1 -> 'disconnected')")
//                    .description("Disconnect hot gas end tank, 0 = connected, 1 = disconnected").build()
//            )
//
//
//            val legacyBitSet =
//                "0 -> 'Heat pump 0';" +
//                        "1 -> 'Heat pump 1';" +
//                        "2 -> 'Heat pump 2';" +
//                        "3 -> 'Heat pump 3';" +
//                        "4 -> 'Heat pump 4';" +
//                        "5 -> 'Heat pump 5';" +
//                        "6 -> 'Heat pump 6';" +
//                        "7 -> 'Heat pump 7';" +
//                        "8 -> 'Heat pump 8';" +
//                        "9 -> 'Heat pump 9';" +
//                        "10 -> 'Heat pump 10';" +
//                        "11 -> 'Heat pump 11';" +
//                        "12 -> 'Heat pump 12';" +
//                        "13 -> 'Heat pump 13';" +
//                        "14 -> 'Heat pump 14';" +
//                        "15 -> 'Heat pump 15'"
//
//            block.addField(
//                Field.builder().id("Legacy heat pump compressor running (bit field)").type(ReturnType.STRINGLIST)
//                    .source(
//                        "bitset( 3x00154; $legacyBitSet )"
//                    ).description("Legacy heat pump compressor running (bit field)").build()
//            )
//            block.addField(
//                Field.builder().id("Legacy heat pump reporting alarm (bit field)").type(ReturnType.STRINGLIST)
//                    .source(
//                        "bitset( 3x00155; $legacyBitSet )"
//                    ).description("Legacy heat pump reporting alarm (bit field)").build()
//            )
//            block.addField(
//                Field.builder().id("Legacy heat pump start signal (bit field)").type(ReturnType.STRINGLIST)
//                    .source(
//                        "bitset( 3x00156; $legacyBitSet )"
//                    ).description("Legacy heat pump start signal (bit field)").build()
//            )
//            block.addField(
//                Field.builder().id("Legacy heat pump tap water signal (bit field)").type(ReturnType.STRINGLIST)
//                    .source(
//                        "bitset( 3x00157; $legacyBitSet )"
//                    ).description("Legacy heat pump tap water signal (bit field)").build()
//            )
//
//            block.addField(
//                Field.builder().id("Primary unit alarm - Class D alarm on secondary heat pump unit.").unit("bitfield")
//                    .type(
//                        ReturnType.LONG
//                    ).source("int16( ir:160 )")
//                    .description("Primary unit alarm - the combined output of all Class D alarms. This signal is a bit field, one bit for each secondary heat pump unit.")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Primary unit alarm - lost communication with secondaries.").unit("bitfield").type(
//                    ReturnType.LONG
//                ).source("int16( ir:161 )")
//                    .description("Primary unit alarm - the primary unit has lost communication with one or more Genesis secondaries. This signal is a bit field, one bit for each heat pump.")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Primary unit alarm - Class A alarm on secondary heat pump unit.").unit("bitfield")
//                    .type(
//                        ReturnType.LONG
//                    ).source("int16( ir:162 )")
//                    .description("Primary unit alarm - Class A alarm detected on the Genesis secondary heat pump unit. This signal is a bit field, one bit for each secondary heat pump unit.")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Primary unit alarm - Class B alarm on secondary heat pump unit.").unit("bitfield")
//                    .type(
//                        ReturnType.LONG
//                    ).source("int16( ir:163 )")
//                    .description("Primary unit alarm - Class B alarm detected on the Genesis secondary heat pump unit. This signal is a bit field, one bit for each secondary heat pump unit.")
//                    .build()
//            )
//
//            block.addField(
//                Field.builder().id("Field 170").type(ReturnType.STRINGLIST).source(
//                    "bitset( 3x00171 ; $legacyBitSet)"
//                )
//                    .description("Primary unit alarm - the combined output of all Class E alarms. This signal is a bit field, one bit for each legacy secondary heat pump unit.")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Field 171").type(ReturnType.STRINGLIST).source(
//                    "bitset( 3x00172 ; $legacyBitSet)"
//                )
//                    .description("Primary unit alarm - general legacy heat pump alarm. This signal is a bit field, one bit for each legacy secondary heat pump unit. Detects if the sum alarm of the secondary unit is active.")
//                    .build()
//            )
//            block.addField(
//                Field.builder().id("Field 173").type(ReturnType.STRINGLIST).source(
//                    "bitset( 3x00174 ; $legacyBitSet)"
//                )
//                    .description("Primary unit alarm - the primary unit can not communicate with the corresponding expansion card for the legacy heat pump. This signal is a bit field, one bit for each legacy secondary heat pump unit.")
//                    .build()
//            )
//
//            block.addField(
//                Field.builder().id("Control software version: Major").type(ReturnType.LONG)
//                    .source("int16( ir:311 )").description("Control software version: Major").build()
//            )
//            block.addField(
//                Field.builder().id("Control software version: Minor").type(ReturnType.LONG)
//                    .source("int16( ir:312 )").description("Control software version: Minor").build()
//            )
//            block.addField(
//                Field.builder().id("Control software version: Micro").type(ReturnType.LONG)
//                    .source("int16( ir:313 )").description("Control software version: Micro").build()
//            )
//            block.addField(
//                Field.builder().id("Expansion valve opening degree").unit("%").type(ReturnType.DOUBLE)
//                    .source("int16( ir:315 )/100").description("Expansion valve opening degree").build()
//            )
//            block.addField(
//                Field.builder().id("Inverter temperature").unit("°C").type(ReturnType.DOUBLE)
//                    .source("int16( ir:319  ; 0x4E20)").description("Inverter temperature").build()
//            )
//
//
//            //            block.addField(Field.builder().id("Field 1"  ).type(STRING).source("enum ( 3x00002;"+Star1Mapping+" )").description("Currently running: First prioritised demand").build());
////        block.addField(Field.builder().id("Field 4"  ).type(DOUBLE).source("int16( 3x00005 )/100").description("Compressor available gears").build());
////        block.addField(Field.builder().id("Field 5"  ).type(DOUBLE).source("int16( 3x00006 )").description("Compressor speed RPM").build());
////        block.addField(Field.builder().id("Field 6"  ).type(DOUBLE).source("int16( 3x00007 )/100").description("External additional heater: Current demand (%)").build());
////        block.addField(Field.builder().id("Field 7"  ).type(DOUBLE).source("int16( 3x00008 ; 0x4E20)/100").description("Discharge pipe temperature").build());
////        block.addField(Field.builder().id("Field 8"  ).type(DOUBLE).source("int16( 3x00009 ; 0x4E20)/100").description("Condenser in temperature").build());
////        block.addField(Field.builder().id("Field 9"  ).type(DOUBLE).source("int16( 3x00010 ; 0x4E20)/100").description("Condenser out temperature").build());
////        block.addField(Field.builder().id("Field 10" ).type(DOUBLE).source("int16( 3x00011 ; 0x4E20)/100").description("Brine in temperature").build());
////        block.addField(Field.builder().id("Field 11" ).type(DOUBLE).source("int16( 3x00012 ; 0x4E20)/100").description("Brine out temperature").build());
////        block.addField(Field.builder().id("Field 12" ).type(DOUBLE).source("int16( 3x00013 ; 0x4E20)/100").description("System supply line temperature").build());
////        block.addField(Field.builder().id("Field 13" ).type(DOUBLE).source("int16( 3x00014 ; 0x4E20)/100").description("Outdoor temperature").build());
////        block.addField(Field.builder().id("Field 15" ).type(DOUBLE).source("int16( 3x00016 ; 0x4E20)/100").description("Tap water top temperature").build());
////        block.addField(Field.builder().id("Field 16" ).type(DOUBLE).source("int16( 3x00017 ; 0x4E20)/100").description("Tap water lower temperature").build());
////        block.addField(Field.builder().id("Field 17" ).type(DOUBLE).source("int16( 3x00018 ; 0x4E20)/100").description("Tap water weighted temperature").build());
////        block.addField(Field.builder().id("Field 18" ).type(DOUBLE).source("int16( 3x00019 )/100").description("System supply line calculated set point").build());
////        block.addField(Field.builder().id("Field 19" ).type(DOUBLE).source("int16( 3x00020 )/100").description("Selected heat curve, (system) supply line").build());
////        block.addField(Field.builder().id("Field 20" ).type(DOUBLE).source("int16( 3x00021 )/100").description("Heat curve, X-coordinate 1 (highest outdoor temperature)").build());
////        block.addField(Field.builder().id("Field 21" ).type(DOUBLE).source("int16( 3x00022 )/100").description("Heat curve, X-coordinate 2").build());
////        block.addField(Field.builder().id("Field 22" ).type(DOUBLE).source("int16( 3x00023 )/100").description("Heat curve, X-coordinate 3").build());
////        block.addField(Field.builder().id("Field 23" ).type(DOUBLE).source("int16( 3x00024 )/100").description("Heat curve, X-coordinate 4").build());
////        block.addField(Field.builder().id("Field 24" ).type(DOUBLE).source("int16( 3x00025 )/100").description("Heat curve, X-coordinate 5").build());
////        block.addField(Field.builder().id("Field 25" ).type(DOUBLE).source("int16( 3x00026 )/100").description("Heat curve, X-coordinate 6").build());
////        block.addField(Field.builder().id("Field 26" ).type(DOUBLE).source("int16( 3x00027 )/100").description("Heat curve, X-coordinate 7 (lowest outdoor temperature)").build());
////        block.addField(Field.builder().id("Field 36" ).type(DOUBLE).source("int16( 3x00037 )").description("Cooling season integral value").build());
////        block.addField(Field.builder().id("Field 39" ).type(DOUBLE).source("int16( 3x00040 )/100").description("Condenser circulation pump speed (%)").build());
////        block.addField(Field.builder().id("Field 40" ).type(DOUBLE).source("int16( 3x00041 ; 0x4E20)/100").description("Mix valve 1 supply line temperature").build());
////        block.addField(Field.builder().id("Field 41" ).type(DOUBLE).source("int16( 3x00042 ; 0x4E20)/100").description("Buffer tank temperature").build());
////        block.addField(Field.builder().id("Field 43" ).type(DOUBLE).source("int16( 3x00044 )/100").description("Mix valve 1 position").build());
////        block.addField(Field.builder().id("Field 44" ).type(DOUBLE).source("int16( 3x00045 )/100").description("Brine circulation pump speed (%)").build());
////        block.addField(Field.builder().id("Field 45" ).type(DOUBLE).source("int16( 3x00046 ; 0x4E20)/100").description("HGW supply line temperature").build());
////        block.addField(Field.builder().id("Field 47" ).type(DOUBLE).source("int16( 3x00048 )").description("Hot water directional valve position (%)").build());
////        // DONE: COMBINE LSB and MSB!
////        block.addField(Field.builder().id("Compressor operating hours" ).type(LONG).source("uint32( 3x00049 3x00050)").description("Compressor operating hours").build());
//////        block.addField(Field.builder().id("Field 48" ).type(DOUBLE).source("int16( 3x00049 )").description("Compressor operating hours (MSB)").build());
//////        block.addField(Field.builder().id("Field 49" ).type(DOUBLE).source("int16( 3x00050 )").description("Compressor operating hours (LSB)").build());
////        // DONE: COMBINE LSB and MSB!
////        block.addField(Field.builder().id("Tap water operating hours" ).type(LONG).source("uint32( 3x00051 3x00052)").description("Tap water operating hours").build());
//////        block.addField(Field.builder().id("Field 50" ).type(DOUBLE).source("int16( 3x00051 )").description("Tap water operating hours (MSB)").build());
//////        block.addField(Field.builder().id("Field 51" ).type(DOUBLE).source("int16( 3x00052 )").description("Tap water operating hours (LSB)").build());
////        // DONE: COMBINE LSB and MSB!
////        block.addField(Field.builder().id("External additional heater operating hours" ).type(LONG).source("uint32( 3x00053 3x00054)").description("External additional heater operating hours").build());
//////        block.addField(Field.builder().id("Field 52" ).type(DOUBLE).source("int16( 3x00053 )").description("External additional heater operating hours (MSB)").build());
//////        block.addField(Field.builder().id("Field 53" ).type(DOUBLE).source("int16( 3x00054 )").description("External additional heater operating hours (LSB)").build());
////        block.addField(Field.builder().id("Field 54" ).type(DOUBLE).source("int16( 3x00055 )/100").description("Compressor speed percent").build());
////        block.addField(Field.builder().id("Field 55" ).type(STRING).source("enum( 3x00056;"+Star1Mapping+" )").description("Currently running: Second prioritised demand *1").build());
////        block.addField(Field.builder().id("Field 56" ).type(STRING).source("enum( 3x00057;"+Star1Mapping+" )").description("Currently running: Third prioritised demand *1").build());
////        block.addField(Field.builder().id("Field 57" ).type(DOUBLE).source("int16( 3x00058 )").description("Software version: Major").build());
////        block.addField(Field.builder().id("Field 58" ).type(DOUBLE).source("int16( 3x00059 )").description("Software version: Minor").build());
////        block.addField(Field.builder().id("Field 59" ).type(DOUBLE).source("int16( 3x00060 )").description("Software version: Micro").build());
////        block.addField(Field.builder().id("Field 60" ).type(DOUBLE).source("int16( 3x00061 )").description("Compressor temporarily blocked, (start restriction timer)").build());
////        block.addField(Field.builder().id("Field 61" ).type(DOUBLE).source("int16( 3x00062 )/100").description("Compressor current gear").build());
////        block.addField(Field.builder().id("Field 62" ).type(STRING).source("enum(  3x00063 ;"+Star1Mapping+" )").description("Queued demand, first priority *1").build());
////        block.addField(Field.builder().id("Field 63" ).type(STRING).source("enum(  3x00064 ;"+Star1Mapping+" )").description("Queued demand, second priority *1").build());
////        block.addField(Field.builder().id("Field 64" ).type(STRING).source("enum(  3x00065 ;"+Star1Mapping+" )").description("Queued demand, third priority *1").build());
////        block.addField(Field.builder().id("Field 65" ).type(STRING).source("enum(  3x00066 ;"+Star1Mapping+" )").description("Queued demand, fourth priority *1").build());
////        block.addField(Field.builder().id("Field 66" ).type(STRING).source("enum(  3x00067 ;"+Star1Mapping+" )").description("Queued demand, fifth priority *1").build());
////        block.addField(Field.builder().id("Field 67" ).type(DOUBLE).source("int16( 3x00068 )").description("Internal additional heater current step").build());
////        block.addField(Field.builder().id("Field 68" ).type(DOUBLE).source("int16( 3x00069 )/100").description("Buffer tank charge set point").build());
////        block.addField(Field.builder().id("Field 69" ).type(DOUBLE).source("int16( 3x00070 )/100").description("Electric meter L1 current (A)").build());
////        block.addField(Field.builder().id("Field 70" ).type(DOUBLE).source("int16( 3x00071 )/100").description("Electric meter L2 current (A)").build());
////        block.addField(Field.builder().id("Field 71" ).type(DOUBLE).source("int16( 3x00072 )/100").description("Electric meter L3 current (A)").build());
////        block.addField(Field.builder().id("Field 72" ).type(DOUBLE).source("int16( 3x00073 )/100").description("Electric meter L1-0 voltage (V)").build());
////        block.addField(Field.builder().id("Field 73" ).type(DOUBLE).source("int16( 3x00074 )/100").description("Electric meter L2-0 voltage (V)").build());
////        block.addField(Field.builder().id("Field 74" ).type(DOUBLE).source("int16( 3x00075 )/100").description("Electric meter L3-0 voltage (V)").build());
////        block.addField(Field.builder().id("Field 75" ).type(DOUBLE).source("int16( 3x00076 )/10").description("Electric meter L1-L2 voltage (V)").build());
////        block.addField(Field.builder().id("Field 76" ).type(DOUBLE).source("int16( 3x00077 )/10").description("Electric meter L2-L3 voltage (V)").build());
////        block.addField(Field.builder().id("Field 77" ).type(DOUBLE).source("int16( 3x00078 )/10").description("Electric meter L3-L1 voltage (V)").build());
////        block.addField(Field.builder().id("Field 78" ).type(DOUBLE).source("int16( 3x00079 )").description("Electric meter L1 power (W)").build());
////        block.addField(Field.builder().id("Field 79" ).type(DOUBLE).source("int16( 3x00080 )").description("Electric meter L2 power (W)").build());
////        block.addField(Field.builder().id("Field 80" ).type(DOUBLE).source("int16( 3x00081 )").description("Electric meter L3 power (W)").build());
////        block.addField(Field.builder().id("Field 81" ).type(DOUBLE).source("int16( 3x00082 )").description("Electric meter - meter value (kWh)").build());
////        block.addField(Field.builder().id("Field 82" ).type(DOUBLE).source("enum( 3x00083 ;"+Star4Mapping+")").description("Comfort mode *4").build());
////
////        // DONE: COMBINE LSB and MSB!
////        block.addField(Field.builder().id("Electric meter kWh total" ).type(DOUBLE).source("uint32( 3x00085 3x00084)/10").description("Electric meter kWh total").build());
//////        block.addField(Field.builder().id("Field 83" ).type(DOUBLE).source("int16( 3x00084 )/10").description("Electric meter kWh total (LSB)").build());
//////        block.addField(Field.builder().id("Field 84" ).type(DOUBLE).source("int16( 3x00085 )/10").description("Electric meter kWh total (MSB)").build());
////
////        block.addField(Field.builder().id("Field 106").type(DOUBLE).source("int16( 3x00107 ; 0x4E20 )/100").description("Cooling circuit supply line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 107").type(DOUBLE).source("int16( 3x00108 ; 0x4E20 )/100").description("Mix valve 5 supply line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 109").type(DOUBLE).source("int16( 3x00110 ; 0x4E20 )/100").description("Mix valve 2 return line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 111").type(DOUBLE).source("int16( 3x00112 ; 0x4E20 )/100").description("Mix valve 3 return line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 113").type(DOUBLE).source("int16( 3x00114 ; 0x4E20 )/100").description("Mix valve 4 return line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 115").type(DOUBLE).source("int16( 3x00116 ; 0x4E20 )/100").description("Mix valve 5 return line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 117").type(DOUBLE).source("int16( 3x00118 ; 0x4E20 )/100").description("Surplus heat return line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 118").type(DOUBLE).source("int16( 3x00119 ; 0x4E20 )/100").description("Surplus heat supply line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 119").type(DOUBLE).source("int16( 3x00120 ; 0x4E20 )/100").description("Pool supply line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 120").type(DOUBLE).source("int16( 3x00121 ; 0x4E20 )/100").description("Pool return line temperature (EM)").build());
////        block.addField(Field.builder().id("Field 121").type(DOUBLE).source("int16( 3x00122 ; 0x4E20 )/10").description("Room temperature sensor").build());
////        block.addField(Field.builder().id("Field 122").type(DOUBLE).source("int16( 3x00123 ; 0x4E20 )/100").description("Bubble point, high pressure temperature").build());
////        block.addField(Field.builder().id("Field 123").type(DOUBLE).source("int16( 3x00124 ; 0x4E20 )/100").description("Dew point, high pressure temperature").build());
////        block.addField(Field.builder().id("Field 124").type(DOUBLE).source("int16( 3x00125 ; 0x4E20 )/100").description("Dew point, low pressure temperature").build());
////        block.addField(Field.builder().id("Field 125").type(DOUBLE).source("int16( 3x00126 ; 0x4E20 )/100").description("Superheat temperature").build());
////        block.addField(Field.builder().id("Field 126").type(DOUBLE).source("int16( 3x00127 ; 0x4E20 )/100").description("Sub cooling temperature").build());
////        block.addField(Field.builder().id("Field 127").type(DOUBLE).source("int16( 3x00128 )/100").description("Low pressure side, pressure (bar(g))").build());
////        block.addField(Field.builder().id("Field 128").type(DOUBLE).source("int16( 3x00129 )/100").description("High pressure side, pressure (bar(g))").build());
////        block.addField(Field.builder().id("Field 129").type(DOUBLE).source("int16( 3x00130 ; 0x4E20)/100").description("Liquid line temperature").build());
////        block.addField(Field.builder().id("Field 130").type(DOUBLE).source("int16( 3x00131 ; 0x4E20)/100").description("Suction gas temperature").build());
////        block.addField(Field.builder().id("Field 131").type(DOUBLE).source("int16( 3x00132 )").description("Heating season integral value").build());
////        block.addField(Field.builder().id("Field 132").type(DOUBLE).source("int16( 3x00133 )/100").description("P - value for gear shifting and demand calculation").build());
////        block.addField(Field.builder().id("Field 133").type(DOUBLE).source("int16( 3x00134 )/100").description("I - value for gear shifting and demand calculation").build());
////        block.addField(Field.builder().id("Field 134").type(DOUBLE).source("int16( 3x00135 )/100").description("D - value for gear shifting and demand calculation").build());
////        block.addField(Field.builder().id("Field 135").type(DOUBLE).source("int16( 3x00136 )/100").description("I - value for compressor ON/OFF (Buffer tank)").build());
////        block.addField(Field.builder().id("Field 136").type(DOUBLE).source("int16( 3x00137 )/100").description("P - value for compressor ON/OFF (Buffer tank)").build());
////        block.addField(Field.builder().id("Field 137").type(DOUBLE).source("int16( 3x00138 )").description("Mix valve cooling opening degree (EM2/3)").build());
////        block.addField(Field.builder().id("Field 139").type(DOUBLE).source("int16( 3x00140 )").description("Desired gear for tap water").build());
////        block.addField(Field.builder().id("Field 140").type(DOUBLE).source("int16( 3x00141 )").description("Desired gear for heating").build());
////        block.addField(Field.builder().id("Field 141").type(DOUBLE).source("int16( 3x00142 )").description("Desired gear for cooling").build());
////        block.addField(Field.builder().id("Field 142").type(DOUBLE).source("int16( 3x00143 )").description("Desired gear for pool").build());
////        block.addField(Field.builder().id("Field 143").type(DOUBLE).source("int16( 3x00144 )").description("Number of available secondaries Genesis").build());
////        block.addField(Field.builder().id("Field 144").type(DOUBLE).source("int16( 3x00145 )").description("Number of available secondaries Legacy").build());
////        block.addField(Field.builder().id("Field 145").type(DOUBLE).source("int16( 3x00146 )").description("Total distributed gears to all units").build());
////        block.addField(Field.builder().id("Field 146").type(DOUBLE).source("int16( 3x00147 )").description("Maximum gear out of all the currently requested gears").build());
////        block.addField(Field.builder().id("Field 147").type(DOUBLE).source("int16( 3x00148 )/100").description("Desired temperature distribution circuit Mix valve 1").build());
////        block.addField(Field.builder().id("Field 148").type(DOUBLE).source("int16( 3x00149 )/100").description("Desired temperature distribution circuit Mix valve 2").build());
////        block.addField(Field.builder().id("Field 149").type(DOUBLE).source("int16( 3x00150 )/100").description("Desired temperature distribution circuit Mix valve 3").build());
////        block.addField(Field.builder().id("Field 150").type(DOUBLE).source("int16( 3x00151 )/100").description("Desired temperature distribution circuit Mix valve 4").build());
////        block.addField(Field.builder().id("Field 151").type(DOUBLE).source("int16( 3x00152 )/100").description("Desired temperature distribution circuit Mix valve 5").build());
////        block.addField(Field.builder().id("Field 152").type(DOUBLE).source("int16( 3x00153 )").description("Disconnect hot gas end tank, 0 = connected, 1 = disconnected").build());
////        block.addField(Field.builder().id("Field 153").type(DOUBLE).source("int16( 3x00154 )").description("Legacy heat pump compressor running (bit field)").build());
////        block.addField(Field.builder().id("Field 154").type(DOUBLE).source("int16( 3x00155 )").description("Legacy heat pump reporting alarm (bit field)").build());
////        block.addField(Field.builder().id("Field 155").type(DOUBLE).source("int16( 3x00156 )").description("Legacy heat pump start signal (bit field)").build());
////        block.addField(Field.builder().id("Field 156").type(DOUBLE).source("int16( 3x00157 )").description("Legacy heat pump tap water signal (bit field)").build());
//
////        block.addField(Field.builder().id("Field 160").type(DOUBLE).source("int16( 3x00161 )").description("Primary unit alarm - the combined output of all Class D alarms. This signal is a bit field, one bit for each secondary heat pump unit.").build());
////        block.addField(Field.builder().id("Field 161").type(DOUBLE).source("int16( 3x00162 )").description("Primary unit alarm - the primary unit has lost communication with one or more Genesis secondaries. This signal is a bit field, one bit for each heat pump.").build());
////        block.addField(Field.builder().id("Field 162").type(DOUBLE).source("int16( 3x00163 )").description("Primary unit alarm - Class A alarm detected on the Genesis secondary heat pump unit. This signal is a bit field, one bit for each secondary heat pump unit.").build());
////        block.addField(Field.builder().id("Field 163").type(DOUBLE).source("int16( 3x00164 )").description("Primary unit alarm - Class B alarm detected on the Genesis secondary heat pump unit. This signal is a bit field, one bit for each secondary heat pump unit.").build());
//
////        block.addField(Field.builder().id("Field 170").type(DOUBLE).source("int16( 3x00171 )").description("Primary unit alarm - the combined output of all Class E alarms. This signal is a bit field, one bit for each legacy secondary heat pump unit.").build());
////        block.addField(Field.builder().id("Field 171").type(DOUBLE).source("int16( 3x00172 )").description("Primary unit alarm - general legacy heat pump alarm. This signal is a bit field, one bit for each legacy secondary heat pump unit. Detects if the sum alarm of the secondary unit is active.").build());
////        block.addField(Field.builder().id("Field 173").type(DOUBLE).source("int16( 3x00174 )").description("Primary unit alarm - the primary unit can not communicate with the corresponding expansion card for the legacy heat pump. This signal is a bit field, one bit for each legacy secondary heat pump unit.").build());
//
////        block.addField(Field.builder().id("H Field 0").type(STRING).source("enum ( 4x00001 ; 1->'OFF'; 2->'Standby'; 3->'ON/Auto')").description("Operational mode").build());
////        block.addField(Field.builder().id("H Field 3").type(DOUBLE).source("int16( 4x00004 ) / 100").description("Max limitation, set point curve radiator").build());
////        block.addField(Field.builder().id("H Field 4").type(DOUBLE).source("int16( 4x00005 ) / 100").description("Min limitation, set point curve radiator").build());
////        block.addField(Field.builder().id("H Field 5").type(DOUBLE).source("int16( 4x00006 ) / 100").description("Comfort wheel setting").build());
////        block.addField(Field.builder().id("H Field 6").type(DOUBLE).source("int16( 4x00007 ) / 100").description("Set point heat curve, Y-coordinate 1 (highest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 7").type(DOUBLE).source("int16( 4x00008 ) / 100").description("Set point heat curve, Y-coordinate 2").build());
////        block.addField(Field.builder().id("H Field 8").type(DOUBLE).source("int16( 4x00009 ) / 100").description("Set point heat curve, Y-coordinate 3").build());
////        block.addField(Field.builder().id("H Field 9").type(DOUBLE).source("int16( 4x00010 ) / 100").description("Set point heat curve, Y-coordinate 4").build());
////        block.addField(Field.builder().id("H Field 10").type(DOUBLE).source("int16( 4x00011 ) / 100").description("Set point heat curve, Y-coordinate 5").build());
////        block.addField(Field.builder().id("H Field 11").type(DOUBLE).source("int16( 4x00012 ) / 100").description("Set point heat curve, Y-coordinate 6").build());
////        block.addField(Field.builder().id("H Field 12").type(DOUBLE).source("int16( 4x00013 ) / 100").description("Set point heat curve, Y-coordinate 7 (lowest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 16").type(DOUBLE).source("int16( 4x00017 ; 0x4E20) / 100").description("Heating season stop temperature").build());
////        block.addField(Field.builder().id("H Field 22").type(DOUBLE).source("int16( 4x00023 ; 0x4E20) / 100").description("Start temperature tap water").build());
////        block.addField(Field.builder().id("H Field 23").type(DOUBLE).source("int16( 4x00024 ; 0x4E20) / 100").description("Stop temperature tap water").build());
////        block.addField(Field.builder().id("H Field 26").type(DOUBLE).source("int16( 4x00027 )").description("Minimum allowed gear in heating").build());
////        block.addField(Field.builder().id("H Field 27").type(DOUBLE).source("int16( 4x00028 )").description("Maximum allowed gear in heating").build());
////        block.addField(Field.builder().id("H Field 28").type(DOUBLE).source("int16( 4x00029 )").description("Maximum allowed gear in tap water").build());
////        block.addField(Field.builder().id("H Field 29").type(DOUBLE).source("int16( 4x00030 )").description("Minimum allowed gear in tap water").build());
////        block.addField(Field.builder().id("H Field 30").type(DOUBLE).source("int16( 4x00031 ) / 100").description("Cooling mix valve set point (EM)").build());
////        block.addField(Field.builder().id("H Field 31").type(DOUBLE).source("int16( 4x00032 ) / 100").description("TWC mix valve set point (EM)").build());
////        block.addField(Field.builder().id("H Field 32").type(DOUBLE).source("int16( 4x00033 ) / 100").description("WCS return line set point (EM)").build());
////        block.addField(Field.builder().id("H Field 33").type(DOUBLE).source("int16( 4x00034 ) / 100").description("TWC mix valve lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 34").type(DOUBLE).source("int16( 4x00035 ) / 100").description("TWC mix valve highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 35").type(DOUBLE).source("int16( 4x00036 ) / 100").description("TWC start temperature immersion heater (EM)").build());
////        block.addField(Field.builder().id("H Field 36").type(DOUBLE).source("int16( 4x00037 ) / 100").description("TWC start delay immersion heater, seconds (EM)").build());
////        block.addField(Field.builder().id("H Field 37").type(DOUBLE).source("int16( 4x00038 ) / 100").description("TWC stop temperature immersion heater (EM)").build());
////        block.addField(Field.builder().id("H Field 38").type(DOUBLE).source("int16( 4x00039 ) / 100").description("WCS mix valve lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 39").type(DOUBLE).source("int16( 4x00040 ) / 100").description("WCS mix valve highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 40").type(DOUBLE).source("int16( 4x00041 ) / 100").description("Mix valve 2 lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 41").type(DOUBLE).source("int16( 4x00042 ) / 100").description("Mix valve 2 highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 42").type(DOUBLE).source("int16( 4x00043 ) / 100").description("Mix valve 3 lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 43").type(DOUBLE).source("int16( 4x00044 ) / 100").description("Mix valve 3 highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 44").type(DOUBLE).source("int16( 4x00045 ) / 100").description("Mix valve 4 lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 45").type(DOUBLE).source("int16( 4x00046 ) / 100").description("Mix valve 4 highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 46").type(DOUBLE).source("int16( 4x00047 ) / 100").description("Mix valve 5 lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 47").type(DOUBLE).source("int16( 4x00048 ) / 100").description("Mix valve 5 highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 48").type(DOUBLE).source("int16( 4x00049 ) / 100").description("Surplus heat chiller set point (EM)").build());
////        block.addField(Field.builder().id("H Field 49").type(DOUBLE).source("int16( 4x00050 ) / 100").description("Cooling supply line mix valve: Lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 50").type(DOUBLE).source("int16( 4x00051 ) / 100").description("Cooling supply line mix valve: Highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 51").type(DOUBLE).source("int16( 4x00052 ) / 100").description("Surplus heat opening degree for starting fan 1 (EM)").build());
////        block.addField(Field.builder().id("H Field 52").type(DOUBLE).source("int16( 4x00053 ) / 100").description("Surplus heat opening degree for starting fan 2 (EM)").build());
////        block.addField(Field.builder().id("H Field 53").type(DOUBLE).source("int16( 4x00054 ) / 100").description("Surplus heat opening degree for stopping fan 1 (EM)").build());
////        block.addField(Field.builder().id("H Field 54").type(DOUBLE).source("int16( 4x00055 ) / 100").description("Surplus heat opening degree for stopping fan 2 (EM)").build());
////        block.addField(Field.builder().id("H Field 55").type(DOUBLE).source("int16( 4x00056 ) / 100").description("Surplus heat lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 56").type(DOUBLE).source("int16( 4x00057 ) / 100").description("Surplus heat highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 58").type(DOUBLE).source("int16( 4x00059 ) / 100").description("Pool charge set point (EM)").build());
////        block.addField(Field.builder().id("H Field 59").type(DOUBLE).source("int16( 4x00060 ) / 100").description("Pool mix valve lowest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 60").type(DOUBLE).source("int16( 4x00061 ) / 100").description("Pool mix valve highest allowed opening degree (EM)").build());
////        block.addField(Field.builder().id("H Field 61").type(DOUBLE).source("int16( 4x00062 )").description("Gear shift delay heating").build());
////        block.addField(Field.builder().id("H Field 62").type(DOUBLE).source("int16( 4x00063 )").description("Gear shift delay pool").build());
////        block.addField(Field.builder().id("H Field 63").type(DOUBLE).source("int16( 4x00064 )").description("Gear shift delay cooling").build());
////        block.addField(Field.builder().id("H Field 67").type(DOUBLE).source("int16( 4x00068 ) / 100").description("Brine in high alarm limit").build());
////        block.addField(Field.builder().id("H Field 68").type(DOUBLE).source("int16( 4x00069 ) / 100").description("Brine in low alarm limit").build());
////        block.addField(Field.builder().id("H Field 69").type(DOUBLE).source("int16( 4x00070 ) / 100").description("Brine out low alarm limit").build());
////        block.addField(Field.builder().id("H Field 70").type(DOUBLE).source("int16( 4x00071 ) / 100").description("Brine max delta limit").build());
////        block.addField(Field.builder().id("H Field 71").type(DOUBLE).source("int16( 4x00072 ) / 100").description("Hot gas pump start temperature discharge pipe").build());
////        block.addField(Field.builder().id("H Field 72").type(DOUBLE).source("int16( 4x00073 ) / 100").description("Hot gas pump lower stop limit temperature discharge pipe").build());
////        block.addField(Field.builder().id("H Field 73").type(DOUBLE).source("int16( 4x00074 ) / 100").description("Hot gas pump upper stop limit temperature discharge pipe").build());
////        block.addField(Field.builder().id("H Field 75").type(DOUBLE).source("int16( 4x00076 )").description("External additional heater start (PID sum)").build());
////        block.addField(Field.builder().id("H Field 76").type(DOUBLE).source("int16( 4x00077 ) / 100").description("Condenser pump lowest allowed speed (%)").build());
////        block.addField(Field.builder().id("H Field 77").type(DOUBLE).source("int16( 4x00078 ) / 100").description("Brine pump lowest allowed speed (%)").build());
////        block.addField(Field.builder().id("H Field 78").type(DOUBLE).source("int16( 4x00079 ) / 100").description("External additional heater stop (PID sum)").build());
////        block.addField(Field.builder().id("H Field 79").type(DOUBLE).source("int16( 4x00080 ) / 100").description("Condenser pump highest allowed speed (%)").build());
////        block.addField(Field.builder().id("H Field 80").type(DOUBLE).source("int16( 4x00081 ) / 100").description("Brine pump highest allowed speed (%)").build());
////        block.addField(Field.builder().id("H Field 81").type(DOUBLE).source("int16( 4x00082 ) / 100").description("Condenser pump standby speed (%)").build());
////        block.addField(Field.builder().id("H Field 82").type(DOUBLE).source("int16( 4x00083 ) / 100").description("Brine pump standby speed (%)").build());
////        block.addField(Field.builder().id("H Field 85").type(DOUBLE).source("int16( 4x00086 )").description("Minimum allowed gear in pool").build());
////        block.addField(Field.builder().id("H Field 86").type(DOUBLE).source("int16( 4x00087 )").description("Maximum allowed gear in pool").build());
////        block.addField(Field.builder().id("H Field 87").type(DOUBLE).source("int16( 4x00088 )").description("Minimum allowed gear in cooling").build());
////        block.addField(Field.builder().id("H Field 88").type(DOUBLE).source("int16( 4x00089 )").description("Maximum allowed gear in cooling").build());
////        block.addField(Field.builder().id("H Field 105").type(DOUBLE).source("int16( 4x00106 ) / 100").description("Start temp for cooling (EM)").build());
////        block.addField(Field.builder().id("H Field 106").type(DOUBLE).source("int16( 4x00107 ) / 100").description("Stop temp for cooling (EM)").build());
////        block.addField(Field.builder().id("H Field 107").type(DOUBLE).source("int16( 4x00108 ) / 100").description("Min limitation Set point curve radiator Mix valve 1").build());
////        block.addField(Field.builder().id("H Field 108").type(DOUBLE).source("int16( 4x00109 ) / 100").description("Max limitation Set point curve radiator Mix valve 1").build());
////        block.addField(Field.builder().id("H Field 109").type(DOUBLE).source("int16( 4x00110 ) / 100").description("Set point curve, Y-coordinate 1 Mix valve 1 (highest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 110").type(DOUBLE).source("int16( 4x00111 ) / 100").description("Set point curve, Y-coordinate 2 Mix valve 1").build());
////        block.addField(Field.builder().id("H Field 111").type(DOUBLE).source("int16( 4x00112 ) / 100").description("Set point curve, Y-coordinate 3 Mix valve 1").build());
////        block.addField(Field.builder().id("H Field 112").type(DOUBLE).source("int16( 4x00113 ) / 100").description("Set point curve, Y-coordinate 4 Mix valve 1").build());
////        block.addField(Field.builder().id("H Field 113").type(DOUBLE).source("int16( 4x00114 ) / 100").description("Set point curve, Y-coordinate 5 Mix valve 1").build());
////        block.addField(Field.builder().id("H Field 114").type(DOUBLE).source("int16( 4x00115 ) / 100").description("Set point curve, Y-coordinate 6 Mix valve 1").build());
////        block.addField(Field.builder().id("H Field 115").type(DOUBLE).source("int16( 4x00116 ) / 100").description("Set point curve, Y-coordinate 7 Mix valve 1 (lowest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 116").type(DOUBLE).source("int16( 4x00117 ) / 100").description("Fixed system supply set point, requires defacto address 42 to be enabled").build());
////        block.addField(Field.builder().id("H Field 199").type(DOUBLE).source("int16( 4x00200 ) / 100").description("Min limitation Set point curve radiator Mix valve 2").build());
////        block.addField(Field.builder().id("H Field 200").type(DOUBLE).source("int16( 4x00201 ) / 100").description("Max limitation Set point curve radiator Mix valve 2").build());
////        block.addField(Field.builder().id("H Field 201").type(DOUBLE).source("int16( 4x00202 ) / 100").description("Set point curve, Y-coordinate 1 Mix valve 2 (highest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 202").type(DOUBLE).source("int16( 4x00203 ) / 100").description("Set point curve, Y-coordinate 2 Mix valve 2").build());
////        block.addField(Field.builder().id("H Field 203").type(DOUBLE).source("int16( 4x00204 ) / 100").description("Set point curve, Y-coordinate 3 Mix valve 2").build());
////        block.addField(Field.builder().id("H Field 204").type(DOUBLE).source("int16( 4x00205 ) / 100").description("Set point curve, Y-coordinate 4 Mix valve 2").build());
////        block.addField(Field.builder().id("H Field 205").type(DOUBLE).source("int16( 4x00206 ) / 100").description("Set point curve, Y-coordinate 5 Mix valve 2").build());
////        block.addField(Field.builder().id("H Field 206").type(DOUBLE).source("int16( 4x00207 ) / 100").description("Set point curve, Y-coordinate 6 Mix valve 2").build());
////        block.addField(Field.builder().id("H Field 207").type(DOUBLE).source("int16( 4x00208 ) / 100").description("Set point curve, Y-coordinate 7 Mix valve 2 (lowest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 208").type(DOUBLE).source("int16( 4x00209 ) / 100").description("Min limitation Set point curve radiator Mix valve 3").build());
////        block.addField(Field.builder().id("H Field 209").type(DOUBLE).source("int16( 4x00210 ) / 100").description("Max limitation Set point curve radiator Mix valve 3").build());
////        block.addField(Field.builder().id("H Field 210").type(DOUBLE).source("int16( 4x00211 ) / 100").description("Set point curve, Y-coordinate 1 Mix valve 3 (highest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 211").type(DOUBLE).source("int16( 4x00212 ) / 100").description("Set point curve, Y-coordinate 2 Mix valve 3").build());
////        block.addField(Field.builder().id("H Field 212").type(DOUBLE).source("int16( 4x00213 ) / 100").description("Set point curve, Y-coordinate 3 Mix valve 3").build());
////        block.addField(Field.builder().id("H Field 213").type(DOUBLE).source("int16( 4x00214 ) / 100").description("Set point curve, Y-coordinate 4 Mix valve 3").build());
////        block.addField(Field.builder().id("H Field 214").type(DOUBLE).source("int16( 4x00215 ) / 100").description("Set point curve, Y-coordinate 5 Mix valve 3").build());
////        block.addField(Field.builder().id("H Field 215").type(DOUBLE).source("int16( 4x00216 ) / 100").description("Set point curve, Y-coordinate 6 Mix valve 3").build());
////        block.addField(Field.builder().id("H Field 216").type(DOUBLE).source("int16( 4x00217 ) / 100").description("Set point curve, Y-coordinate 7 Mix valve 3 (lowest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 239").type(DOUBLE).source("int16( 4x00240 ) / 100").description("Min limitation Set point curve radiator Mix valve 4").build());
////        block.addField(Field.builder().id("H Field 240").type(DOUBLE).source("int16( 4x00241 ) / 100").description("Max limitation Set point curve radiator Mix valve 4").build());
////        block.addField(Field.builder().id("H Field 241").type(DOUBLE).source("int16( 4x00242 ) / 100").description("Set point curve, Y-coordinate 1 Mix valve 4 (highest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 242").type(DOUBLE).source("int16( 4x00243 ) / 100").description("Set point curve, Y-coordinate 2 Mix valve 4").build());
////        block.addField(Field.builder().id("H Field 243").type(DOUBLE).source("int16( 4x00244 ) / 100").description("Set point curve, Y-coordinate 3 Mix valve 4").build());
////        block.addField(Field.builder().id("H Field 244").type(DOUBLE).source("int16( 4x00245 ) / 100").description("Set point curve, Y-coordinate 4 Mix valve 4").build());
////        block.addField(Field.builder().id("H Field 245").type(DOUBLE).source("int16( 4x00246 ) / 100").description("Set point curve, Y-coordinate 5 Mix valve 4").build());
////        block.addField(Field.builder().id("H Field 246").type(DOUBLE).source("int16( 4x00247 ) / 100").description("Set point curve, Y-coordinate 6 Mix valve 4").build());
////        block.addField(Field.builder().id("H Field 247").type(DOUBLE).source("int16( 4x00248 ) / 100").description("Set point curve, Y-coordinate 7 Mix valve 4 (lowest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 248").type(DOUBLE).source("int16( 4x00249 ) / 100").description("Min limitation Set point curve radiator Mix valve 5").build());
////        block.addField(Field.builder().id("H Field 249").type(DOUBLE).source("int16( 4x00250 ) / 100").description("Max limitation Set point curve radiator Mix valve 5").build());
////        block.addField(Field.builder().id("H Field 250").type(DOUBLE).source("int16( 4x00251 ) / 100").description("Set point curve, Y-coordinate 1 Mix valve 5 (highest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 251").type(DOUBLE).source("int16( 4x00252 ) / 100").description("Set point curve, Y-coordinate 2 Mix valve 5").build());
////        block.addField(Field.builder().id("H Field 252").type(DOUBLE).source("int16( 4x00253 ) / 100").description("Set point curve, Y-coordinate 3 Mix valve 5").build());
////        block.addField(Field.builder().id("H Field 253").type(DOUBLE).source("int16( 4x00254 ) / 100").description("Set point curve, Y-coordinate 4 Mix valve 5").build());
////        block.addField(Field.builder().id("H Field 254").type(DOUBLE).source("int16( 4x00255 ) / 100").description("Set point curve, Y-coordinate 5 Mix valve 5").build());
////        block.addField(Field.builder().id("H Field 255").type(DOUBLE).source("int16( 4x00256 ) / 100").description("Set point curve, Y-coordinate 6 Mix valve 5").build());
////        block.addField(Field.builder().id("H Field 256").type(DOUBLE).source("int16( 4x00257 ) / 100").description("Set point curve, Y-coordinate 7 Mix valve 5 (lowest outdoor temperature)").build());
////        block.addField(Field.builder().id("H Field 299").type(DOUBLE).source("int16( 4x00300 ) / 10").description("Set point return temp from pool to heat exchanger (EM)").build());
////        block.addField(Field.builder().id("H Field 300").type(DOUBLE).source("int16( 4x00301 ) / 10").description("Set point pool hysteresis (EM)").build());
////        block.addField(Field.builder().id("H Field 302").type(DOUBLE).source("int16( 4x00303 ) / 100").description("Set point for supply line temp passive cooling with mixing valve 1").build());
////        block.addField(Field.builder().id("H Field 303").type(DOUBLE).source("int16( 4x00304 ) / 100").description("Set point minimum outdoor temp when cooling is permitted").build());
////        block.addField(Field.builder().id("H Field 304").type(DOUBLE).source("int16( 4x00305 ) / 100").description("External heater outdoor temp limit").build());
////        block.addField(Field.builder().id("H Field 305").type(DOUBLE).source("int16( 4x00306 )").description("Selected mode for mixing valve 2, 0:Heat, 1:Cool, 2:Auto (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 306").type(DOUBLE).source("int16( 4x00307 ) / 100").description("Desired cooling temperature setpoint mixing valve 2 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 307").type(DOUBLE).source("int16( 4x00308 ) / 100").description("Seasonal cooling temperature (outdoor temp.), mixing valve 2 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 308").type(DOUBLE).source("int16( 4x00309 ) / 100").description("Seasonal heating temperature (outdoor temp.), mixing valve 2 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 309").type(DOUBLE).source("int16( 4x00310 )").description("Selected mode for mixing valve 3, 0:Heat, 1:Cool, 2:Auto (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 310").type(DOUBLE).source("int16( 4x00311 ) / 100").description("Desired cooling temperature setpoint mixing valve 3 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 311").type(DOUBLE).source("int16( 4x00312 ) / 100").description("Seasonal cooling temperature (outdoor temp.), mixing valve 3 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 312").type(DOUBLE).source("int16( 4x00313 ) / 100").description("Seasonal heating temperature (outdoor temp.), mixing valve 3 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 313").type(DOUBLE).source("int16( 4x00314 )").description("Selected mode for mixing valve 4, 0:Heat, 1:Cool, 2:Auto (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 314").type(DOUBLE).source("int16( 4x00315 ) / 100").description("Desired cooling temperature setpoint mixing valve 4 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 315").type(DOUBLE).source("int16( 4x00316 ) / 100").description("Seasonal cooling temperature (outdoor temp.), mixing valve 4 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 316").type(DOUBLE).source("int16( 4x00317 ) / 100").description("Seasonal heating temperature (outdoor temp.), mixing valve 4 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 317").type(DOUBLE).source("int16( 4x00318 )").description("Selected mode for mixing valve 5, 0:Heat, 1:Cool, 2:Auto (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 318").type(DOUBLE).source("int16( 4x00319 ) / 100").description("Desired cooling temperature setpoint mixing valve 5 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 319").type(DOUBLE).source("int16( 4x00320 ) / 100").description("Seasonal cooling temperature (outdoor temp.), mixing valve 5 (EM3 only)").build());
////        block.addField(Field.builder().id("H Field 320").type(DOUBLE).source("int16( 4x00321 ) / 100").description("Seasonal heating temperature (outdoor temp.), mixing valve 5 (EM3 only)").build());
//            return device
//        }
//
//    val modbusDevice: ModbusDevice
//        get() = builder()
//            .withLogging()
//            .withRegisters(
//                AddressClass.HOLDING_REGISTER, 0,  // 1
//                "0003 0000 0000 1194 07D0 07D0 07D0 0960 0A8C 0BB8" +
//                        "0C1C 0DAC 0F3C 0000 0000 0000 06A4 0000 0000 0000" +
//                        "0000 0000 1130 1770 0000 0000 0001 0009 0007 0005" +
//                        "0708 1770 1194 0000 2710 1770 0000 1964 05DC 2710" +
//                        "0000 2710 0000 2710 0000 2710 0000 2710 1194 0000" +
//                        "2710 1388 1F40 07D0 0FA0 0000 2710 0002 0A28 0000" +
//                        "2710 012C 012C 003C 00B4 000A 001E 07D0 FE0C 00C8" +
//                        "04B0 1964 1770 30D4 0001 FFFA 0FA0 07D0 0190 2710" +
//                        "2710 1388 1388 0001 0001 0001 0009 0001 0009 0BB8" +
//                        "2710 0096 FF6A FF9C 0064 7530 8AD0 0000 FF9C 0000" +  // 101
//                        "00C8 F6A0 0960 0BB8 F448 0708 0514 03E8 1770 0834" +
//                        "0B54 0D48 0FA0 10CC 13EC 170C 09C4 0000 4E20"
//            )
//            .withRegisters(
//                AddressClass.INPUT_REGISTER, 0,  // 0
//                "0000 0008 0080 0000 0384 0000 0000 0827 077B 0716" +
//                        "06AD 0763 4E20 0A3D 0A3D 134C 1316 1329 0000 0BB8" +
//                        "06A4 03E8 01F4 0000 FE0C FA24 F63C 4E20 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 03E8 0000 0000 1D4C" +
//                        "4E20 4E20 09C4 0000 044C 4E20 044C 0000 0000 0B22" +
//                        "0000 01CE 0000 0000 0000 0063 0063 000B 0000 00C5" +
//                        "0000 0000 0008 0063 0063 0063 0063 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0004 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 C3DC 0000 0000 0000 4E20 4E20 4E20 4E20 4E20" +  // 100
//                        "4E20 4E20 0064 4E20 4E20 4E20 4E20 4E20 0708 4E20" +
//                        "4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20" +
//                        "4E20 00E9 0800 080C 0657 01A9 FFFE 04AE 0556 07FD" +
//                        "07FE 0096 0000 0000 0000 0000 0000 000B 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +  // 200
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +  // 300
//                        "0000 0000 0000 0000 0000 0000 0000 0000 0000 0000" +
//                        "0000 000B 0000 000C 0001 0000 0000 0000 0000 0014"
//            )
//            .build() //    20:01:12,478 [INFO ] TestSchemaDeviceThermia                :   89: Input Register starting at ir:00001:
//    //    0004 0008 0000 0384 04AE 0000 11CD 094E 0B19 0405 039A 4E20 02EA 02EA 1379 1326 1343 0A5A 0BB8 06A4 03E8 01F4 0000 FE0C FA24 F63C 4E20 0000 0000 0106 0000 0000 0000 0000 0000 FF6A 0000 0000 0FA0 4E20 4E20 1770 0000 17CC 4E20 17CC 0000 0000 0FC0 0000 0280 0000 0000 0AE2 0063 0063 000B 0000 00C5 0000 0064 0004 0063 0063 0063 0063 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0004 0000 0000 0000 0000 0000 0000 0000 0000 C3DC 0000 0000 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 0064 4E20 4E20 4E20 4E20 4E20 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 00CD 0B13 0B1E 0352 00F8 01A7 03A8 06AD 096C 044A FF6A 007C 0083 0007 0000 0000 0000 0000 0000 0001 0000 0000 0000 0000 0001 0001 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0107 001E 0000 0000 0000 0000 0000 null null null null null null 0000 0000 0000 0000 null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null 000B 0000 000C 0001 08A0 0000 0000 0000 001B
//    //20:17:46,467 [INFO ] TestSchemaDeviceThermia                :   89: Input Register starting at ir:00001:
//    //        0004 0008 0000 0384 04AE 0000 129C 0968 0B28 0408 0341 4E20 02EB 02EB 136A 131D 1335 0A58 0BB8 06A4 03E8 01F4 0000 FE0C FA24 F63C 4E20 0000 0000 014B 0000 0000 0000 0000 0000 FF6A 0000 0000 0FA0 4E20 4E20 1770 0000 0BAF 4E20 0BAF 0000 0000 0FC1 0000 0280 0000 0000 0AE2 0063 0063 000B 0000 00C5 0000 0064 0004 0063 0063 0063 0063 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0004 0000 0000 0000 0000 0000 0000 0000 0000 C3DC 0000 0000 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 0064 4E20 4E20 4E20 4E20 4E20 0000 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 4E20 00CD 0B23 0B2F 02AF 01A4 01B2 0377 06B5 096E 0453 FF6A 00A6 00A2 0003 0000 0000 0000 0000 0000 0001 0000 0000 0000 0000 0001 0001 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 00FC 001F 0000 0000 0000 0000 0000 null null null null null null 0000 0000 0000 0000 null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null null 000B 0000 000C 0001 0793 0000 0000 0000 001B
//}
