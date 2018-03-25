package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author hzxcaryn
/**
 * A handler for the {@code OutletDetailsPanel} of the UI
 */
public class OutletDetailsPanelHandle extends NodeHandle<Node> {

    public static final String OUTLET_ID = "#outletDetailsPanelPlaceholder";

    private static final String OUTLET_NAME_ID = "#outletNamePanelHeader";
    private static final String OUTLET_INFORMATION_ID = "#outletInformation";
    private static final String OUTLET_ANNOUNCEMENT_ID = "#announcement";

    private final Label outletNameNode;
    private final Label outletInformationNode;
    private final Label outletAnnouncementNode;

    private String lastRememberedOutletName;
    private String lastRememberedOutletInformation;

    public OutletDetailsPanelHandle(Node outletDetailsNode) {
        super(outletDetailsNode);

        this.outletNameNode = getChildNode(OUTLET_NAME_ID);
        this.outletInformationNode = getChildNode(OUTLET_INFORMATION_ID);
        this.outletAnnouncementNode = getChildNode(OUTLET_ANNOUNCEMENT_ID);
    }

    /**
     * @return the outlet name in outlet panel.
     */
    public String getOutletName() {
        return outletNameNode.getText();
    }

    /**
     * @return the outlet info in outlet panel.
     */
    public String getAnnouncement() {
        return outletAnnouncementNode.getText();
    }

    /**
     * @return the outlet info in outlet panel.
     */
    public String getOutletInformation() {
        return outletInformationNode.getText();
    }

    /**
     * Remembers the outlet name in the outlet panel.
     */
    public void rememberOutletName() {
        lastRememberedOutletName = getOutletName();
    }

    /**
     * Returns true if the current outlet name is different from the value remembered by the most recent
     * {@code rememberOutletName()} call.
     */
    public boolean isOutletNameChanged() {
        return !lastRememberedOutletName.equals(getOutletName());
    }

    /**
     * Remembers the outlet info in the outlet panel.
     */
    public void rememberOutletInformation() {
        lastRememberedOutletInformation = getOutletInformation();
    }

    /**
     * Returns true if the current outlet information is different from the value remembered by the most
     * recent {@code rememberOutletInformation()} call.
     */
    public boolean isOutletInformationChanged() {
        return !lastRememberedOutletInformation.equals(getOutletInformation());
    }

}
