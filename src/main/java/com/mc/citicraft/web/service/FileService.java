package com.mc.citicraft.web.service;

import com.mc.citicraft.web.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileService implements IFileService {

    @Value("${app.upload.dir:${user.home}")
    private String uploadDir;

    @Autowired
    private IConnectorService service;

    @Override
    public void uploadFile(MultipartFile file) {
        Path copyLocation;

        try {
            copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            initConnector(service, Files.newInputStream(copyLocation));
        } catch (Exception ex) {
            throw new FileStorageException(ex.getMessage());
        }
    }

    public void initConnector(IConnectorService s, InputStream resource) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8))) {
            List<AbstractMap.SimpleEntry<String, String>> data = reader
                    .lines()
                    .filter(line -> line.contains(","))
                    .filter(line -> line.split(",").length == 2)
                    .map(line -> {
                        String[] args = line.split(",");
                        return new AbstractMap.SimpleEntry<>(args[0].trim(), args[1].trim());
                    })
                    .collect(Collectors.toList());

            s.init(data);
        } catch (IOException ex) {
            throw new FileStorageException(ex.getMessage());
        }
    }
}
