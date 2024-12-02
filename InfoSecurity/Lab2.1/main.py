
import math


# Функция для нахождения простых чисел p и q методом квадратичных корней
def find_prime_factors(N):
    # Начальное приближение корня
    n = int(math.sqrt(N)) + 1

    i = 0
    while True:
        i += 1
        t = n + i
        w = t ** 2 - N
        sqrt_w = math.sqrt(w)
        if sqrt_w % 1 == 0:
            sqrt_w = int(sqrt_w)
            p = t + sqrt_w
            q = t - sqrt_w
            print(f"p={p}, q={q}")
            return p, q
        else:
            print("error")


# Функция для расшифровки сообщения
def decrypt_message(C, d, N):
    result = ""
    for i in C:
        # Расчет m = C^d mod N
        m = pow(int(i), d, N)
        # Преобразование числа в текст (кодировка cp1251)
        part = m.to_bytes(4, byteorder='big').decode('cp1251')
        print(f'{i}^{d} mod {N} = {m} => text({m}) = {part}')
        result += part
    return result


if __name__ == '__main__':
    # Исходные данные
    N = 48992988576733  # Модуль N
    e = 4545733  # Открытая экспонента e
    C = [  # Зашифрованные данные
        12530303611339,
        47274247086952,
        20068556933394,
        41300245344157,
        27564916776233,
        45997492729411,
        11416336760074,
        17516700753417,
        10586755223028,
        5642378694993,
        17949047899806,
        13276902592875
    ]

    # Нахождение простых чисел p и q
    p, q = find_prime_factors(N)

    # Вычисление функции Эйлера
    phi = round((p - 1) * (q - 1))

    # Вычисление закрытой экспоненты d
    d = pow(e, -1, phi)

    # Расшифровка сообщения
    result = decrypt_message(C, d, N)

    # Печать результата
    print(f"result = {result}")

