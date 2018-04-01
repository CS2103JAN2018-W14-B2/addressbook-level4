package seedu.ptman.model.outlet;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.ptman.model.Password;
import seedu.ptman.model.outlet.exceptions.NoOutletInformationFieldChangeException;

/**
 * Represents an outlet in PTMan.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OutletInformation {

    public static final String DEFAULT_OUTLET_NAME = "DefaultOutlet";
    public static final String DEFAULT_OPERATING_HOURS = "09:00-22:00";
    public static final String DEFAULT_OUTLET_CONTACT = "91234567";
    public static final String DEFAULT_OUTLET_EMAIL = "DefaultOutlet@gmail.com";
    public static final String DEFAULT_ANNOUNCEMENT_MESSAGE = "No announcement. "
            + "Please add new announcement with announcement command.";

    private OutletName name;
    private Password masterPassword;
    private OperatingHours operatingHours;
    private OutletContact outletContact;
    private OutletEmail outletEmail;
    private Announcement announcement;
    private Timetable timetable;

    /**
     * Constructs an {@code OutletInformation}.
     *
     * @param name a valid outlet name
     * @param operatingHours a valid operating hours
     */
    public OutletInformation(OutletName name, OperatingHours operatingHours, OutletContact outletContact,
                             OutletEmail outletEmail, Password masterPassword, Announcement announcement) {
        requireAllNonNull(name, operatingHours, outletContact, outletEmail, masterPassword, announcement);
        this.name = name;
        this.operatingHours = operatingHours;
        this.outletContact = outletContact;
        this.outletEmail = outletEmail;
        this.masterPassword = masterPassword;
        this.announcement = announcement;
        this.timetable = new Timetable(LocalDate.now());
    }

    public OutletInformation(OutletInformation outlet) {
        this.name = new OutletName(outlet.getName().toString());
        this.masterPassword = new Password(outlet.getMasterPassword());
        this.outletContact = new OutletContact(outlet.getOutletContact().toString());
        this.timetable = new Timetable(outlet.getTimetable());
        this.operatingHours = new OperatingHours(outlet.getOperatingHours().toString());
        this.outletEmail = new OutletEmail(outlet.getOutletEmail().toString());
        this.announcement = new Announcement(outlet.getAnnouncement().toString());
    }

    /**
     * Default constructor with default values
     */
    public OutletInformation() {
        this.name = new OutletName(DEFAULT_OUTLET_NAME);
        this.masterPassword = new Password();
        this.operatingHours = new OperatingHours(DEFAULT_OPERATING_HOURS);
        this.outletContact = new OutletContact(DEFAULT_OUTLET_CONTACT);
        this.outletEmail = new OutletEmail(DEFAULT_OUTLET_EMAIL);
        this.announcement = new Announcement(DEFAULT_ANNOUNCEMENT_MESSAGE);
        this.timetable = new Timetable(LocalDate.now());
    }

    public OutletName getName() {
        return name;
    }

    public Password getMasterPassword() {
        return masterPassword;
    }

    public OperatingHours getOperatingHours() {
        return operatingHours;
    }

    public OutletContact getOutletContact() {
        return outletContact;
    }

    public OutletEmail getOutletEmail() {
        return outletEmail;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    /**
     * Set the outlet password.
     * only set after checking against outlet password.
     * @param password
     */
    public void setOutletPassword (Password password) {
        requireNonNull(password);
        this.masterPassword = password;

    }
    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setOutletInformation(OutletName name, OperatingHours operatingHours, OutletContact outletContact,
                                     OutletEmail outletEmail)
            throws NoOutletInformationFieldChangeException {
        if (name == null && operatingHours == null && outletContact == null && outletEmail == null) {
            throw new NoOutletInformationFieldChangeException();
        }
        if (name != null) {
            this.name = name;
        }
        if (operatingHours != null) {
            this.operatingHours = operatingHours;
        }
        if (outletContact != null) {
            this.outletContact = outletContact;
        }
        if (outletEmail != null) {
            this.outletEmail = outletEmail;
        }
    }


    public void setOutletInformation(OutletInformation outlet) throws NoOutletInformationFieldChangeException {
        try {
            requireAllNonNull(outlet.getName(), outlet.getOperatingHours(), outlet.getMasterPassword(),
                    outlet.getTimetable(), outlet.getOutletEmail(), outlet.getOutletContact(),
                    outlet.getAnnouncement());
        } catch (NullPointerException e) {
            throw new NoOutletInformationFieldChangeException();
        }
        this.name = outlet.getName();
        this.operatingHours = outlet.getOperatingHours();
        this.outletContact = outlet.getOutletContact();
        this.outletEmail = outlet.getOutletEmail();
        this.timetable = outlet.getTimetable();
        this.masterPassword = outlet.getMasterPassword();
        this.announcement = outlet.getAnnouncement();
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }


    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof OutletInformation
                && ((OutletInformation) other).getName().equals(this.getName())
                && ((OutletInformation) other).getMasterPassword().equals(this.getMasterPassword())
                && ((OutletInformation) other).getOperatingHours().equals(this.getOperatingHours())
                && ((OutletInformation) other).getOutletContact().equals(this.getOutletContact())
                && ((OutletInformation) other).getOutletEmail().equals(this.getOutletEmail())
                && ((OutletInformation) other).getAnnouncement().equals(this.getAnnouncement()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, masterPassword, operatingHours, outletContact, outletEmail, announcement);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Operating Hours: ")
                .append(getOperatingHours())
                .append(" Contact: ")
                .append(getOutletContact())
                .append(" Email: ")
                .append(getOutletEmail());
        return builder.toString();
    }

}
