<?php

// Endpoint para cadastrar um usuário
function cadastrarUsuario($nome, $email, $senha) {
    // Aqui você implementa o código para cadastrar o usuário no banco de dados
    // e retorna o ID do usuário cadastrado
    return 123;
}

// Endpoint para autenticar um usuário
function autenticarUsuario($email, $senha) {
    // Aqui você implementa o código para verificar se o email e a senha são válidos
    // e retorna true se forem válidos ou false se não forem
    return ($email == 'usuario@teste.com' && $senha == '123456');
}

// Exemplo de uso
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $json = file_get_contents('php://input');
    $data = json_decode($json, true);

    if ($data['acao'] === 'cadastrar') {
        $nome = $data['nome'];
        $email = $data['email'];
        $senha = $data['senha'];

        $id = cadastrarUsuario($nome, $email, $senha);
        echo json_encode(['id' => $id]);
    } elseif ($data['acao'] === 'autenticar') {
        $email = $data['email'];
        $senha = $data['senha'];

        $autenticado = autenticarUsuario($email, $senha);
        echo json_encode(['autenticado' => $autenticado]);
    }
}
