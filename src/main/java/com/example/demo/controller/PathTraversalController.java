package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.owasp.encoder.Encode;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RestController
public class PathTraversalController {


    @GetMapping("/file")
        Path baseDir = Path.of("/tmp").toAbsolutePath().normalize();
        Path resolved = baseDir.resolve(filename).normalize();
        if (!resolved.startsWith(baseDir)) {
            throw new IOException("Invalid filename: path traversal attempt detected");
        }
        log.info("Getting File: {}", resolved);
        return Files.readString(resolved);
        return Encode.forHtml(Files.readString(Path.of("/tmp/" + filename)));
    }


    @GetMapping(value = "/file", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> readFile_secure(@RequestParam String filename) throws IOException {

        Path path = Path.of("/tmp", filename).normalize();

        log.info("File Path: {}", path);

        String content = Files.readString(path);

        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(content);
    }
}
