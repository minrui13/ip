package yappa.task;

import yappa.exception.YappaException;

public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String code;

    TaskType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static TaskType fromStorageCode(String code) throws YappaException {
        for (TaskType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new YappaException("Unknown task type: " + code);
    }
}