/**
 *
 * @author Nabil EL Mahiri
 */
package com.gpsapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Channels;
import jdk.dio.DeviceConfig;
import jdk.dio.DeviceManager;
import jdk.dio.uart.UART;
import jdk.dio.uart.UARTConfig;

/**
 * NEO-6M GPS sensor accessed through the UART interface of the device IO API.
 *
 */
public class GPSUARTSensor extends GPSSensor implements AutoCloseable {

  private final int UART_DEVICE_ID = 40;
  private final int BAUD_RATE = 9600;

  private UART uart;

  /**
   * Constructor
   *
   * @throws IOException If there is an IO error
   */
  public GPSUARTSensor() throws IOException {
    try {
      UARTConfig config = new UARTConfig(DeviceConfig.DEFAULT,
              UART_DEVICE_ID, 
              BAUD_RATE, 
              UARTConfig.DATABITS_7, 
              UARTConfig.PARITY_NONE, 
              UARTConfig.STOPBITS_1, 
              UARTConfig.FLOWCONTROL_NONE);
      uart = DeviceManager.open(config);
      InputStream serialInputStream = Channels.newInputStream(uart);
      serialBufferedReader
              = new BufferedReader(new InputStreamReader(serialInputStream));
    } catch (IOException ioe) {
        System.out.println("Unable to open the UART");
        System.out.println("Exception = " + ioe.getMessage());
      throw ioe;
    }

    System.out.println("NEO-6M GPS Sensor: DIO API UART opened");
  }

  /**
   * Close the connection to the GPS receiver via the UART
   *
   * @throws IOException If there is an IO error
   */
  @Override
  public void close() throws IOException {
    serialBufferedReader.close();
    if (uart.isOpen()) {
      uart.close();
    }
  }
}
