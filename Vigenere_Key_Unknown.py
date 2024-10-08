from collections import Counter

# Function to encrypt plaintext
def encrypt_vigenere(plain_text, key):
    encrypted_text = []
    key_len = len(key)
    
    for i, char in enumerate(plain_text):

        if char.isalpha():
            shift = ord(key[i % key_len].upper()) - ord('A')
            encrypted_char = chr(((ord(char.upper()) - ord('A') + shift) % 26) + ord('A'))
            encrypted_text.append(encrypted_char)

        else:
            encrypted_text.append(char)  # Non-alphabetic characters are not encrypted
    
    return ''.join(encrypted_text)

# Function to decrypt ciphertext without knowing the key using frequency analysis
def frequency_analysis_decrypt(ciphertext):
    # Frequency analysis to estimate the key
    # We assume English letters with 'E' being the most frequent
    
    def get_most_frequent_char(text):
        # Count frequency of letters in the text
        frequency = Counter([c for c in text if c.isalpha()])
        most_frequent_char = frequency.most_common(1)[0][0]  
        return most_frequent_char

    estimated_key = []
    key_len = 3  # Assume we know key length

    for i in range(key_len):
        # Extract every 3rd character starting from index i

        segment = ''.join([ciphertext[j] for j in range(i, len(ciphertext), key_len)])
        most_frequent_char = get_most_frequent_char(segment)
        shift = (ord(most_frequent_char) - ord('E')) % 26
        estimated_key.append(chr(ord('A') + shift))

    estimated_key = ''.join(estimated_key)

    print(f"Estimated Key: {estimated_key}")
    
    # Decrypt using estimated key
    return decrypt_vigenere(ciphertext, estimated_key)

# Vigenère decryption using a given key
def decrypt_vigenere(ciphertext, key):
    decrypted_text = []
    key_len = len(key)
    
    for i, char in enumerate(ciphertext):
        if char.isalpha():
            shift = ord(key[i % key_len].upper()) - ord('A')
            decrypted_char = chr(((ord(char.upper()) - ord('A') - shift + 26) % 26) + ord('A'))
            decrypted_text.append(decrypted_char)
        else:
            decrypted_text.append(char)  # Non-alphabetic characters are not decrypted
    
    return ''.join(decrypted_text)

key = "KEY"  # Fixed key for encryption
plain_text = "Once more she found herself in the long hall, and close to the little glass table. 'Now, I’ll manage better this time,' she said to herself, and began by taking the little golden key, and unlocking the door that led into the garden. Then she set to work nibbling at the mushroom (she had kept a piece of it in her pocket) till she was about a foot high: then she walked down the little passage: and then—she found herself at last in the beautiful garden, among the bright flower-beds and the cool fountains. A large rose-tree stood near the entrance of the garden: the roses growing on it were white, but there were three gardeners at it, busily painting them red. Alice thought this a very curious thing, and she went nearer to watch them, and just as she came up to them she heard one of them say, 'Look out now, Five! Don’t go splashing paint over me like that!' 'I couldn’t help it,' said Five, in a sulky tone; 'Seven jogged my elbow.' On which Seven looked up and said, 'That’s right, Five! Always lay the blame on others!' 'You’d better not talk!' said Five. 'I heard the Queen say only yesterday you deserved to be beheaded!' 'What for?' said the one who had spoken first. 'That’s none of your business, Two!' said Seven. 'Yes, it is his business!' said Five, 'and I’ll tell him—it was for bringing the cook tulip-roots instead of onions.' Seven flung down his brush, and had just begun 'Well, of all the unjust things—' when his eye chanced to fall upon Alice, as she stood watching them, and he checked himself suddenly: the others looked round also, and all of them bowed low. 'Would you tell me,' said Alice, a little timidly, 'why you are painting those roses?' Five and Seven said nothing, but looked at Two. Two began in a low voice, 'Why, the fact is, you see, Miss, this here ought to have been a red rose-tree, and we put a white one in by mistake; and if the Queen was to find it out, we should all have our heads cut off, you know. So you see, Miss, we’re doing our best, afore she comes, to—' At this moment Five, who had been anxiously looking across the garden, called out 'The Queen! The Queen!' and the three gardeners instantly threw themselves flat upon their faces. There was a sound of many footsteps, and Alice looked round, eager to see the Queen. First came ten soldiers carrying clubs; these were all shaped like the three gardeners, oblong and flat, with their hands and feet at the corners: next the ten courtiers; these were ornamented all over with diamonds, and walked two and two, as the soldiers did. After these came the royal children; there were ten of them, and the little dears came jumping merrily along hand in hand, in couples: they were all ornamented with hearts. Next came the guests, mostly Kings and Queens, and among them Alice recognised the White Rabbit: it was talking in a hurried nervous manner, smiling at everything that was said, and went by without noticing her. Then followed the Knave of Hearts, carrying the King’s crown on a crimson velvet cushion; and, last of all this grand procession, came THE KING AND QUEEN OF HEARTS. Alice was rather doubtful whether she ought not to lie down on her face like the three gardeners, but she could not remember ever having heard of such a rule at processions; 'and besides, what would be the use of a procession,' thought she, 'if people had all to lie down upon their faces, so that they couldn’t see it?' So she stood still where she was, and waited. When the procession came opposite to Alice, they all stopped and looked at her, and the Queen said severely, 'Who is this?' She said it to the Knave of Hearts, who only bowed and smiled in reply. 'Idiot!' said the Queen, tossing her head impatiently; and, turning to Alice, she went on, 'What’s your name, child?' 'My name is Alice, so please your Majesty,' said Alice very politely; but she added, to herself, 'Why, they’re only a pack of cards, after all. I needn’t be afraid of them!' 'And who are these?' said the Queen, pointing to the three gardeners who were lying round the rose-tree; for, you see, as they were lying on their faces, and the pattern on their backs was the same as the rest of the pack, she could not tell whether they were gardeners, or soldiers, or courtiers, or three of her own children. 'How should I know?' said Alice, surprised at her own courage. 'It’s no business of mine.' The Queen turned crimson with fury, and, after glaring at her for a moment like a wild beast, screamed 'Off with her head! Off—'"

# Encrypt the plaintext
ciphertext = encrypt_vigenere(plain_text, key)
print(f"Encrypted text: {ciphertext}")

print()

# Decrypt the ciphertext using frequency analysis
decrypted_text = frequency_analysis_decrypt(ciphertext)
print(f"Decrypted text (using frequency analysis): {decrypted_text}")