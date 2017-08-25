/**
 *
 * @author Nabil EL Mahiri
 */

package com.gpsapp;

import com.gpsapp.GPSUARTSensor;
import com.gpsapp.Position;
import java.io.IOException;
import javax.microedition.midlet.MIDlet;


public class GPSIMlet extends MIDlet {

    private static final String SERIAL_PORT = "ttyS0";

    /**
     * Imlet lifecycle start method
     */
    @Override
    public void startApp() {
        try (GPSUARTSensor gps = new GPSUARTSensor())  {
        //try (GPSCommSensor gps = new GPSCommSensor(SERIAL_PORT)) {

            /* Take one reading every second for 10 readings */
            
            while(true){
                for (int i = 0; i < 10; i++) {
                Position p = gps.getPosition();
                // Use the toString method to print the result
                System.out.println(p + "\n");
                Thread.sleep(1000);
            } }
        } catch (IOException ioe) {
            System.out.println("GPSTestMidlet: IOException " + ioe.getMessage());
        } catch (InterruptedException ex) {
            // Ignore
        }

        /* Terminate the Imlet correctly */
        System.out.println("GPSTestMidlet finished");
        notifyDestroyed();
    }

    @Override
    public void destroyApp(boolean unconditional) {
        // Nothing to do here, since we are using AutoCloseable to gracefully close connections
    }
}
