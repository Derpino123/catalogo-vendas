package br.com.davsantos.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.davsantos.services.exceptions.FileException;

@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String extensao = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if(!"png".equals(extensao) && !"jpg".equals(extensao)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}
		
		
		try {
			
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if("pgn".equals(img)) {
				img = pngToJpg(img);
			}
			
			return img;
			
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo!");
		}
		
	}

	public BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		
		return jpgImage;
	}
	
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(img, extension, output);
			return new ByteArrayInputStream(output.toByteArray());
			
		} catch(IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
}