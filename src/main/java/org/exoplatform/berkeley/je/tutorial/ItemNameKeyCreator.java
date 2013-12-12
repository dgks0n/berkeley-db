// file ItemNameKeyCreator.java

package org.exoplatform.berkeley.je.tutorial;

import java.io.IOException;

import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.SecondaryDatabase;
import com.sleepycat.je.SecondaryKeyCreator;

public class ItemNameKeyCreator implements SecondaryKeyCreator {

    private final TupleBinding theBinding;

    // Use the constructor to set the tuple binding
    ItemNameKeyCreator(TupleBinding binding) {
        theBinding = binding;
    }

    // Abstract method that we must implement
    public boolean createSecondaryKey(SecondaryDatabase secDb,
             DatabaseEntry keyEntry,      // From the primary
             DatabaseEntry dataEntry,     // From the primary
             DatabaseEntry resultEntry) { // set the key data on this.
        if (dataEntry != null) {
            // Convert dataEntry to an Inventory object
            Inventory inventoryItem =
                  (Inventory)theBinding.entryToObject(dataEntry);
            // Get the item name and use that as the key
            String theItem = inventoryItem.getItemName();
            try {
                resultEntry.setData(theItem.getBytes("UTF-8"));
            } catch (IOException willNeverOccur) {}
        }
        return true;
    }
}
