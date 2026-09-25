package at.mci.ct.smtimerapp.backend.timer;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "timer_entries")
public class TimerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long durationMs;

    private LocalDateTime createdAt;

    public TimerEntry() {
    }

    public TimerEntry(Long durationMs) {
        this.durationMs = durationMs;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}