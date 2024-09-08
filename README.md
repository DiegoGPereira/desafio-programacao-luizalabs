# Desafio programação LuizaLabs - Diego Guedes Pereira

Este projeto consiste em um backend Spring e um frontend Astro com componentes React.

## Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina local:

- Kit de Desenvolvimento Java (JDK) 21
- Node.js (versão 22 ou posterior)
- npm (geralmente vem com o Node.js)
- Maven (para construir o projeto Spring)

## Configuração do Backend (Spring)

1. Clone o repositório e va para o diretorio do backend:
cd server

2. Construa o projeto:
mvn clean install

3. Execute a aplicação Spring:
mvn spring-boot:run

O backend agora deve estar rodando em `http://localhost:8080`.

## Configuração do Frontend (Astro com React)

1. Navegue até o diretório do frontend:
cd ../client/purchase

2. Instale as dependências:
npm install

3. Inicie o servidor de desenvolvimento Astro:
npm run dev

O frontend agora deve estar acessível em `http://localhost:4321`.

## Notas Adicionais

- O projeto utiliza autenticação JWT stateless e tem suas rotas protegidadas tanto server como client side, portanto, o usuario e senha padrão para testes são: `Usuário: user1, Senha: 123`.
- Certifique-se de que o front-end esteja na porta 4321, caso a mesma já esteja em uso, alterar o application.properties do backend para a porta que inicializou pois o CORS de desenvolvimento está configurado para a porta 4321 por default.
- O Projeto utiliza as versões mais recentes do Java, Spring Boot, Node, Astro e React respeitando as versões recomendadas pela documentação oficial (LTS).
- O Projeto foi construído utilizando o H2 Database no modo MySql para fins de agilidade na avaliação e montagem do ambiente de testes pelo avaliador, porém, o mesmo pode ser facilmente substituído por qualquer outro banco de dados relacional.

