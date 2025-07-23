package com.raxrot.back.services;

import com.raxrot.back.models.Note;

import java.util.List;

public interface NoteService {
    Note createNoteForUser(String username, String content);

    List<Note> getNotesForUser(String username);

    Note updateNoteForUser(Long noteId, String content, String username);

    void deleteNoteForUser(Long noteId, String username);
}
