console.log("SCRIPT CARREGADO COM SUCESSO");

const API_LIVRO = "http://localhost:8080/api/livros";
const API_USUARIO = "http://localhost:8080/api/usuarios";
const API_EMPRESTIMO = "http://localhost:8080/api/emprestimos";

/* =========================
   USUÁRIOS
========================= */

async function cadastrarUsuario() {

    const usuario = {
        nome: document.getElementById("nomeUsuario").value,
        matricula: document.getElementById("matriculaUsuario").value,
        contato: document.getElementById("contatoUsuario").value
    };

    try {

        const response = await fetch(API_USUARIO, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(usuario)
        });

        if (response.ok) {

            alert("Usuário cadastrado com sucesso!");

            document.getElementById("nomeUsuario").value = "";
            document.getElementById("matriculaUsuario").value = "";
            document.getElementById("contatoUsuario").value = "";

            listarUsuarios();

        } else {

            alert(await response.text());
        }

    } catch (error) {

        console.error(error);
        alert("Erro ao cadastrar usuário.");
    }
}

async function listarUsuarios() {

    const lista = document.getElementById("listaUsuarios");

    if (!lista) return;

    try {

        const response = await fetch(API_USUARIO);

        const usuarios = await response.json();

        lista.innerHTML = "";

        usuarios.forEach(usuario => {

            lista.innerHTML += `
                <li>
                    ID: ${usuario.id} |
                    Nome: ${usuario.nome} |
                    Matrícula: ${usuario.matricula} |
                    Contato: ${usuario.contato}
                </li>
            `;
        });

    } catch (error) {

        console.error(error);
    }
}

async function excluirUsuario(id) {

    if (!confirm("Deseja excluir este usuário?")) return;

    try {

        const response = await fetch(`${API_USUARIO}/${id}`, {
            method: "DELETE"
        });

        if (response.ok) {

            alert("Usuário removido com sucesso.");
            listarUsuarios();

        } else {

            alert(await response.text());
        }

    } catch (error) {

        console.error(error);
    }
}

/* =========================
   LIVROS
========================= */

async function cadastrarLivro() {

    const livro = {

        titulo: document.getElementById("tituloLivro").value,
        autor: document.getElementById("autorLivro").value,
        isbn: document.getElementById("isbnLivro").value,
        categoria: document.getElementById("categoriaLivro").value,
        editora: document.getElementById("editoraLivro").value,
        ano: parseInt(document.getElementById("anoLivro").value)
    };

    try {

        const response = await fetch(API_LIVRO, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(livro)
        });

        if (response.ok) {

            alert("Livro cadastrado com sucesso!");

            document.getElementById("tituloLivro").value = "";
            document.getElementById("autorLivro").value = "";
            document.getElementById("isbnLivro").value = "";
            document.getElementById("categoriaLivro").value = "";
            document.getElementById("editoraLivro").value = "";
            document.getElementById("anoLivro").value = "";

            listarLivros();

        } else {

            alert(await response.text());
        }

    } catch (error) {

        console.error(error);
        alert("Erro ao cadastrar livro.");
    }
}

async function listarLivros() {

    const lista = document.getElementById("listaLivros");

    if (!lista) return;

    try {

        const response = await fetch(API_LIVRO);

        const livros = await response.json();

        lista.innerHTML = "";

        livros.forEach(livro => {

            lista.innerHTML += `
                <li>
                    ID: ${livro.id} |
                    Título: ${livro.titulo} |
                    Autor: ${livro.autor} |
                    ISBN: ${livro.isbn} |
                    Categoria: ${livro.categoria} |
                    Status: ${livro.status}
                </li>
            `;
        });

    } catch (error) {

        console.error(error);
    }
}
async function excluirLivro(id) {

    if (!confirm("Deseja excluir este livro?")) return;

    try {

        const response = await fetch(`${API_LIVRO}/${id}`, {
            method: "DELETE"
        });

        if (response.ok) {

            alert("Livro removido com sucesso.");

            listarLivros();
            listarLivrosEmprestados();

        } else {

            alert(await response.text());
        }

    } catch (error) {

        console.error(error);
    }
}

