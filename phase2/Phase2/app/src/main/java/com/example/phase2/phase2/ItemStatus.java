package com.example.phase2.phase2;

import java.io.Serializable;

public enum ItemStatus implements Serializable {
    AVAILABLE,
    REQUESTED,
    UNAVAILABLE,
    INACTIVE,
    REMOVED
}