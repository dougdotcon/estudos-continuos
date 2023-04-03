from flask import Flask, jsonify, request

app = Flask(__name__)

usuarios = {}
proximo_id = 1

@app.route('/usuarios', methods=['POST'])
def cadastrar_usuario():
    global proximo_id
    data = request.get_json()
    nome = data['nome']
    email = data['email']
    senha = data['senha']

    id = proximo_id
    usuarios[id] = {'nome': nome, 'email': email, 'senha': senha}
    proximo_id += 1

    return jsonify({'id': id})

@app.route('/usuarios/autenticar', methods=['POST'])
def autenticar_usuario():
    data = request.get_json()
    email = data['email']
    senha = data['senha']

    for usuario in usuarios.values():
        if usuario['email'] == email and usuario['senha'] == senha:
            return jsonify({'autenticado': True})

    return jsonify({'autenticado': False})

if __name__ == '__main__':
    app.run()
