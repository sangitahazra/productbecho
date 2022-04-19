package form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class Product {
    private String code;

    private String description;

    private String name;

}
