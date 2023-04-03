import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.google.gson.Gson;

public class UsuarioAPI {
    private static Map<Integer, Usuario> usuarios = new HashMap<>();
    private static int proximoId = 1;

    public static void main(String[] args) {
        Spark.post("/usuarios", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String body = request.body();
                Gson gson = new Gson();
                Usuario usuario = gson.fromJson(body, Usuario.class);

                int id = cadastrarUsuario(usuario.getNome(), usuario.getEmail(), usuario.getSenha());
                return gson.toJson(new Resposta(id));
            }
        });

        Spark.post("/usuarios/autenticar", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String body = request.body();
                Gson gson = new Gson();
                Credenciais credenciais = gson.fromJson(body, Credenciais.class);

                boolean autenticado = autenticarUsuario(credenciais.getEmail(), credenciais.getSenha());
                return gson.toJson(new Resposta(autenticado));
            }
        });
    }

    private static int cadastrarUsuario(String nome, String email, String senha) {
        int id = proximoId;
        usuarios.put(id, new Usuario(id, nome, email, senha));
        proximoId++;
        return id;
    }

    private static boolean autenticarUsuario(String email, String senha) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }

    private static class Usuario {
        private int id;
        private String nome;
        private String email;
        private String senha;

        public Usuario(int id, String nome, String email, String senha) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.senha = senha;
        }

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getEmail() {
            return email;
        }

        public String getSenha() {
            return senha;
        }
    }

    private static class Credenciais {
        private String email;
        private String senha;

        public String getEmail() {
            return email;
        }

        public String getSenha() {
            return senha;
        }
    }

    private static class Resposta {
        private int id;
        private boolean autenticado;

        public Resposta(int id) {
            this.id = id;
        }

        public Resposta(boolean autenticado) {
            this.autenticado = autenticado;
        }
    }
}
