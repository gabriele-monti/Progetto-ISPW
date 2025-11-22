package it.foodmood.persistence.filesystem;

public final class FileSystemPaths {
    private static final String CSV_DIRECTORY = "data/csv/";

    public static final String USERS = CSV_DIRECTORY + "users.csv";
    public static final String CREDENTIALS = CSV_DIRECTORY + "credentials.csv";
    public static final String DISHES = CSV_DIRECTORY + "dishes.csv";
    public static final String INGREDIENTS = CSV_DIRECTORY + "ingredients.csv";

    private FileSystemPaths(){}

}
