package structure.com.foodportal.helper;

public class DataSyncEvent {
    private final int syncStatusMessage;
    public DataSyncEvent(int syncStatusMessage) {
        this.syncStatusMessage = syncStatusMessage;
    }
    public int getSyncStatusMessage() {
        return syncStatusMessage;
    }
}
