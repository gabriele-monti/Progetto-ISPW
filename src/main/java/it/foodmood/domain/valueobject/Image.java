package it.foodmood.domain.valueobject;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

/*
 * Rappresenta il riferimento a un'immagine associata al piatto o al prodotto
 * Contiene solamente il percorso dell'immagine (URI), non i byte associati
*/

public final class Image{
    private final URI uri;

    private Image(URI uri){
        this.uri = Objects.requireNonNull(uri, "Percorso nullo!");
    }

    public static Image of(URI uri) { 
        return new Image(uri); 
    }

    public URI uri() { return uri; }

    public static Optional<Image> optional(URI uri){
        return Optional.ofNullable(uri).map(Image::of);
    }
}