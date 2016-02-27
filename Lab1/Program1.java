/*
* Name: Jonathon Reynolds
* EID: jar6493
*/

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;

/**
* Your solution goes in this class.
*
* Please do not modify the other files we have provided for you, as we will use
* our own versions of those files when grading your project. You are
* responsible for ensuring that your solution works with the original version
* of all the other files we have provided for you.
*
* That said, please feel free to add additional files and classes to your
* solution, as you see fit. We will use ALL of your additional files when
* grading your solution.
*/
public class Program1 extends AbstractProgram1 {
    /**
    * Determines whether a candidate Matching represents a solution to the
    * Stable Matching problem. Study the description of a Matching in the
    * project documentation to help you with this.
    */
    public boolean isStableMatching(Matching given_matching) {
        int numTenant = given_matching.getTenantCount();
        int numApartment = numTenant;
        int numLandlord = given_matching.getLandlordCount();

        ArrayList<Integer> tenantMatching = given_matching.getTenantMatching();
        ArrayList<ArrayList<Integer>> tenantPref = given_matching.getTenantPref();
        ArrayList<ArrayList<Integer>> landlordPref = given_matching.getLandlordPref();
        ArrayList<ArrayList<Integer>> landlordOwn = given_matching.getLandlordOwners();

        Dictionary<Integer, Integer> apartmentToLandlord = new Hashtable<Integer, Integer>();
        Dictionary<Integer, Integer> apartmentToTenant = new Hashtable<Integer, Integer>();

        /* populate apartmentToLandlord; maps apartments to their landlords */
        for (Integer landlord = 0; landlord < numLandlord; landlord++) {
            ArrayList<Integer> owns = landlordOwn.get(landlord);
            for (Integer apt : owns) {
                apartmentToLandlord.put(apt, landlord);
            }
        }

        /* populate apartmentToTenant; maps apartments to their matched tenant */
        for (Integer tenant = 0; tenant < numTenant; tenant++) {
            Integer apt = tenantMatching.get(tenant);
            apartmentToTenant.put(apt, tenant);
        }

        for (Integer tenant = 0; tenant < numTenant; tenant++) {
            Integer matchedApt = tenantMatching.get(tenant);
            Integer matchedAptRank = tenantPref.get(tenant).get(matchedApt);

            /* for every tenant, see if there is a strictly "higher" ranking apartment than the one he is currently assigned to */
            for (Integer apartment = 0; apartment < numApartment; apartment++) {
                /* there is no need to compare against the tenant's currently assigned apartment */
                if (apartment != matchedApt) {
                    Integer newAptRank = tenantPref.get(tenant).get(apartment);
                    /* if the new apartment ranks strictly "higher" than the assigned apartment, we need to see if the landlord agrees */
                    if (newAptRank < matchedAptRank) {
                        Integer matchedTenant = apartmentToTenant.get(apartment);
                        Integer landlord = apartmentToLandlord.get(apartment);
                        Integer matchedTenantRank = landlordPref.get(landlord).get(matchedTenant);
                        Integer currentTenantRank = landlordPref.get(landlord).get(tenant);
                        /* compare the landlord's rankings of the current tenant and the assigned tenant */
                        if (currentTenantRank < matchedTenantRank) {
                            /* both the tenant prefers apartment and the owner of apartment prefers tenant above their
                            * respective rankings */
                            return false;
                        }
                    }
                }
            }
        }

        /* if we've gotten here, then the matching is weakly stable */
        return true;
    }