/* =========================
   EMPRÉSTIMOS
========================= */

async function registrarEmprestimo() {

    const emprestimo = {

        usuarioId: parseInt(
            document.getElementById("usuarioId").value
        ),

        livroId: parseInt(
            document.getElementById("livroId").value
        )
    };

    console.log("JSON enviado:", emprestimo);

    try {

        const response = await fetch(API_EMPRESTIMO, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(emprestimo)
        });

        if (response.ok) {

            alert("Empréstimo registrado com sucesso!");

            document.getElementById("usuarioId").value = "";
            document.getElementById("livroId").value = "";

            listarEmprestimos();
            listarLivros();
            listarLivrosEmprestados();

        } else {

            const erro = await response.text();

            console.error("Erro Backend:", erro);

            alert(erro);
        }

    } catch (error) {

        console.error(error);

        alert("Erro ao registrar empréstimo.");
    }
}

async function devolverEmprestimo() {

    const id = document.getElementById("emprestimoId").value;

    try {

        const response = await fetch(
            `${API_EMPRESTIMO}/${id}/devolucao`,
            {
                method: "PUT"
            }
        );

        if (response.ok) {

            alert("Livro devolvido com sucesso!");

            listarEmprestimos();
            listarLivros();
            listarLivrosEmprestados();

        } else {

            alert(await response.text());
        }

    } catch (error) {

        console.error(error);
    }
}

async function listarEmprestimos() {

    const lista =
        document.getElementById("historicoEmprestimosLista");

    if (!lista) return;

    try {

        const response = await fetch(API_EMPRESTIMO);

        const emprestimos = await response.json();

        lista.innerHTML = "";

emprestimos.forEach(e => {

    lista.innerHTML += `
        <li>
            ID: ${e.id} |
            Usuário: ${e.usuario || "-"} |
            Livro: ${e.livro || "-"} |
            Data Empréstimo: ${e.dataEmprestimo || "-"} |
            Data Devolução: ${e.dataDevolucao || "-"} |
            Devolvido: ${e.devolvido ? "Sim" : "Não"}
        </li>
    `;
});

    } catch (error) {

        console.error(error);
    }
}

/* =========================
   LIVROS EMPRESTADOS
========================= */

async function listarLivrosEmprestados() {

    const lista = document.getElementById("listaLivrosEmprestados");

    if (!lista) return;

    try {

        const response = await fetch(API_LIVRO);
        const livros = await response.json();

        lista.innerHTML = "";

        livros
            .filter(livro => livro.status === "EMPRESTADO")
            .forEach(livro => {

                lista.innerHTML += `
                    <li class="card-livro emprestado">
                        <div class="card-header">
                            <strong>${livro.titulo}</strong>
                        </div>

                        <div class="card-body">
                            <p><b>ID:</b> ${livro.id}</p>
                            <p><b>Autor:</b> ${livro.autor}</p>
                            <p><b>Categoria:</b> ${livro.categoria || "-"}</p>
                        </div>

                        <div class="status">
                            <span class="status-emprestado">EMPRESTADO</span>
                        </div>
                    </li>
                `;
            });

    } catch (error) {
        console.error(error);
    }
}

/* =========================
   LOGOUT
========================= */

function logout() {

    sessionStorage.clear();
    localStorage.clear();

    window.location.href = "/login.html";
}

/* =========================
   INICIALIZAÇÃO
========================= */
document.addEventListener("DOMContentLoaded", () => {

    console.log("JS CARREGADO");

    if (document.getElementById("listaUsuarios")) {
        listarUsuarios();
    }

    if (document.getElementById("listaLivros")) {
        listarLivros();
    }

    if (document.getElementById("historicoEmprestimosLista")) {
        listarEmprestimos();
    }

    if (document.getElementById("listaLivrosEmprestados")) {
        listarLivrosEmprestados();
    }

});

console.log("SCRIPT CARREGADO COM SUCESSO");





























































