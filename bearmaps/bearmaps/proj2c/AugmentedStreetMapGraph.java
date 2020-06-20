package bearmaps.proj2c;

import bearmaps.hw4.WeightedEdge;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;

import java.util.*;

import static java.lang.Character.toUpperCase;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    List<Node> nodes = getNodes();
    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // List<Node> nodes = this.getNodes();
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */

    public long closest(double lon, double lat) {
        List<Point> pointList = new ArrayList<>();
        for (Node i : nodes) {
            if (i.name() == null) {
                pointList.add(new Point(i.lon(), i.lat()));
            }
        }

        WeirdPointSet pointSet = new WeirdPointSet(pointList);
        Point p = pointSet.nearest(lon, lat);
        for (Node i : nodes) {
            if (i.name() == null) {
                if (i.lon() == p.getX() && i.lat() == p.getY()) {
                    return i.id();
                }
            }
        }

        return 0;
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    private Trie root;

    private class Trie {
        private HashMap<Character, Trie> map;
        private boolean isKey;
        private char c;

        private Trie(char c, boolean b) {
            map = new HashMap<>();
            this.c = c;
            isKey = b;
        }
    }

    public List<String> getLocationsByPrefix(String prefix) {
        List<String> list = new ArrayList<>();
        root = new Trie('\u0000', false);
        for (Node i : nodes) {
            if (i.name() != null) {
                add(i.name());
            }
        }

        if (root == null) {
            return null;
        }

        Trie curr = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c) && (!curr.map.containsKey(toUpperCase(c)))) {
                return null;
            }
            curr = curr.map.get(toUpperCase(c));
        }

        helper(list, curr, prefix);
        return list;
    }

    private void helper(List list, Trie curr, String prefix) {
        if (curr.isKey) {
            list.add(prefix);
        } else if (curr == null) {
            return;
        }

        for (Trie i : curr.map.values()) {
            helper(list, i, prefix + i.c);
        }

    }

    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Trie curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c) && c != ' ') {
                curr.map.put(c, new Trie(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }



    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> list = new LinkedList<>();
        for (Node i : getNodes()) {
            Map<String, Object> m = new HashMap<>();
            if (i.name().equals(locationName)) {
                m.put(i.name(), i);
                list.add(m);
            }
        }
        return list;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }
}
