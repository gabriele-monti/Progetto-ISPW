package it.foodmood.domain.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.nio.file.Paths;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class ImageTest {
    @Test
    void of_createImageFromLocalResource() throws Exception{
        var url = Objects.requireNonNull(getClass().getResource("/img/test/test.png"), "Risorsa '/img/test/test.png' non trovata nel percorso");

        URI uri = url.toURI();
        Image img = Image.of(uri);

        assertEquals(uri, img.getUri());
        assertEquals("test.png", Paths.get(img.getUri()).getFileName().toString());
    }

    @Test
    void of_nullUri(){
        assertThrows(NullPointerException.class, () -> Image.of(null));
    }

    @Test
    void of_fileUri(){
        URI uri = Paths.get("src/test/resources/img/test/test.png").toUri();
        Image img = Image.of(uri);
        assertEquals(uri, img.getUri());
    }
}
