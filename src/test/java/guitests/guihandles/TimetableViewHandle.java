package guitests.guihandles;

import javafx.scene.Node;

//@@author hzxcaryn
/**
 * A handler for the {@code TimetableView} of the UI
 */
public class TimetableViewHandle extends NodeHandle<Node> {

    public static final String TIMETABLE_ID = "#timetableViewPlaceholder";

    protected TimetableViewHandle(Node rootNode) {
        super(rootNode);
    }
}
