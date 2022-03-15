package kr.buttercorp.firstproject.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@NoArgsConstructor
@Slf4j
public class FileSystemStorageService {
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("업로드 경로 생성을 실패했습니다.");
        }
    }

    public void store(MultipartFile file) {
        store(file, Paths.get(""));
    }

    public void store(MultipartFile file, Path additionalPath) {
        try {
            if (file.isEmpty()) throw new Exception("File is Empty");

            /*--- 업로드 폴더 없으면 생성 ---*/
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) init();

            Path fullPath = Paths.get(root.toString(), additionalPath.toString());
            if (!Files.exists(fullPath)) Files.createDirectories(fullPath);

            try (InputStream stream = file.getInputStream()) {
                Files.copy(stream, fullPath.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            }
            log.info("file.getOriginalFilename(): {}", file.getOriginalFilename());
            log.info("root: {}", root);
            log.info("root.resolve(file.getOriginalFilename()): {}", root.resolve(file.getOriginalFilename()));
        } catch (Exception ex) {
            throw new RuntimeException("파일 저장을 실패했습니다.\n" + ex.getMessage());
        }
    }

    public Path load(String filename) {
        return Paths.get(uploadPath).resolve(filename);
    }

    public Resource loadAsResource(Path path) {
        return loadAsResource(path.toString());
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable())
                return resource;
            else
                throw new RuntimeException(filename + " => 해당 파일을 읽을 수 없습니다");

        } catch (MalformedURLException e) {
            throw new RuntimeException(filename + " => 해당 파일을 읽을 수 없습니다");
        }
    }
}
