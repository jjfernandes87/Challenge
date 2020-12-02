# Desafio - Desenvolvedor Android

Nós de mobile somos fãs da Marvel, então por isso gostaríamos de um app para listar os personagens e saber mais sobre eles.

O objetivo é implementar um app onde podemos ver os a lista de personagens da Marvel. O app deve mostrar uma lista e ser possível navegar para os detalhes de cada personagem. Além disso, tanto na lista quanto na tela de detalhes, deve ser possível favoritar o personagem. Os personagens favoritados devem ser persistidos no device para que possam ser acessados offline e serem mostrados em uma aba própria.

## API

Para desenvolver o app você vai precisar usar o endpoint de "Characters" da API Marvel. 
Mais informações: https://developer.marvel.com/docs.

## API

Para desenvolver o app você vai precisar usar o endpoint de "Characters" da API Marvel. 
Mais informações: https://developer.marvel.com/docs.

## Interface

A interface do app é dividida em 3 partes e deve ser desenvolvida conforme os pontos abaixo.

### Home - Characters

* Listagem dos personagens ordenados por nome.
* Botão para favoritar nas células.
* Barra de busca para filtrar lista de jogos por nome.
* Pull-to-refresh para atualizar a lista.
* Paginação na lista: Carregar 20 personagens por vez, baixando a próxima página ao chegar no fim da lista.
* Interface de lista vazia, erro ou sem internet.

### Detalhes do personagem

* Botão de favorito.
* Foto em tamanho maior 
* Nome do personagem na barra de navegação
* Descrição (se houver).
* Lista horizontal de Comics (se houver).
* Lista horizontal de Series (se houver).

### Favoritos

* Listagem dos personagens favoritados pelo usuário.
* Favoritos devem ser persistidos para serem acessados offline.
* Interface de lista vazia, erro ou sem internet.

### Wireframe

Abaixo temos os wireframes das telas do app.

| ![Page1](android/Characters.png)  | ![Page2](android/Favorites.png) | ![Page3](android/Detail.png) |
|:---:|:---:|:---:|
| Lista de Personagens | Favoritos | Detalhes do Personagem |

## Requisitos Essenciais

* Usar Kotlin.
* Desenvolver o App em uma arquitetura robusta.
* Tratamento para falha de conexão.
* O teste não pode apresenter crash.
* Testes unitários.

## Observações

* Fique a vontade para usar quantas bibliotecas você achar pertinente em sua solução. Estamos interessados em saber se você está atualizado com as melhores práticas em desenvolvimento Android. Um código bem organizado e que se preocupe com o alto desempenho será um diferencial.
