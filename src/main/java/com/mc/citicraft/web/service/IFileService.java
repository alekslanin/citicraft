package com.mc.citicraft.web.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    void uploadFile(MultipartFile file);
}
