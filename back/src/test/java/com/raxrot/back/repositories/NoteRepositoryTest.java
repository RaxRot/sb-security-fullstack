package com.raxrot.back.repositories;

import com.raxrot.back.models.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class NoteRepositoryTest {
    @Autowired
    private NoteRepository noteRepository;

    private Note note;

    @BeforeEach
    void setUp() {
        note = new Note();
        note.setContent("Test note");
        note.setOwnerUsername("testUser");
    }

    @Test
    @DisplayName("Create note")
    void createNote() {
        Note savedNote = noteRepository.save(note);
        assertThat(savedNote.getId()).isNotNull();
        assertThat(savedNote.getContent()).isEqualTo("Test note");
    }

    @Test
    @DisplayName("Find all notes")
    void findAllNotes() {
        Note note1 = new Note();
        note1.setContent("Note 1");
        note1.setOwnerUsername("testUser");

        Note note2 = new Note();
        note2.setContent("Note 2");
        note2.setOwnerUsername("testUser");

        noteRepository.save(note1);
        noteRepository.save(note2);

        List<Note> notes = noteRepository.findAll();
        assertThat(notes).hasSize(2);
    }

    @Test
    @DisplayName("Find by owner username")
    void findByOwnerUsername() {
        noteRepository.save(note);
        List<Note> notes = noteRepository.findByOwnerUsername("testUser");

        assertThat(notes).hasSize(1);
        assertThat(notes.get(0).getContent()).isEqualTo("Test note");
    }

    @Test
    @DisplayName("Update note")
    void updateNote() {
        Note savedNote = noteRepository.save(note);
        savedNote.setContent("Updated content");
        Note updatedNote = noteRepository.save(savedNote);

        assertThat(updatedNote.getContent()).isEqualTo("Updated content");
    }

    @Test
    @DisplayName("Delete note")
    void deleteNote() {
        Note savedNote = noteRepository.save(note);
        noteRepository.delete(savedNote);

        Optional<Note> deleted = noteRepository.findById(savedNote.getId());
        assertThat(deleted).isNotPresent();
    }
}