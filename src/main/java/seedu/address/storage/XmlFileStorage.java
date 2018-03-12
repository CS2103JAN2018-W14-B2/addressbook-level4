package seedu.address.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores parttimemanager data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given parttimemanager data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializablePartTimeManager partTimeManager)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, partTimeManager);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializablePartTimeManager loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializablePartTimeManager.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
