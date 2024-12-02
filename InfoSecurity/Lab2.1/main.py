import math

if __name__ == '__main__':
    N = 65815671868057
    e = 7423489
    C = [
        38932868535359
    ]

    n = int(math.sqrt(N)) + 1

    b = 2
    a = N
    p = 0
    q = 0

    i = 0
    while True:
        i = i + 1
        t = n + i
        d = a ^ b
        w = t ** 2 - N
        sqrt_w = math.sqrt(w)
        if sqrt_w % 1 == 0:
            sqrt_w = int(sqrt_w)
            p = t + sqrt_w
            q = t - sqrt_w
            print(f"p={p}, q={q}")
            break
        else:
            print("error, keep searching for p and q")
    phi = round((p - 1) * (q - 1))
    d = pow(e, -1, phi)

    result = ""
    for i in C:
        m = pow(int(i), d, N)
        part = m.to_bytes(4, byteorder='big').decode('cp1251')
        print(f'{i}^{d} mod {N} = {m} => text({m}) = {part}')
        result += part
    print(f"result = {result}")
