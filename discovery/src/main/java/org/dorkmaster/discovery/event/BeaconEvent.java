package org.dorkmaster.discovery.event;

import com.google.common.eventbus.Subscribe;
import org.dorkmaster.discovery.mesg.Beacon;

public class BeaconEvent {
    private Beacon beacon;

    public BeaconEvent(Beacon beacon) {
        this.beacon = beacon;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public static class BeaconEventHandler {
        @Subscribe
        public void beaconHandler(BeaconEvent event) {
            System.out.println("got a beacon");
            System.out.println("  " + event.getBeacon());
        }
    }
}
