/*
* Name: <your name>
* EID: <your EID>
*/

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

                            // System.out.println("Failing " + tenantMatching + " because: ");
                            // System.out.println("Tenant " + tenant + " prefers apartment " + apartment + " (" + newAptRank + ") over " + matchedApt + " (" + matchedAptRank + ")");
                            // System.out.println("Apartment " + apartment + " prefers tenant " + tenant + " (" + currentTenantRank + ") over " + matchedTenant + " (" + matchedTenantRank + ")");
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
        /* TODO implement this function */
        return null; /* TODO remove this line */
    }
}
