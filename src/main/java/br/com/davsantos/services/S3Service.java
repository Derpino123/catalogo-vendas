package br.com.davsantos.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.davsantos.services.exceptions.FileException;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucketName;

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream input = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();

			
			return uploadFile(input, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO : " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream input, String fileName, String contentType) {

		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentType(contentType);
		LOG.info("Iniciando UPLOAD!");
		s3Client.putObject(bucketName, fileName, input, meta);
		LOG.info("UPLOAD finalizado!");

		try {

			return s3Client.getUrl(bucketName, fileName).toURI();

		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL para URI");
		}
	}
}
