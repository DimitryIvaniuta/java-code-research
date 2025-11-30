package com.code.research.springboot.profile;

public class FeatureToggle {

    private final boolean enabled;

    public FeatureToggle(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "FeatureToggle{enabled=" + enabled + '}';
    }
}
