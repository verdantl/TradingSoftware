package com.example.phase2.deliverable;

import java.io.Serializable;

public enum ItemStatus implements Serializable {
    AVAILABLE,
    REQUESTED,
    UNAVAILABLE,
    INACTIVE_REQ,
    INACTIVE_AVA,
    INACTIVE,
    REMOVED,
    FROZEN,
    FROZEN_REQ
}
