package com.raxrot.back.services.impl;

import com.raxrot.back.exceptions.ApiException;
import com.raxrot.back.models.Note;
import com.raxrot.back.repositories.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    private Note note;

    @BeforeEach
    void setUp() {
        note = new Note();
        note.setId(1L);
        note.setContent("Test note");
        note.setOwnerUsername("testUser");
    }

    @Test
    @DisplayName("Create note for user")
    void createNoteForUser() {
        BDDMockito.given(noteRepository.save(Mockito.any(Note.class))).willReturn(note);

        Note created = noteService.createNoteForUser("testUser", "Test note");

        assertThat(created).isNotNull();
        assertThat(created.getContent()).isEqualTo("Test note");
        assertThat(created.getOwnerUsername()).isEqualTo("testUser");
    }

    @Test
    @DisplayName("Get notes for user")
    void getNotesForUser() {
        List<Note> notes = List.of(note);
        BDDMockito.given(noteRepository.findByOwnerUsername("testUser")).willReturn(notes);

        List<Note> userNotes = noteService.getNotesForUser("testUser");

        assertThat(userNotes).hasSize(1);
        assertThat(userNotes.get(0).getOwnerUsername()).isEqualTo("testUser");
    }

    @Test
    @DisplayName("Update note for user - success")
    void updateNoteForUser() {
        BDDMockito.given(noteRepository.findById(1L)).willReturn(Optional.of(note));
        BDDMockito.given(noteRepository.save(Mockito.any(Note.class))).willReturn(note);

        Note updated = noteService.updateNoteForUser(1L, "Updated content", "testUser");

        assertThat(updated.getContent()).isEqualTo("Updated content");
    }

    @Test
    @DisplayName("Update note for user - wrong owner")
    void updateNoteForWrongUser() {
        note.setOwnerUsername("someoneElse");
        BDDMockito.given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        assertThatThrownBy(() ->
                noteService.updateNoteForUser(1L, "Updated content", "testUser")
        ).isInstanceOf(ApiException.class)
                .hasMessageContaining("You cannot edit someone else's note");
    }

    @Test
    @DisplayName("Delete note for user - success")
    void deleteNoteForUser() {
        BDDMockito.given(noteRepository.findById(1L)).willReturn(Optional.of(note));
        BDDMockito.willDoNothing().given(noteRepository).delete(note);

        noteService.deleteNoteForUser(1L, "testUser");

        BDDMockito.then(noteRepository).should().delete(note);
    }

    @Test
    @DisplayName("Delete note for user - wrong owner")
    void deleteNoteForWrongUser() {
        note.setOwnerUsername("notYou");
        BDDMockito.given(noteRepository.findById(1L)).willReturn(Optional.of(note));

        assertThatThrownBy(() ->
                noteService.deleteNoteForUser(1L, "testUser")
        ).isInstanceOf(ApiException.class)
                .hasMessageContaining("You cannot delete someone else's note");
    }
}