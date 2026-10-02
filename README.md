# CampusHub

Aplicativo Android desenvolvido para facilitar o acesso dos alunos aos eventos da universidade.

O CampusHub permite que o aluno crie uma conta, faça login, visualize eventos, consulte detalhes, realize inscrições, favorite eventos, faça comentários, avalie eventos encerrados e gerencie seu perfil.

## Funcionalidades

- Cadastro de usuários
- Login com e-mail e senha
- Recuperação de senha
- Logout
- Dashboard principal
- Listagem de eventos
- Detalhes dos eventos
- Inscrição em eventos
- Cancelamento de inscrição
- Tela de Meus Eventos
- Favoritar eventos
- Desfavoritar eventos
- Tela de Meus Favoritos
- Comentários nos eventos
- Edição dos próprios comentários
- Exclusão dos próprios comentários
- Avaliação de eventos encerrados
- Avaliação de 1 a 5 estrelas
- Alteração da própria avaliação
- Exibição da média das avaliações
- Busca de eventos pelo título
- Filtro de eventos por categoria
- Filtro de eventos por situação
- Visualização do perfil
- Edição do perfil
- Salvamento das informações no Firebase

## Tecnologias utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Firebase Authentication
- Cloud Firestore

## Estrutura do aplicativo

### Login

Permite que o usuário entre na aplicação utilizando e-mail e senha cadastrados.

### Cadastro

Permite criar uma nova conta informando nome, e-mail e senha.

### Recuperação de Senha

Permite solicitar a recuperação da senha através do e-mail cadastrado.

### Dashboard

Tela principal do aplicativo com acesso às principais funcionalidades:

- Eventos
- Meus Eventos
- Meus Favoritos
- Meu Perfil
- Sair

### Eventos

Exibe os eventos disponíveis para os alunos.

A tela permite:

- Buscar eventos pelo título
- Filtrar por categoria
- Filtrar por situação
- Visualizar eventos próximos
- Visualizar eventos encerrados

### Detalhes do Evento

Apresenta informações como:

- Nome do evento
- Data
- Horário
- Local
- Descrição
- Média das avaliações

Também permite:

- Realizar uma inscrição
- Cancelar uma inscrição
- Adicionar o evento aos favoritos
- Remover o evento dos favoritos
- Comentar no evento
- Editar o próprio comentário
- Excluir o próprio comentário
- Avaliar eventos encerrados

### Meus Eventos

Exibe os eventos nos quais o usuário está inscrito.

O usuário também pode cancelar sua inscrição.

### Meus Favoritos

Exibe os eventos que o usuário marcou como favoritos.

Favoritar um evento é independente da inscrição no evento.

O usuário pode remover eventos da lista de favoritos.

### Comentários

Usuários autenticados podem comentar nos eventos.

Cada comentário apresenta:

- Nome do autor
- Texto do comentário
- Data de publicação

O usuário pode editar ou excluir somente os próprios comentários.

### Avaliações

Alunos inscritos podem avaliar eventos que já foram encerrados.

A avaliação utiliza uma escala de:

- 1 estrela
- 2 estrelas
- 3 estrelas
- 4 estrelas
- 5 estrelas

Cada aluno pode possuir apenas uma avaliação por evento, podendo alterar sua nota posteriormente.

A média das avaliações é apresentada nos detalhes do evento.

### Meu Perfil

Permite visualizar os dados do usuário e editar as informações do perfil.

## Firebase

O projeto utiliza o Firebase para:

- Autenticação dos usuários
- Cadastro e login
- Recuperação de senha
- Armazenamento das inscrições
- Armazenamento dos eventos favoritos
- Armazenamento dos comentários
- Armazenamento das avaliações
- Armazenamento das informações relacionadas aos usuários

## Firestore

O projeto utiliza o Cloud Firestore para armazenar os dados relacionados às funcionalidades do aplicativo.

As principais coleções utilizadas são:

- `inscricoes`
- `favoritos`
- `comentarios`
- `avaliacoes`

As regras de segurança do Firestore controlam o acesso aos dados de acordo com o usuário autenticado.

## Como executar o projeto

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Configure o arquivo `google-services.json` no diretório `app`.
5. Configure o projeto no Firebase.
6. Execute o aplicativo em um emulador ou dispositivo Android.

## Autor

**Pedro Copette**

## Projeto acadêmico

Projeto desenvolvido para a disciplina de **Programação para Dispositivos Móveis**.