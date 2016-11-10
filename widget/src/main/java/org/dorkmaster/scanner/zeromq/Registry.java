package org.dorkmaster.scanner.zeromq;

import org.elasticsearch.common.collect.CopyOnWriteHashSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by mjackson on 11/9/16.
 */
public class Registry {
    private Map<PeerType, Collection<Peer>> peers = new HashMap<>();

    public Registry() {
        peers.put(PeerType.AGENT, new CopyOnWriteHashSet<Peer>());
        peers.put(PeerType.CONTROLLER, new CopyOnWriteHashSet<Peer>());
        peers.put(PeerType.UPDATER, new CopyOnWriteHashSet<Peer>());
    }

    private Runnable pinger = new Runnable() {
        @Override
        public void run() {
            while (true) {

            }
        }
    };

    public void addPeer(Peer peer) {
        peers.get(peer.getType()).add(peer);
    }

    public void removePeer(Peer peer) {
        peer.disconnect();
        peers.get(peer.getType()).remove(peer);
    }
}
