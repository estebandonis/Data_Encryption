import socket
from Crypto.Cipher import AES
from Crypto.Util.Padding import unpad

key = b'0123456789abcdef'  # Clave de 16 bytes

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(("0.0.0.0", 8080))
server.listen(1)
print(f"Listening on port 8080")

while True:
    conn, addr = server.accept()
    print(f"Connection from {addr}")

    data = b''
    while True:
        packet = conn.recv(1024)
        if not packet:
            break
        data += packet

    iv = data[:16]
    encrypted_data = data[16:]

    cipher = AES.new(key, AES.MODE_CBC, iv)
    try:
        decrypted_data = unpad(cipher.decrypt(encrypted_data), AES.block_size)
        with open('archivo_recibido.png', 'wb') as f:
            f.write(decrypted_data)
        print("Archivo recibido y guardado correctamente")
    except Exception as e:
        print(f"Error al descifrar el archivo: {e}")

    conn.close()