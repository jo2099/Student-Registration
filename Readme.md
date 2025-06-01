
# Student Registration System

## Descrição
Este projeto é um sistema de registro de estudantes desenvolvido em Java. Ele permite gerenciar informações de estudantes, incluindo cadastro, exclusão e visualização de dados. O sistema utiliza uma interface gráfica baseada em Swing e armazena os dados em arquivos de texto.

## Estrutura do Projeto
- **Controller**: Contém a lógica de controle do sistema, como o `StudentController`, que gerencia eventos e interações entre a interface e o repositório.
- **Model**: Define as classes de dados, como `Student`, `Person`, e enums como `NivelMatricula` e `Genders`.
- **View**: Implementa a interface gráfica com o usuário, como `SwingInterface`, que exibe páginas de cadastro e listagem de estudantes.
- **Repository**: Gerencia o armazenamento de dados, como o `TextFileStudentRepository`, que utiliza arquivos de texto para persistência.
- **Utils**: Contém classes utilitárias, como `Event`, `Publisher`, e `Subscriber`, para comunicação entre componentes.

## Funcionalidades
- **Cadastro de Estudantes**: Permite registrar novos estudantes com informações como nome, gênero, data de nascimento e nível de matrícula.
- **Listagem de Estudantes**: Exibe uma tabela com os dados dos estudantes cadastrados.
- **Exclusão de Estudantes**: Remove estudantes selecionados da lista.
- **Persistência de Dados**: Armazena os dados em arquivos de texto para garantir que as informações sejam mantidas entre execuções.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação principal.
- **Swing**: Biblioteca para criação da interface gráfica.
- **JSON**: Utilizado para manipulação de dados estruturados.
- **Arquivos de Texto**: Persistência de dados.

## Como Executar
1. Clone o repositório:
   ```bash
   git clone https://github.com/jo2099/Student-Registration.git
   ```
2. Execute o sistema:
   ```bash
   java -jar target/student-registration-1.0-SNAPSHOT-jar-with-dependencies.jar
