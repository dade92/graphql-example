package utils;

public class AuthorNameNormalizer {

    public String get(String authorName) {
        if (authorName == null || authorName.isEmpty()) {
            return "Unknown Author";
        }
        String[] words = authorName.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    sb.append(word.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

}