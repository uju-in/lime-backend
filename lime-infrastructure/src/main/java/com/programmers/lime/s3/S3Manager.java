package com.programmers.lime.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Manager {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void uploadFile(
		final MultipartFile multipartFile,
		final String directory,
		final String fileName
	) throws IOException {
		final File uploadFile = convert(multipartFile)
			.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

		upload(uploadFile, directory, fileName);
	}

	private void upload(
		final File uploadFile,
		final String directory,
		final String fileName
	) {
		final String fileNameWithDir = getFileNameWithDir(directory, fileName);
		putS3(uploadFile, fileNameWithDir);
		removeNewFile(uploadFile);
	}
	private void putS3(
		final File uploadFile,
		final String fileName
	) {
		amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
	}

	private void removeNewFile(final File targetFile) {
		if (targetFile.delete()) {
			log.info("파일이 삭제되었습니다.");
		} else {
			log.info("파일이 삭제되지 못했습니다.");
		}
	}

	private Optional<File> convert(final MultipartFile file) throws IOException {
		final File convertFile = new File(file.getOriginalFilename());
		if(convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}

			return Optional.of(convertFile);
		}

		return Optional.empty();
	}

	public void deleteFile(
		final String directory,
		final String fileName
	) {
		final String fileNameWithDir = getFileNameWithDir(directory, fileName);
		amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileNameWithDir));
	}

	public URL getUrl(String directory, String fileName) {
		String fileNameWithDir = getFileNameWithDir(directory, fileName);
		return amazonS3.getUrl(bucket, fileNameWithDir);
	}

	private String getFileNameWithDir(final String directory, final String fileName) {
		return directory + "/" + fileName;
	}
}
