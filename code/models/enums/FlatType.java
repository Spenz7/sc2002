package models.enums;

public enum FlatType {
    THREE_ROOM("3-room"),
    FOUR_ROOM("4-room"),
    FIVE_ROOM("5-room"),
    EXECUTIVE("Executive"),
    STUDIO("Studio");

    private final String displayName;

    FlatType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
