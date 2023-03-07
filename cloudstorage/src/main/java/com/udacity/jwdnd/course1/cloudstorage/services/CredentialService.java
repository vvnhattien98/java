package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public void addCredential(String url, String username,String credentialName,String key, String password){
        Credential credential = new Credential(0, url,credentialName,key,password, userMapper.getUser(username).getUserId());
        credentialMapper.addCredential(credential);
    }

    public List<Credential> getAllCredentialsByUserId(Integer userId){
        return credentialMapper.getAllCredentials(userId);
    }

    public Credential getCredential(Integer credentialId){
        return credentialMapper.getCredential(credentialId);
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.deleleCredential(credentialId);
    }

    public void updateCredential(Integer credentialId, String url, String key, String password, String newUsername){
        credentialMapper.updateCrdential(credentialId,url,key,password,newUsername);
    }
}
