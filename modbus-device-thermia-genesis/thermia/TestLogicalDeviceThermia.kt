//package nl.basjes.modbus.schema.thermia
//
//import nl.basjes.modbus.device.api.AddressClass
//import nl.basjes.modbus.schema.fetcher.OptimizingRegisterBlockFetcher
//import nl.basjes.modbus.schema.fetcher.RegisterBlockFetcher
//import org.apache.logging.log4j.LogManager
//import org.apache.logging.log4j.Logger
//import org.junit.jupiter.api.Disabled
//import org.junit.jupiter.api.Test
//
//internal class TestSchemaDeviceThermia {
//    @Disabled("Requires real device")
//    @Test
//    @Throws(Exception::class)
//    fun dumpDevice() {
//        val schemaDevice = DeviceThermia.getSchemaDevice()
//
//        // Needs local SSH tunnel
//        // $ ssh  -L127.0.0.1:1502:192.168.22.101:502 warmtepomppi
//        val hostname = "localhost"
//        val port = 1502
//        val unitid = 1
//
//        val connectionString = "modbus-tcp:tcp://$hostname:$port?unit-identifier=$unitid"
//        DeviceThermia.getModbusDevice().use { modbusDevice ->
////        try(ModbusDevicePlc4j modbusDevice = new ModbusDevicePlc4j(connectionString)) {
////            modbusDevice.setVerbose(true);
//            LOG.error("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv")
//
//
//            val fetcher = OptimizingRegisterBlockFetcher(schemaDevice, modbusDevice)
//
//            schemaDevice!!.setMaxRegistersPerModbusRequest(40)
//            fetcher.setAllowedGapReadSize(0)
//
//            // We need all fields for this
//            fetcher.needAll()
//
//            //            Block block = schemaDevice.getBlocks().get(0);
////            fetcher.need(block.getField("Field 7"));
////            fetcher.need(block.getField("Field 8"));
////            fetcher.need(block.getField("Field 9"));
////            fetcher.need(block.getField("Field 10"));
////            fetcher.need(block.getField("Field 11"));
////            fetcher.need(block.getField("Field 12"));
////            fetcher.need(block.getField("Field 13"));
//////            fetcher.need(block.getField("Field 14"));
////            fetcher.need(block.getField("Field 15"));
////            fetcher.need(block.getField("Field 16"));
////            fetcher.need(block.getField("H Field 0"));
////            fetcher.need(block.getField("H Field 5"));
//            fetcher.update(RegisterBlockFetcher.FORCE_UPDATE_MAX_AGE)
//            LOG.error("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
//
//            //        LOG.error("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
////        schemaDevice.createTestsUsingCurrentRealData();
////        StringTable table = new StringTable();
////        table.withHeaders("Block", "Field", "Expected", "Actual", "Ok?");
////        for (Map.Entry<String, Map<String, Triple<String, String, Boolean>>> blockResultEntry : schemaDevice.verifyProvidedTests().entrySet()) {
////            table.addRowSeparator();
////            String blockId = blockResultEntry.getKey();
////            for (Map.Entry<String, Triple<String, String, Boolean>> fieldResultEntry : blockResultEntry.getValue().entrySet()) {
////                String fieldId = fieldResultEntry.getKey();
////                String expected = fieldResultEntry.getValue().a;
////                String actual = fieldResultEntry.getValue().b;
////                Boolean ok = fieldResultEntry.getValue().c;
////                table.addRow(blockId, fieldId, expected, actual, ok.toString());
////            }
////        }
////        LOG.warn("\n{}", table);
////
////        LOG.error("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//            LOG.info("\n{}", schemaDevice!!.toYaml())
//            for (addressClass in AddressClass.entries) {
//                val registerBlock = schemaDevice!!.getRegisterBlock(addressClass)
//                if (registerBlock.isEmpty()) {
//                    continue
//                }
//                LOG.info(
//                    "{} starting at {}: \n{}",
//                    addressClass.getName(),
//                    registerBlock.firstKey(),
//                    registerBlock.toHexString()
//                )
//            }
//            LOG.info("\n{}", schemaDevice!!.toTable(false))
//        }
//    } //    @Disabled
//    //    @Test
//    //    void showRealSunSpecDevice() throws ModbusException {
//    //        AbstractModbusMaster master = new ModbusTCPMaster("SMA3005067415.iot.basjes.nl");
//    //        try {
//    //            master.connect();
//    //            ModbusDevice modbusDevice = new ModbusDeviceJ2Mod(master, SUNSPEC_STANDARD_UNITID);
//    //            dumpSunSpec(modbusDevice);
//    //        } catch (Exception e) {
//    //            throw new ModbusException("Unable to connect to the master", e);
//    //        } finally {
//    //            master.disconnect();
//    //        }
//    //    }
//
//    companion object {
//        private val LOG: Logger = LogManager.getLogger()
//    }
//}
