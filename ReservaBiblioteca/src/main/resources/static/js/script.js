const API_URL = "http://localhost:8080/api";

// Listar livros
async function listarLivros() {
  const response = await fetch(`${API_URL}/livros`, {
    headers: { "Authorization": "Basic " + btoa("user:user123") }
  });
  const livros = await response.json();
  const lista = document.getElementById("lista-livros");
  lista.innerHTML = "";
  livros.forEach(livro => {
    const li = document.createElement("li");
    li.textContent = `${livro.titulo} - ${livro.status}`;
    lista.appendChild(li);
  });
}

// Registrar empréstimo
async function registrarEmprestimo(event) {
  event.preventDefault();
  const usuarioId = document.getElementById("usuarioId").value;
  const livroId = document.getElementById("livroId").value;

  const response = await fetch(`${API_URL}/emprestimos`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": "Basic " + btoa("user:user123")
    },
    body: JSON.stringify({ usuarioId, livroId })
  });

  if (response.ok) {
    alert("Empréstimo registrado com sucesso!");
    listarEmprestimos();
  } else {
    alert("Erro ao registrar empréstimo");
  }
}

// Listar empréstimos
async function listarEmprestimos() {
  const response = await fetch(`${API_URL}/emprestimos`, {
    headers: { "Authorization": "Basic " + btoa("user:user123") }
  });
  const emprestimos = await response.json();
  const lista = document.getElementById("lista-emprestimos");
  lista.innerHTML = "";
  emprestimos.forEach(emp => {
    const li = document.createElement("li");
    li.textContent = `Livro: ${emp.livro.titulo} | Usuário: ${emp.usuario.nome} | Devolvido: ${emp.devolvido}`;
    lista.appendChild(li);
  });
}
