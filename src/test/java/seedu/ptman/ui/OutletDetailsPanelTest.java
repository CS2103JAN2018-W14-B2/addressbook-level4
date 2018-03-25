package seedu.ptman.ui;

import static org.junit.Assert.assertEquals;
import static seedu.ptman.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.OutletDetailsPanelHandle;
import seedu.ptman.commons.events.ui.AnnouncementChangedEvent;
import seedu.ptman.commons.events.ui.OutletInformationChangedEvent;
import seedu.ptman.commons.events.ui.OutletNameChangedEvent;
import seedu.ptman.model.outlet.OutletInformation;

//@@author hzxcaryn
public class OutletDetailsPanelTest extends GuiUnitTest {
    private OutletInformationChangedEvent outletInformationChangedEventStub;
    private OutletNameChangedEvent outletNameChangedEventStub;
    private AnnouncementChangedEvent announcementChangedEventStub;

    private OutletDetailsPanel outletDetailsPanel;
    private OutletDetailsPanelHandle outletDetailsPanelHandle;
    private OutletInformation outlet = new OutletInformation();

    @Before
    public void setUp() {
        outletInformationChangedEventStub = new OutletInformationChangedEvent("New Outlet Information");
        outletNameChangedEventStub = new OutletNameChangedEvent("New Outlet Name");
        announcementChangedEventStub = new AnnouncementChangedEvent("New Announcement");
        outletDetailsPanel = new OutletDetailsPanel(outlet);

        uiPartRule.setUiPart(outletDetailsPanel);
        outletDetailsPanelHandle = new OutletDetailsPanelHandle(outletDetailsPanel.getRoot());
    }

    @Test
    public void display() {
        // Default outlet name and information
        String expectedDefaultOutletName = "DefaultOutlet";
        String expectedDefaultOutletInformation = "Operating Hour: 09:00-22:00 Contact: 91234567 "
                + "Email: DefaultOutlet@gmail.com";
        assertEquals(expectedDefaultOutletInformation, outletDetailsPanelHandle.getOutletInformation());
        assertEquals(expectedDefaultOutletName, outletDetailsPanelHandle.getOutletName());

        // Outlet Information Updated
        postNow(outletInformationChangedEventStub);
        String expectedOutletInformation = "New Outlet Information";
        assertEquals(expectedOutletInformation, outletDetailsPanelHandle.getOutletInformation());

        // Outlet Name Updated
        postNow(outletNameChangedEventStub);
        String expectedOutletName = "New Outlet Name";
        assertEquals(expectedOutletName, outletDetailsPanelHandle.getOutletName());

        // Announcement Updated
        postNow(announcementChangedEventStub);
        String expectedAnnouncement = "New Announcement";
        assertEquals(expectedAnnouncement, outletDetailsPanelHandle.getAnnouncement());

    }

}
