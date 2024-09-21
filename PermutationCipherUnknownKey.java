import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PermutationCipherUnknownKey {

    // Fixed permutation key for encryption
    private static int[] encryptionKey = {2, 0, 3, 1}; // Same as before

    // Encryption function using a known key
    public static String encrypt(String plaintext) {
        int n = encryptionKey.length;
        StringBuilder encrypted = new StringBuilder();

        // Process text in blocks of 'key.length'
        for (int i = 0; i < plaintext.length(); i += n) {
            char[] block = new char[n];

            // Fill the block with characters from the plaintext or padding spaces
            for (int j = 0; j < n; j++) {
                if (i + j < plaintext.length()) {
                    block[j] = plaintext.charAt(i + j);
                } else {
                    block[j] = ' '; // padding with spaces if the block is incomplete
                }
            }

            // Apply the permutation to the block
            char[] encryptedBlock = new char[n];
            for (int j = 0; j < n; j++) {
                encryptedBlock[j] = block[encryptionKey[j]];
            }

            // Append the encrypted block to the result
            encrypted.append(new String(encryptedBlock));
        }

        return encrypted.toString();
    }

    // Function to decrypt the ciphertext using a given permutation key
    public static String decryptWithKey(String ciphertext, int[] key) {
        int n = key.length;
        StringBuilder decrypted = new StringBuilder();

        // Reverse key to undo permutation
        int[] reverseKey = new int[n];
        for (int i = 0; i < n; i++) {
            reverseKey[key[i]] = i;
        }

        // Process text in blocks of 'key.length'
        for (int i = 0; i < ciphertext.length(); i += n) {
            char[] block = new char[n];

            // Fill the block with characters from the ciphertext
            for (int j = 0; j < n; j++) {
                if (i + j < ciphertext.length()) {
                    block[j] = ciphertext.charAt(i + j);
                } else {
                    block[j] = ' '; // Handle incomplete blocks with padding spaces
                }
            }

            // Apply the reverse permutation to the block
            char[] decryptedBlock = new char[n];
            for (int j = 0; j < n; j++) {
                decryptedBlock[j] = block[reverseKey[j]];
            }

            // Append the decrypted block to the result
            decrypted.append(new String(decryptedBlock));
        }

        return decrypted.toString().trim(); // Trim extra padding spaces
    }

    // Function to generate all permutations of the key
    public static void generatePermutations(int[] arr, int index, List<int[]> permutations) {
        if (index == arr.length - 1) {
            permutations.add(arr.clone()); // Add the current permutation to the list
            return;
        }

        for (int i = index; i < arr.length; i++) {
            swap(arr, index, i);
            generatePermutations(arr, index + 1, permutations);
            swap(arr, index, i); // Backtrack
        }
    }

    // Helper function to swap elements in an array
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input for plaintext
        System.out.println("Enter plaintext to encrypt:");
        String plaintext = scanner.nextLine();

        // Encrypt the plaintext using the known encryption key
        String ciphertext = encrypt(plaintext);
        System.out.println("\nEncrypted ciphertext: " + ciphertext);

        // Known block size (key length)
        int blockSize = encryptionKey.length;

        // Generate all possible permutations of the key 
        int[] key = new int[blockSize];
        for (int i = 0; i < blockSize; i++) {
            key[i] = i;
        }

        List<int[]> permutations = new ArrayList<>();
        generatePermutations(key, 0, permutations);

        // Try each permutation to decrypt the ciphertext
        System.out.println("\nAttempting all possible key permutations:");

        boolean matchFound = false;
        for (int[] permutation : permutations) {
            String decryptedText = decryptWithKey(ciphertext, permutation);
            System.out.println("Key: " + java.util.Arrays.toString(permutation) + " -> Decrypted: " + decryptedText);

            // Check if the decrypted text matches the original plaintext
            if (decryptedText.equals(plaintext)) {
                System.out.println("\nMatch found with key: " + java.util.Arrays.toString(permutation));
                System.out.println("Decrypted text matches the original plaintext!");
                matchFound = true;
                break;
            }
        }

        if (!matchFound) {
            System.out.println("\nNo match found for the decrypted text.");
        }

        scanner.close();
    }
}