    /**
    * Determines a solution to the Stable Matching problem from the given input
    * set. Study the project description to understand the variables which
    * represent the input to your solution.
    *
    * @return A stable Matching.
    */
    public Matching stableMatchingGaleShapley(Matching given_matching) {
        int numTenant = given_matching.getTenantCount();
        int numApartment = numTenant; /* interchangeable values */
        int numLandlord = given_matching.getLandlordCount();

        /* given data structures */
        ArrayList<ArrayList<Integer>> tenantPref = given_matching.getTenantPref();
        ArrayList<ArrayList<Integer>> landlordPref = given_matching.getLandlordPref();
        ArrayList<ArrayList<Integer>> landlordOwn = given_matching.getLandlordOwners();

        /* helper data structures */
        Dictionary<Integer, Integer> apartmentToLandlord = new Hashtable<Integer, Integer>();
        ArrayList<Tenant> tenants = new ArrayList<Tenant>();
        ArrayList<Apartment> apartments = new ArrayList<Apartment>();
        LinkedList<Tenant> freeTenants = new LinkedList<Tenant>();
        Integer[] matching = new Integer[numTenant];

        /* NOTE: an apartment has the same preference list as the landlord who owns it */

        /* populate apartmentToLandlord; maps apartments to their landlords */
        for (Integer landlordId = 0; landlordId < numLandlord; landlordId++) {
            ArrayList<Integer> owns = landlordOwn.get(landlordId);
            for (Integer aptId : owns) {
                apartmentToLandlord.put(aptId, landlordId);
            }
        }

        /* populate apartments; array indexed by id to each apartment's pref list */
        for (Integer apartmentId = 0; apartmentId < numApartment; apartmentId++) {
            Integer landlordId = apartmentToLandlord.get(apartmentId);
            Apartment a = new Apartment(apartmentId, landlordPref.get(landlordId));
            apartments.add(a);
        }

        /* populate tenants; array indexed by id to each tenant's pref list */
        for (Integer tenantId = 0; tenantId < numTenant; tenantId++) {
            Tenant t = new Tenant(tenantId, tenantPref.get(tenantId), apartments);
            tenants.add(t);
        }

        /* populate the freeTenants with every tenant to begin with since none of them have been matched */
        for (Integer tenantId = 0; tenantId < numTenant; tenantId++) {
            freeTenants.add(tenants.get(tenantId));
        }

        /* initialize matching with -1 at every index; index of matching is the apartment, and the value at that index is the matched tenant */
        for (Integer i = 0; i < matching.length; i++) {
            matching[i] = -1;
        }

        /* algorithm based on Gale-Shapely; tenants propose to apartments (which each have the same preference list as their landlord owner) */

        /* while there are free tenants */
        while (freeTenants.size() > 0) {
            /* choose some free tenant */
            Tenant lonelyTenant = freeTenants.removeFirst();
            /* choose the apartment highest on tenant's pref list that he has not proposed to yet */
            Apartment apartment = lonelyTenant.nextApt();
            /* if the apartment is not engaged, then lonelyTenant and apartment become engaged  */
            if (matching[apartment.id] < 0) {
                matching[apartment.id] = lonelyTenant.id;
            } else {
                /* otherwise, see which tenant the apartment prefers */
                Integer currentTenantId = matching[apartment.id];
                Tenant currentTenant = tenants.get(currentTenantId);
                if (currentTenantId != currentTenant.id) { System.out.println("FUUUUUCK"); }
                /* if the apartment strictly prefers lonelyTenant, engage lonelyTenant and apartment in matching, and add apartment's old tenant to the free list */
                if (apartment.striclyPrefersFirstOverSecond(lonelyTenant, currentTenant)) {
                    matching[apartment.id] = lonelyTenant.id;
                    freeTenants.add(currentTenant);
                } else {
                    /* otherwise, add lonelyTenant back to the free list */
                    freeTenants.add(lonelyTenant);
                }
            }
        }

        /* convert my matching to the necessary format; my indeces and values are inverse of the required format */
        Integer[] matchingInverse = new Integer[numTenant];
        for (Integer apartmentId = 0; apartmentId < numApartment; apartmentId++) {
            Integer tenantId = matching[apartmentId];
            matchingInverse[tenantId] = apartmentId;
        }
        ArrayList<Integer> matchingArrList = new ArrayList<Integer>(Arrays.asList(matchingInverse));
        given_matching.setTenantMatching(matchingArrList);
        return given_matching;
    }

    public class Tenant {
        Integer id;
        private ArrayList<Integer> rawPrefList; // the provided preference list for this tenant
        /*
         * prefList contains all of the apartments in order of preference for this tenant;
         * at each index, there is a linked list of apartments (all of which have the same ranking for this tenant);
         * if there is a unique ranking for every apartment for this tenant, then each linked list will
         * have a length of one
         */
        private ArrayList<LinkedList<Apartment>> prefList;
        private Integer next = 0;

        public Tenant(Integer id, ArrayList<Integer> rawPrefList, ArrayList<Apartment> apartments) {
            this.id = id;
            this.rawPrefList = rawPrefList;
            this.prefList = new ArrayList<LinkedList<Apartment>>(apartments.size());

            /* initialize linked lists for each ranking */
            for (Integer rank = 0; rank < apartments.size(); rank++) {
                this.prefList.add(new LinkedList<Apartment>());
            }

            for (Apartment apt : apartments) {
                Integer scaledRanking = this.rawPrefList.get(apt.id) - 1; // ranking should be 0-indexed
                this.prefList.get(scaledRanking).add(apt);
            }
        }

        /* this function returns the next highest ranking apartment for this tenant */
        public Apartment nextApt() {
            /* while there is no apartment at the current ranking, go to the next ranking */
            while (prefList.get(next).size() == 0) {
                next++;
            }
            /* grab the first apartment at the highest ranking */
            Apartment a = prefList.get(next).removeFirst();
            return a;
        }
    }

    public class Apartment {
        Integer id;
        private ArrayList<Integer> prefList; // the ranking as given by given_matching for this apartment (i.e. landlord's pref)

        public Apartment(Integer id, ArrayList<Integer> prefList) {
            this.id = id;
            this.prefList = prefList;
        }

        public Boolean striclyPrefersFirstOverSecond(Tenant t1, Tenant t2) {
            Integer pref1 = prefList.get(t1.id);
            Integer pref2 = prefList.get(t2.id);

            return pref1 < pref2;
        }
    }
}
