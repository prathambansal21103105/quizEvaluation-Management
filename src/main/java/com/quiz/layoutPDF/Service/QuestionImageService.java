package com.quiz.layoutPDF.Service;
import com.quiz.layoutPDF.Repository.QuestionImageRepository;
import com.quiz.layoutPDF.models.QuestionImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;

@Service
public class QuestionImageService {
    private final QuestionImageRepository questionImageRepository;

    public QuestionImageService(QuestionImageRepository questionImageRepository) {
        this.questionImageRepository = questionImageRepository;
    }

    public String saveImage(MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        QuestionImage questionImage = new QuestionImage(imageData);
        return questionImageRepository.save(questionImage).getId();
    }

    public byte[] getImage(String id) {
        Optional<QuestionImage> image = questionImageRepository.findById(id);
        return image.map(QuestionImage::getImageData).orElse(null);
    }
}
