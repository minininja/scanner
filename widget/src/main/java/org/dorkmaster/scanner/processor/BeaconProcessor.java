package org.dorkmaster.scanner.processor;

import org.dorkmaster.discovery.event.BeaconEvent;

/**
 * Created by mjackson on 11/7/2016.
 */
public class BeaconProcessor implements Processor {

    @Override
    public boolean canProcess(Object cmd) {
        return cmd instanceof BeaconEvent;
    }

    @Override
    public void process(Object cmd) {
        // add the beacon to the table we're maintaining
        // setup communications with the beacon host on it's port
        // ask the host what kind of host it is, the main ones we're interested in
        // are updaters and commanders.
        //
        // updaters update elastic search and allow us to search
        //
        // commanders tell us what to do


    }
}
