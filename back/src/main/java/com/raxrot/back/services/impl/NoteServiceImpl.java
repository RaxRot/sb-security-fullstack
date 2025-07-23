package com.raxrot.back.services.impl;

import com.raxrot.back.configurations.AppConfig;
import com.raxrot.back.exceptions.ApiException;
import com.raxrot.back.models.Note;
import com.raxrot.back.repositories.NoteRepository;
import com.raxrot.back.services.NoteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note createNoteForUser(String username, String content) {
        Note note = new Note();
        note.setContent(content);
        note.setOwnerUsername(username);
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }

    @Override
    public List<Note> getNotesForUser(String username) {
        List<Note> personalNotes = noteRepository
                .findByOwnerUsername(username);
        return personalNotes;
    }

    @Override
    public Note updateNoteForUser(Long noteId, String content, String username) {
        Note note = noteRepository.findById(noteId).orElseThrow(()
                -> new ApiException(AppConfig.NOTE_ERROR_NOT_FOUND));

        //temp protection
        if (!note.getOwnerUsername().equals(username)) {
            throw new ApiException(AppConfig.NOTE_ERROR_UPDATE_FORBIDDEN);
        }

        note.setContent(content);
        Note updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ApiException(AppConfig.NOTE_ERROR_NOT_FOUND));

        //temp protection
        if (!note.getOwnerUsername().equals(username)) {
            throw new ApiException(AppConfig.NOTE_ERROR_DELETE_FORBIDDEN);
        }
        noteRepository.delete(note);
    }
}
