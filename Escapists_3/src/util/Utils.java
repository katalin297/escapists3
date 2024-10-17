package util;

public class Utils {
	public static String GetFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        
        // Check if there's no dot or if it's the last character in the file name (no extension)
        if (lastDotIndex == -1 || lastDotIndex == filePath.length() - 1) {
            return ""; // No extension found
        }

        return filePath.substring(lastDotIndex + 1);
    }
}
