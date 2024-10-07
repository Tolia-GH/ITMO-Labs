# generate key matrix
def generate_key_matrix(key):
    alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ"  # J和I合并
    key = key.upper()
    key = ''.join(sorted(set(key.upper()), key=lambda x: key.index(x)))  # 去除重复字母
    key_matrix = []
    key_matrix_row = []
    used = set()

    for letter in key:
        if letter not in used and letter != 'J':  # 忽略 'J'
            key_matrix_row.append(letter)
            used.add(letter)
        if key_matrix_row.__len__() == 5:
            key_matrix.append(key_matrix_row)
            key_matrix_row = []

    for letter in alphabet:
        if letter not in used:
            key_matrix_row.append(letter)
            used.add(letter)
        if key_matrix_row.__len__() == 5:
            key_matrix.append(key_matrix_row)
            key_matrix_row = []

    return key_matrix


# find position of character in key matrix
def find_position(key_matrix, letter):
    for row in range(5):
        for col in range(5):
            if key_matrix[row][col] == letter:
                return row, col
    return None


# preprocess
def preprocess_text(text):
    text = text.upper().replace("J", "I").replace(' ', '')  # replace J with I and remove spaces
    processed_text = ""
    i = 0

    while i < len(text):
        pair_l = text[i]
        pair_r = text[i + 1] if (i + 1) < len(text) else 'X'  # add 'X' if len(text)%2 == 1

        if pair_l == pair_r:  # if pair_l == pair_r, add 'X' to pairs
            processed_text += pair_l + 'X'
            i += 1
        else:
            processed_text += pair_l + pair_r
            i += 2

    if len(processed_text) % 2 != 0:
        processed_text += 'X'

    return processed_text


# encrypt process
def encrypt(text, key_matrix):
    encrypted_text = ""
    text = preprocess_text(text)  # 预处理文本

    for i in range(0, len(text), 2):
        row1, col1 = find_position(key_matrix, text[i])
        row2, col2 = find_position(key_matrix, text[i + 1])

        if row1 == row2:  # same row
            pair_l = key_matrix[row1][(col1 + 1) % 5]
            pair_r = key_matrix[row2][(col2 + 1) % 5]
        elif col1 == col2:  # same col
            pair_l = key_matrix[(row1 + 1) % 5][col1]
            pair_r = key_matrix[(row2 + 1) % 5][col2]
        else:  # square
            pair_l = key_matrix[row1][col2]
            pair_r = key_matrix[row2][col1]

        encrypted_text += pair_l + pair_r

    return encrypted_text


# decrypt process
def decrypt(ciphertext, key_matrix):
    decrypted_text = ""

    for i in range(0, len(ciphertext), 2):
        row1, col1 = find_position(key_matrix, ciphertext[i])
        row2, col2 = find_position(key_matrix, ciphertext[i + 1])

        if row1 == row2:  # same row
            pair_l = key_matrix[row1][(col1 - 1) % 5]
            pair_r = key_matrix[row2][(col2 - 1) % 5]
        elif col1 == col2:  # same col
            pair_l = key_matrix[(row1 - 1) % 5][col1]
            pair_r = key_matrix[(row2 - 1) % 5][col2]
        else:  # square
            pair_l = key_matrix[row1][col2]
            pair_r = key_matrix[row2][col1]

        decrypted_text += pair_l + pair_r

    decrypted_text = remove_x(decrypted_text)

    return decrypted_text


def remove_x(decrypted_text):
    for i in range(0, len(decrypted_text) - 2):
        if decrypted_text[i] == decrypted_text[i + 2] and decrypted_text[i] == 'X':
            decrypted_text = decrypted_text[:i] + decrypted_text[i + 2:]
        i += 1
    if decrypted_text[-1] == 'X':
        decrypted_text = decrypted_text[:-1]

    return decrypted_text


# read from file
def read_file(filename):
    with open(filename, 'r') as file:
        return file.read()


# write into file
def write_file(filename, content):
    with open(filename, 'w') as file:
        file.write(content)


# main function
if __name__ == "__main__":
    key_input = input("Please input keyword：")
    matrix = generate_key_matrix(key_input)

    # 从read plain text from file
    plaintext = read_file('plaintext.txt')

    # encrypt text
    text_encrypted = encrypt(plaintext, matrix)
    write_file('encrypted.txt', text_encrypted)

    print(f"encrypted text: {text_encrypted}")

    # decrypt text
    text_decrypted = decrypt(text_encrypted, matrix)
    write_file('decrypted.txt', text_decrypted)

    print(f"decrypted text: {text_decrypted}")
