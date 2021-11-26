package com.tuannq.store.controller.admin;

import com.tuannq.store.entity.Image;
import com.tuannq.store.exception.BadRequestException;
import com.tuannq.store.exception.InternalServerException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.UUID;

import static com.tuannq.store.config.DefaultVariable.IMAGE_EXTENSION;

@RestController
public class ManageImageController {
    private static final String UPLOAD_DIR = System.getProperty("user.home") + "/media/upload";

    private final ImageService imageService;

    @Autowired
    public ManageImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/api/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        // Create folder to save file if not exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        if (originalFilename != null && originalFilename.length() > 0) {
            if (!IMAGE_EXTENSION.contains(extension)){
                throw new BadRequestException("Không hỗ trợ định dạng file này");
            }
            try {
                Image img = new Image();
                img.setName(file.getName());
                img.setSize(file.getSize());
                img.setType(extension);
                img.setUploadedAt(new Timestamp(System.currentTimeMillis()));
                img.setUploadedBy(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
                img.setId(UUID.randomUUID().toString());
                String link = "/media/static/" + img.getId() + "." + extension;
                img.setLink(link);

                // Create file
                File serverFile = new File(UPLOAD_DIR + "/" + img.getId() + "." + extension);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();

                imageService.save(img);
                return ResponseEntity.ok(link);
            } catch (Exception e) {
                throw new InternalServerException("Lỗi khi upload file");
            }
        }

        throw new BadRequestException("File không hợp lệ");
    }

    @GetMapping("/media/static/{filename:.+}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new NotFoundException("File không tồn tại");
        }

        UrlResource resource;
        try {
            resource = new UrlResource(file.toURI());
        } catch (MalformedURLException e) {
            throw new NotFoundException("File không tồn tại");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @DeleteMapping("/api/delete-image/{filename:.+}")
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {
        imageService.deleteImage(UPLOAD_DIR, filename);
        return ResponseEntity.ok("Xóa thành công");
    }
}
