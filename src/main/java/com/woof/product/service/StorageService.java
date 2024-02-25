package com.woof.product.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageService {

    private final Path rootLocation = Paths.get("src/main/resources/static/productImage");

    public String store(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID().toString() + ".png";
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file " + filename);
        }
        Path destinationFile = rootLocation.resolve(Paths.get(filename))
                .normalize().toAbsolutePath();
        file.transferTo(destinationFile);
        return filename;
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new IOException("Could not read file: " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
