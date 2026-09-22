# CampusHub

Aplicativo Android desenvolvido para facilitar o acesso dos alunos aos eventos da universidade.

O CampusHub permite que o aluno crie uma conta, faça login, visualize eventos, veja detalhes, realize inscrições e gerencie seu perfil.

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

### Dashboard
Tela principal do aplicativo com acesso às principais funcionalidades:

- Eventos
- Meus Eventos
- Meu Perfil
- Sair

### Eventos
Exibe os eventos disponíveis para os alunos.

### Detalhes do Evento
Apresenta informações como:

- Nome do evento
- Data
- Horário
- Local
- Descrição

Também permite realizar ou cancelar uma inscrição.

### Meus Eventos
Exibe os eventos nos quais o usuário está inscrito.

### Meu Perfil
Permite visualizar os dados da conta e editar o nome do usuário.

### Recuperação de Senha
Permite solicitar um e-mail para redefinição da senha.

## Firebase

O projeto utiliza o Firebase para:

- Autenticação dos usuários
- Cadastro e login
- Recuperação de senha
- Armazenamento das inscrições dos eventos
- Armazenamento das informações relacionadas aos usuários

## Como executar o projeto

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Aguarde a sincronização do Gradle.
4. Configure o arquivo `google-services.json` no diretório `app`.
5. Execute o aplicativo em um emulador ou dispositivo Android.

## Autor

**Pedro Copette**

## Projeto acadêmico

Projeto desenvolvido para a disciplina de Programação para Dispositivos Móveis.

---