package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.api.ClientEvents;
import org.jetbrains.annotations.ApiStatus;

public class CalculatePlayerTurnEvent extends CarminiteEvent {
    private double mouseSensitivity;
    private boolean cinematicCameraEnabled;

    @ApiStatus.Internal
    public CalculatePlayerTurnEvent(double mouseSensitivity, boolean cinematicCameraEnabled) {
        setMouseSensitivity(mouseSensitivity);
        setCinematicCameraEnabled(cinematicCameraEnabled);
    }

    public double getMouseSensitivity() {
        return mouseSensitivity;
    }

    public void setMouseSensitivity(double mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    public boolean getCinematicCameraEnabled() {
        return cinematicCameraEnabled;
    }

    public void setCinematicCameraEnabled(boolean cinematicCameraEnabled) {
        this.cinematicCameraEnabled = cinematicCameraEnabled;
    }

    @Override
    public CalculatePlayerTurnEvent post() {
        ClientEvents.CALCULATE_PLAYER_TURN.invoker().getTurnPlayerValues(this);
        return this;
    }
}