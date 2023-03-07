package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public void addNote(String title, String description, String username){
        Integer userId = userMapper.getUser(username).getUserId();
        noteMapper.addNote(new Note(0, title, description, userId));
    }

    public List<Note> getNotesByUser(int id){
        return noteMapper.getAllNoteByUserId(id);
    }

    public Note getNoteById(int noteId){
        return noteMapper.getNoteByUserId(noteId);
    }

    public void deleteNote(int noteId){
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(int noteId, String title, String description ){
        noteMapper.updateNote(noteId, title, description);
    }

}
