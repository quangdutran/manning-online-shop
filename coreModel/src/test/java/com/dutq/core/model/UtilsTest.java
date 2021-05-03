package com.dutq.core.model;

import com.dutq.core.utils.Utils;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
public class UtilsTest {
    @Test
    public void testValidateImageFileExtension() {
        MultipartFile file = mock(MultipartFile.class);
        assertFalse(Utils.validateImageFileExtension(file));
    }
}
