package models.enums;

public enum FlatType {
    TWO_ROOM("2-room"),
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
    
    // Make sure this method is inside the enum body!
    public static FlatType fromString(String flatType) {
        for (FlatType type : FlatType.values()) {
            if (type.getDisplayName().equalsIgnoreCase(flatType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid flat type: " + flatType);
    }
}

//either 2 room or 3 room flat
