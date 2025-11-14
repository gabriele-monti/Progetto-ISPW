package it.foodmood.bean;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DishBean {
    
    private String id;
    private String name;
    private String description;
    private String courseType;
    private String dietCategory;
    private BigDecimal price;
    private String imageUri;
    private List<IngredientPortionBean> ingredients;

    // Costruttore
    public DishBean(){this.ingredients = new ArrayList<>();}

    // Getter
    public String getId() {return id;}

    public String getName(){ return name;}

    public String getDescription(){ return description;}

    public String getCourseType(){ return courseType;}

    public String getDietCategory(){ return dietCategory;}

    public BigDecimal getPrice(){ return price;}

    public String getImageUri(){ return imageUri;}

    public List<IngredientPortionBean> getIngredients(){ return new  ArrayList<>(ingredients);}

    // Setter + validazione sintattica

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        if(!isValidName(name)){
            throw new IllegalArgumentException("Il nome del piatto non deve essere vuoto.");
        }
        this.name = name.trim().toUpperCase();
    }

    public void setDescription(String description){
        this.description = (description != null && !description.isBlank()) ? description.trim().toUpperCase() : null;
    }

    public void setCourseType(String courseType){
        if(!isValidEnumString(courseType)){
            throw new IllegalArgumentException("Tipologia portata non valida.");
        }
        this.courseType = courseType.trim().toUpperCase();
    }

    public void setDietCategory(String dietCategory){
        if(!isValidEnumString(dietCategory)){
            throw new IllegalArgumentException("Categoria dietetica non valida");
        }
        this.dietCategory = dietCategory.trim().toUpperCase();
    }

    public void setPrice(BigDecimal price){
        if(!isValidPrice(price)){
            throw new IllegalArgumentException("Il prezzo deve essere > 0");
        }
        this.price = price.setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public void setImageUri(String imageUri){
        if(imageUri != null && !imageUri.isBlank()){
            if(!isValidUri(imageUri)){
                throw new IllegalArgumentException("Percorso dell'immagine non valido.");
            }
            this.imageUri = imageUri.trim();
        } else {
            this.imageUri = null;
        }
    }

    public void setIngredients(List<IngredientPortionBean> ingredients){
        if(ingredients == null){
            this.ingredients = new ArrayList<>();
            return;
        }
        for(IngredientPortionBean i : ingredients){
            if(i == null){
                throw new IllegalArgumentException("La lista contiene ingredienti nulli!");
            }
        }
        this.ingredients = new ArrayList<>(ingredients);
    }

    public void addIngredient(IngredientPortionBean ingredient){
        Objects.requireNonNull(ingredient, "L'ingrediente non può essere nullo.");
        this.ingredients.add(ingredient);
    }

    // Metodi privati per la validazione sintattica

    private boolean isValidName(String name){
        return name != null && !name.isBlank() && name.length() <= 50;
    }

    private boolean isValidEnumString(String type){
        return type != null && !type.isBlank();
    }

    private boolean isValidPrice(BigDecimal price){
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isValidUri(String uri){
        try {
            URI.create(uri);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
