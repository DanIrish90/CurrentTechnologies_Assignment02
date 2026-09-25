package at.mci.ct.smtimerapp.backend.timer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimerEntryRepository extends JpaRepository<TimerEntry, Long> {

    List<TimerEntry> findAllByOrderByCreatedAtDesc();
}